package com.risun.flowable.service.impl;

import static java.util.stream.Collectors.toList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.MultiInstanceLoopCharacteristics;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.risun.common.constant.HttpStatus;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.exception.ServiceException;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.common.constant.ProcessConstants;
import com.risun.flowable.domain.SysFlowAssign;
import com.risun.flowable.domain.SysFlowGroup;
import com.risun.flowable.domain.dto.FlowAttachmentDto;
import com.risun.flowable.domain.dto.FlowHistRecordDto;
import com.risun.flowable.domain.dto.FlowNextDto;
import com.risun.flowable.domain.dto.FlowTaskDto;
import com.risun.flowable.domain.dto.FlowViewerDto;
import com.risun.flowable.domain.vo.FlowQueryVo;
import com.risun.flowable.domain.vo.FlowTaskVo;
import com.risun.flowable.factory.FlowServiceFactory;
import com.risun.flowable.flow.CustomProcessDiagramGenerator;
import com.risun.flowable.flow.FindNextNodeUtil;
import com.risun.flowable.flow.FlowableUtils;
import com.risun.flowable.mapper.FlowTaskMapper;
import com.risun.flowable.mapper.SysFlowAssignMapper;
import com.risun.flowable.service.IFlowTaskService;
import com.risun.flowable.service.ISysDeployFormService;
import com.risun.flowable.service.ISysFlowGroupUserService;
import com.risun.system.mapper.SysUserMapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 流程任务服务实现类
 * 
 * @author dante
 * @date 2021-04-03
 **/
@Service
@Slf4j
public class FlowTaskServiceImpl extends FlowServiceFactory implements IFlowTaskService {

	@Autowired
	private FlowTaskMapper flowTaskMapper;

	@Autowired
    private SysUserMapper userMapper;
	
	@Autowired
	private SysFlowAssignMapper sysFlowAssignMapper;

	@Autowired
	private ISysFlowGroupUserService sysFlowGroupUserService;

	@Resource
	private ISysDeployFormService sysInstanceFormService;
	

	/**
	 * 代办任务列表
	 * 
	 * @param flowTaskDto
	 *
	 * @return
	 */

	@Override
	public List<FlowTaskDto> todoList(FlowQueryVo queryVo) {
		String curUserId = SecurityUtils.getUserId().toString();
		List<String> groups = sysFlowGroupUserService.selectGroupByUserId(Long.parseLong(curUserId));
		queryVo.setUserId(curUserId);
		queryVo.setUserGroupIds(groups);
		return flowTaskMapper.selectFlowTodoList(queryVo);
	}

	/**
	 * 已办任务列表
	 *
	 * @param FlowQueryVo 查询参数
	 * @return
	 */
	public List<FlowTaskDto> doneList(FlowQueryVo queryVo) {
		Long curUserId = SecurityUtils.getUserId();
		queryVo.setUserId(curUserId.toString());
		queryVo.setIsAdmin(SecurityUtils.isAdmin(curUserId) ? 1 : 0);
		return flowTaskMapper.selectFlowDoneList(queryVo);
	}

	/**
	 * 办结任务列表
	 *
	 * @param FlowQueryVo 查询参数
	 * @return
	 */
	@Override
	public List<FlowTaskDto> finishedList(FlowQueryVo queryVo) {
		Long curUserId = SecurityUtils.getUserId();
		queryVo.setUserId(curUserId.toString());
		queryVo.setIsAdmin(SecurityUtils.isAdmin(curUserId) ? 1 : 0);
		return flowTaskMapper.selectFlowFinishList(queryVo);
	}

	/**
	 * 审批流程任务
	 * 
	 * @param flowTask
	 * @return
	 */
	@Override
	public int approval(FlowTaskVo flowTask) {
		int result = 1;
		Assert.hasText(flowTask.getComment(), "审批意见不能为空！");
		List<Task> tasks = taskService.createTaskQuery().taskId(flowTask.getTaskId()).list();
		if (CollUtil.isEmpty(tasks)) {
			throw new ServiceException("未获取到任务，请联系系统管理员", HttpStatus.ERROR);
		}
		try {
			Long userId = SecurityUtils.getUserId();
			Task task = tasks.get(0);
			
			// 设置动态审批人、审批组（单个）
			if (StrUtil.isNotEmpty(flowTask.getAssignee())) {
				flowTask.addVariables(ProcessConstants.PROCESS_APPROVAL, flowTask.getAssignee());
			}
			// 设置会签（人员）
			if (CollUtil.isNotEmpty(flowTask.getCandidateUsers())) {
				flowTask.addVariables(ProcessConstants.PROCESS_MULTI_INSTANCE_USER, flowTask.getCandidateUsers());
			}
			// 设置会签（组）
			if (CollUtil.isNotEmpty(flowTask.getCandidateGroups())) {
				flowTask.addVariables(ProcessConstants.PROCESS_MULTI_INSTANCE_GROUP, flowTask.getCandidateGroups());
			}
			// 设置审批意见
			taskService.addComment(task.getId(), task.getProcessInstanceId(), flowTask.getComment());
			if(ObjectUtil.isNotNull(flowTask.getAgree())) {
				flowTask.addVariables(ProcessConstants.PROCESS_ARG_AGREE, flowTask.getAgree());
				if(!flowTask.getAgree().booleanValue()) {
					// 驳回操作，设置之前的执行人
					this.rejectTask(flowTask, task);
				}
			}
			
			// 设置附件
			if (StrUtil.isNotEmpty(flowTask.getAttachment())) {
				taskService.createAttachment("", task.getId(), task.getProcessInstanceId(), "", "",
						flowTask.getAttachment());
			}
			
			DelegationState delegationState = task.getDelegationState();
			// 判断是否为委派的任务
			if(delegationState != null && DelegationState.PENDING.compareTo(delegationState) == 0) {
				taskService.resolveTask(task.getId(), flowTask.getVariables());
				taskService.complete(task.getId(), flowTask.getVariables());
			} else {
				taskService.setAssignee(task.getId(), userId.toString());
				taskService.complete(task.getId(), flowTask.getVariables());
			}
			
		} catch (Exception e) {
			log.error("流程【{}】审批失败：", flowTask.getTaskId(), e);
			throw new ServiceException(e.getLocalizedMessage(), HttpStatus.ERROR);
		}
		return result;
	}
	
	/**
	 * 驳回操作
	 * 
	 * @param flowTask
	 * @param task
	 */
	private void rejectTask(FlowTaskVo flowTask, Task task) {
		// 获取上一个任务的执行人（单点或会签）
		// 已完成历史任务
		List<HistoricTaskInstance> histTasks = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(task.getProcessInstanceId())
				.orderByHistoricTaskInstanceStartTime().desc()
				.list();
		if(CollUtil.isEmpty(histTasks)) {
			throw new ServiceException("已完成流程记录缺失！", HttpStatus.ERROR);
		}
		// 过滤掉当前待办任务
		histTasks = histTasks.stream().filter(t -> ObjectUtil.isNotNull(t.getEndTime())).collect(toList());
		List<String> multiUsers = this.getMultiTask(histTasks);
		
		// 会签
		if(CollUtil.isNotEmpty(multiUsers)) {
			flowTask.addVariables(ProcessConstants.PROCESS_MULTI_INSTANCE_USER, multiUsers);
		} else {
			// 单点
			flowTask.addVariables(ProcessConstants.PROCESS_APPROVAL, histTasks.get(0).getAssignee());
		}
		
		/*
		for (HistoricTaskInstance histTask : histTasks) {
			// 会签 TODO: 待开发
			if(histTask.getName().equals("会签")) {
				flowTask.addVariables(ProcessConstants.PROCESS_MULTI_INSTANCE_USER, "待开发");
			} else {
				// 单点
				flowTask.addVariables(ProcessConstants.PROCESS_APPROVAL, histTask.getAssignee());
			}
			break;
		}
		*/
	}
	
	/**
	 * 获取会签节点任务完成人员列表（如果完成人员列表不为空，则判断为会签）
	 * 
	 * @param histTasks 已完成任务
	 * 
	 * @return 会签节点任务完成人员列表
	 */
	private List<String> getMultiTask(List<HistoricTaskInstance> histTasks) {
		List<String> multiUsers = Lists.newArrayList();
		int size = histTasks.size();
		if(size <= 1) {
			return multiUsers;
		}
		String taskDefinitionKey = histTasks.get(0).getTaskDefinitionKey();
		String firstAssignee = histTasks.get(0).getAssignee();
		for (int i = 1; i < size; i++) {
			if(taskDefinitionKey.equals(histTasks.get(i).getTaskDefinitionKey())) {
				if(CollUtil.isEmpty(multiUsers)) {
					multiUsers.add(firstAssignee);
				}
				multiUsers.add(histTasks.get(i).getAssignee());
			} else {
				break;
			}
		}
		return multiUsers;
	}
	
	/**
	 * 转办任务
	 *
	 * @param flowTaskVo 请求实体参数
	 */
	@Override
	@Transactional
	public void assignTask(FlowTaskVo flowTaskVo) {
		String[] taskIds = flowTaskVo.getAssignTaskIds();
		Boolean keepTodo = flowTaskVo.getKeepTodo();
		String ownerId = SecurityUtils.getUserId().toString();
		for (String taskId : taskIds) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			DelegationState delegationState = task.getDelegationState();
			// 多次转办
			if(delegationState != null && DelegationState.PENDING.compareTo(delegationState) == 0) {
				ownerId = task.getOwner();
			}
			taskService.setOwner(taskId, ownerId);
			recordFlowAssign(task, flowTaskVo);
			if(keepTodo) {
				taskService.delegateTask(taskId, flowTaskVo.getAssignee());
			} else {
				taskService.setAssignee(taskId, flowTaskVo.getAssignee());
			}
			taskService.setVariable(taskId, ProcessConstants.KEEP_TODO, keepTodo);
			
		}
	}
	
	/**
	 * 记录任务转办信息
	 * 
	 * @param flowTaskVo
	 */
	private void recordFlowAssign(Task task, FlowTaskVo flowTaskVo) {
		SysFlowAssign sysFlowAssign = new SysFlowAssign();
		sysFlowAssign.setTaskId(task.getId());
		sysFlowAssign.setProcInstId(task.getProcessInstanceId());
		sysFlowAssign.setComment(flowTaskVo.getComment());
		sysFlowAssign.setKeepTodo(flowTaskVo.getKeepTodo());
		
		SysUser owner = SecurityUtils.getLoginUser().getUser();
		sysFlowAssign.setOwnerId(owner.getUserId());
		sysFlowAssign.setOwnerName(owner.getNickName());
		sysFlowAssign.setOwnerDept(owner.getDept().getDeptName());
		
		SysUser assignee = userMapper.selectUserById(Long.parseLong(flowTaskVo.getAssignee()));
		sysFlowAssign.setAssigneeId(assignee.getUserId());
		sysFlowAssign.setAssigneeName(assignee.getNickName());
		sysFlowAssign.setAssigneeDept(assignee.getDept().getDeptName());
		
		sysFlowAssign.setCreateTime(DateUtils.getNowDate());
		sysFlowAssignMapper.insertSysFlowAssign(sysFlowAssign);
	}

	/**
	 * 流程历史记录
	 *
	 * @param procInsId 流程实例Id
	 * @param 用户列表
	 * @return
	 */
	@Override
	public List<FlowHistRecordDto> flowRecordList(String procInsId) {
		List<SysUser> userList = userMapper.selectUserList(new SysUser());
		List<FlowHistRecordDto> histVos = new ArrayList<>();
		List<FlowHistRecordDto> unHandleVos = new ArrayList<>(); // 未处理的任务
		List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(procInsId).orderByHistoricActivityInstanceStartTime().asc().list();
		List<Comment> comments = new ArrayList<>();
		List<FlowAttachmentDto> attachments = new ArrayList<>();
		if (CollUtil.isNotEmpty(haiList)) {
			comments = taskService.getProcessInstanceComments(procInsId, ProcessConstants.COMMENT_TYPE);
			attachments = flowTaskMapper.selectFlowAttachmentList(procInsId);
		}

		List<FlowHistRecordDto> tmps = new ArrayList<>();
		FlowHistRecordDto vo = null;
		int seq = 1;
		HistoricTaskInstance taskHist = null;
		for (HistoricActivityInstance h : haiList) {
			if (ProcessConstants.USER_TASK.equals(h.getActivityType())
					|| ProcessConstants.SERVICE_TASK.equals(h.getActivityType())) {
				List<HistoricTaskInstance> taskHists = historyService.createHistoricTaskInstanceQuery()
						.taskId(h.getTaskId()).list();
				if (CollUtil.isNotEmpty(taskHists)) {
					taskHist = taskHists.get(0);
				}
				if (CollUtil.isEmpty(histVos)) {
					// 初始化
					vo = this.buildFlowCandidate(userList, h, taskHist, seq++);
					histVos.add(vo);
				} else {
					vo = histVos.get(histVos.size() - 1); // 上一个任务
					if (StrUtil.isEmpty(vo.getToTask())) {
						if (!h.getActivityName().equals(vo.getFromTask())) {
							vo.setToActivityId(h.getActivityId());
							vo.setToTaskId(h.getTaskId());
							vo.setToTask(h.getActivityName());
						} else {
							tmps.add(vo);
							vo = this.buildFlowCandidate(userList, h, taskHist, seq++);
							histVos.add(vo);
						}
					}
				}

				if (StrUtil.isNotEmpty(vo.getToTask())) {
					vo = this.buildFlowCandidate(userList, h, taskHist, seq++);
					histVos.add(vo);
				}

				for (int i = comments.size() - 1; i >= 0; i--) {
					Comment c = comments.get(i);
//					if (h.getTaskId().equals(c.getTaskId()) && StrUtil.isEmpty(c.getUserId())) {
					if (h.getTaskId().equals(c.getTaskId())) {
						vo.setComment(c.getFullMessage());
						break;
					}
				}
				for (int i = attachments.size() - 1; i >= 0; i--) {
					FlowAttachmentDto attach = attachments.get(i);
					if (h.getTaskId().equals(attach.getTaskId())) {
						vo.setAttachUrl(attach.getUrl());
						break;
					}
				}
				
			} else if (ProcessConstants.SEQUENCE_FLOW.equals(h.getActivityType()) && vo != null) {
				vo.setFlowName(h.getActivityName());
				if (StrUtil.isEmpty(vo.getComment())) {
					vo.setComment(h.getActivityName());
				}
			} else if (ProcessConstants.END_EVENT.equals(h.getActivityType()) && vo != null) {
				vo.setToActivityId(h.getActivityId());
				vo.setToTaskId(h.getTaskId());
				vo.setToTask(h.getActivityName());
				vo.setFinished(Boolean.TRUE);
			}
			if (vo != null && vo.getUserId() == null) {
				// 待处理任务
				unHandleVos.add(vo);
			}
		}
		for (FlowHistRecordDto t : tmps) {
			for (FlowHistRecordDto h : histVos) {
				if (h.getSeqNum() > t.getSeqNum() && StrUtil.isEmpty(t.getToActivityId())
						&& StrUtil.isNotEmpty(h.getToActivityId())
						&& t.getFromActivityId().equals(h.getFromActivityId())) {
					t.setToActivityId(h.getToActivityId());
					t.setToTask(h.getToTask());
					t.setFlowName(h.getFlowName());
					t.setFinished(h.getFinished());
					break;
				}
			}
		}
		Map<String, List<IdentityLink>> unMap = Maps.newHashMap();
		List<IdentityLink> idenLinks = null;
		for (int i = 0; i < unHandleVos.size(); i++) {
			FlowHistRecordDto v = unHandleVos.get(i);
			if(unMap.containsKey(v.getFromTaskId())) {
				idenLinks = unMap.get(v.getFromTaskId());
				this.buildFlowCandidate(v, idenLinks.get(0), userList);
			} else {
				try {
					idenLinks = taskService.getIdentityLinksForTask(v.getFromTaskId());
					this.buildFlowCandidate(v, idenLinks.get(0), userList);
					unMap.put(v.getFromTaskId(), idenLinks);
				} catch (Exception e) {
					break;
				}
				
			}
		}
		
		return Stream.of(histVos, unHandleVos, tmps)
				.flatMap(Collection::stream)
				.filter(v -> v.getUserId() != null || v.getDeptId() != null)
				.distinct()
				.collect(toList());
	}

	
	/**
	 * 查询流程附件记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	@Override
	public List<FlowAttachmentDto> selectFlowAttachmentList(String procInsId) {
		return flowTaskMapper.selectFlowAttachmentList(procInsId);
	}
	
	/**
	 * 查询流程转办记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	@Override
	public List<SysFlowAssign> selectFlowAssignList(String procInsId) {
		SysFlowAssign flowAssign = new SysFlowAssign();
		flowAssign.setProcInstId(procInsId);
		return sysFlowAssignMapper.selectSysFlowAssignList(flowAssign);
	}
	
	/**
	 * 根据任务ID查询挂载的表单信息
	 *
	 * @param taskId 任务Id
	 * @return
	 */
	@Override
	public Task getTaskForm(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	/**
	 * 获取流程过程图
	 *
	 * @param processId
	 * @return
	 */
	@Override
	public InputStream diagram(String processId) {
		String processDefinitionId;
		// 获取当前的流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId)
				.singleResult();
		// 如果流程已经结束，则得到结束节点
		if (Objects.isNull(processInstance)) {
			HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
					.processInstanceId(processId).singleResult();

			processDefinitionId = pi.getProcessDefinitionId();
		} else {// 如果流程没有结束，则取当前活动节点
			// 根据流程实例ID获得当前处于活动状态的ActivityId合集
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId)
					.singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		}

		// 获得活动的节点
		List<HistoricActivityInstance> highLightedFlowList = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();

		List<String> highLightedFlows = new ArrayList<>();
		List<String> highLightedNodes = new ArrayList<>();
		// 高亮线
		for (HistoricActivityInstance tempActivity : highLightedFlowList) {
			if ("sequenceFlow".equals(tempActivity.getActivityType())) {
				// 高亮线
				highLightedFlows.add(tempActivity.getActivityId());
			} else {
				// 高亮节点
				highLightedNodes.add(tempActivity.getActivityId());
			}
		}

		// 获取流程图
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
		// 获取自定义图片生成器
		ProcessDiagramGenerator diagramGenerator = new CustomProcessDiagramGenerator();
		InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedNodes, highLightedFlows,
				configuration.getActivityFontName(), configuration.getLabelFontName(),
				configuration.getAnnotationFontName(), configuration.getClassLoader(), 1.0, true);
		return in;

	}

	/**
	 * 获取流程执行过程
	 *
	 * @param procInsId 流程实例id
	 * @return
	 */
	@Override
	public AjaxResult getFlowViewer(String procInsId) {
		List<FlowViewerDto> flowViewerList = new ArrayList<>();
		List<HistoricActivityInstance> hisActIns = historyService
				.createHistoricActivityInstanceQuery()
//				.executionId(executionId)
				.processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().list();
		for (HistoricActivityInstance activityInstance : hisActIns) {
			if (!ProcessConstants.SEQUENCE_FLOW.equals(activityInstance.getActivityType())) {
				FlowViewerDto flowViewerDto = new FlowViewerDto();
				flowViewerDto.setKey(activityInstance.getActivityId());
				// 根据流程节点处理时间校验改节点是否已完成
				flowViewerDto.setCompleted(!Objects.isNull(activityInstance.getEndTime()));
				// 设置上一个节点Key
				if(CollUtil.isNotEmpty(flowViewerList)) {
					flowViewerDto.setPrevKey(flowViewerList.get(flowViewerList.size() - 1).getKey());
				}
				flowViewerList.add(flowViewerDto);
			}
		}
		return AjaxResult.success(flowViewerList);
	}
	
	/**
	 * 获取流程变量
	 *
	 * @param taskId
	 * @return
	 */
	@Override
	public Map<String, Object> processVariablesByTaskId(String taskId) {
		// 流程变量
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
				.includeProcessVariables().finished().taskId(taskId).singleResult();
		if (Objects.nonNull(historicTaskInstance)) {
			return historicTaskInstance.getProcessVariables();
		} else {
			return taskService.getVariables(taskId);
		}
	}
	
	/**
	 * 获取流程变量
	 *
	 * @param procInsId
	 * @return
	 */
	@Override
	public Map<String, Object> processVariablesByProcInsId(String procInsId) {
		Map<String, Object> vars = Maps.newHashMap();
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(procInsId).singleResult();
		if (Objects.nonNull(historicTaskInstance)) {
			vars = historicTaskInstance.getProcessVariables();
		} 
		return vars;
	}
	
	/**
	 * 获取所有可回退的节点
	 *
	 * @param flowTaskVo
	 * @return
	 */
	@Override
	public AjaxResult findReturnTaskList(FlowTaskVo flowTaskVo) {
		// 当前任务 task
		Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
		// 获取流程定义信息
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(task.getProcessDefinitionId()).singleResult();
		// 获取所有节点信息，暂不考虑子流程情况
		Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		// 获取当前任务节点元素
		UserTask source = null;
		if (flowElements != null) {
			for (FlowElement flowElement : flowElements) {
				// 类型为用户节点
				if (flowElement.getId().equals(task.getTaskDefinitionKey())) {
					source = (UserTask) flowElement;
				}
			}
		}
		// 获取节点的所有路线
		List<List<UserTask>> roads = FlowableUtils.findRoad(source, null, null, null);
		// 可回退的节点列表
		List<UserTask> userTaskList = new ArrayList<>();
		for (List<UserTask> road : roads) {
			if (userTaskList.size() == 0) {
				// 还没有可回退节点直接添加
				userTaskList = road;
			} else {
				// 如果已有回退节点，则比对取交集部分
				userTaskList.retainAll(road);
			}
		}
		return AjaxResult.success(userTaskList);
	}
	
	/**
	 * 获取下一节点
	 *
	 * @param flowTaskVo 任务
	 * @return
	 */
	@Override
	public AjaxResult getNextFlowNode(FlowTaskVo flowTaskVo) {
		// Step 1. 获取当前节点并找到下一步节点
		Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
		FlowNextDto flowNextDto = new FlowNextDto();
		if (Objects.nonNull(task)) {
			// Step 2. 获取当前流程所有流程变量(网关节点时需要校验表达式)
			Map<String, Object> variables = taskService.getVariables(task.getId());
			List<UserTask> nextUserTask = FindNextNodeUtil.getNextUserTasks(repositoryService, task, variables);
			if (CollectionUtils.isNotEmpty(nextUserTask)) {
				for (UserTask userTask : nextUserTask) {
					MultiInstanceLoopCharacteristics multiInstance = userTask.getLoopCharacteristics();
					// 会签节点
					if (Objects.nonNull(multiInstance)) {
						List<SysUser> list = userMapper.selectUserList(new SysUser());

						flowNextDto.setVars(ProcessConstants.PROCESS_MULTI_INSTANCE_USER);
						flowNextDto.setType(ProcessConstants.PROCESS_MULTI_INSTANCE);
						flowNextDto.setUserList(list);
					} else {

						// 读取自定义节点属性 判断是否是否需要动态指定任务接收人员、组
						String dataType = userTask.getAttributeValue(ProcessConstants.NAMASPASE,
								ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
						String userType = userTask.getAttributeValue(ProcessConstants.NAMASPASE,
								ProcessConstants.PROCESS_CUSTOM_USER_TYPE);

						// 处理加载动态指定下一节点接收人员信息
						if (ProcessConstants.DATA_TYPE.equals(dataType)) {
							// 指定单个人员
							if (ProcessConstants.USER_TYPE_ASSIGNEE.equals(userType)) {
								List<SysUser> list = userMapper.selectUserList(new SysUser());

								flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
								flowNextDto.setType(ProcessConstants.USER_TYPE_ASSIGNEE);
								flowNextDto.setUserList(list);
							}
							// 候选人员(多个)
							if (ProcessConstants.USER_TYPE_USERS.equals(userType)) {
								List<SysUser> list = userMapper.selectUserList(new SysUser());

								flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
								flowNextDto.setType(ProcessConstants.USER_TYPE_USERS);
								flowNextDto.setUserList(list);
							}
							// 候选组
							if (ProcessConstants.USER_TYPE_ROUPS.equals(userType)) {
								List<SysFlowGroup> sysFlowGroups = sysFlowGroupService.selectSysFlowGroupList(new SysFlowGroup());

								flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
								flowNextDto.setType(ProcessConstants.USER_TYPE_ROUPS);
								flowNextDto.setGroupList(sysFlowGroups);
							}
						} else {
							flowNextDto.setType(ProcessConstants.FIXED);
						}
					}
				}
			} else {
				return AjaxResult.success("流程已完结", null);
			}
		}
		return AjaxResult.success(flowNextDto);
	}

}

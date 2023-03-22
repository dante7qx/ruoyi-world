package com.risun.flowable.service.impl;

import static java.util.stream.Collectors.toList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysDept;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.common.constant.ProcessConstants;
import com.risun.flowable.common.enums.SysFlowStatusEnum;
import com.risun.flowable.common.enums.SysFlowTypeEnum;
import com.risun.flowable.demo.service.IFlowDemoService;
import com.risun.flowable.domain.SysFlowAssign;
import com.risun.flowable.domain.SysFlowGroupUser;
import com.risun.flowable.domain.SysFlowRecord;
import com.risun.flowable.domain.SysFlowTrace;
import com.risun.flowable.domain.dto.FlowViewerDto;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysFlowApproverVo;
import com.risun.flowable.domain.vo.SysFlowAssignVo;
import com.risun.flowable.domain.vo.SysFlowTaskQueryVo;
import com.risun.flowable.domain.vo.SysFlowTaskVo;
import com.risun.flowable.domain.vo.SysFlowVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;
import com.risun.flowable.event.SysFlowEventBody;
import com.risun.flowable.event.SysFlowEventPublisher;
import com.risun.flowable.exception.CustomWorkflowException;
import com.risun.flowable.factory.FlowServiceFactory;
import com.risun.flowable.flow.CustomProcessDiagramGenerator;
import com.risun.flowable.mapper.SysFlowAssignMapper;
import com.risun.flowable.mapper.SysFlowTaskMapper;
import com.risun.flowable.service.ISysFlowBpmnModelService;
import com.risun.flowable.service.ISysFlowGroupUserService;
import com.risun.flowable.service.ISysFlowRecordService;
import com.risun.flowable.service.ISysFlowTaskService;
import com.risun.flowable.service.ISysFlowTraceService;
import com.risun.system.mapper.SysUserMapper;
import com.risun.system.service.ISysDeptService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统流程服务接口实现类
 * 
 * @author dante
 *
 */
@Slf4j
@Service
public class SysFlowTaskServiceImpl extends FlowServiceFactory implements ISysFlowTaskService {

	@Autowired
	private ISysFlowBpmnModelService sysFlowBpmnModelService;
	@Autowired
	private ISysFlowGroupUserService sysFlowGroupUserService;
	@Autowired
	private ISysDeptService sysDeptService;
	@Autowired
    private SysUserMapper userMapper;
	@Autowired
	private SysFlowAssignMapper sysFlowAssignMapper;
	@Autowired
	private ISysFlowTraceService sysFlowTraceService;
	@Autowired
	private ISysFlowRecordService sysFlowRecordService;
	@Autowired
	private SysFlowTaskMapper sysFlowTaskMapper;
	@Autowired
	private SysFlowEventPublisher<SysFlowEventBody> sysFlowEventPublisher;
	@Autowired
	private IFlowDemoService flowDemoService;

	/** 同一部门，指定流程组 */
	static final String SEL_DEPT_GROUP = "D|G_";
	/** 指定部门，指定流程组 */
	static final String SEL_DEPT = "D_";
	/** 指定流程组 */
	static final String SEL_GROUP = "G_";
	/** 流程发起人 */
	static final String STARTER = "STARTER";
	/** 候选用户多选 */
	static final String MULTI_SEL = "|M";
	/** 候选用户分隔符 */
	static final String SEL_SEPARATOR = "\\|";

	/**
	 * 获取任务审批候选人
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @param starterUserId
	 * @return
	 */
	@Override
	public SysFlowApproverVo selectFlowApprover(String procDefId, String taskDefId, Long starterUserId) {
		SysFlowApproverVo flowApprover = new SysFlowApproverVo();
		flowApprover.setUsers(Lists.newArrayList());
		UserTask userTask = sysFlowBpmnModelService.getFirstUserTaskOrTaskDef(procDefId, taskDefId);
		// 根据流程定义描述获取审批候选用户
		List<SysFlowGroupUser> groupUsers = Lists.newArrayList();
		if (userTask != null) {
			String taskDefDesc = userTask.getDocumentation();
			flowApprover.setMulti(StrUtil.endWith(taskDefDesc, MULTI_SEL));
			if (StrUtil.isEmpty(taskDefDesc)) {
				return flowApprover;
			}
			if (taskDefDesc.startsWith(SEL_GROUP)) {
				String groupKey = StrUtil.replaceFirst(taskDefDesc, SEL_GROUP, "")
					.replace(MULTI_SEL, "")
					.trim();
				groupUsers.addAll(sysFlowGroupUserService.selectSysFlowGroupUserByGroupKey(groupKey));
			} else if (taskDefDesc.startsWith(SEL_DEPT) || taskDefDesc.startsWith(SEL_DEPT_GROUP)) {
				if (taskDefDesc.startsWith(SEL_DEPT)) {
					String[] arr = taskDefDesc.replace(MULTI_SEL, "")
						.split("\\|");
					String[] deptKeyArr = StrUtil.replaceFirst(arr[0], SEL_DEPT, "")
						.split(",");
					String[] groupKeyArr = StrUtil.replaceFirst(arr[1], SEL_GROUP, "")
						.split(",");
					for (int i = 0; i < deptKeyArr.length; i++) {
						groupUsers.addAll(this.selectUserByDeptAndGroupKey(deptKeyArr[i], groupKeyArr[i]));
					}
				} else {
					SysDept sysDept = sysDeptService.selectDeptById(SecurityUtils.getDeptId());
					String deptKey = sysDept.getDeptKey();
					String groupKey = StrUtil.replaceFirst(taskDefDesc, SEL_DEPT_GROUP, "")
						.replace(MULTI_SEL, "")
						.trim();
					groupUsers.addAll(this.selectUserByDeptAndGroupKey(deptKey, groupKey));
				}
			} else if (taskDefDesc.startsWith(STARTER)) {
				SysFlowGroupUser user = new SysFlowGroupUser();
				user.setUserId(starterUserId);
				groupUsers.add(user);
			}
		}
		if (CollUtil.isNotEmpty(groupUsers)) {
			flowApprover.setUsers(groupUsers.stream()
				.filter(distinctByKey(SysFlowGroupUser::getUserId))
				.collect(toList()));
		}

		return flowApprover;
	}

	/**
	 * 发起流程
	 * 
	 * @param startFlowVo
	 */
	@Override
	@Transactional
	public void startFlowTask(SysStartFlowVo startFlowVo) {
		String procDefKey = startFlowVo.getProcDefKey();
		Assert.hasText(startFlowVo.getProcDefKey(), "流程定义Key不能为空！");
		Assert.hasText(startFlowVo.getBizUid(), "业务UID不能为空！");
		Assert.hasText(startFlowVo.getBizDesc(), "业务详情描述不能为空！");
		Assert.hasText(startFlowVo.getFlowType(), "流程类型不能为空！");

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
			.processDefinitionKey(startFlowVo.getProcDefKey())
			.latestVersion()
			.singleResult();
		if (processDefinition == null) {
			throw new CustomWorkflowException("流程[" + procDefKey + "]未定义。");
		} else if (processDefinition.isSuspended()) {
			throw new CustomWorkflowException("流程[" + procDefKey + "]已挂起，不能发起流程。");
		}
		try {
			// 设置流程发起人
			Long starterId = SecurityUtils.getUserId();
			startFlowVo.setHandleUserId(starterId);
			identityService.setAuthenticatedUserId(starterId.toString());
			// 设置任务的处理人（流程的发起人 initiator）
			Map<String, Object> flowVarMap = Maps.newHashMap();
			flowVarMap.put(ProcessConstants.PROCESS_INITIATOR, starterId);
			flowVarMap.put(ProcessConstants.FLOWABLE_SKIP_EXPRESSION_ENABLED, Boolean.FALSE);
			ProcessInstance procInst = runtimeService.createProcessInstanceBuilder()
				.processDefinitionId(processDefinition.getId())
				.name(processDefinition.getName())
				.businessKey(startFlowVo.getBizUid())
				.variables(flowVarMap)
				.start();
			// 起始任务
			Task task = taskService.createTaskQuery()
				.processInstanceId(procInst.getId())
				.singleResult();
			taskService.setAssignee(task.getId(), starterId.toString());
			// 设置下一步审批人
			setNextApprovalUser(startFlowVo, startFlowVo.getApprovalUserId());
			if (CollUtil.isEmpty(startFlowVo.getVariable())) {
				taskService.complete(task.getId());
			} else {
				taskService.complete(task.getId(), startFlowVo.getVariable());
			}
			// 当前任务
			List<Task> curTasks = taskService.createTaskQuery()
				.processInstanceId(procInst.getId())
				.active()
				.list();
			// 流程跟踪记录
			sysFlowTraceService.insertSysFlowTrace(startFlowVo, procInst, curTasks);
			// 流程历史记录
			sysFlowRecordService.insertSysFlowRecord(startFlowVo, task, curTasks);
			// 发布流程事件消息
			sysFlowEventPublisher.publish(buildEvent(startFlowVo, curTasks));

		} catch (Exception e) {
			identityService.setAuthenticatedUserId(null);
			log.error("流程[" + procDefKey + "]启动失败。", e);
			throw new CustomWorkflowException("流程[" + procDefKey + "]启动失败。");
		}
	}

	/**
	 * 审批流程
	 * 
	 * @param approvalFlowVo
	 */
	@Override
	@Transactional
	public int approvalFlowTask(SysApprovalFlowVo taskVo) {
		int result = 0;
		String taskId = taskVo.getTaskId();
		Task task = taskService.createTaskQuery()
			.taskId(taskId)
			.singleResult();
		if (task == null) {
			throw new CustomWorkflowException("未获取到任务，请联系系统管理员");
		}
		UserTask userTask = sysFlowBpmnModelService.getUserTaskByTaskDefId(task.getProcessDefinitionId(),
				task.getTaskDefinitionKey());
		Long[] approvalUserId = taskVo.getApprovalUserId();
		Boolean agree = taskVo.getAgree();
		taskVo.setHandleUserId(SecurityUtils.getUserId());
		if (agree == null) {
			// 设置下一步审批人
			setNextApprovalUser(taskVo, approvalUserId);
			taskService.setAssignee(task.getId(), taskVo.getHandleUserId() .toString());
			completeTask(task, taskVo);
		} else if (agree) {
			// 设置下一步审批人
			setNextApprovalUser(taskVo, approvalUserId);
			// 通过
			taskVo.add(ProcessConstants.PROCESS_ARG_AGREE, agree);
			taskService.setAssignee(task.getId(), taskVo.getHandleUserId() .toString());
			completeTask(task, taskVo);
		} else if (!agree) {
			// 驳回
			taskVo.clearVariable();
			taskVo.add(ProcessConstants.PROCESS_ARG_AGREE, agree);
			// 会签驳回
			if (ObjectUtil.isNotNull(userTask.getLoopCharacteristics())) {
				this.rejectMultiTask(taskVo, task);
			} else {
				taskService.setAssignee(task.getId(), taskVo.getHandleUserId().toString());
				completeTask(task, taskVo);
			}
		}

		// 当前任务
		List<Task> curTasks = taskService.createTaskQuery()
			.processInstanceId(task.getProcessInstanceId())
			.active()
			.list();
		
		if(BooleanUtil.isFalse(agree) && CollUtil.isNotEmpty(curTasks)) {
			// 获取退回后目标任务的处理人
			Long[] targetApprovalUserId = this.getRejectTaskApprover(taskVo, curTasks.get(0));
			taskVo.setApprovalUserId(targetApprovalUserId);
		}
		// 流程跟踪记录
		result = sysFlowTraceService.updateSysFlowTrace(taskVo, curTasks);
		// 流程历史记录
		sysFlowRecordService.updateSysFlowRecord(taskVo, curTasks);
		// 发布流程事件消息
		sysFlowEventPublisher.publish(buildEvent(taskVo, curTasks));
		return result;
	}
	
	/**
	 * 设置下一步审批人
	 * 
	 * @param multi
	 * @param approvalUserId
	 */
	private void setNextApprovalUser(SysFlowVo flowVo, Long[] approvalUserId) {
		if(ArrayUtil.isNotEmpty(approvalUserId)) {
			if(BooleanUtil.isTrue(flowVo.getMulti())) {
				flowVo.add(ProcessConstants.PROCESS_MULTI_INSTANCE_USER, Arrays.stream(approvalUserId).collect(toList()));
			} else {
				flowVo.add(ProcessConstants.PROCESS_APPROVAL, approvalUserId[0]);
			}
		}
	}

	/**
	 * 多实例任务退回操作
	 * 
	 * @param tasks
	 * @param approvalUserId
	 */
	private void rejectMultiTask(SysApprovalFlowVo taskVo, Task curTask) {
		List<Task> tasks = taskService.createTaskQuery()
			.processInstanceId(curTask.getProcessInstanceId())
			.active()
			.list();
		List<SysFlowRecord> records = sysFlowRecordService.selectByBizUidAndTaskDefId(taskVo.getBizUid(), taskVo.getTaskDefId());
		records = records.stream().filter(r -> ObjectUtil.isNull(r.getEndTime())).collect(toList());
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			taskService.setAssignee(task.getId(), records.get(i)
				.getUserId()
				.toString());
			completeTask(task, taskVo);
		}
	}
	
	/**
	 * 当前登录人完成当前任务
	 * 
	 * @param task
	 * @param taskVo
	 */
	private void completeTask(Task task, SysApprovalFlowVo taskVo) {
		DelegationState delegationState = task.getDelegationState();
		// 判断是否为委派的任务
		if(delegationState != null && DelegationState.PENDING.compareTo(delegationState) == 0) {
			if (CollUtil.isNotEmpty(taskVo.getVariable())) {
				taskService.resolveTask(task.getId(), taskVo.getVariable());
				taskService.complete(task.getId(), taskVo.getVariable());
			} else {
				taskService.resolveTask(task.getId());
				taskService.complete(task.getId());
			}
		} else {
			if (CollUtil.isNotEmpty(taskVo.getVariable())) {
				taskService.complete(task.getId(), taskVo.getVariable());
			} else {
				taskService.complete(task.getId());
			}
		}
	}
	

	/**
	 * 获取退回后，目标任务及处理人
	 * 
	 * @param taskVo
	 * @param task
	 * @return
	 */
	private Long[] getRejectTaskApprover(SysApprovalFlowVo taskVo, Task task) {
		List<SysFlowRecord> records = sysFlowRecordService.selectSysFlowRecordByBizUid(taskVo.getBizUid());
//		String prevTaskDefId = getPrevTaskDefId(task.getTaskDefinitionKey(), records);
		String prevTaskDefId = task.getTaskDefinitionKey();
		if (StrUtil.isEmpty(prevTaskDefId)) {
			throw new CustomWorkflowException("未找到上一步审批人");
		}
		int size = records.size();
		records.sort((r1, r2) -> Long.compare(r2.getRecordId(), r1.getRecordId()));
		boolean isPrev = false;
		List<Long> prevApprover = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			if (prevTaskDefId.equals(records.get(i).getTaskDefId())) {
				prevApprover.add(records.get(i).getUserId());
				isPrev = true;
			} else {
				if (isPrev) {
					break;
				}
				continue;
			}
		}
		return prevApprover.stream().toArray(Long[]::new);
	}
	
	private List<SysFlowGroupUser> selectUserByDeptAndGroupKey(String deptKey, String groupKey) {
		SysFlowGroupUser groupUser = new SysFlowGroupUser();
		groupUser.setDeptKey(deptKey);
		groupUser.setGroupKey(groupKey);
		return sysFlowGroupUserService.selectSysFlowGroupUserList(groupUser);
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	/**
	 * 查询系统流程列表（我的、已办、监控）
	 * 
	 * @param query
	 * @return
	 */
	@Override
	public List<SysFlowTaskVo> selectSysFlowTaskList(SysFlowTaskQueryVo query) {
		return sysFlowTaskMapper.selectSysFlowTaskList(query);
	}

	/**
	 * 查询系统流程待办列表
	 * 
	 * @param query
	 * @return
	 */
	@Override
	public List<SysFlowTaskVo> selectSysFlowTaskTodoList(SysFlowTaskQueryVo query) {
		query.setHandledUserId(SecurityUtils.getUserId());
		return sysFlowTaskMapper.selectSysFlowTaskTodoList(query);
	}

	/**
	 * 根据bizUid查询流程跟踪
	 * 
	 * @param bizUid
	 * @return
	 */
	@Override
	public SysFlowTrace selectSysFlowTraceByBizUid(String bizUid) {
		return sysFlowTraceService.selectSysFlowTraceByBizUid(bizUid);
	}

	/**
	 * 撤销流程
	 * 
	 * @param bizUid
	 * @return
	 */
	@Override
	@Transactional
	public int revoke(String bizUid) {
		int result = 1;
		SysFlowTrace trace = sysFlowTraceService.selectSysFlowTraceByBizUid(bizUid);
		if (trace != null) {
			UserTask firstUserTask = sysFlowBpmnModelService.getFirstUserTaskOrTaskDef(trace.getProcDefId(), null);
			trace.setTaskDefId(firstUserTask.getId());
			trace.setTaskDefName(firstUserTask.getName());
			trace.setTaskDefDesc(firstUserTask.getDocumentation());
			trace.setFlowStatus(SysFlowStatusEnum.TO_BE_COMMIT.getType());
			sysFlowTraceService.revokeSysFlowTrace(trace);
			// 删除流程数据
			deleteFlowByProcInstId(trace.getProcInstId());
		}
		// 删除流程记录
		sysFlowRecordService.deleteSysFlowRecordByBizUid(bizUid);

		return result;
	}

	/**
	 * 根据流程实例Id删除流程
	 * 
	 * @param procInstId
	 */
	@Override
	@Transactional
	public void deleteFlowByProcInstId(String procInstId) {
		// 删除流程实例
		List<ProcessInstance> procInsts = runtimeService.createProcessInstanceQuery()
			.processInstanceId(procInstId)
			.list();
		if (CollUtil.isNotEmpty(procInsts)) {
			runtimeService.deleteProcessInstance(procInstId, "");
		}
		// 查询历史数据
		List<HistoricProcessInstance> hists = historyService.createHistoricProcessInstanceQuery()
			.processInstanceId(procInstId)
			.list();
		if (CollUtil.isNotEmpty(hists)) {
			historyService.deleteHistoricProcessInstance(procInstId);
		}
	}

	/**
	 * 删除流程 
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	@Override
	@Transactional
	public int delete(SysFlowTaskVo[] sysFlowTaskVoArr) {
		int result = 0;
		for (SysFlowTaskVo sysFlowTaskVo : sysFlowTaskVoArr) {
			result += delete(sysFlowTaskVo);
		}
		return result;
	}

	/**
	 * 删除流程 
	 * TODO: 每个业务根据流程类型实现相关删除
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	@Override
	@Transactional
	public int delete(SysFlowTaskVo sysFlowTaskVo) {
		SysFlowTypeEnum flowTypeEnum = SysFlowTypeEnum.fromType(sysFlowTaskVo.getFlowType());
		switch (flowTypeEnum) {
		case FLOW_DEMO:
			flowDemoService.deleteFlowDemoByUid(sysFlowTaskVo.getBizUid());
			break;

		default:
			break;
		}
		if (StrUtil.isNotEmpty(sysFlowTaskVo.getProcInstId())) {
			deleteFlowByProcInstId(sysFlowTaskVo.getProcInstId());
		}
		sysFlowAssignMapper.deleteSysFlowAssignByProcInstId(sysFlowTaskVo.getProcInstId());
		sysFlowRecordService.deleteSysFlowRecordByBizUid(sysFlowTaskVo.getBizUid());
		return sysFlowTraceService.deleteSysFlowTraceByBizUid(sysFlowTaskVo.getBizUid());
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
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			.processInstanceId(processId)
			.singleResult();
		// 如果流程已经结束，则得到结束节点
		if (Objects.isNull(processInstance)) {
			HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processId)
				.singleResult();

			processDefinitionId = pi.getProcessDefinitionId();
		} else {// 如果流程没有结束，则取当前活动节点
			// 根据流程实例ID获得当前处于活动状态的ActivityId合集
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processId)
				.singleResult();
			processDefinitionId = pi.getProcessDefinitionId();
		}

		// 获得活动的节点
		List<HistoricActivityInstance> highLightedFlowList = historyService.createHistoricActivityInstanceQuery()
			.processInstanceId(processId)
			.orderByHistoricActivityInstanceStartTime()
			.asc()
			.list();

		List<String> highLightedFlows = new ArrayList<>();
		List<String> highLightedNodes = new ArrayList<>();
		// 高亮线
		for (HistoricActivityInstance tempActivity : highLightedFlowList) {
			if (ProcessConstants.SEQUENCE_FLOW.equals(tempActivity.getActivityType())) {
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
		List<HistoricActivityInstance> hisActIns = historyService.createHistoricActivityInstanceQuery()
//				.executionId(executionId)
			.processInstanceId(procInsId)
			.orderByHistoricActivityInstanceStartTime()
			.asc()
			.list();
		for (HistoricActivityInstance activityInstance : hisActIns) {
			if (!ProcessConstants.SEQUENCE_FLOW.equals(activityInstance.getActivityType())) {
				FlowViewerDto flowViewerDto = new FlowViewerDto();
				flowViewerDto.setKey(activityInstance.getActivityId());
				// 根据流程节点处理时间校验改节点是否已完成
				flowViewerDto.setCompleted(!Objects.isNull(activityInstance.getEndTime()));
				// 设置上一个节点Key
				if (CollUtil.isNotEmpty(flowViewerList)) {
					flowViewerDto.setPrevKey(flowViewerList.get(flowViewerList.size() - 1)
						.getKey());
				}
				flowViewerList.add(flowViewerDto);
			}
		}
		return AjaxResult.success(flowViewerList);
	}
	
	/**
     * 转办任务
     *
     * @param sysFlowAssignVo
     */
	@Override
	@Transactional
	public void assignTask(SysFlowAssignVo sysFlowAssignVo) {
		String[] taskIds = sysFlowAssignVo.getAssignTaskIds();
		Boolean keepTodo = sysFlowAssignVo.getKeepTodo();
		String ownerId = SecurityUtils.getUserId().toString();
		for (String taskId : taskIds) {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			DelegationState delegationState = task.getDelegationState();
			// 多次转办
			if(delegationState != null && DelegationState.PENDING.compareTo(delegationState) == 0) {
				ownerId = task.getOwner();
			}
			taskService.setOwner(taskId, ownerId);
			recordFlowAssign(task, sysFlowAssignVo);
			if(keepTodo) {
				taskService.delegateTask(taskId, sysFlowAssignVo.getAssignee());
			} else {
				taskService.setAssignee(taskId, sysFlowAssignVo.getAssignee());
			}
			taskService.setVariable(taskId, ProcessConstants.KEEP_TODO, keepTodo);
			
		}
	}
	
	/**
	 * 查询流程转办记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	public List<SysFlowAssign> selectFlowAssignList(String procInsId) {
		SysFlowAssign flowAssign = new SysFlowAssign();
		flowAssign.setProcInstId(procInsId);
		return sysFlowAssignMapper.selectSysFlowAssignList(flowAssign);
	}
	
	/**
	 * 记录任务转办信息
	 * 
	 * @param flowTaskVo
	 */
	private void recordFlowAssign(Task task, SysFlowAssignVo sysFlowAssignVo) {
		SysFlowAssign sysFlowAssign = new SysFlowAssign();
		sysFlowAssign.setTaskId(task.getId());
		sysFlowAssign.setProcInstId(task.getProcessInstanceId());
		sysFlowAssign.setComment(sysFlowAssignVo.getComment());
		sysFlowAssign.setKeepTodo(sysFlowAssignVo.getKeepTodo());
		
		SysUser owner = SecurityUtils.getLoginUser().getUser();
		sysFlowAssign.setOwnerId(owner.getUserId());
		sysFlowAssign.setOwnerName(owner.getNickName());
		sysFlowAssign.setOwnerDept(owner.getDept().getDeptName());
		
		SysUser assignee = userMapper.selectUserById(Long.parseLong(sysFlowAssignVo.getAssignee()));
		sysFlowAssign.setAssigneeId(assignee.getUserId());
		sysFlowAssign.setAssigneeName(assignee.getNickName());
		sysFlowAssign.setAssigneeDept(assignee.getDept().getDeptName());
		
		sysFlowAssign.setCreateTime(DateUtils.getNowDate());
		sysFlowAssignMapper.insertSysFlowAssign(sysFlowAssign);
	}
	
	/** 
	 * 构造流程事件消息
	 * 
	 * @param startFlowVo
	 * @param curTasks
	 * @return
	 */
	private SysFlowEventBody buildEvent(SysFlowVo flowVo, List<Task> curTasks) {
		SysFlowEventBody body = new SysFlowEventBody();
		body.setFlowType(flowVo.getFlowType());
		body.setBizUid(flowVo.getBizUid());
		
		if (ArrayUtil.isNotEmpty(flowVo.getApprovalUserId())) {
			body.setNextUserId(flowVo.getApprovalUserId());
		} else if(CollUtil.isNotEmpty(curTasks)){
			Task curTask = curTasks.get(0);
			UserTask userTask = sysFlowBpmnModelService.getUserTaskByTaskDefId(curTask.getProcessDefinitionId(), curTask.getTaskDefinitionKey());
			List<String> candidateGroups = userTask.getCandidateGroups();
			if(CollUtil.isNotEmpty(candidateGroups)) {
				body.setNextGroupId(Long.parseLong(candidateGroups.get(0)));
			} 
		}
		return body;
	}
	
	
	
}

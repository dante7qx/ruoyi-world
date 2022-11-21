package com.risun.flowable.service.impl;

import java.util.List;
import java.util.Objects;

import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.common.constant.ProcessConstants;
import com.risun.flowable.domain.SysFlowBizMonitor;
import com.risun.flowable.domain.vo.FlowTaskVo;
import com.risun.flowable.domain.vo.StartFlowVo;
import com.risun.flowable.exception.CustomWorkflowException;
import com.risun.flowable.factory.FlowServiceFactory;
import com.risun.flowable.mapper.SysFlowAssignMapper;
import com.risun.flowable.service.IFlowInstanceService;
import com.risun.flowable.service.IFlowTaskService;
import com.risun.flowable.service.ISysFlowBizMonitorService;

import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>工作流流程实例管理<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Slf4j
@Service
public class FlowInstanceServiceImpl extends FlowServiceFactory implements IFlowInstanceService {
	
	@Autowired
	private ISysFlowBizMonitorService sysFlowBizMonitorService;
	@Autowired
	private IFlowTaskService flowTaskService;
	@Autowired
	private SysFlowAssignMapper sysFlowAssignMapper;
	
	/**
     * 根据流程定义Key启动流程实例
     * 
     * @param procDefKey 	流程定义Key
     * @param startFlowVo	流程启动参数
     * @return
     */
	@Override
	public ProcessInstance startProcessInstanceByKey(String procDefKey, StartFlowVo startFlowVo) {
		Assert.hasText(procDefKey, "流程定义Key不能为空！");
		Assert.hasText(startFlowVo.getBizUid(), "业务标识ID不能为空！");
		Assert.notNull(startFlowVo.getParams().get(ProcessConstants.PROCESS_BIZ_DETAIL_DESC), "流程业务详情描述不能为空！");
		ProcessInstance processInstance = null;
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(procDefKey).latestVersion().singleResult();
		if (processDefinition == null) {
			throw new CustomWorkflowException("流程[" + procDefKey + "]未定义。");
		} else if (processDefinition.isSuspended()) {
			throw new CustomWorkflowException("流程[" + procDefKey + "]已挂起，不能发起流程。");
		}
		try { 
			Long starterId = startFlowVo.getStarterId() != null ? startFlowVo.getStarterId() : SecurityUtils.getUserId();
			// 设置流程发起人
			identityService.setAuthenticatedUserId(starterId.toString());
			// 设置任务的处理人（流程的发起人 initiator）
			startFlowVo.addParams(ProcessConstants.PROCESS_INITIATOR, starterId);
			startFlowVo.addParams(ProcessConstants.FLOWABLE_SKIP_EXPRESSION_ENABLED, Boolean.FALSE);
			startFlowVo.addParams(ProcessConstants.PROCESS_BIZ_UID, startFlowVo.getBizUid());
			processInstance = runtimeService.createProcessInstanceBuilder()
				.processDefinitionId(processDefinition.getId())
				.name(processDefinition.getName())
				.businessStatus(startFlowVo.getBizModel())
				.businessKey(startFlowVo.getBizId())
				.variables(startFlowVo.getParams())
				.start();
			
			// 起始任务
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			taskService.addComment(task.getId(), processInstance.getId(), startFlowVo.getComment());
			taskService.setAssignee(task.getId(), starterId.toString());
			taskService.complete(task.getId(), startFlowVo.getParams());
		} catch (Exception e) {
			identityService.setAuthenticatedUserId(null);
			log.error("流程[" + procDefKey + "]启动失败。", e);
			throw new CustomWorkflowException("流程[" + procDefKey + "]启动失败。");
		}
		return processInstance;
	}
	
	/**
     * 业务提交操作
     * 
     * @param procDefKey
     * @param startFlowVo
     */
    public void commit(String procDefKey, StartFlowVo startFlowVo) {
    	String bizUid = startFlowVo.getBizUid();
    	SysFlowBizMonitor flowBizMonitor = sysFlowBizMonitorService.selectSysFlowBizMonitorByBizUid(bizUid);
    	if(flowBizMonitor == null) {
    		this.startProcessInstanceByKey(procDefKey, startFlowVo);
    	} else {
    		List<Task> tasks = this.queryListByInstanceId(flowBizMonitor.getProcInstId());
    		if(CollUtil.isNotEmpty(tasks)) {
    			FlowTaskVo flowTask = new FlowTaskVo();
    			flowTask.setTaskId(tasks.get(0).getId());
        		flowTask.setComment(startFlowVo.getComment());
        		flowTask.setVariables(startFlowVo.getParams());
        		flowTaskService.approval(flowTask);
    		}
    	}
    }

	/**
	 * 根据流程实例Id查询任务
	 * 
	 * @param instanceId
	 * @return
	 */
	@Override
    public List<Task> queryListByInstanceId(String instanceId) {
        return taskService.createTaskQuery().processInstanceId(instanceId).active().list();
    }

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    @Override
    public void updateState(Integer state, String instanceId) {
        if (state == 1) {
        	// 激活
            runtimeService.activateProcessInstanceById(instanceId);
        } else if (state == 2) {
        	// 挂起
            runtimeService.suspendProcessInstanceById(instanceId);
        }
    }

    /**
     * 删除流程实例ID
     *
     * @param instanceId   流程实例ID
     * @param deleteReason 删除原因
     */
    @Override
    @Transactional
    public int delete(String[] proInstIds) {
    	int result = 1;
    	for (String instanceId : proInstIds) {
    		// 删除业务流程监控
            sysFlowBizMonitorService.deleteSysFlowBizMonitorByProcInstId(instanceId);
            // 删除流程任务转办
            sysFlowAssignMapper.deleteSysFlowAssignByProcInstId(instanceId);
    		// 查询历史数据
            HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(instanceId);
            if (historicProcessInstance.getEndTime() != null) {
                historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
                continue;
            }
            // 删除流程实例
            runtimeService.deleteProcessInstance(instanceId, "管理员删除");
            // 删除历史流程实例
            historyService.deleteHistoricProcessInstance(instanceId);
		}
    	return result;
    }

    /**
     * 根据实例ID查询历史实例数据
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new FlowableObjectNotFoundException("流程实例不存在: " + processInstanceId);
        }
        return historicProcessInstance;
    }

    

}

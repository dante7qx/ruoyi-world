package com.risun.flowable.listener;

import javax.annotation.Resource;

import com.risun.flowable.common.constant.ProcessConstants;
import com.risun.flowable.domain.SysFlowBizMonitor;
import com.risun.flowable.service.IFlowDefinitionService;
import com.risun.flowable.service.ISysFlowBizMonitorService;

import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.common.engine.impl.event.FlowableEntityEventImpl;
import org.flowable.engine.RuntimeService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 待办Task到达
 * 
 * @author dante
 */
@Slf4j
@Component
public class FlowTaskCreateListener implements FlowableEventListener {
	@Resource
    protected RuntimeService runtimeService;
	@Autowired
	private IFlowDefinitionService flowDefinitionService;
	@Autowired
	private ISysFlowBizMonitorService sysFlowBizMonitorService;

	@Override
	public void onEvent(FlowableEvent event) {
		log.info("进入待办Task到达监听器------------------------Start---------------------->");
		
		TaskEntity taskEntity = (TaskEntity) ((FlowableEntityEventImpl) event).getEntity();
		
		String beginUserTaskKey = flowDefinitionService.getBeginUserTask(taskEntity.getProcessDefinitionId());
		
        String name = taskEntity.getName();
        String bizUid = (String) taskEntity.getVariable(ProcessConstants.PROCESS_BIZ_UID);
        SysFlowBizMonitor sysFlowBizMonitor = new SysFlowBizMonitor();
        sysFlowBizMonitor.setBizUid(bizUid);
        sysFlowBizMonitor.setProcInstId(taskEntity.getProcessInstanceId());
        sysFlowBizMonitor.setStatus(name);
        if(beginUserTaskKey.equals(taskEntity.getTaskDefinitionKey())) {
        	sysFlowBizMonitor.setCommited(Boolean.TRUE);
        }
        /*
        if(update) {
        	sysFlowBizMonitorService.updateSysFlowBizMonitor(sysFlowBizMonitor);
        } else {
        	sysFlowBizMonitorService.insertSysFlowBizMonitor(sysFlowBizMonitor);
        }
        */
        sysFlowBizMonitorService.deleteSysFlowBizMonitorByBizUid(bizUid);
        sysFlowBizMonitorService.insertSysFlowBizMonitor(sysFlowBizMonitor);
        log.info("结束进入待办Task监听器------------------------Start---------------------->");
        
        /*
        List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<String> userNameList = new ArrayList<>();
        // 获取接收人，此处从Identity获取，实际情况会更复杂
        idList.forEach(identityLink -> {
            if (StringUtils.isNotBlank(identityLink.getUserId())) {
                userNameList.add(identityLink.getUserId());
            }
        });
        */

	}

	@Override
	public boolean isFailOnException() {
		return false;
	}

	@Override
	public boolean isFireOnTransactionLifecycleEvent() {
		return false;
	}

	@Override
	public String getOnTransaction() {
		return null;
	}

}

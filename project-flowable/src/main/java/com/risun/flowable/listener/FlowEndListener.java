package com.risun.flowable.listener;

import com.risun.flowable.common.constant.ProcessConstants;
import com.risun.flowable.domain.SysFlowBizMonitor;
import com.risun.flowable.service.ISysFlowBizMonitorService;

import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局的流程启动的监听器
 * 
 * @author dante
 * 
 */
@Slf4j
@Component
public class FlowEndListener extends AbstractFlowableEngineEventListener {
	
	@Autowired
	private ISysFlowBizMonitorService sysFlowBizMonitorService;
	
	@Override
    protected void processCompleted(FlowableEngineEntityEvent event) {
		log.info("进入流程完成监听器------------------------Start---------------------->");

        FlowableEntityEventImpl flowableEntityEvent = (FlowableEntityEventImpl) event;
        ExecutionEntityImpl processInstance = (ExecutionEntityImpl) flowableEntityEvent.getEntity();
        
        String bizUid = (String) processInstance.getVariable(ProcessConstants.PROCESS_BIZ_UID);
        Boolean agree = (Boolean)processInstance.getVariable(ProcessConstants.PROCESS_ARG_AGREE);
        
        SysFlowBizMonitor sysFlowBizMonitor = new SysFlowBizMonitor();
        sysFlowBizMonitor.setBizUid(bizUid);
        sysFlowBizMonitor.setProcInstId(processInstance.getId());
        sysFlowBizMonitor.setStatus("结束");
    	sysFlowBizMonitor.setFinished(Boolean.TRUE);
    	sysFlowBizMonitor.setPassed(agree);
    	sysFlowBizMonitorService.deleteSysFlowBizMonitorByBizUid(bizUid);
    	sysFlowBizMonitorService.insertSysFlowBizMonitor(sysFlowBizMonitor);

        /*
        String eventName = event.getType().name();
        Date startTime = processInstance.getStartTime();
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        String processInstanceId = processInstance.getProcessInstanceId();
        String processInstanceBusinessKey = processInstance.getProcessInstanceBusinessKey();
        int suspensionState = processInstance.getSuspensionState();
        
        log.info("流程事件类型->{}", eventName);
        log.info("流程开始时间->{}", startTime);
        log.info("流程定义Key->{}", processDefinitionKey);
        log.info("流程实例ID->{}", processInstanceId);
        log.info("流程业务key->{}", processInstanceBusinessKey);
        log.info("流程是否挂起标志->{}", suspensionState);
		*/

        log.info("流程完成监听器------------------------End---------------------->");
    }
	
}

package com.spirit.flowable.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spirit.flowable.common.enums.SysFlowTypeEnum;
import com.spirit.flowable.demo.service.IFlowDemoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 系统流程事件消息消费处理器
 * 
 * @author dante
 *
 */
@Slf4j
@AllArgsConstructor
@Component
public class SysFlowEventListener {
	
	private final IFlowDemoService flowDemoService;
	
	@Order
	@EventListener(SysFlowEvent.class)
	public void handleSysFlowEvent(SysFlowEvent<SysFlowEventBody> event) {
		SysFlowEventBody sysFlowBody = (SysFlowEventBody) event.getSource();
		SysFlowTypeEnum fromType = SysFlowTypeEnum.fromType(sysFlowBody.getFlowType());
		switch (fromType) {
		case FLOW_DEMO:
//			flowDemoService.selectFlowDemoByUid(sysFlowBody.getBizUid())
			log.info("FlowDemo 业务处理消息 ==> {}", event);
			break;

		default:
			break;
		}
		
	}
}

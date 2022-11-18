package com.risun.flowable.listener;

import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Flowable 流程全局监听配置​
 * 
 * @author dante
 * 
 */
@Configuration
public class FlowGlobalListenerConfig implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
    private SpringProcessEngineConfiguration configuration;
	@Autowired
	private FlowEndListener flowEndListener;
	@Autowired
	private FlowTaskCreateListener flowTaskCreateListener;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		FlowableEventDispatcher dispatcher = configuration.getEventDispatcher();
        // 待办Task到达全局监听
        dispatcher.addEventListener(flowTaskCreateListener, FlowableEngineEventType.TASK_CREATED);
        // 流程结束全局监听
        dispatcher.addEventListener(flowEndListener, FlowableEngineEventType.PROCESS_COMPLETED);
		
		// TODO: 注册各类事件监听器    
		// https://blog.csdn.net/Azhuzhu_chaste/article/details/109111936
		// https://blog.51cto.com/gblfy/5656723#3__104
	}

}

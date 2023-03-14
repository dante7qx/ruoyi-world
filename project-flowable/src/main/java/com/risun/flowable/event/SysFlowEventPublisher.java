package com.risun.flowable.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统流程事件消息发布处理器
 * 
 * @author dante
 *
 * @param <T>
 */
@Slf4j
@Component
public class SysFlowEventPublisher<T> implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public void publish(T event) {
		log.info("发布系统流程事件 --> {}", event);
		applicationContext.publishEvent(new SysFlowEvent<>(event));
	}

}

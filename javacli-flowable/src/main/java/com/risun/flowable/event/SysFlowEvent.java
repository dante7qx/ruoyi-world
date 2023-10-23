package com.spirit.flowable.event;

import org.springframework.context.ApplicationEvent;

/**
 * 系统流程事件
 * 
 * @author dante
 *
 * @param <T>
 */
public class SysFlowEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public SysFlowEvent(T event) {
		super(event);
	}
}

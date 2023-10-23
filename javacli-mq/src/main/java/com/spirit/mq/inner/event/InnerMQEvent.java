package com.spirit.mq.inner.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件消息
 * 
 * @author dante
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnerMQEvent<T> {
	
	/** 消费处理类 */
	private String consumerClass;
	
	/** 消息体 */
	private T payload;
	
	/**
	 * 清理业务数据
	 */
	public void clear() {
		payload = null;
	}
}

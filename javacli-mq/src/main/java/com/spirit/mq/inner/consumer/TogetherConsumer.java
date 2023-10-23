package com.spirit.mq.inner.consumer;

import com.lmax.disruptor.WorkHandler;
import com.spirit.mq.inner.event.InnerMQEvent;

/**
 * 共同事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface TogetherConsumer<T> extends WorkHandler<InnerMQEvent<T>> {
	
}

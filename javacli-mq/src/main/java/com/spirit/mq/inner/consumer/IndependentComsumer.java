package com.spirit.mq.inner.consumer;

import com.lmax.disruptor.EventHandler;
import com.spirit.mq.inner.event.InnerMQEvent;

/**
 * 独立事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface IndependentComsumer<T> extends EventHandler<InnerMQEvent<T>> {

}

package com.risun.mq.inner.consumer;

import com.lmax.disruptor.EventHandler;
import com.risun.mq.inner.event.InnerMQEvent;

/**
 * 独立事件消息处理接口
 * 
 * @author dante
 *
 * @param <T>
 */
public interface IndependentComsumer<T> extends EventHandler<InnerMQEvent<T>> {

}

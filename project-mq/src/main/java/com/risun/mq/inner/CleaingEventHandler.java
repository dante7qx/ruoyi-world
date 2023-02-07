package com.risun.mq.inner;


import com.lmax.disruptor.EventHandler;
import com.risun.mq.inner.event.InnerMQEvent;

/**
 * 数据清理处理类
 * 
 * @author dante
 *
 * @param <T>
 */
public class CleaingEventHandler<T> implements EventHandler<InnerMQEvent<T>> {

	@Override
	public void onEvent(InnerMQEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
		event.clear();
	}
	
}

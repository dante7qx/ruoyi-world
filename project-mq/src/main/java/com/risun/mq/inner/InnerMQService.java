package com.risun.mq.inner;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.risun.common.utils.spring.SpringUtils;
import com.risun.mq.inner.event.InnerMQEvent;
import com.risun.mq.inner.exception.InnerMQException;
import com.risun.mq.service.impl.SysInnerMqExceptionServiceImpl;

/**
 * 抽象内部消息队列服务类
 * 
 * @author dante
 *
 */
public abstract class InnerMQService<T> {

	/** RingBuffer 大小 */
	private static final Integer RINGBUFFER_SIZE = 4096 << 1 << 1;

	protected Disruptor<InnerMQEvent<T>> innerMQ;
	
	/** 统计消息总数 */
	protected final AtomicLong eventCount = new AtomicLong();

	public void publish(InnerMQEvent<T> bizEvent) throws Exception {
		if (bizEvent != null) {
			innerMQ.getRingBuffer()
				.publishEvent((EventTranslatorTwoArg<InnerMQEvent<T>, String, T>) (event, sequence, consumerClass, payload) -> {
//					event.setConsumerClass(consumerClass);
					event.setPayload(payload);
				}, bizEvent.getConsumerClass(), bizEvent.getPayload());
		} else {
			throw new Exception("InnerMQEvent error, event is null");
		}
	}

	/**
	 * 子类实现具体的事件消费逻辑
	 */
	protected abstract void bizConsume();

	/**
	 * 获取消息总数
	 * 
	 * @return
	 */
	public long eventCount() {
		return eventCount.get();
	}

	/**
	 * 服务启动，内部消息队列初始化
	 * 
	 */
	@PostConstruct
	private void init() {
		innerMQ = new Disruptor<>(
				InnerMQEvent::new, 
				RINGBUFFER_SIZE,
				new CustomizableThreadFactory("risun-inner-mq-event-"), 
				ProducerType.MULTI, 
				new BlockingWaitStrategy()
		);
		// 处理业务事件消费逻辑
		bizConsume();
		
		// 设置异常处理器
		innerMQ.setDefaultExceptionHandler(new InnerMQException<>(SpringUtils.getBean(SysInnerMqExceptionServiceImpl.class)));
		
		// 启动
		innerMQ.start();
	}

}

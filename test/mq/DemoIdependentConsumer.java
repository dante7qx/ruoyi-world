package com.risun.mq;

import com.risun.mq.inner.consumer.IndependentComsumer;
import com.risun.mq.inner.event.InnerMQEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 独立消费者
 * 
 * @author dante
 *
 */
@Slf4j
public class DemoIdependentConsumer implements IndependentComsumer<DemoBiz> {

	private final DemoBizService demoBizService;

	public DemoIdependentConsumer(DemoBizService demoBizService) {
		this.demoBizService = demoBizService;
	}

	@Override
	public void onEvent(InnerMQEvent<DemoBiz> event, long sequence, boolean endOfBatch) throws Exception {
		event.setConsumerClass(DemoIdependentConsumer.class.getName());
		log.info("消费者开始处理 -> {}", event);

		demoBizService.handleBizLogic(event);
	}

}

package com.risun.mq;

import com.risun.mq.inner.consumer.TogetherConsumer;
import com.risun.mq.inner.event.InnerMQEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * 共同消费者
 * 
 * @author dante
 *
 */
@Slf4j
public class DemoTogetherConsumer implements TogetherConsumer<DemoBiz> {
	
	private final DemoBizService demoBizService;
	
	public DemoTogetherConsumer(DemoBizService demoBizService) {
		this.demoBizService = demoBizService;
	}

	@Override
	public void onEvent(InnerMQEvent<DemoBiz> event) throws Exception {
		event.setConsumerClass(DemoTogetherConsumer.class.getName());
		log.info("消费者开始处理 -> {}", event);
		demoBizService.handleBizLogic(event);
	}

}

package com.risun.mq;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.risun.RisunApplicationTests;
import com.risun.mq.inner.InnerMQService;
import com.risun.mq.inner.event.InnerMQEvent;

import cn.hutool.core.thread.ThreadUtil;

public class DemoInnerMQTests extends RisunApplicationTests {

	@Autowired
	@Qualifier("demoInnerMQService")
	private InnerMQService<DemoBiz> demoInnerMQService;

	int EVENT_COUNT = 5;

	@Test
	public void testPublishAndConsumer() throws Exception {
		for (int i = 1; i <= EVENT_COUNT; i++) {
			InnerMQEvent<DemoBiz> event = new InnerMQEvent<>();
			event.setPayload(new DemoBiz(i, Long.valueOf(100 * i), "事件消息"));
			demoInnerMQService.publish(event);
		}
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
	}

}

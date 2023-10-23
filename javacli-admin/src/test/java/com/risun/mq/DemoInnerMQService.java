package com.spirit.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.mq.inner.InnerMQService;

/**
 * 发布消息以及设置消费者处理方式
 * 
 * 如果消费者需要加载业务相关SpringBean，请在这里声明。例如：DemoBizService demoBizService
 * 
 * @author dante
 *
 */
@Service("demoInnerMQService")
public class DemoInnerMQService extends InnerMQService<DemoBiz> {
	
	@Autowired
	private DemoBizService demoBizService;

	@Override
	protected void bizConsume() {
		// 设置消费者处理方式（独立消费、共同消费）
		// 独立消费
		innerMQ.handleEventsWith(new DemoIdependentConsumer(demoBizService));
		
		// 共同消费
//		innerMQ.handleEventsWithWorkerPool(new DemoTogetherConsumer(demoBizService), new DemoTogetherConsumer(demoBizService));
		
		// 消费后清理数据
//		innerMQ.handleEventsWith(new DemoIdependentConsumer(demoBizService)).then(new CleaingEventHandler<>());
	}

}

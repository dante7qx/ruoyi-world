package com.spirit.mq;

import org.springframework.stereotype.Service;

import com.spirit.mq.inner.event.InnerMQEvent;

@Service
public class DemoBizService {

	public void handleBizLogic(InnerMQEvent<DemoBiz> event) throws Exception {
		if(2 == event.getPayload().getSeq()) {
			throw new Exception("业务逻辑处理错误");
		}
		System.out.println("业务逻辑处理 【" + event.toString() + "】");
	}
	
}

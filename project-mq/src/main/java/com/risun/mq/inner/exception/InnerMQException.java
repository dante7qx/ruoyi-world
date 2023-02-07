package com.risun.mq.inner.exception;

import com.alibaba.fastjson2.JSON;
import com.lmax.disruptor.ExceptionHandler;
import com.risun.mq.domain.SysInnerMqException;
import com.risun.mq.inner.event.InnerMQEvent;
import com.risun.mq.service.ISysInnerMqExceptionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 内部事件消息全局异常处理类
 * 
 * @author dante
 *
 */
@Slf4j
public class InnerMQException<T> implements ExceptionHandler<InnerMQEvent<T>> {
	
	private ISysInnerMqExceptionService sysInnerMqExceptionService;
	
	public InnerMQException(ISysInnerMqExceptionService sysInnerMqExceptionService) {
		this.sysInnerMqExceptionService = sysInnerMqExceptionService;
	}

	@Override
	public void handleEventException(Throwable ex, long sequence, InnerMQEvent<T> event) {
		// 补偿业务服务类 - 持久化数据到数据库
		SysInnerMqException sysInnerMqException = new SysInnerMqException();
		sysInnerMqException.setConsumerClass(event.getConsumerClass());
		sysInnerMqException.setBizMsg(JSON.toJSONString(event.getPayload()));
		sysInnerMqExceptionService.insertSysInnerMqException(sysInnerMqException);
		log.error("{} - {}", sequence, event, ex);
		
	}

	@Override
	public void handleOnStartException(Throwable ex) {
		log.error("HandleOnStartException -> {}", ex.getMessage());
	}

	@Override
	public void handleOnShutdownException(Throwable ex) {
		log.error("HandleOnShutdownException -> {}", ex.getMessage());
	}

}

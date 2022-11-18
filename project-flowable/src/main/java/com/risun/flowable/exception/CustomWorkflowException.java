package com.risun.flowable.exception;

import com.risun.common.exception.base.BaseException;

/**
 * 自定义工作流异常
 * 
 * @author sunchao
 * 
 */
public class CustomWorkflowException extends BaseException {
	private static final long serialVersionUID = 1L;

	public CustomWorkflowException(String defaultMessage) {
		super(defaultMessage);
	}
}

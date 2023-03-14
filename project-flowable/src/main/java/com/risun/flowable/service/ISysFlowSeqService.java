package com.risun.flowable.service;

import com.risun.flowable.domain.SysFlowSeq;

/**
 * 流程序号Service接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface ISysFlowSeqService {
	
	/**
	 * 获取最新的流程序号
	 * 
	 * @return
	 */
	public SysFlowSeq selectNextSysFlowSeq();
}

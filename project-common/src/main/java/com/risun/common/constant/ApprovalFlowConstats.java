package com.risun.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 审批流程 常量
 * 
 * @author sunchao
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApprovalFlowConstats {
	
	/** 提交 */
	public static final String ACTION_TJ = "COMMIT"; 
	
	/** 审批通过 */
	public static final String ACTION_PASS = "PASS"; 
	
	/** 审批驳回 */
	public static final String ACTION_REJECT = "REJECT";
	
	/** 撤销发布 */
	public static final String ACTION_WITHDRAW = "WITHDRAW";
	
}

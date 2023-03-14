package com.risun.flowable.common.constant;

/**
 * 流程常量信息
 *
 * @author Xuan xuan
 * @date 2021/4/17 22:46
 */
public final class ProcessConstants {

	/** 顺序流 */
	public static final String SEQUENCE_FLOW = "sequenceFlow";

	/**
	 * 单个审批人
	 */
	public static final String PROCESS_APPROVAL = "approval";

	/**
	 * 会签人员
	 */
	public static final String PROCESS_MULTI_INSTANCE_USER = "userList";

	/**
	 * 初始化人员
	 */
	public static final String PROCESS_INITIATOR = "INITIATOR";

	/**
	 * 流程跳过
	 */
	public static final String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";

	/**
	 * 审批通过参数Key
	 */
	public static final String PROCESS_ARG_AGREE = "agree";

	/**
	 * 任务转办参数Key
	 */
	public static final String KEEP_TODO = "TASK_KEEP_TODO";

}

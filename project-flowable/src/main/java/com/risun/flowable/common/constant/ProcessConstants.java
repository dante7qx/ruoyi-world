package com.risun.flowable.common.constant;

/**
 * 流程常量信息
 *
 * @author Xuan xuan
 * @date 2021/4/17 22:46
 */
public final class ProcessConstants {
	
	/** 用户任务 */
	public static final String USER_TASK = "userTask";
	
	/** Java任务 */
	public static final String SERVICE_TASK = "serviceTask";
	
	/** 顺序流 */
	public static final String SEQUENCE_FLOW = "sequenceFlow";
	
	/** 流程结束事件 */
	public static final String END_EVENT = "endEvent";
	
	/** 审批意见类型 */
	public static final String COMMENT_TYPE = "comment";

    /** 动态数据 */
    public static final String DATA_TYPE = "dynamic";

    /**
     * 固定任务接收
     */
    public static final String FIXED = "fixed";

    /**
     * 单个审批人
     */
    public static final String USER_TYPE_ASSIGNEE = "assignee";


    /**
     * 候选人
     */
    public static final String USER_TYPE_USERS = "candidateUsers";


    /**
     * 审批组
     */
    public static final String USER_TYPE_ROUPS = "candidateGroups";

    /**
     * 单个审批人
     */
    public static final String PROCESS_APPROVAL = "approval";

    /**
     * 会签人员
     */
    public static final String PROCESS_MULTI_INSTANCE_USER = "userList";
    
    /**
     * 会签组
     */
    public static final String PROCESS_MULTI_INSTANCE_GROUP = "groupList";

    /**
     * nameapace
     */
    public static final String NAMASPASE = "http://flowable.org/bpmn";

    /**
     * 会签节点
     */
    public static final String PROCESS_MULTI_INSTANCE = "multiInstance";

    /**
     * 自定义属性 dataType
     */
    public static final String PROCESS_CUSTOM_DATA_TYPE = "dataType";

    /**
     * 自定义属性 userType
     */
    public static final String PROCESS_CUSTOM_USER_TYPE = "userType";

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
     * 业务标识ID（非业务主键）
     */
    public static final String PROCESS_BIZ_UID = "businessUID";
    
    
    /**
     * 任务转办参数Key
     */
    public static final String KEEP_TODO = "TASK_KEEP_TODO";

}

package com.spirit.flowable.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系统流程列表Vo
 * 
 * @author dante
 *
 */
@Data
public class SysFlowTaskVo {

	/** 业务uid */
	private String bizUid;
	
	/** 业务详情 */
	private String bizDesc;
	
	/** 流程序号 */
	private Long flowNum;
	
	/** 流程序号（日期） */
	private Long flowDateNum;
	
	/** 流程类型 */
	private String flowType;
	
	/** 流程发起人  */
	private Long userId;
	
	/** 流程发起人姓名 */
	private String userName;
	
	/** 流程发起人部门名称 */
	private String userDept;
	
	/** 流程实例Id */
	private String procInstId;
	
	/** 流程定义Id */
	private String procDefId;
	
	/** 流程部署Id */
	private String procDeployId;
	
	/** 流程任务Id */
	private String taskId;
	
	/** 流程任务定义Id */
	private String taskDefId;
	
	/** 流程任务定义名称 */
	private String taskDefName;
	
	/** 流程任务定义描述 */
	private String taskDefDesc;
	
	/** 提交时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date commitTime;
	
	 /** 状态（1 待提交 2 审批中 3 完成） */
	private Integer flowStatus;
	
	/** 审批结果（1 同意 0 退回） */
	private Integer flowResult;
	
	/** 流程第一个用户任务 */
	private Boolean firstTask;

	/** 记录数 */
	private int recordCount;
	
}

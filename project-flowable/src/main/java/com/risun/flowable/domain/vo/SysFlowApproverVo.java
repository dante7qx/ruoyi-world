package com.risun.flowable.domain.vo;

import java.util.List;

import com.risun.flowable.domain.SysFlowGroupUser;

import lombok.Data;

/**
 * 流程审批候选用户
 * 
 * @author dante
 *
 */
@Data
public class SysFlowApproverVo {
	
	/** 多选 */
	private boolean multi = Boolean.FALSE;
	
	/** 候选用户列表 */
	private List<SysFlowGroupUser> users;
	
	/** 流程定义Id */
	private String procDefId;
	
	/** 用户任务定义Id */
	private String taskDefId;
	
	/** 流程发起人 */
	private Long starterUserId;
}

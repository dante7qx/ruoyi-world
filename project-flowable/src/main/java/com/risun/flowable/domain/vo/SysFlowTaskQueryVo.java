package com.risun.flowable.domain.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系统流程查询Vo
 * 
 * @author dante
 *
 */
@Data
public class SysFlowTaskQueryVo {
	
	/** 流程类别 */
	private String flowType;
	
	/** 发起人 */
	private Long starterUserId;
	
	private String starterUserName;
	
	/** 所在部门 */
	private Long[] deptIds;
	
	/** 开始提交时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/** 结束提交时间*/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/** 状态（1 待提交 2 审批中 3 完成） */
    private Integer flowStatus;
    
    /** 上一级审批结果（1 同意 0 驳回） */
    private Integer flowResult;
    
    /** 已办理人 */
    private Long handledUserId;
}

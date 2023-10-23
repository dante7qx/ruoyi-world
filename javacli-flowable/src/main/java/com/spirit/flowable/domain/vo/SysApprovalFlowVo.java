package com.spirit.flowable.domain.vo;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程审批VO
 * 
 * 
 * @author dante
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysApprovalFlowVo extends SysFlowVo {
	
	private static final long serialVersionUID = 1L;
	
	/** 任务实例Id */
	private String taskId;
	
	/** 任务定义Id */
	private String taskDefId;
	
	/** 审批结果 （通过、退回） */
	private Boolean agree;
	
	/** 提交时间 */
	private Date commitDate;

}

package com.spirit.flowable.domain.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 系统流程任务转办Vo
 * 
 * @author dante
 *
 */
@Data
public class SysFlowAssignVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String assignee;
	
	/** 转办任务Id */
	private String[] assignTaskIds;
	
	/** 保留我的待办（转办任务） */
	private Boolean keepTodo;
	
	private String comment;
	
}

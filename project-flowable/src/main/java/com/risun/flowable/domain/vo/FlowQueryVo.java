package com.risun.flowable.domain.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 流程查询 VO
 * 
 * @author dante
 * 
 */
@Data
public class FlowQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 当前处理人 */
	private String userId;
	
	/** 当前处理人所属审批组 */
	private List<String> userGroupIds;
	
	/** 流程名称 */
	private String flowName;
	
	/** 流程分类 */
	private String flowCategory;
	
	/** 业务模块 */
	private String bizModel;
	
	/** 流程详情 */
	private String bizDetail;
	
	/** 开始时间 */
	private Date startTime;
	
	/** 结束时间 */
	private Date endTime;
	
	/** 超级管理员 */
	private int isAdmin = 0;
	
	
	
	
}

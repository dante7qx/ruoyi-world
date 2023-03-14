package com.risun.flowable.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 流程记录对象 sys_flow_record
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public class SysFlowRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** Id */
	private Long recordId;

	/** 业务UID */
	@Excel(name = "业务UID")
	private String bizUid;

	/** 处理用户Id */
	private Long userId;
	@Excel(name = "处理人")
	private String userName;
	@Excel(name = "所在部门")
	private String userDept;

	/** 处理组Id */
	private Long groupId;
	private String groupName;

	/** 开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	/** 结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	/** 处理任务Id */
	private String taskId;

	/** 处理任务定义Id */
	private String taskDefId;

	/** 处理任务定义名称 */
	@Excel(name = "处理任务")
	private String taskDefName;

	/** 处理任务定义Key */
	private String taskDefDesc;

	/** 审批结果（1 通过 0 驳回） */
	private Boolean flowResult;

	/** 审批意见 */
	@Excel(name = "审批意见")
	private String comment;

	/** 审批附件 */
	@Excel(name = "审批附件")
	private String attachment;

	private String flowResultDisplay;

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setBizUid(String bizUid) {
		this.bizUid = bizUid;
	}

	public String getBizUid() {
		return bizUid;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefName(String taskDefName) {
		this.taskDefName = taskDefName;
	}

	public String getTaskDefName() {
		return taskDefName;
	}

	public void setTaskDefDesc(String taskDefDesc) {
		this.taskDefDesc = taskDefDesc;
	}

	public String getTaskDefDesc() {
		return taskDefDesc;
	}

	public void setFlowResult(Boolean flowResult) {
		this.flowResult = flowResult;
	}

	public Boolean getFlowResult() {
		return flowResult;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachment() {
		return attachment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDept() {
		return userDept;
	}

	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setFlowResultDisplay(String flowResultDisplay) {
		this.flowResultDisplay = flowResultDisplay;
	}

	public String getFlowResultDisplay() {
		this.flowResultDisplay = this.flowResult == null || this.flowResult ? "通过" : "驳回";
		return flowResultDisplay;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("recordId", getRecordId())
			.append("bizUid", getBizUid())
			.append("userId", getUserId())
			.append("startTime", getStartTime())
			.append("endTime", getEndTime())
			.append("taskId", getTaskId())
			.append("taskDefId", getTaskDefId())
			.append("taskDefName", getTaskDefName())
			.append("taskDefDesc", getTaskDefDesc())
			.append("flowResult", getFlowResult())
			.append("comment", getComment())
			.append("attachment", getAttachment())
			.toString();
	}
}

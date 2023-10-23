package com.spirit.flowable.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 流程跟踪对象 sys_flow_trace
 * 
 * @author sunchao
 * @date 2023-03-03
 */
public class SysFlowTrace extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** Id */
	private Long traceId;

	/** 流程序号 */
	@Excel(name = "流程序号")
	private Long flowNum;

	/** 流程日期序号 */
	@Excel(name = "流程日期序号")
	private Long flowDateNum;

	/** 流程类型 */
	@Excel(name = "流程类型")
	private String flowType;
	private String flowTypeLabel;

	/** 业务UID */
	@Excel(name = "业务UID")
	private String bizUid;

	/** 业务UID */
	@Excel(name = "业务UID")
	private String bizDesc;

	/** 提交人Id */
	@Excel(name = "提交人Id")
	private Long commitUserId;

	/** 提交时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date commitTime;

	/** 流程实例Id */
	private String procInstId;

	/** 流程定义Id */
	private String procDefId;
	
	/** 流程定义Key */
	private String procDefKey;

	/** 当前任务Id */
	private String taskId;

	/** 当前任务定义Id */
	private String taskDefId;

	/** 当前任务定义名称 */
	@Excel(name = "当前任务定义名称")
	private String taskDefName;

	/** 当前任务定义Key */
	private String taskDefDesc;

	/** 状态（1 待提交 2 审批中 3 完成） */
	@Excel(name = "状态", readConverterExp = "1,待提交,2,审批中,3,完成")
	private Integer flowStatus;

	/** 审批结果（1 通过 0 驳回） */
	@Excel(name = "审批结果", readConverterExp = "1,通过,0=,驳回")
	private Boolean flowResult;

	/** 第一个任务（1 是 0 否） */
	@Excel(name = "第一个任务", readConverterExp = "1,是,0,否")
	private Boolean firstTask;
	
	/** 多选 */
	private Boolean multi = Boolean.FALSE;
	
	/** 审批候选人 */
	private Long[] approvalUserId;
	
	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}

	public Long getTraceId() {
		return traceId;
	}

	public void setFlowNum(Long flowNum) {
		this.flowNum = flowNum;
	}

	public Long getFlowNum() {
		return flowNum;
	}

	public void setFlowDateNum(Long flowDateNum) {
		this.flowDateNum = flowDateNum;
	}

	public Long getFlowDateNum() {
		return flowDateNum;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getFlowType() {
		return flowType;
	}

	public String getFlowTypeLabel() {
		return flowTypeLabel;
	}

	public void setFlowTypeLabel(String flowTypeLabel) {
		this.flowTypeLabel = flowTypeLabel;
	}

	public void setBizUid(String bizUid) {
		this.bizUid = bizUid;
	}

	public String getBizUid() {
		return bizUid;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public String getBizDesc() {
		return bizDesc;
	}

	public void setCommitUserId(Long commitUserId) {
		this.commitUserId = commitUserId;
	}

	public Long getCommitUserId() {
		return commitUserId;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
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

	public void setFlowStatus(Integer flowStatus) {
		this.flowStatus = flowStatus;
	}

	public Integer getFlowStatus() {
		return flowStatus;
	}

	public void setFlowResult(Boolean flowResult) {
		this.flowResult = flowResult;
	}

	public Boolean getFlowResult() {
		return flowResult;
	}

	public void setFirstTask(Boolean firstTask) {
		this.firstTask = firstTask;
	}

	public Boolean getFirstTask() {
		return firstTask;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	public Long[] getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(Long[] approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("traceId", getTraceId())
			.append("flowNum", getFlowNum())
			.append("flowDateNum", getFlowDateNum())
			.append("flowType", getFlowType())
			.append("bizUid", getBizUid())
			.append("bizDesc", getBizDesc())
			.append("commitUserId", getCommitUserId())
			.append("commitTime", getCommitTime())
			.append("procInstId", getProcInstId())
			.append("procDefId", getProcDefId())
			.append("taskId", getTaskId())
			.append("taskDefId", getTaskDefId())
			.append("taskDefName", getTaskDefName())
			.append("taskDefDesc", getTaskDefDesc())
			.append("flowStatus", getFlowStatus())
			.append("flowResult", getFlowResult())
			.append("firstTask", getFirstTask())
			.toString();
	}
}

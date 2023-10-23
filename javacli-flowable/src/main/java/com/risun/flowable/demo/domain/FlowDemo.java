package com.spirit.flowable.demo.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;
import com.spirit.flowable.domain.SysFlowTrace;

import cn.hutool.core.date.DateUtil;

/**
 * 业务流程示例对象 t_flow_demo
 * 
 * @author sunchao
 * @date 2023-03-02
 */
public class FlowDemo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 业务主键ID */
	private Long demoId;

	/** 申请人Id */
	@Excel(name = "申请人Id")
	private Long userId;

	@Excel(name = "申请人")
	private String userName;

	/** 业务UID */
	@Excel(name = "业务UID")
	private String uid;

	/** 开始时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date startTime;

	/** 结束时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date endTime;

	/** 申请原因 */
	@Excel(name = "申请原因")
	private String applReason;

	/** 补充材料 */
	@Excel(name = "补充材料")
	private String applAttachment;

	/** 请假天数 */
	private long days;

	/** 流程跟踪 */
	private SysFlowTrace trace;

	public void setDemoId(Long demoId) {
		this.demoId = demoId;
	}

	public Long getDemoId() {
		return demoId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
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

	public void setApplReason(String applReason) {
		this.applReason = applReason;
	}

	public String getApplReason() {
		return applReason;
	}

	public void setApplAttachment(String applAttachment) {
		this.applAttachment = applAttachment;
	}

	public String getApplAttachment() {
		return applAttachment;
	}

	public SysFlowTrace getTrace() {
		return trace;
	}

	public void setTrace(SysFlowTrace trace) {
		this.trace = trace;
	}

	public long getDays() {
		if (this.startTime != null && this.endTime != null) {
			this.days = DateUtil.betweenDay(this.startTime, this.endTime, true);
		}
		return this.days;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("demoId", getDemoId())
			.append("userId", getUserId())
			.append("uid", getUid())
			.append("startTime", getStartTime())
			.append("endTime", getEndTime())
			.append("applReason", getApplReason())
			.append("applAttachment", getApplAttachment())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}

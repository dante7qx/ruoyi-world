package com.risun.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 邮件日志对象 sys_email_log
 * 
 * @author sunchao
 * @date 2022-08-16
 */
public class SysEmailLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 邮件id */
	private Long emailId;

	/** 接受人 */
	@Excel(name = "接受人")
	private String sendTo;

	/** 抄送人 */
	@Excel(name = "抄送人")
	private String sendCc;

	/** 密送人 */
	@Excel(name = "密送人")
	private String sendBcc;

	/** 邮件主题 */
	@Excel(name = "邮件主题")
	private String subject;

	/** 邮件内容 */
	@Excel(name = "邮件内容")
	private String content;

	/** 发送时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate;

	/** 发送日志 */
	private String sendLog;
	
	/** 状态 */
	@Excel(name = "状态", readConverterExp = "0=成功,1=失败")
	private String status;

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}

	public Long getEmailId() {
		return emailId;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendCc(String sendCc) {
		this.sendCc = sendCc;
	}

	public String getSendCc() {
		return sendCc;
	}

	public void setSendBcc(String sendBcc) {
		this.sendBcc = sendBcc;
	}

	public String getSendBcc() {
		return sendBcc;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public String getSendLog() {
		return sendLog;
	}

	public void setSendLog(String sendLog) {
		this.sendLog = sendLog;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("emailId", getEmailId())
				.append("sendTo", getSendTo()).append("sendCc", getSendCc()).append("sendBcc", getSendBcc())
				.append("subject", getSubject()).append("content", getContent()).append("sendDate", getSendDate())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).toString();
	}
}

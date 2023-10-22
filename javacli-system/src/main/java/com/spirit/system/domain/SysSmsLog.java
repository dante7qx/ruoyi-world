package com.spirit.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 短信日志对象 sys_sms_log
 * 
 * @author sunchao
 * @date 2022-08-17
 */
public class SysSmsLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 短信id */
	private Long smsId;

	/** 接收人 */
	@Excel(name = "接收人")
	private String sendTo;

	/** 短信内容 */
	@Excel(name = "短信内容")
	private String content;

	/** 发送时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date sendDate;

	/** 发送日志 */
	@Excel(name = "发送日志")
	private String sendLog;

	/** 状态 */
	@Excel(name = "状态", readConverterExp = "0=成功,1=失败")
	private String status;

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public Long getSmsId() {
		return smsId;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSendTo() {
		return sendTo;
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

	public void setSendLog(String sendLog) {
		this.sendLog = sendLog;
	}

	public String getSendLog() {
		return sendLog;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("smsId", getSmsId())
				.append("sendTo", getSendTo()).append("content", getContent()).append("sendDate", getSendDate())
				.append("sendLog", getSendLog()).append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).toString();
	}
}

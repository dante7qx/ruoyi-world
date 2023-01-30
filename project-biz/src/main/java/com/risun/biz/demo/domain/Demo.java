package com.risun.biz.demo.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.annotation.DesensitizeField;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;
import com.risun.common.enums.DesensitizeType;

/**
 * 业务对象 t_demo
 * 
 * @author sunchao
 * @date 2022-07-30
 */
public class Demo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 业务主键ID */
	private Long demoId;

	/** 业务名称 */
	@Excel(name = "业务名称")
	@DesensitizeField(type = DesensitizeType.ID_CARD)
	private String demoName;

	/** 业务时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "业务时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date demoTime;

	/** 业务图片 */
	@Excel(name = "业务图片")
	private String demoImage;

	/** 业务附件 */
	@Excel(name = "业务附件")
	private String attachment;

	/** 业务内容 */
	@Excel(name = "业务内容")
	private String demoContent;
	
	public void setDemoId(Long demoId) {
		this.demoId = demoId;
	}

	public Long getDemoId() {
		return demoId;
	}

	public void setDemoName(String demoName) {
		this.demoName = demoName;
	}

	public String getDemoName() {
		return demoName;
	}

	public void setDemoTime(Date demoTime) {
		this.demoTime = demoTime;
	}

	public Date getDemoTime() {
		return demoTime;
	}

	public void setDemoImage(String demoImage) {
		this.demoImage = demoImage;
	}

	public String getDemoImage() {
		return demoImage;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachment() {
		return attachment;
	}

	public String getDemoContent() {
		return demoContent;
	}

	public void setDemoContent(String demoContent) {
		this.demoContent = demoContent;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("demoId", getDemoId())
				.append("demoName", getDemoName()).append("demoTime", getDemoTime()).append("demoImage", getDemoImage())
				.append("attachment", getAttachment()).append("createBy", getCreateBy())
				.append("createTime", getCreateTime()).append("updateBy", getUpdateBy())
				.append("updateTime", getUpdateTime()).toString();
	}
}

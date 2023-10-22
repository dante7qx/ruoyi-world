package com.spirit.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 信息发布浏览记录对象 sys_info_view
 * 
 * @author sunchao
 * @date 2023-08-31
 */
public class SysInfoView extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 访问id */
	private Long viewId;

	/** 信息发布id */
	@Excel(name = "信息发布id")
	private Long infoId;

	/** 访问日期 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "访问日期", width = 30, dateFormat = "yyyy-MM-dd")
	private Date viewDate;

	/** 访问IP */
	@Excel(name = "访问IP")
	private String viewIp;

	public void setViewId(Long viewId) {
		this.viewId = viewId;
	}

	public Long getViewId() {
		return viewId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewIp(String viewIp) {
		this.viewIp = viewIp;
	}

	public String getViewIp() {
		return viewIp;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("viewId", getViewId())
			.append("infoId", getInfoId())
			.append("viewDate", getViewDate())
			.append("viewIp", getViewIp())
			.toString();
	}
}
package com.spirit.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 视频监控对象 sys_camera_monitor
 * 
 * @author sunchao
 * @date 2023-09-25
 */
public class SysCameraMonitor extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 监控id */
	private Long monitorId;

	/** 父id */
	private Long parentId;

	/** 祖级列表 */
	private String ancestors;

	/** 监控名称 */
	@Excel(name = "监控名称")
	private String monitorName;
	
	/** 显示顺讯 */
	private Integer orderNum;

	/** RTSP地址 */
	@Excel(name = "RTSP地址")
	private String rtspUrl;

	/** 播放地址 */
	@Excel(name = "播放地址")
	private String playUri;

	public void setMonitorId(Long monitorId) {
		this.monitorId = monitorId;
	}

	public Long getMonitorId() {
		return monitorId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	public String getAncestors() {
		return ancestors;
	}

	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}

	public String getMonitorName() {
		return monitorName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setRtspUrl(String rtspUrl) {
		this.rtspUrl = rtspUrl;
	}

	public String getRtspUrl() {
		return rtspUrl;
	}

	public void setPlayUri(String playUri) {
		this.playUri = playUri;
	}

	public String getPlayUri() {
		return playUri;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("monitorId", getMonitorId())
			.append("parentId", getParentId())
			.append("ancestors", getAncestors())
			.append("monitorName", getMonitorName())
			.append("orderNum", getOrderNum())
			.append("rtspUrl", getRtspUrl())
			.append("playUri", getPlayUri())
			.append("remark", getRemark())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}

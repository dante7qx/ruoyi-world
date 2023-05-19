package com.risun.report.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.core.domain.BaseEntity;

/**
 * 报表预览对象 jimu_report_share
 * 
 * @author sunchao
 * @date 2023-05-19
 */
public class RiJmReportShare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 在线excel设计器id */
    private String reportId;
    
    /** 在线excel设计器名称 */
    private String reportName;

    /** 预览地址 */
    private String previewUrl;

    /** 密码锁 */
    private String previewLock;

    /** 是否锁定(0否，1是) */
    private String previewLockStatus;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /** 有效期(0:永久有效，1:1天，2:7天) */
    private String termOfValidity;

    /** 是否过期(0未过期，1已过期) */
    private String status;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setReportId(String reportId) 
    {
        this.reportId = reportId;
    }

    public String getReportId() 
    {
        return reportId;
    }
    
    public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setPreviewUrl(String previewUrl) 
    {
        this.previewUrl = previewUrl;
    }

    public String getPreviewUrl() 
    {
        return previewUrl;
    }
    public void setPreviewLock(String previewLock) 
    {
        this.previewLock = previewLock;
    }

    public String getPreviewLock() 
    {
        return previewLock;
    }
    public void setPreviewLockStatus(String previewLockStatus) 
    {
        this.previewLockStatus = previewLockStatus;
    }

    public String getPreviewLockStatus() 
    {
        return previewLockStatus;
    }
    public void setLastUpdateTime(Date lastUpdateTime) 
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() 
    {
        return lastUpdateTime;
    }
    public void setTermOfValidity(String termOfValidity) 
    {
        this.termOfValidity = termOfValidity;
    }

    public String getTermOfValidity() 
    {
        return termOfValidity;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reportId", getReportId())
            .append("previewUrl", getPreviewUrl())
            .append("previewLock", getPreviewLock())
            .append("previewLockStatus", getPreviewLockStatus())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("termOfValidity", getTermOfValidity())
            .append("status", getStatus())
            .toString();
    }
}

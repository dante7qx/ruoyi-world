package com.risun.flowable.domain;

import com.risun.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 业务流程监控对象 sys_flow_biz_monitor
 * 
 * @author sunchao
 * @date 2022-11-11
 */
public class SysFlowBizMonitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long monitorId;

    /** 业务UID */
    private String bizUid;

    /** 流程实例Id */
    private String procInstId;

    /** 当前状态 */
    private String status;

    /** 审核通过（0: 驳回 1: 通过） */
    private Boolean passed;

    /** 流程结束（0: 未结束 1: 结束） */
    private Boolean finished = Boolean.FALSE;
    
    /** 待提交申请 */
    private Boolean commited = Boolean.FALSE; 

    public void setMonitorId(Long monitorId) 
    {
        this.monitorId = monitorId;
    }

    public Long getMonitorId() 
    {
        return monitorId;
    }
    public void setBizUid(String bizUid) 
    {
        this.bizUid = bizUid;
    }

    public String getBizUid() 
    {
        return bizUid;
    }
    public void setProcInstId(String procInstId) 
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId() 
    {
        return procInstId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setPassed(Boolean passed) 
    {
        this.passed = passed;
    }

    public Boolean getPassed() 
    {
        return passed;
    }
    public void setFinished(Boolean finished) 
    {
        this.finished = finished;
    }

    public Boolean getFinished() 
    {
        return finished;
    }

    public Boolean getCommited() {
		return commited;
	}

	public void setCommited(Boolean commited) {
		this.commited = commited;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("monitorId", getMonitorId())
            .append("bizUid", getBizUid())
            .append("procInstId", getProcInstId())
            .append("status", getStatus())
            .append("passed", getPassed())
            .append("finished", getFinished())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

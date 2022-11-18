package com.risun.flowable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 业务流程示例对象 t_flow_demo
 * 
 * @author sunchao
 * @date 2022-11-11
 */
public class FlowDemo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 业务主键ID */
    private Long demoId;

    /** 请假申请人Id */
    @Excel(name = "请假申请人Id")
    private Long leaveUserId;

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

    /** 请假原因 */
    @Excel(name = "请假原因")
    private String leaveReason;
    
    /** 流程相关 */
    private SysFlowBizMonitor flowMonitor;
    
    public void setDemoId(Long demoId) 
    {
        this.demoId = demoId;
    }

    public Long getDemoId() 
    {
        return demoId;
    }
    public void setLeaveUserId(Long leaveUserId) 
    {
        this.leaveUserId = leaveUserId;
    }

    public Long getLeaveUserId() 
    {
        return leaveUserId;
    }
    public void setUid(String uid) 
    {
        this.uid = uid;
    }

    public String getUid() 
    {
        return uid;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setLeaveReason(String leaveReason) 
    {
        this.leaveReason = leaveReason;
    }

    public String getLeaveReason() 
    {
        return leaveReason;
    }

	public SysFlowBizMonitor getFlowMonitor() {
		return flowMonitor;
	}

	public void setFlowMonitor(SysFlowBizMonitor flowMonitor) {
		this.flowMonitor = flowMonitor;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("demoId", getDemoId())
            .append("leaveUserId", getLeaveUserId())
            .append("uid", getUid())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("leaveReason", getLeaveReason())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

package com.risun.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 审批日志对象 sys_approval_log
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public class SysApprovalLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志id */
    private Long logId;

    /** 业务模块 */
    @Excel(name = "业务模块")
    private String bizModel;

    /** 业务id */
    @Excel(name = "业务id")
    private Long bizId;

    /** 审批意见 */
    @Excel(name = "审批意见")
    private String comment;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String operateType;

    /** 操作人 */
    @Excel(name = "操作人")
    private String operator;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }
    public void setBizModel(String bizModel) 
    {
        this.bizModel = bizModel;
    }

    public String getBizModel() 
    {
        return bizModel;
    }
    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setOperateType(String operateType) 
    {
        this.operateType = operateType;
    }

    public String getOperateType() 
    {
        return operateType;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setOperateTime(Date operateTime) 
    {
        this.operateTime = operateTime;
    }

    public Date getOperateTime() 
    {
        return operateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("bizModel", getBizModel())
            .append("bizId", getBizId())
            .append("comment", getComment())
            .append("operateType", getOperateType())
            .append("operator", getOperator())
            .append("operateTime", getOperateTime())
            .toString();
    }
}

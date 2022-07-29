package com.risun.biz.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 业务管理对象 t_biz
 * 
 * @author sunchao
 * @date 2022-07-29
 */
public class Biz extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 业务主键ID */
    private Long bizId;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private String bizName;

    /** 业务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "业务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bizTime;

    /** 业务附件 */
    @Excel(name = "业务附件")
    private String attachment;

    public void setBizId(Long bizId) 
    {
        this.bizId = bizId;
    }

    public Long getBizId() 
    {
        return bizId;
    }
    public void setBizName(String bizName) 
    {
        this.bizName = bizName;
    }

    public String getBizName() 
    {
        return bizName;
    }
    public void setBizTime(Date bizTime) 
    {
        this.bizTime = bizTime;
    }

    public Date getBizTime() 
    {
        return bizTime;
    }
    public void setAttachment(String attachment) 
    {
        this.attachment = attachment;
    }

    public String getAttachment() 
    {
        return attachment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bizId", getBizId())
            .append("bizName", getBizName())
            .append("bizTime", getBizTime())
            .append("attachment", getAttachment())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

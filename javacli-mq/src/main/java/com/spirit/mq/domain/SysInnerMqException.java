package com.spirit.mq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 内部消息队列异常消息对象 sys_inner_mq_exception
 * 
 * @author sunchao
 * @date 2023-02-06
 */
public class SysInnerMqException extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 业务模块 */
    @Excel(name = "消费处理类")
    private String consumerClass;

    /** 业务消息 */
    @Excel(name = "业务消息")
    private String bizMsg;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConsumerClass(String consumerClass) 
    {
        this.consumerClass = consumerClass;
    }

    public String getConsumerClass() 
    {
        return consumerClass;
    }
    public void setBizMsg(String bizMsg) 
    {
        this.bizMsg = bizMsg;
    }

    public String getBizMsg() 
    {
        return bizMsg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("consumerClass", getConsumerClass())
            .append("bizMsg", getBizMsg())
            .append("createTime", getCreateTime())
            .toString();
    }
}

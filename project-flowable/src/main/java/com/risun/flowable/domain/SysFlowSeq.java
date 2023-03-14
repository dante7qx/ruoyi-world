package com.risun.flowable.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.core.domain.BaseEntity;

/**
 * 流程序号对象 sys_flow_seq
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public class SysFlowSeq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** 自然序号 */
    private Long seqNum;

    /** 日期序号 */
    private Long dateNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSeqNum(Long seqNum) 
    {
        this.seqNum = seqNum;
    }

    public Long getSeqNum() 
    {
        return seqNum;
    }
    public void setDateNum(Long dateNum) 
    {
        this.dateNum = dateNum;
    }

    public Long getDateNum() 
    {
        return dateNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("seqNum", getSeqNum())
            .append("dateNum", getDateNum())
            .toString();
    }
}

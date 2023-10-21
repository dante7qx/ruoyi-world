package com.risun.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 信息访问范围对象 sys_info_range
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public class SysInfoRange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 访问范围id */
    private Long rangeId;

    /** 信息发布id */
    @Excel(name = "信息发布id")
    private Long infoId;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    public void setRangeId(Long rangeId) 
    {
        this.rangeId = rangeId;
    }

    public Long getRangeId() 
    {
        return rangeId;
    }
    public void setInfoId(Long infoId) 
    {
        this.infoId = infoId;
    }

    public Long getInfoId() 
    {
        return infoId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("rangeId", getRangeId())
            .append("infoId", getInfoId())
            .append("deptId", getDeptId())
            .toString();
    }
}

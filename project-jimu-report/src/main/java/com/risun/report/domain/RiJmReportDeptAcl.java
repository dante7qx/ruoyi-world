package com.risun.report.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 积木报部门权限对象 jimu_report_dept_acl
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public class RiJmReportDeptAcl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 在线excel设计器id */
    @Excel(name = "在线excel设计器id")
    private String reportId;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
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
            .append("id", getId())
            .append("reportId", getReportId())
            .append("deptId", getDeptId())
            .toString();
    }
}

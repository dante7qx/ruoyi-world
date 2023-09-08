package com.risun.biz.demo.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 全部业务功能对象 t_demo_all
 * 
 * @author sunchao
 * @date 2023-09-08
 */
public class DemoAll extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 业务主键ID */
    private Long demoId;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private String demoName;

    /** 部门ID */
    private Long deptId;
    
    /** 所属部门 */
    @Excel(name = "所属部门")
    private String deptName;

    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public Long getDemoId() {
        return demoId;
    }
    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDemoName() {
        return demoName;
    }
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("demoId", getDemoId())
            .append("demoName", getDemoName())
            .append("deptName", getDeptName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

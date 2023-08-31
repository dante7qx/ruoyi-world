package com.risun.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 信息栏目属性对象 sys_info_category_prop
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public class SysInfoCategoryProp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 属性id */
    private Long propId;

    /** 栏目id */
    private Long categoryId;
    
    /** 栏目 */
    @Excel(name = "栏目")
    private String categoryName;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String propName;

    /** 属性类型 */
    @Excel(name = "属性类型")
    private String propType;

    /** 属性字典类型值 */
    @Excel(name = "属性字典类型值")
    private String propTypeVal;
    
    /** 信息发布Id 查询*/
    private Long infoId;

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getPropId() {
        return propId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropName() {
        return propName;
    }
    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getPropType() {
        return propType;
    }
    public void setPropTypeVal(String propTypeVal) {
        this.propTypeVal = propTypeVal;
    }

    public String getPropTypeVal() {
        return propTypeVal;
    }
    
    public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("propId", getPropId())
            .append("categoryId", getCategoryId())
            .append("propName", getPropName())
            .append("propType", getPropType())
            .append("propTypeVal", getPropTypeVal())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

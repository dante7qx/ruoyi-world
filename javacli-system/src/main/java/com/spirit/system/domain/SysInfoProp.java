package com.spirit.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 信息属性对象 sys_info_prop
 * 
 * @author sunchao
 * @date 2023-08-30
 */
public class SysInfoProp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 信息属性id */
    private Long propId;

    /** 信息发布id */
    @Excel(name = "信息发布id")
    private Long infoId;

    /** 栏目属性id */
    private Long categoryPropId;
    
    /** 属性名 */
    @Excel(name = "属性名")
    private String propName;
    
    /** 属性类型 */
    private String propType;

    /** 属性值 */
    @Excel(name = "属性值")
    private String propVal;
    
    /** 属性字典标签 */
    private String propDictLabel;

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getPropId() {
        return propId;
    }
    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getInfoId() {
        return infoId;
    }
    public void setCategoryPropId(Long categoryPropId) {
        this.categoryPropId = categoryPropId;
    }

    public Long getCategoryPropId() {
        return categoryPropId;
    }
    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getPropName() {
        return propName;
    }
    
    public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

	public void setPropVal(String propVal) {
        this.propVal = propVal;
    }

    public String getPropVal() {
        return propVal;
    }

    public String getPropDictLabel() {
		return propDictLabel;
	}

	public void setPropDictLabel(String propDictLabel) {
		this.propDictLabel = propDictLabel;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("propId", getPropId())
            .append("infoId", getInfoId())
            .append("categoryPropId", getCategoryPropId())
            .append("propVal", getPropVal())
            .append("remark", getRemark())
            .toString();
    }
}

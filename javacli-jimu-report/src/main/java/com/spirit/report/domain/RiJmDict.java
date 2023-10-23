package com.spirit.report.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;
import com.spirit.common.core.domain.entity.SysDictType;

/**
 * 报表字典对象 jimu_dict
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public class RiJmDict extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String id;

    /** 字典名称 */
    @Excel(name = "字典名称")
    private String dictName;

    /** 字典编码 */
    @Excel(name = "字典编码")
    private String dictCode;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 删除状态 */
    private Integer delFlag;

    /** 字典类型0为string,1为number */
    @Excel(name = "字典类型0为string,1为number")
    private Integer type;

    /** 多租户标识 */
    @Excel(name = "多租户标识")
    private String tenantId;
    
    /**
     * 系统数据字典
     */
    private List<SysDictType> sysDicts;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDictName(String dictName) 
    {
        this.dictName = dictName;
    }

    public String getDictName() 
    {
        return dictName;
    }
    public void setDictCode(String dictCode) 
    {
        this.dictCode = dictCode;
    }

    public String getDictCode() 
    {
        return dictCode;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setTenantId(String tenantId) 
    {
        this.tenantId = tenantId;
    }

    public String getTenantId() 
    {
        return tenantId;
    }
    
    public List<SysDictType> getSysDicts() {
		return sysDicts;
	}

	public void setSysDicts(List<SysDictType> sysDicts) {
		this.sysDicts = sysDicts;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dictName", getDictName())
            .append("dictCode", getDictCode())
            .append("description", getDescription())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("type", getType())
            .append("tenantId", getTenantId())
            .toString();
    }
    
}

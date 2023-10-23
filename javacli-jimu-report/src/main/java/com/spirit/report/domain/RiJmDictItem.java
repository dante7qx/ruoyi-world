package com.spirit.report.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * dict对象 jimu_dict_item
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public class RiJmDictItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String id;

    /** 字典id */
    @Excel(name = "字典id")
    private String dictId;

    /** 字典项文本 */
    @Excel(name = "字典项文本")
    private String itemText;

    /** 字典项值 */
    @Excel(name = "字典项值")
    private String itemValue;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sortOrder;

    /** 状态（1启用 0不启用） */
    @Excel(name = "状态", readConverterExp = "1=启用,0=不启用")
    private Boolean status;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDictId(String dictId) 
    {
        this.dictId = dictId;
    }

    public String getDictId() 
    {
        return dictId;
    }
    public void setItemText(String itemText) 
    {
        this.itemText = itemText;
    }

    public String getItemText() 
    {
        return itemText;
    }
    public void setItemValue(String itemValue) 
    {
        this.itemValue = itemValue;
    }

    public String getItemValue() 
    {
        return itemValue;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setSortOrder(Integer sortOrder) 
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() 
    {
        return sortOrder;
    }
    public void setStatus(Boolean status) 
    {
        this.status = status;
    }

    public Boolean getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dictId", getDictId())
            .append("itemText", getItemText())
            .append("itemValue", getItemValue())
            .append("description", getDescription())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

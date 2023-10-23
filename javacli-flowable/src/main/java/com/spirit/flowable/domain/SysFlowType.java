package com.spirit.flowable.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 流程类型对象 sys_flow_type
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public class SysFlowType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型id */
    private Long typeId;

    /** 类型 */
    @Excel(name = "类型")
    private String typeName;

    /** 流程组id */
    private Long flowGroupId;
    @Excel(name = "流程组")
    private String flowGroupName;

    /** 流程定义Key */
    @Excel(name = "流程定义Key")
    private String flowDefKey;
    
    /** 权重 */
    @Excel(name = "权重")
    private Integer orderNum;

    /** 流程分类 */
    private String flowCategory;
    
    /** 流程定义名称 */
    private String flowDefName;
    
    /** 流程定义Id */
    private String flowDefId;
    
    /** 流程部署Id */
    private String flowDeployId;
    
    /** 登录用户Id */
    private Long loginUserId;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setFlowCategory(String flowCategory) 
    {
        this.flowCategory = flowCategory;
    }

    public String getFlowCategory() 
    {
        return flowCategory;
    }
    public void setFlowGroupId(Long flowGroupId) 
    {
        this.flowGroupId = flowGroupId;
    }

    public Long getFlowGroupId() 
    {
        return flowGroupId;
    }
    
    public String getFlowGroupName() {
		return flowGroupName;
	}

	public void setFlowGroupName(String flowGroupName) {
		this.flowGroupName = flowGroupName;
	}

	public void setFlowDefKey(String flowDefKey) 
    {
        this.flowDefKey = flowDefKey;
    }

    public String getFlowDefKey() 
    {
        return flowDefKey;
    }
    
    public String getFlowDefName() {
		return flowDefName;
	}

	public void setFlowDefName(String flowDefName) {
		this.flowDefName = flowDefName;
	}

	public String getFlowDefId() {
		return flowDefId;
	}

	public void setFlowDefId(String flowDefId) {
		this.flowDefId = flowDefId;
	}

	public void setFlowDeployId(String flowDeployId) 
    {
        this.flowDeployId = flowDeployId;
    }

    public String getFlowDeployId() 
    {
        return flowDeployId;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }

    public Long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId) {
		this.loginUserId = loginUserId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("flowCategory", getFlowCategory())
            .append("flowGroupId", getFlowGroupId())
            .append("flowDefKey", getFlowDefKey())
            .append("flowDeployId", getFlowDeployId())
            .append("orderNum", getOrderNum())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

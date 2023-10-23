package com.spirit.flowable.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.spirit.common.core.domain.BaseEntity;

/**
 * 流程组对象 sys_flow_group
 * 
 * @author sunchao
 * @date 2022-11-09
 */
public class SysFlowGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组id */
    private Long groupId;

    /** 组名称 */
    private String groupName;
    
    /** 组Key */
    private String groupKey;
    
    /** 分配用户 */
    private Long[] userIds;
    
    /** 已分配用户 */
    private List<SysFlowGroup> children;
    
    /** 用户标识 */
    private Boolean groupUser = Boolean.FALSE;
    
    private String rowKey;
    
    public SysFlowGroup() {
	}
    
    public SysFlowGroup(Long groupId, String groupName, String remark, String createBy, Date createTime, Boolean groupUser) {
    	this.groupId = groupId;
    	this.groupName = groupName;
    	this.setRemark(remark);
    	this.setCreateBy(createBy);
    	this.setCreateTime(createTime);
    	this.groupUser = groupUser;
	}
    
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }

    public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public Long[] getUserIds() {
		return userIds;
	}

	public void setUserIds(Long[] userIds) {
		this.userIds = userIds;
	}

	public List<SysFlowGroup> getChildren() {
		return children;
	}

	public void setChildren(List<SysFlowGroup> children) {
		this.children = children;
	}

	public Boolean getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(Boolean groupUser) {
		this.groupUser = groupUser;
	}
	
	public String getRowKey() {
		this.rowKey = groupUser.booleanValue() ? "u_" + groupId : "g_" + groupId;
		return rowKey;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("groupName", getGroupName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

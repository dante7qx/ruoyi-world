package com.risun.flowable.domain;

import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 流程任务转办对象 sys_flow_assign
 * 
 * @author sunchao
 * @date 2022-11-17
 */
public class SysFlowAssign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long flowAssignId;

    /** 流程实例Id */
    @Excel(name = "流程实例Id")
    private String procInstId;

    /** 流程任务Id */
    @Excel(name = "流程任务Id")
    private String taskId;

    /** 保留我的待办 */
    @Excel(name = "保留我的待办")
    private Boolean keepTodo;

    /** 转办说明 */
    @Excel(name = "转办说明")
    private String comment;

    /** 任务所有者Id */
    @Excel(name = "任务所有者Id")
    private Long ownerId;

    /** 任务所有者姓名 */
    @Excel(name = "任务所有者姓名")
    private String ownerName;

    /** 任务所有者部门名称 */
    @Excel(name = "任务所有者部门名称")
    private String ownerDept;

    /** 任务接收者Id */
    @Excel(name = "任务接收者Id")
    private Long assigneeId;

    /** 任务接收者姓名 */
    @Excel(name = "任务接收者姓名")
    private String assigneeName;

    /** 任务接收者部门名称 */
    @Excel(name = "任务接收者部门名称")
    private String assigneeDept;

    @SuppressWarnings("unused")
	private String fullFlowRecord;
    
    public void setFlowAssignId(Long flowAssignId) 
    {
        this.flowAssignId = flowAssignId;
    }

    public Long getFlowAssignId() 
    {
        return flowAssignId;
    }
    public void setProcInstId(String procInstId) 
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId() 
    {
        return procInstId;
    }
    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }
    public void setKeepTodo(Boolean keepTodo) 
    {
        this.keepTodo = keepTodo;
    }

    public Boolean getKeepTodo() 
    {
        return keepTodo;
    }
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public String getComment() 
    {
        return comment;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }
    public void setOwnerDept(String ownerDept) 
    {
        this.ownerDept = ownerDept;
    }

    public String getOwnerDept() 
    {
        return ownerDept;
    }
    public void setAssigneeId(Long assigneeId) 
    {
        this.assigneeId = assigneeId;
    }

    public Long getAssigneeId() 
    {
        return assigneeId;
    }
    public void setAssigneeName(String assigneeName) 
    {
        this.assigneeName = assigneeName;
    }

    public String getAssigneeName() 
    {
        return assigneeName;
    }
    public void setAssigneeDept(String assigneeDept) 
    {
        this.assigneeDept = assigneeDept;
    }

    public String getAssigneeDept() 
    {
        return assigneeDept;
    }

    public String getFullFlowRecord() {
    	StringBuffer buf = new StringBuffer();
    	buf.append(ownerName)
    		.append("（")
    		.append(ownerDept)
    		.append("）交与 ")
    		.append(assigneeName)
    		.append("（")
    		.append(assigneeDept)
    		.append("）办理");
    	return buf.toString();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("flowAssignId", getFlowAssignId())
            .append("procInstId", getProcInstId())
            .append("taskId", getTaskId())
            .append("keepTodo", getKeepTodo())
            .append("comment", getComment())
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("ownerDept", getOwnerDept())
            .append("assigneeId", getAssigneeId())
            .append("assigneeName", getAssigneeName())
            .append("assigneeDept", getAssigneeDept())
            .append("createTime", getCreateTime())
            .toString();
    }
}

package com.risun.biz.demo.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.annotation.DesensitizeField;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;
import com.risun.common.enums.DesensitizeType;

/**
 * 业务对象 t_demo
 * 
 * @author sunchao
 * @date 2022-07-30
 */
public class Demo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 业务主键ID */
	private Long demoId;

	/** 业务名称 */
	@Excel(name = "业务名称")
	@DesensitizeField(type = DesensitizeType.DB)
	private String demoName;

	/** 业务时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "业务时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date demoTime;

	/** 业务图片 */
	private String demoImage;

	/** 业务附件 */
	private String attachment;

	/** 业务内容 */
	@Excel(name = "业务内容")
	private String demoContent;
	
	/** 角色ID */
    private Long roleId;
    
    /** 角色名 */
    @Excel(name = "角色名")
    private String roleName;

    /** 岗位ID */
    private Long postId;
    
    /** 岗位码 */
    @Excel(name = "岗位码")
    private String postCode;

    /** 删除标识 0 未删除 1 已删除 */
    private Boolean delFlag;
	
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

	public void setDemoTime(Date demoTime) {
		this.demoTime = demoTime;
	}

	public Date getDemoTime() {
		return demoTime;
	}

	public void setDemoImage(String demoImage) {
		this.demoImage = demoImage;
	}

	public String getDemoImage() {
		return demoImage;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachment() {
		return attachment;
	}

	public String getDemoContent() {
		return demoContent;
	}

	public void setDemoContent(String demoContent) {
		this.demoContent = demoContent;
	}

	public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }
    

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
    

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }
    

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostCode() {
        return postCode;
    }
    

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }
    

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("demoId", getDemoId())
            .append("demoName", getDemoName())
            .append("demoTime", getDemoTime())
            .append("demoImage", getDemoImage())
            .append("attachment", getAttachment())
            .append("demoContent", getDemoContent())
            .append("roleId", getRoleId())
            .append("postId", getPostId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
	
}

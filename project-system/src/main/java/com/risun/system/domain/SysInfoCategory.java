package com.risun.system.domain;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 信息栏目对象 sys_info_category
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public class SysInfoCategory extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 栏目id */
	private Long categoryId;

	/** 父栏目id */
	@Excel(name = "父栏目id")
	private Long parentId;

	/** 祖级列表 */
	@Excel(name = "祖级列表")
	private String ancestors;

	/** 栏目名称 */
	@Excel(name = "栏目名称")
	private String categoryName;

	/** 显示顺序 */
	@Excel(name = "显示顺序")
	private Long orderNum;

	/** 停用 */
	@Excel(name = "停用")
	private Boolean disabled;

	/** 子栏目 */
	private List<SysInfoCategory> children = Lists.newArrayList();

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	public String getAncestors() {
		return ancestors;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public List<SysInfoCategory> getChildren() {
		return children;
	}

	public void setChildren(List<SysInfoCategory> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("categoryId", getCategoryId())
			.append("parentId", getParentId())
			.append("ancestors", getAncestors())
			.append("categoryName", getCategoryName())
			.append("orderNum", getOrderNum())
			.append("disabled", getDisabled())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}

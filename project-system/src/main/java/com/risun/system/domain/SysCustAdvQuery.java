package com.risun.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 自定义高级查询模板对象 sys_cust_adv_query
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public class SysCustAdvQuery extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键Id */
	private Long queryId;

	/** 模板名称 */
	@Excel(name = "模板名称")
	private String templateName;

	/** 数据库表名 */
	@Excel(name = "数据库表名")
	private String tableName;

	/** 数据库表描述 */
	@Excel(name = "数据库表描述")
	private String tableDesc;

	/** 数据库表别名 */
	@Excel(name = "数据库表别名")
	private String tableAlias;

	/** 数据库表主键列名 */
	@Excel(name = "数据库表主键列名")
	private String pkCol;
	
	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setPkCol(String pkCol) {
		this.pkCol = pkCol;
	}

	public String getPkCol() {
		return pkCol;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("queryId", getQueryId())
			.append("templateName", getTemplateName())
			.append("tableName", getTableName())
			.append("tableAlias", getTableAlias())
			.append("pkCol", getPkCol())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}

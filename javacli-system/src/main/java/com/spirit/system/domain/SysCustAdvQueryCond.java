package com.spirit.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 自定义高级查询条件对象 sys_cust_adv_query_cond
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public class SysCustAdvQueryCond extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 主键Id */
	private Long condId;

	/** 模板Id */
	@Excel(name = "模板Id")
	private Long queryId;

	/** 列名 */
	@Excel(name = "列名")
	private String colName;

	/** 列描述 */
	@Excel(name = "列描述")
	private String colDesc;

	/** 列类型 */
	private String colType;

	/** Java字段名 */
	@Excel(name = "Java字段名")
	private String javaField;

	/** Java字段类型 */
	@Excel(name = "Java字段类型")
	private String javaType;

	/** 字典类型 */
	@Excel(name = "字典类型")
	private String dictType;
	
	/** 密文标识 */
	@Excel(name = "密文标识")
	private Boolean cipherFlag = Boolean.FALSE;

	/** 查询标识 */
	@Excel(name = "查询标识")
	private Boolean queryFlag = Boolean.FALSE;
	
	/** 查询方式 */
	@Excel(name = "查询方式")
	private String queryType = "=";

	/** 主键标识 */
	private Boolean pkFlag = Boolean.FALSE;

	public void setCondId(Long condId) {
		this.condId = condId;
	}

	public Long getCondId() {
		return condId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColName() {
		return colName;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public String getColDesc() {
		return colDesc;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	public String getJavaField() {
		return javaField;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return dictType;
	}

	public Boolean getCipherFlag() {
		return cipherFlag;
	}

	public void setCipherFlag(Boolean cipherFlag) {
		this.cipherFlag = cipherFlag;
	}

	public void setQueryFlag(Boolean queryFlag) {
		this.queryFlag = queryFlag;
	}

	public Boolean getQueryFlag() {
		return queryFlag;
	}

	public Boolean getPkFlag() {
		return pkFlag;
	}

	public void setPkFlag(Boolean pkFlag) {
		this.pkFlag = pkFlag;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("condId", getCondId())
			.append("queryId", getQueryId())
			.append("colName", getColName())
			.append("colDesc", getColDesc())
			.append("colType", getColType())
			.append("pkFlag", getPkFlag())
			.append("javaField", getJavaField())
			.append("javaType", getJavaType())
			.append("dictType", getDictType())
			.append("queryFlag", getQueryFlag())
			.toString();
	}
}

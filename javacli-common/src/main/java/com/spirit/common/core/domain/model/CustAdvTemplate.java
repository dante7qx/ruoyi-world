package com.spirit.common.core.domain.model;

import java.util.List;

import lombok.Data;

/**
 * 自定义查询模板
 * 
 * @author dante
 *
 */
@Data
public class CustAdvTemplate {
	/** 表名 */
	private String tableName;
	/** 别名 */
	private String tableAlias;
	/** 主键字段 */
	private String pkCol;
	
	/** 查询条件 */
	private List<CustAdvCond> conditions;
}

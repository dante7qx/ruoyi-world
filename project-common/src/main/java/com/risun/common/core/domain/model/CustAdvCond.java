package com.risun.common.core.domain.model;

import lombok.Data;

/**
 * 自定义查询模板条件
 * 
 * @author dante
 *
 */
@Data
public class CustAdvCond {
	
	private String col;
	private String queryType;
	private String javaType;
	private Object value;
	
	
}

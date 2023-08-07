package com.risun.framework.aspectj;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.risun.common.annotation.CustomizeAdvancedSearch;
import com.risun.common.constant.GenConstants;
import com.risun.common.core.domain.BaseEntity;
import com.risun.common.core.domain.model.CustAdvTemplate;
import com.risun.common.core.domain.model.CustAdvCond;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义高级查询
 * 
 * @author dante
 *
 */
@Aspect
@Component
@Slf4j
public class CustoAdvSearchAspect {

	/** 查询参数Key */
	private static final String SEARCH_PARAM_KEY = "customizeAdvancedSearch";
	/** 数据库参数Key */
	private static final String SEARCH_DB_KEY = "customizeAdvanced";

	/** 数据库参数Key */
	private static final String DB_TABLE_ALIAS = "cads";

	@Before("@annotation(customizeAdvancedSearch)")
	public void doBefore(JoinPoint point, CustomizeAdvancedSearch customizeAdvancedSearch) throws Throwable {
		handleAdvanceSearch(point, customizeAdvancedSearch);
	}

	/**
	 * 处理自定义高级查询 - 根据查询条件组装查询SQL
	 * 
	 * @param joinPoint
	 * @param customizeAdvancedSearch
	 */
	protected void handleAdvanceSearch(final JoinPoint joinPoint, CustomizeAdvancedSearch customizeAdvancedSearch) {
		log.info("AOP切面处理自定义高级查询");
		Object params = joinPoint.getArgs()[0];
		if (ObjectUtil.isNotNull(params) && params instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) params;
			// 拼接权限sql前先清空params.customizeAdvanced参数防止注入
			baseEntity.getParams().put(SEARCH_DB_KEY, "");
			String sql = assembleQuerySQL(baseEntity.getParams().get(SEARCH_PARAM_KEY));
			baseEntity.getParams().put(SEARCH_DB_KEY, sql);
		}

	}

	/**
	 * 组装查询SQL
	 * 
	 * @param searchCondition
	 * @return
	 */
	private String assembleQuerySQL(Object searchCondition) {
		StringBuilder builder = new StringBuilder();
		if (ObjectUtil.isEmpty(searchCondition)) {
			return builder.toString();
		}
		CustAdvTemplate cusAdvance = JSON.parseObject(searchCondition.toString(), CustAdvTemplate.class);
		List<CustAdvCond> conditions = cusAdvance.getConditions();
		String table = cusAdvance.getTableName();
		String tableAlias = cusAdvance.getTableAlias();
		String pkCol = cusAdvance.getPkCol();

		if (StrUtil.isEmpty(table) || StrUtil.isEmpty(tableAlias) || StrUtil.isEmpty(pkCol)
				|| CollUtil.isEmpty(conditions)) {
			return builder.toString();
		}

		builder.append(" AND exists (select 1 from ")
			.append(table)
			.append(" ")
			.append(DB_TABLE_ALIAS)
			.append(" where ")
			.append(DB_TABLE_ALIAS)
			.append(".")
			.append(pkCol)
			.append(" = ")
			.append(tableAlias)
			.append(".")
			.append(pkCol);

		// 各个查询条件组装
		for (CustAdvCond condition : conditions) {
			String col = condition.getCol();
			String javaType = condition.getJavaType();
			String queryType = condition.getQueryType();
			Object value = condition.getValue();
			if (ObjectUtil.isEmpty(value)) {
				continue;
			}
			if (GenConstants.TYPE_STRING.equalsIgnoreCase(javaType)) {
				assembleStringTypeSQL(builder, col, javaType, queryType, value);
			} else if (GenConstants.TYPE_INTEGER.equalsIgnoreCase(javaType)) {
				assembleNumberTypeSQL(builder, col, javaType, queryType, value);
			} else if (GenConstants.TYPE_DATE.equalsIgnoreCase(javaType)) {
				assembleDateTypeSQL(builder, col, javaType, queryType, value);
			}
		}
		builder.append(")");
		return builder.toString();
	}

	/**
	 * 组装字符类型查询SQL
	 * 
	 * @param builder
	 * @param col
	 * @param javaType
	 * @param operType
	 * @param value
	 */
	private void assembleStringTypeSQL(StringBuilder builder, String col, String javaType, String queryType,
			Object value) {
		if ("LIKE".equalsIgnoreCase(queryType)) {
			builder.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" ")
				.append(queryType)
				.append(" ")
				.append("'%")
				.append(value)
				.append("%'");
		} else if ("=".equalsIgnoreCase(queryType) || "!=".equalsIgnoreCase(queryType)) {
			builder.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" ")
				.append(transfer(queryType))
				.append(" ")
				.append("'")
				.append(value)
				.append("'");
		}
	}
	
	/**
	 * 组装数字类型查询SQL
	 * 
	 * @param builder
	 * @param col
	 * @param javaType
	 * @param operType
	 * @param value
	 */
	private void assembleNumberTypeSQL(StringBuilder builder, String col, String javaType, String queryType,
			Object value) {
		builder.append(" and ")
			.append(DB_TABLE_ALIAS)
			.append(".")
			.append(col)
			.append(" ")
			.append(transfer(queryType))
			.append(" ")
			.append("'")
			.append(value)
			.append("'");
	}

	/**
	 * 组装日期类型查询SQL
	 * 
	 * @param builder
	 * @param col
	 * @param javaType
	 * @param operType
	 * @param value
	 */
	private void assembleDateTypeSQL(StringBuilder builder, String col, String javaType, String queryType,
			Object value) {
		if ("between".equalsIgnoreCase(queryType)) {
			JSONArray range = (JSONArray) value;
			builder.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" >= ")
				.append("'")
				.append(range.get(0))
				.append(" 00:00:00'")
				.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" <= ")
				.append("'")
				.append(range.get(1))
				.append(" 23:59:59'");
		} else if ("&gt;".equalsIgnoreCase(queryType) || "&gt;=".equalsIgnoreCase(queryType)) {
			builder.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" ")
				.append(transfer(queryType))
				.append(" ")
				.append("'")
				.append(value)
				.append(" 00:00:00'");
		} else if ("&lt;".equalsIgnoreCase(queryType) || "&lt;=".equalsIgnoreCase(queryType)) {
			builder.append(" and ")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(" ")
				.append(transfer(queryType))
				.append(" ")
				.append("'")
				.append(value)
				.append(" 23:59:59'");
		} else if ("=".equalsIgnoreCase(queryType)) {
			builder.append(" and DATE(")
				.append(DB_TABLE_ALIAS)
				.append(".")
				.append(col)
				.append(")")
				.append(transfer(queryType))
				.append("'")
				.append(value.toString().substring(0, 10))
				.append("'");
		}
	}
	
	private String transfer(String queryType) {
		String result = "";
		switch (queryType) {
		case "&lt;":
			result = "<";
			break;
		case "&lt;=":
			result = "<=";
			break;
		case "&gt;":
			result = ">";
			break;
		case "&gt;=":
			result = ">=";
			break;
		default:
			result = queryType;
			break;
		}
		return result;
	}

}

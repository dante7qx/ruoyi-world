package com.spirit.flowable.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 流程定义
 * <p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowProcDefDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 流程id */
	private String id;

	/** 流程名称 */
	private String name;

	/** 流程key */
	private String flowKey;

	/** 流程分类 */
	private String category;

	/** 配置表单名称 */
	private String formName;

	/** 配置表单id */
	private Long formId;

	/** 版本 */
	private int version;

	/** 部署ID */
	private String deploymentId;

	/** 流程定义状态: 1:激活 , 2:中止 */
	private int suspensionState;

	/** 部署时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deploymentTime;

	/** 请求参数 */
	private Map<String, Object> params;

	public Map<String, Object> getParams() {
		if (params == null) {
			params = Maps.newHashMap();
		}
		return params;
	}

}
package com.risun.flowable.domain.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 系统流程操作VO
 * 
 * @author dante
 *
 */
@Data
public class SysFlowVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 流程类型 */
	private String flowType;
	
	/** 业务UID */
	private String bizUid;
	
	/** 业务描述 */
	private String bizDesc;
	
	/** 多选 */
	private Boolean multi = Boolean.FALSE;
	
	/** 候选审批用户（<= 1 单实例, > 1 多实例） */
	private Long[] approvalUserId;
	
	/** 处理任务 */
	private Long handleUserId;
	
	/** 审批意见 */
	private String comment;
	
	/** 审批附件 */
	private String attachment;
	
	/** 审批参数 */
	private Map<String, Object> variable;
	
	public Map<String, Object> add(String key, Object val) {
		if(CollUtil.isEmpty(variable)) {
			this.variable = Maps.newHashMap();
		}
		this.variable.put(key, val);
		return this.variable;
	}
	
	public void clearVariable() {
		if(CollUtil.isNotEmpty(variable)) {
			this.variable.clear();
		}
	}
	
}

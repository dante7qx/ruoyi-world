package com.risun.flowable.domain.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * <p>
 * 流程任务
 * <p>
 *
 * @author dante
 */
@Data
public class FlowTaskVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 任务编号 */
	private String taskId;

	/** 审批意见 */
	private String comment;

	/** 审批附件 */
	private String attachment;

	/** 审批通过（默认为驳回） */
	private Boolean agree;

	/** 审批人(组) */
	private String assignee;

	/** 候选人 */
	private List<String> candidateUsers;

	/** 审批组 */
	private List<String> candidateGroups;

	/** 审批变量 */
	private Map<String, Object> variables;
	
	/** 转办任务Id */
	private String[] assignTaskIds;
	
	/** 保留我的待办（转办任务） */
	private Boolean keepTodo;

	public void addVariables(String key, Object val) {
		if (CollUtil.isEmpty(variables)) {
			this.variables = Maps.newHashMap();
		}
		this.variables.put(key, val);
	}
}

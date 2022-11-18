package com.risun.flowable.domain.dto;

import java.io.Serializable;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 流程历史记录
 * 
 * @author dante
 */
@Data
public class FlowHistRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 序号 */
    private int seqNum;

    /** 上一任务Id */
	private String fromTaskId;

	/** 上一任务活动Id */
	private String fromActivityId;

	/** 上一任务名称 */
	private String fromTask;

	/** 下一任务Id */
	private String toTaskId;

	/** 下一任务活动Id */
	private String toActivityId;

	/** 下一任务名称 */
	private String toTask;

	/** 处理人Id */
	private Long userId;

	/** 处理人姓名 */
	private String userNickName;

	/** 处理人所在部门Id */
	private Long deptId;

	/** 处理人所在部门名称 */
	private String deptName;

	/** 流程名称 */
	private String flowName;

	/** 任务到达时间 */
	private String startDate;

	/** 任务审批时间 */
	private String endDate;

	/** 审批意见 */
	private String comment;

	/** 流程完结标识 */
	private Boolean finished = Boolean.FALSE;
	
	/** 流程记录 */
	@SuppressWarnings("unused")
	private String fullFlowRecord;
	
	public String getFullFlowRecord() {
		String flowStr = StrUtil.isNotEmpty(flowName) ? flowName : StrUtil.isNotEmpty(toTask) ? toTask : "";
		StringBuilder buffer = new StringBuilder();
		if (userId != null) {
			buffer.append(userNickName);
			if(StrUtil.isNotEmpty(deptName)) {
				buffer.append("（").append(deptName).append("）");
			}
		} else {
			buffer.append("（").append(deptName).append("）");
		}
		if (endDate == null) {
			buffer.append("正在处理， 到达时间: ").append(startDate);
		} else {
			buffer.append("选择【").append(flowStr).append("】，提交于：").append(endDate).append("，处理意见：")
					.append(StrUtil.isNotEmpty(comment) ? comment : flowStr);
		}

		return buffer.toString();
	}
}

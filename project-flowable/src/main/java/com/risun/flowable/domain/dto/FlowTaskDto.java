package com.risun.flowable.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>工作流任务相关-返回参数<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@Getter
@Setter
public class FlowTaskDto {
	
	/** 任务编号 */
    private String taskId;

    /** 任务执行编号 */
    private String executionId;

    /** 任务名称 */
    private String taskName;

    /** 任务Key */
    private String taskDefKey;
    
    /** 业务主键 */
    private String bizId;
    
    /** 业务主键 */
    private String bizUid;
    
    /** 业务模块 */
    private String bizModel;

    /** 任务执行人Id */
    private Long assigneeId;

    /** 部门名称 */
    private String deptName;

    /** 流程发起人部门名称 */
    private String startDeptName;

    /** 任务执行人名称 */
    private String assigneeName;

    /** 流程发起人Id */
    private String startUserId;

    /** 流程发起人名称 */
    private String startUserName;

    /** 流程类型 */
    private String category;

    /** 流程部署编号 */
    private String deployId;

    /** 流程ID */
    private String procDefId;

    /** 流程key */
    private String procDefKey;

    /** 流程定义名称 */
    private String procDefName;

    /** 流程定义内置使用版本 */
    private int procDefVersion;

    /** 流程实例ID */
    private String procInsId;

    /** 任务耗时 */
    private String duration;

    /** 任务创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 任务完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    /** 审批通过 */
    private Boolean passed;
    
    /** 审批过程是否需要附件 */
    private Boolean hasApprovalAttach = Boolean.FALSE;
	
    public String getDuration() {
    	if(createTime != null && endTime != null) {
    		this.duration = getDate(DateUtil.between(createTime, endTime, DateUnit.MS));
    	}
    	return this.duration;
    }

    /**
     * 设置审批过程是否需要附件
     *
     * @return
     */
    public Boolean getHasApprovalAttach() {
    	// 可以通过业务模块bizModel来设置是否在审核时需要传递附件
		switch (bizModel) {
			case "<业务模块>":
				// 设置
				break;
			default:
				break;

		}
    	return this.hasApprovalAttach;
    }
    
    /**
     * 流程完成时间处理
     *
     * @param ms
     * @return
     */
    private String getDate(long ms) {
        long day = ms / (24 * 60 * 60 * 1000);
        long hour = (ms / (60 * 60 * 1000) - day * 24);
        long minute = ((ms / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (ms / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);

        if (day > 0) {
            return day + "天" + hour + "小时" + minute + "分钟";
        }
        if (hour > 0) {
            return hour + "小时" + minute + "分钟";
        }
        if (minute > 0) {
            return minute + "分钟";
        }
        if (second > 0) {
            return second + "秒";
        } else {
            return ms + "毫秒";
        }
    }
}

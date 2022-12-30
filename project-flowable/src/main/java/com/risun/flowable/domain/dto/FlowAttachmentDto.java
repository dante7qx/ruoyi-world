package com.risun.flowable.domain.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

/**
 * 流程附件记录
 * 
 * @author dante
 */
@Data
public class FlowAttachmentDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 任务附件Id */
	private String id;
	
	/** 任务Id */
	private String taskId;

	/** 任务名称 */
	private String taskName;
	
	/** 上传用户名 */
	private String userNickName;
	
	/** 上传用户部门名称 */
	private String userDeptName;
	
	/** 附件URL */
	private String url;
	
	/** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @SuppressWarnings("unused")
	private String fullFlowRecord;
    
    public String getFullFlowRecord() {
    	return this.taskName
    			.concat(" - ")
    			.concat(userNickName)
    			.concat("（")
    			.concat(userDeptName)
    			.concat("）")
    			.concat("【")
    			.concat(DateUtil.formatDateTime(createTime))
    			.concat("】");
    }

}

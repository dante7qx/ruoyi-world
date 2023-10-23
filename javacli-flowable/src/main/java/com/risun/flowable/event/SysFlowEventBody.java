package com.spirit.flowable.event;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * 系统流程消息实体
 * 
 * @author dante
 *
 */
@Data
public class SysFlowEventBody implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 流程类型 */
	private String flowType;
	
	/** 业务uid */
	private String bizUid;
	
	/** 下一步处理人Id */
	private Long[] nextUserId;
	
	/** 下一步处理组Id */
	private Long nextGroupId;
	
	/** 业务数据 */
	private Map<String, Object> bizMap;
}

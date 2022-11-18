package com.risun.flowable.domain.vo;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;

import org.springframework.util.CollectionUtils;

import lombok.Data;

/**
 * 启动流程VO
 * 
 * @author dante
 */
@Data
public class StartFlowVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String COMMENT = "提交申请";
	
	/** 流程发起人 Id（默认是当前登录用户） **/
	private Long starterId;
	
	/** 业务主键ID */
	private String bizId;
	/** 业务标识ID（不是主键，推荐UUID） */
	private String bizUid;
	/** 业务模块 BizModelConstants 中定义 */
	private String bizModel;
	
	/** 流程变量，根据流程定义进行设置 **/
	private Map<String, Object> params;
	
	/** 审批意见 **/
	private String comment = COMMENT;
	
	public Map<String, Object> addParams(String key, Object val) {
		if(CollectionUtils.isEmpty(params)) {
			this.params = Maps.newHashMap();
		}
		this.params.put(key, val);
		return this.params;
	}
	
	
}

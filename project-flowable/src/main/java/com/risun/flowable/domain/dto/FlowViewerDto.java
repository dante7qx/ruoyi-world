package com.spirit.flowable.domain.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Xuan xuan
 * @date 2021/4/21 20:55
 */
@Data
public class FlowViewerDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 流程节点key */
    private String key;
    
    /** 上一个流程节点key */
    private String prevKey;

    /**
     * 是否完成(已经审批)
     */
    private boolean completed;
    
}

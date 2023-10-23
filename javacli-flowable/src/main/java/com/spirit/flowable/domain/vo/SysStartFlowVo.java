package com.spirit.flowable.domain.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程启动Vo
 * 
 * @author dante
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysStartFlowVo extends SysFlowVo {

	private static final long serialVersionUID = 1L;

	/** 流程定义Key */
	private String procDefKey;

}

package com.spirit.flowable.mapper;

import java.util.List;

import com.spirit.flowable.domain.dto.FlowProcDefDto;

/**
 * 流程定义查询
 *
 * @author dante
 **/
public interface SysFlowDeployMapper {

	/**
     * 流程定义列表
     * 
     * @param name
     * @return
     */
    List<FlowProcDefDto> selectDeployList(FlowProcDefDto flowProcDefDto);
	
    /**
     * 查询最新版流程定义
     * 
     * @return
     */
    List<FlowProcDefDto> selectLatestProcDefList();
}

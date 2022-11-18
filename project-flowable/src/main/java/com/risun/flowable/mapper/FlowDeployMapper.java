package com.risun.flowable.mapper;

import java.util.List;

import com.risun.flowable.domain.dto.FlowProcDefDto;

/**
 * 流程定义查询
 *
 * @author Xuan Xuan
 * @email
 * @date 2022/1/29 5:44 下午
 **/
public interface FlowDeployMapper {

	/**
     * 流程定义列表
     * @param name
     * @return
     */
    List<FlowProcDefDto> selectDeployList(FlowProcDefDto flowProcDefDto);
	
}

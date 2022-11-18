package com.risun.flowable.mapper;

import java.util.List;

import com.risun.flowable.domain.dto.FlowAttachmentDto;
import com.risun.flowable.domain.dto.FlowTaskDto;
import com.risun.flowable.domain.vo.FlowQueryVo;

public interface FlowTaskMapper {
	
	/**
	 * 查询待办任务
	 * 
	 * @param queryVo
	 * @return
	 */
	public List<FlowTaskDto> selectFlowTodoList(FlowQueryVo queryVo);
	
	/**
	 * 查询已办任务
	 * 
	 * @param queryVo
	 * @return
	 */
	public List<FlowTaskDto> selectFlowDoneList(FlowQueryVo queryVo);
	
	/**
	 * 查询办结任务
	 * 
	 * @param queryVo
	 * @return
	 */
	public List<FlowTaskDto> selectFlowFinishList(FlowQueryVo queryVo);
	
	/**
	 * 查询流程附件记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	public List<FlowAttachmentDto> selectFlowAttachmentList(String procInsId);
	
}

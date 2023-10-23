package com.spirit.flowable.mapper;

import java.util.List;

import com.spirit.flowable.domain.vo.SysFlowTaskQueryVo;
import com.spirit.flowable.domain.vo.SysFlowTaskVo;

/**
 * 流程列表Mapper接口
 * 
 * @author sunchao
 * @date 2023-03-06
 */
public interface SysFlowTaskMapper {
	
	/**
	 * 查询系统流程列表（我的、已办、监控）
	 * 
	 * @param query
	 * @return
	 */
	public List<SysFlowTaskVo> selectSysFlowTaskList(SysFlowTaskQueryVo query);
	
	/**
	 * 查询系统流程待办列表
	 * 
	 * @param query
	 * @return
	 */
	public List<SysFlowTaskVo> selectSysFlowTaskTodoList(SysFlowTaskQueryVo query);
	
	/**
	 * 查询流程发起人
	 * 
	 * @param procInstId
	 * @return
	 */
	public String selectFlowStarterByProcInstId(String procInstId);
}

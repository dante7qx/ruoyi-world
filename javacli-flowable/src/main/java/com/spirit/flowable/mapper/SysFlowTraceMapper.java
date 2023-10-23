package com.spirit.flowable.mapper;

import java.util.List;

import com.spirit.flowable.domain.SysFlowTrace;

/**
 * 流程跟踪Mapper接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface SysFlowTraceMapper {
	/**
	 * 查询流程跟踪
	 * 
	 * @param bizUid 业务UID
	 * @return 流程跟踪
	 */
	public SysFlowTrace selectSysFlowTraceByBizUid(String bizUid);

	/**
	 * 查询流程跟踪列表
	 * 
	 * @param sysFlowTrace 流程跟踪
	 * @return 流程跟踪集合
	 */
	public List<SysFlowTrace> selectSysFlowTraceList(SysFlowTrace sysFlowTrace);
	
	/**
	 * 新增流程跟踪
	 * 
	 * @param sysFlowTrace 流程跟踪
	 * @return 结果
	 */
	public int insertSysFlowTrace(SysFlowTrace sysFlowTrace);

	/**
	 * 修改流程跟踪
	 * 
	 * @param sysFlowTrace 流程跟踪
	 * @return 结果
	 */
	public int updateSysFlowTrace(SysFlowTrace sysFlowTrace);

	/**
	 * 撤销流程
	 * 
	 * @param sysFlowTrace
	 * @return
	 */
	public int revokeSysFlowTrace(SysFlowTrace sysFlowTrace);

	/**
	 * 删除流程跟踪
	 * 
	 * @param bizUid 业务UID
	 * @return 结果
	 */
	public int deleteSysFlowTraceByBizUid(String bizUid);

}

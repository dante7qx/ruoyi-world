package com.spirit.system.service;

import java.util.List;

import com.spirit.system.domain.SysApprovalLog;

/**
 * 审批日志Service接口
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public interface ISysApprovalLogService {

	/**
	 * 查询审批日志列表
	 * 
	 * @param sysApprovalLog 审批日志
	 * @return 审批日志集合
	 */
	public List<SysApprovalLog> selectSysApprovalLogList(SysApprovalLog sysApprovalLog);

	/**
	 * 新增审批日志
	 * 
	 * @param sysApprovalLog 审批日志
	 * @return 结果
	 */
	public int insertSysApprovalLog(SysApprovalLog sysApprovalLog);

}

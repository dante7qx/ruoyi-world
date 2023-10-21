package com.spirit.system.service.impl;

import java.util.List;

import com.spirit.system.domain.SysApprovalLog;
import com.spirit.system.mapper.SysApprovalLogMapper;
import com.spirit.system.service.ISysApprovalLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 审批日志Service业务层处理
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@Service
public class SysApprovalLogServiceImpl implements ISysApprovalLogService {
	@Autowired
	private SysApprovalLogMapper sysApprovalLogMapper;

	/**
	 * 查询审批日志列表
	 * 
	 * @param sysApprovalLog 审批日志
	 * @return 审批日志
	 */
	@Override
	public List<SysApprovalLog> selectSysApprovalLogList(SysApprovalLog sysApprovalLog) {
		return sysApprovalLogMapper.selectSysApprovalLogList(sysApprovalLog);
	}

	/**
	 * 新增审批日志
	 * 
	 * @param sysApprovalLog 审批日志
	 * @return 结果
	 */
	@Override
	public int insertSysApprovalLog(SysApprovalLog sysApprovalLog) {
		return sysApprovalLogMapper.insertSysApprovalLog(sysApprovalLog);
	}

}

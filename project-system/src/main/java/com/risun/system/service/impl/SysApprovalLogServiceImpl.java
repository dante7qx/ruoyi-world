package com.risun.system.service.impl;

import java.util.List;

import com.risun.system.domain.SysApprovalLog;
import com.risun.system.mapper.SysApprovalLogMapper;
import com.risun.system.service.ISysApprovalLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 审批日志Service业务层处理
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@Service
public class SysApprovalLogServiceImpl implements ISysApprovalLogService 
{
    @Autowired
    private SysApprovalLogMapper sysApprovalLogMapper;

    /**
     * 查询审批日志
     * 
     * @param logId 审批日志主键
     * @return 审批日志
     */
    @Override
    public SysApprovalLog selectSysApprovalLogByLogId(Long logId)
    {
        return sysApprovalLogMapper.selectSysApprovalLogByLogId(logId);
    }

    /**
     * 查询审批日志列表
     * 
     * @param sysApprovalLog 审批日志
     * @return 审批日志
     */
    @Override
    public List<SysApprovalLog> selectSysApprovalLogList(SysApprovalLog sysApprovalLog)
    {
        return sysApprovalLogMapper.selectSysApprovalLogList(sysApprovalLog);
    }

    /**
     * 新增审批日志
     * 
     * @param sysApprovalLog 审批日志
     * @return 结果
     */
    @Override
    public int insertSysApprovalLog(SysApprovalLog sysApprovalLog)
    {
        return sysApprovalLogMapper.insertSysApprovalLog(sysApprovalLog);
    }

    /**
     * 修改审批日志
     * 
     * @param sysApprovalLog 审批日志
     * @return 结果
     */
    @Override
    public int updateSysApprovalLog(SysApprovalLog sysApprovalLog)
    {
        return sysApprovalLogMapper.updateSysApprovalLog(sysApprovalLog);
    }

    /**
     * 批量删除审批日志
     * 
     * @param logIds 需要删除的审批日志主键
     * @return 结果
     */
    @Override
    public int deleteSysApprovalLogByLogIds(Long[] logIds)
    {
        return sysApprovalLogMapper.deleteSysApprovalLogByLogIds(logIds);
    }

    /**
     * 删除审批日志信息
     * 
     * @param logId 审批日志主键
     * @return 结果
     */
    @Override
    public int deleteSysApprovalLogByLogId(Long logId)
    {
        return sysApprovalLogMapper.deleteSysApprovalLogByLogId(logId);
    }
}

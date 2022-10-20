package com.risun.system.service;

import java.util.List;
import com.risun.system.domain.SysApprovalLog;

/**
 * 审批日志Service接口
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public interface ISysApprovalLogService 
{
    /**
     * 查询审批日志
     * 
     * @param logId 审批日志主键
     * @return 审批日志
     */
    public SysApprovalLog selectSysApprovalLogByLogId(Long logId);

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

    /**
     * 修改审批日志
     * 
     * @param sysApprovalLog 审批日志
     * @return 结果
     */
    public int updateSysApprovalLog(SysApprovalLog sysApprovalLog);

    /**
     * 批量删除审批日志
     * 
     * @param logIds 需要删除的审批日志主键集合
     * @return 结果
     */
    public int deleteSysApprovalLogByLogIds(Long[] logIds);

    /**
     * 删除审批日志信息
     * 
     * @param logId 审批日志主键
     * @return 结果
     */
    public int deleteSysApprovalLogByLogId(Long logId);
}

package com.spirit.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.domain.SysEmailLog;
import com.spirit.system.mapper.SysEmailLogMapper;
import com.spirit.system.service.ISysEmailLogService;

/**
 * 邮件日志Service业务层处理
 * 
 * @author sunchao
 * @date 2022-08-16
 */
@Service
public class SysEmailLogServiceImpl implements ISysEmailLogService 
{
    @Autowired
    private SysEmailLogMapper sysEmailLogMapper;

    /**
     * 查询邮件日志
     * 
     * @param emailId 邮件日志主键
     * @return 邮件日志
     */
    @Override
    public SysEmailLog selectSysEmailLogByEmailId(Long emailId)
    {
        return sysEmailLogMapper.selectSysEmailLogByEmailId(emailId);
    }

    /**
     * 查询邮件日志列表
     * 
     * @param sysEmailLog 邮件日志
     * @return 邮件日志
     */
    @Override
    public List<SysEmailLog> selectSysEmailLogList(SysEmailLog sysEmailLog)
    {
        return sysEmailLogMapper.selectSysEmailLogList(sysEmailLog);
    }

    /**
     * 新增邮件日志
     * 
     * @param sysEmailLog 邮件日志
     * @return 结果
     */
    @Override
    public int insertSysEmailLog(SysEmailLog sysEmailLog)
    {
        sysEmailLog.setCreateBy(SecurityUtils.getUsername());
        sysEmailLog.setCreateTime(DateUtils.getNowDate());
        return sysEmailLogMapper.insertSysEmailLog(sysEmailLog);
    }

    /**
     * 批量删除邮件日志
     * 
     * @param emailIds 需要删除的邮件日志主键
     * @return 结果
     */
    @Override
    public int deleteSysEmailLogByEmailIds(Long[] emailIds)
    {
        return sysEmailLogMapper.deleteSysEmailLogByEmailIds(emailIds);
    }

    /**
     * 删除邮件日志信息
     * 
     * @param emailId 邮件日志主键
     * @return 结果
     */
    @Override
    public int deleteSysEmailLogByEmailId(Long emailId)
    {
        return sysEmailLogMapper.deleteSysEmailLogByEmailId(emailId);
    }
}

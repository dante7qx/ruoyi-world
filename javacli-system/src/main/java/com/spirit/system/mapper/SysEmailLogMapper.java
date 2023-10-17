package com.spirit.system.mapper;

import java.util.List;

import com.spirit.system.domain.SysEmailLog;

/**
 * 邮件日志Mapper接口
 * 
 * @author sunchao
 * @date 2022-08-16
 */
public interface SysEmailLogMapper 
{
    /**
     * 查询邮件日志
     * 
     * @param emailId 邮件日志主键
     * @return 邮件日志
     */
    public SysEmailLog selectSysEmailLogByEmailId(Long emailId);

    /**
     * 查询邮件日志列表
     * 
     * @param sysEmailLog 邮件日志
     * @return 邮件日志集合
     */
    public List<SysEmailLog> selectSysEmailLogList(SysEmailLog sysEmailLog);

    /**
     * 新增邮件日志
     * 
     * @param sysEmailLog 邮件日志
     * @return 结果
     */
    public int insertSysEmailLog(SysEmailLog sysEmailLog);
    
    /**
     * 删除邮件日志
     * 
     * @param emailId 邮件日志主键
     * @return 结果
     */
    public int deleteSysEmailLogByEmailId(Long emailId);

    /**
     * 批量删除邮件日志
     * 
     * @param emailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEmailLogByEmailIds(Long[] emailIds);

}

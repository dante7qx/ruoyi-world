package com.spirit.system.service;

import java.util.List;

import com.spirit.system.domain.SysSmsLog;

/**
 * 短信日志Service接口
 * 
 * @author sunchao
 * @date 2022-08-17
 */
public interface ISysSmsLogService 
{
    /**
     * 查询短信日志
     * 
     * @param smsId 短信日志主键
     * @return 短信日志
     */
    public SysSmsLog selectSysSmsLogBySmsId(Long smsId);

    /**
     * 查询短信日志列表
     * 
     * @param sysSmsLog 短信日志
     * @return 短信日志集合
     */
    public List<SysSmsLog> selectSysSmsLogList(SysSmsLog sysSmsLog);

    /**
     * 新增短信日志
     * 
     * @param sysSmsLog 短信日志
     * @return 结果
     */
    public int insertSysSmsLog(SysSmsLog sysSmsLog);

    /**
     * 修改短信日志
     * 
     * @param sysSmsLog 短信日志
     * @return 结果
     */
    public int updateSysSmsLog(SysSmsLog sysSmsLog);

    /**
     * 批量删除短信日志
     * 
     * @param smsIds 需要删除的短信日志主键集合
     * @return 结果
     */
    public int deleteSysSmsLogBySmsIds(Long[] smsIds);

    /**
     * 删除短信日志信息
     * 
     * @param smsId 短信日志主键
     * @return 结果
     */
    public int deleteSysSmsLogBySmsId(Long smsId);
}

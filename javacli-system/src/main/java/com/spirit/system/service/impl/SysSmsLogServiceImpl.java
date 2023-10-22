package com.spirit.system.service.impl;

import java.util.List;
import com.spirit.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.mapper.SysSmsLogMapper;
import com.spirit.system.domain.SysSmsLog;
import com.spirit.system.service.ISysSmsLogService;

/**
 * 短信日志Service业务层处理
 * 
 * @author sunchao
 * @date 2022-08-17
 */
@Service
public class SysSmsLogServiceImpl implements ISysSmsLogService 
{
    @Autowired
    private SysSmsLogMapper sysSmsLogMapper;

    /**
     * 查询短信日志
     * 
     * @param smsId 短信日志主键
     * @return 短信日志
     */
    @Override
    public SysSmsLog selectSysSmsLogBySmsId(Long smsId)
    {
        return sysSmsLogMapper.selectSysSmsLogBySmsId(smsId);
    }

    /**
     * 查询短信日志列表
     * 
     * @param sysSmsLog 短信日志
     * @return 短信日志
     */
    @Override
    public List<SysSmsLog> selectSysSmsLogList(SysSmsLog sysSmsLog)
    {
        return sysSmsLogMapper.selectSysSmsLogList(sysSmsLog);
    }

    /**
     * 新增短信日志
     * 
     * @param sysSmsLog 短信日志
     * @return 结果
     */
    @Override
    public int insertSysSmsLog(SysSmsLog sysSmsLog)
    {
        sysSmsLog.setCreateBy(SecurityUtils.getUsername());
        sysSmsLog.setCreateTime(DateUtils.getNowDate());
        return sysSmsLogMapper.insertSysSmsLog(sysSmsLog);
    }

    /**
     * 修改短信日志
     * 
     * @param sysSmsLog 短信日志
     * @return 结果
     */
    @Override
    public int updateSysSmsLog(SysSmsLog sysSmsLog)
    {
        sysSmsLog.setUpdateBy(SecurityUtils.getUsername());
        sysSmsLog.setUpdateTime(DateUtils.getNowDate());
        return sysSmsLogMapper.updateSysSmsLog(sysSmsLog);
    }

    /**
     * 批量删除短信日志
     * 
     * @param smsIds 需要删除的短信日志主键
     * @return 结果
     */
    @Override
    public int deleteSysSmsLogBySmsIds(Long[] smsIds)
    {
        return sysSmsLogMapper.deleteSysSmsLogBySmsIds(smsIds);
    }

    /**
     * 删除短信日志信息
     * 
     * @param smsId 短信日志主键
     * @return 结果
     */
    @Override
    public int deleteSysSmsLogBySmsId(Long smsId)
    {
        return sysSmsLogMapper.deleteSysSmsLogBySmsId(smsId);
    }
}

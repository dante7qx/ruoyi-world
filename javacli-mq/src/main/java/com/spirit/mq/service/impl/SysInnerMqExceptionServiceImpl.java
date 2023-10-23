package com.spirit.mq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.common.utils.DateUtils;
import com.spirit.mq.domain.SysInnerMqException;
import com.spirit.mq.mapper.SysInnerMqExceptionMapper;
import com.spirit.mq.service.ISysInnerMqExceptionService;

/**
 * 内部消息队列异常消息Service业务层处理
 * 
 * @author sunchao
 * @date 2023-02-06
 */
@Service
public class SysInnerMqExceptionServiceImpl implements ISysInnerMqExceptionService 
{
    @Autowired
    private SysInnerMqExceptionMapper sysInnerMqExceptionMapper;

    /**
     * 查询内部消息队列异常消息
     * 
     * @param id 内部消息队列异常消息主键
     * @return 内部消息队列异常消息
     */
    @Override
    public SysInnerMqException selectSysInnerMqExceptionById(Long id)
    {
        return sysInnerMqExceptionMapper.selectSysInnerMqExceptionById(id);
    }

    /**
     * 查询内部消息队列异常消息列表
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 内部消息队列异常消息
     */
    @Override
    public List<SysInnerMqException> selectSysInnerMqExceptionList(SysInnerMqException sysInnerMqException)
    {
        return sysInnerMqExceptionMapper.selectSysInnerMqExceptionList(sysInnerMqException);
    }

    /**
     * 新增内部消息队列异常消息
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 结果
     */
    @Override
    public int insertSysInnerMqException(SysInnerMqException sysInnerMqException)
    {
        sysInnerMqException.setCreateTime(DateUtils.getNowDate());
        return sysInnerMqExceptionMapper.insertSysInnerMqException(sysInnerMqException);
    }

    /**
     * 修改内部消息队列异常消息
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 结果
     */
    @Override
    public int updateSysInnerMqException(SysInnerMqException sysInnerMqException)
    {
        return sysInnerMqExceptionMapper.updateSysInnerMqException(sysInnerMqException);
    }

    /**
     * 批量删除内部消息队列异常消息
     * 
     * @param ids 需要删除的内部消息队列异常消息主键
     * @return 结果
     */
    @Override
    public int deleteSysInnerMqExceptionByIds(Long[] ids)
    {
        return sysInnerMqExceptionMapper.deleteSysInnerMqExceptionByIds(ids);
    }

    /**
     * 删除内部消息队列异常消息信息
     * 
     * @param id 内部消息队列异常消息主键
     * @return 结果
     */
    @Override
    public int deleteSysInnerMqExceptionById(Long id)
    {
        return sysInnerMqExceptionMapper.deleteSysInnerMqExceptionById(id);
    }
}

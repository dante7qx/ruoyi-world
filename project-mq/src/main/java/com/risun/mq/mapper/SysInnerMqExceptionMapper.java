package com.risun.mq.mapper;

import java.util.List;
import com.risun.mq.domain.SysInnerMqException;

/**
 * 内部消息队列异常消息Mapper接口
 * 
 * @author sunchao
 * @date 2023-02-06
 */
public interface SysInnerMqExceptionMapper 
{
    /**
     * 查询内部消息队列异常消息
     * 
     * @param id 内部消息队列异常消息主键
     * @return 内部消息队列异常消息
     */
    public SysInnerMqException selectSysInnerMqExceptionById(Long id);

    /**
     * 查询内部消息队列异常消息列表
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 内部消息队列异常消息集合
     */
    public List<SysInnerMqException> selectSysInnerMqExceptionList(SysInnerMqException sysInnerMqException);

    /**
     * 新增内部消息队列异常消息
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 结果
     */
    public int insertSysInnerMqException(SysInnerMqException sysInnerMqException);

    /**
     * 修改内部消息队列异常消息
     * 
     * @param sysInnerMqException 内部消息队列异常消息
     * @return 结果
     */
    public int updateSysInnerMqException(SysInnerMqException sysInnerMqException);

    /**
     * 删除内部消息队列异常消息
     * 
     * @param id 内部消息队列异常消息主键
     * @return 结果
     */
    public int deleteSysInnerMqExceptionById(Long id);

    /**
     * 批量删除内部消息队列异常消息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInnerMqExceptionByIds(Long[] ids);
}

package com.spirit.system.service;

import java.util.List;

import com.spirit.system.domain.SysCustAdvQueryCond;

/**
 * 自定义高级查询条件Service接口
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public interface ISysCustAdvQueryCondService 
{
    /**
     * 查询自定义高级查询条件
     * 
     * @param condId 自定义高级查询条件主键
     * @return 自定义高级查询条件
     */
    public SysCustAdvQueryCond selectSysCustAdvQueryCondByCondId(Long condId);
    
    /**
     * 查询自定义高级查询条件
     * 
     * @param queryId 自定义高级查询Id
     * @return 自定义高级查询条件
     */
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondByQueryId(Long queryId);
    
    /**
     * 查询自定义高级查询条件
     * 
     * @param queryId 自定义高级查询Id
     * @param configured 是否已配置模板条件。True: 是；False: 组合所有的字段
     * @return 自定义高级查询条件
     */
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondByQueryId(Long queryId, boolean configured);

    /**
     * 查询自定义高级查询条件列表
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 自定义高级查询条件集合
     */
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondList(SysCustAdvQueryCond sysCustAdvQueryCond);

    /**
     * 新增自定义高级查询条件
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 结果
     */
    public int insertSysCustAdvQueryCond(SysCustAdvQueryCond sysCustAdvQueryCond);

    /**
     * 修改自定义高级查询条件
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 结果
     */
    public int updateSysCustAdvQueryCond(SysCustAdvQueryCond sysCustAdvQueryCond);

    /**
     * 批量删除自定义高级查询条件
     * 
     * @param condIds 需要删除的自定义高级查询条件主键集合
     * @return 结果
     */
    public int deleteSysCustAdvQueryCondByCondIds(Long[] condIds);

    /**
     * 删除自定义高级查询条件信息
     * 
     * @param condId 自定义高级查询条件主键
     * @return 结果
     */
    public int deleteSysCustAdvQueryCondByCondId(Long condId);
    
    /**
     * 根据模板ID删除自定义高级查询条件
     * 
     * @param queryId
     * @return
     */
    public int deleteSysCustAdvQueryCondByQueryId(Long queryId);
    
    /**
     * 批量新增自定义高级查询条件
     * 
     * @param conds 自定义高级查询条件列表
     * @return 结果
     */
    public int batchInsertSysCustAdvQueryCond(Long queryId, List<SysCustAdvQueryCond> conds);
}

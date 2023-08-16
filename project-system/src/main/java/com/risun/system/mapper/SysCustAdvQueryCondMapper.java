package com.risun.system.mapper;

import java.util.List;
import com.risun.system.domain.SysCustAdvQueryCond;

/**
 * 自定义高级查询条件Mapper接口
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public interface SysCustAdvQueryCondMapper 
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
     * @param queryId
     * @return
     */
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondByQueryId(Long queryId);

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
     * 删除自定义高级查询条件
     * 
     * @param condId 自定义高级查询条件主键
     * @return 结果
     */
    public int deleteSysCustAdvQueryCondByCondId(Long condId);

    /**
     * 批量删除自定义高级查询条件
     * 
     * @param condIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCustAdvQueryCondByCondIds(Long[] condIds);
    
    /**
     * 根据模板ID删除自定义高级查询条件
     * 
     * @param queryId
     * @return
     */
    public int deleteSysCustAdvQueryCondByQueryId(Long queryId);
    
    /**
     * 根据数据库表名获取字段信息
     * 
     * @param tableName
     * @return
     */
    public List<SysCustAdvQueryCond> selectSysDBTableColByTableName(String tableName);
}

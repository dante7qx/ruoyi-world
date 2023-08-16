package com.risun.system.mapper;

import java.util.List;
import com.risun.system.domain.SysCustAdvQuery;

/**
 * 自定义高级查询模板Mapper接口
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public interface SysCustAdvQueryMapper 
{
    /**
     * 查询自定义高级查询模板
     * 
     * @param queryId 自定义高级查询模板主键
     * @return 自定义高级查询模板
     */
    public SysCustAdvQuery selectSysCustAdvQueryByQueryId(Long queryId);

    /**
     * 查询自定义高级查询模板列表
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 自定义高级查询模板集合
     */
    public List<SysCustAdvQuery> selectSysCustAdvQueryList(SysCustAdvQuery sysCustAdvQuery);

    /**
     * 新增自定义高级查询模板
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 结果
     */
    public int insertSysCustAdvQuery(SysCustAdvQuery sysCustAdvQuery);

    /**
     * 修改自定义高级查询模板
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 结果
     */
    public int updateSysCustAdvQuery(SysCustAdvQuery sysCustAdvQuery);

    /**
     * 删除自定义高级查询模板
     * 
     * @param queryId 自定义高级查询模板主键
     * @return 结果
     */
    public int deleteSysCustAdvQueryByQueryId(Long queryId);

    /**
     * 批量删除自定义高级查询模板
     * 
     * @param queryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCustAdvQueryByQueryIds(Long[] queryIds);
    
    /**
     * 根据数据库表名获取表描述
     * 
     * @param tableName
     * @return
     */
    public String selectSysDBTableByTableName(String tableName);
    
    /**
     * 根据数据库表名获取主键字段
     * 
     * @param tableName
     * @return
     */
    public String selectSysDBTablePkColByTableName(String tableName);
    
}

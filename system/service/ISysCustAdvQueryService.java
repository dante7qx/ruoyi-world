package com.risun.system.service;

import java.util.List;
import com.risun.system.domain.SysCustAdvQuery;

/**
 * 自定义高级查询模板Service接口
 * 
 * @author sunchao
 * @date 2023-08-02
 */
public interface ISysCustAdvQueryService 
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
     * 批量删除自定义高级查询模板
     * 
     * @param queryIds 需要删除的自定义高级查询模板主键集合
     * @return 结果
     */
    public int deleteSysCustAdvQueryByQueryIds(Long[] queryIds);

    /**
     * 删除自定义高级查询模板信息
     * 
     * @param queryId 自定义高级查询模板主键
     * @return 结果
     */
    public int deleteSysCustAdvQueryByQueryId(Long queryId);
}

package com.risun.system.service.impl;

import java.util.List;
import com.risun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.risun.common.utils.SecurityUtils;
import com.risun.system.mapper.SysCustAdvQueryCondMapper;
import com.risun.system.mapper.SysCustAdvQueryMapper;
import com.risun.system.domain.SysCustAdvQuery;
import com.risun.system.service.ISysCustAdvQueryService;

import cn.hutool.core.util.StrUtil;

/**
 * 自定义高级查询模板Service业务层处理
 * 
 * @author sunchao
 * @date 2023-08-02
 */
@Service
public class SysCustAdvQueryServiceImpl implements ISysCustAdvQueryService 
{
    @Autowired
    private SysCustAdvQueryMapper sysCustAdvQueryMapper;
    @Autowired
    private SysCustAdvQueryCondMapper sysCustAdvQueryCondMapper;

    /**
     * 查询自定义高级查询模板
     * 
     * @param queryId 自定义高级查询模板主键
     * @return 自定义高级查询模板
     */
    @Override
    public SysCustAdvQuery selectSysCustAdvQueryByQueryId(Long queryId)
    {
        return sysCustAdvQueryMapper.selectSysCustAdvQueryByQueryId(queryId);
    }

    /**
     * 查询自定义高级查询模板列表
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 自定义高级查询模板
     */
    @Override
    public List<SysCustAdvQuery> selectSysCustAdvQueryList(SysCustAdvQuery sysCustAdvQuery)
    {
        return sysCustAdvQueryMapper.selectSysCustAdvQueryList(sysCustAdvQuery);
    }

    /**
     * 新增自定义高级查询模板
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 结果
     */
    @Override
    public int insertSysCustAdvQuery(SysCustAdvQuery sysCustAdvQuery)
    {
    	String tableName = sysCustAdvQuery.getTableName();
    	String tableDesc = sysCustAdvQueryMapper.selectSysDBTableByTableName(tableName);
    	String pkCol = sysCustAdvQueryMapper.selectSysDBTablePkColByTableName(tableName);
    	sysCustAdvQuery.setPkCol(pkCol);
    	sysCustAdvQuery.setTableDesc(StrUtil.isNotEmpty(tableDesc) ? tableDesc : tableName);
        sysCustAdvQuery.setCreateBy(SecurityUtils.getUsername());
        sysCustAdvQuery.setCreateTime(DateUtils.getNowDate());
        return sysCustAdvQueryMapper.insertSysCustAdvQuery(sysCustAdvQuery);
    }

    /**
     * 修改自定义高级查询模板
     * 
     * @param sysCustAdvQuery 自定义高级查询模板
     * @return 结果
     */
    @Override
    public int updateSysCustAdvQuery(SysCustAdvQuery sysCustAdvQuery)
    {
        sysCustAdvQuery.setUpdateBy(SecurityUtils.getUsername());
        sysCustAdvQuery.setUpdateTime(DateUtils.getNowDate());
        return sysCustAdvQueryMapper.updateSysCustAdvQuery(sysCustAdvQuery);
    }

    /**
     * 批量删除自定义高级查询模板
     * 
     * @param queryIds 需要删除的自定义高级查询模板主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSysCustAdvQueryByQueryIds(Long[] queryIds)
    {
    	int result = 0;
    	for (Long queryId : queryIds) {
    		result += deleteSysCustAdvQueryByQueryId(queryId);
		}
        return result;
    }

    /**
     * 删除自定义高级查询模板信息
     * 
     * @param queryId 自定义高级查询模板主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSysCustAdvQueryByQueryId(Long queryId)
    {
    	sysCustAdvQueryCondMapper.deleteSysCustAdvQueryCondByQueryId(queryId);
        return sysCustAdvQueryMapper.deleteSysCustAdvQueryByQueryId(queryId);
    }
}

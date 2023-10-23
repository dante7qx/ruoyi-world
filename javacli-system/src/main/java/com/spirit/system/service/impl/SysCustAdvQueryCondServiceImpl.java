package com.spirit.system.service.impl;

import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spirit.common.constant.GenConstants;
import com.spirit.system.domain.SysCustAdvQuery;
import com.spirit.system.domain.SysCustAdvQueryCond;
import com.spirit.system.mapper.SysCustAdvQueryCondMapper;
import com.spirit.system.mapper.SysCustAdvQueryMapper;
import com.spirit.system.service.ISysCustAdvQueryCondService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 自定义高级查询条件Service业务层处理
 * 
 * @author sunchao
 * @date 2023-08-02
 */
@Service
public class SysCustAdvQueryCondServiceImpl implements ISysCustAdvQueryCondService 
{
	@Autowired
    private SysCustAdvQueryMapper sysCustAdvQueryMapper;
    @Autowired
    private SysCustAdvQueryCondMapper sysCustAdvQueryCondMapper;

    /**
     * 查询自定义高级查询条件
     * 
     * @param condId 自定义高级查询条件主键
     * @return 自定义高级查询条件
     */
    @Override
    public SysCustAdvQueryCond selectSysCustAdvQueryCondByCondId(Long condId)
    {
        return sysCustAdvQueryCondMapper.selectSysCustAdvQueryCondByCondId(condId);
    }
    
    /**
     * 查询自定义高级查询条件
     * 
     * @param queryId 自定义高级查询Id
     * @return 自定义高级查询条件
     */
    @Override
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondByQueryId(Long queryId) {
    	return selectSysCustAdvQueryCondByQueryId(queryId, false);
    }
    
    /**
     * 查询自定义高级查询条件
     * 
     * @param queryId 自定义高级查询Id
     * @param configured 是否已配置模板条件。True: 是；False: 组合所有的字段
     * @return 自定义高级查询条件
     */
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondByQueryId(Long queryId, boolean configured) {
    	List<SysCustAdvQueryCond> conds = sysCustAdvQueryCondMapper.selectSysCustAdvQueryCondByQueryId(queryId);
    	if(!configured) {
    		SysCustAdvQuery template = sysCustAdvQueryMapper.selectSysCustAdvQueryByQueryId(queryId);
    		if(CollUtil.isEmpty(conds)) {
        		List<SysCustAdvQueryCond> dbCols = sysCustAdvQueryCondMapper.selectSysDBTableColByTableName(template.getTableName());
        		conds = dbCol2QueryCond(dbCols);
        	}
    	} else {
    		conds = conds.stream().filter(col -> col.getQueryFlag()).collect(toList());
    	}
    	return conds;
    }
    
    private List<SysCustAdvQueryCond> dbCol2QueryCond(List<SysCustAdvQueryCond> dbCols) {
    	List<SysCustAdvQueryCond> conds = Lists.newArrayList();
		for (SysCustAdvQueryCond col : dbCols) {
			// 去除主键、富文本、关联外键、删除标识
			if(col.getPkFlag() || "text".equals(col.getColType()) || StrUtil.endWith(col.getColName(), "id") || StrUtil.contains(col.getColName(), "del_")) {
				continue;
			}
			col.setJavaField(StrUtil.toCamelCase(col.getColName()));
			// 设置默认类型
			col.setJavaType(GenConstants.TYPE_STRING);
			String colType = getDbType(col.getColType());
			if (arraysContains(GenConstants.COLUMNTYPE_TIME, colType)) {
	            col.setJavaType(GenConstants.TYPE_DATE);
	        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, colType)) {
	        	col.setJavaType(GenConstants.TYPE_INTEGER);
	        }
			conds.add(col);
		}
		
    	return conds;
    }
    

    /**
     * 查询自定义高级查询条件列表
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 自定义高级查询条件
     */
    @Override
    public List<SysCustAdvQueryCond> selectSysCustAdvQueryCondList(SysCustAdvQueryCond sysCustAdvQueryCond)
    {
        return sysCustAdvQueryCondMapper.selectSysCustAdvQueryCondList(sysCustAdvQueryCond);
    }

    /**
     * 新增自定义高级查询条件
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 结果
     */
    @Override
    public int insertSysCustAdvQueryCond(SysCustAdvQueryCond sysCustAdvQueryCond)
    {
        return sysCustAdvQueryCondMapper.insertSysCustAdvQueryCond(sysCustAdvQueryCond);
    }

    /**
     * 修改自定义高级查询条件
     * 
     * @param sysCustAdvQueryCond 自定义高级查询条件
     * @return 结果
     */
    @Override
    public int updateSysCustAdvQueryCond(SysCustAdvQueryCond sysCustAdvQueryCond)
    {
        return sysCustAdvQueryCondMapper.updateSysCustAdvQueryCond(sysCustAdvQueryCond);
    }

    /**
     * 批量删除自定义高级查询条件
     * 
     * @param condIds 需要删除的自定义高级查询条件主键
     * @return 结果
     */
    @Override
    public int deleteSysCustAdvQueryCondByCondIds(Long[] condIds)
    {
        return sysCustAdvQueryCondMapper.deleteSysCustAdvQueryCondByCondIds(condIds);
    }

    /**
     * 删除自定义高级查询条件信息
     * 
     * @param condId 自定义高级查询条件主键
     * @return 结果
     */
    @Override
    public int deleteSysCustAdvQueryCondByCondId(Long condId) {
        return sysCustAdvQueryCondMapper.deleteSysCustAdvQueryCondByCondId(condId);
    }
    
    /**
     * 根据模板ID删除自定义高级查询条件
     * 
     * @param queryId
     * @return
     */
    @Override
    public int deleteSysCustAdvQueryCondByQueryId(Long queryId) {
    	return sysCustAdvQueryCondMapper.deleteSysCustAdvQueryCondByQueryId(queryId);
    }
    
    /**
     * 批量新增自定义高级查询条件
     * 
     * @param conds 自定义高级查询条件列表
     * @return 结果
     */
    @Override
    @Transactional
    public int batchInsertSysCustAdvQueryCond(Long queryId, List<SysCustAdvQueryCond> conds) {
    	int result = 0;
    	sysCustAdvQueryCondMapper.deleteSysCustAdvQueryCondByQueryId(queryId);
    	for (SysCustAdvQueryCond cond : conds) {
    		cond.setQueryId(queryId);
    		result += sysCustAdvQueryCondMapper.insertSysCustAdvQueryCond(cond);
		}
    	return result;
    }
    
    
    /**
     * 校验数组是否包含指定值
     * 
     * @param arr 数组
     * @param targetValue 值
     * @return 是否包含
     */
    private boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

	/**
	 * 获取数据库类型字段
	 * 
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	private String getDbType(String columnType) {
		if (StrUtil.indexOfIgnoreCase(columnType, "(") > 0) {
			return StrUtil.subBefore(columnType, "(", false);
		} else {
			return columnType;
		}
	}

}

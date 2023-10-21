package com.risun.generator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.core.text.Convert;
import com.risun.generator.domain.GenTableColumn;
import com.risun.generator.mapper.GenTableColumnMapper;
import com.risun.generator.util.GenUtils;

/**
 * 业务字段 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService 
{
	@Autowired
	private GenTableColumnMapper genTableColumnMapper;

	/**
     * 查询业务字段列表
     * 
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
	@Override
	public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId)
	{
	    return genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
	}
	
	/**
     * 根据表名称查询列信息
     * 
     * @param tableName 表名称
     * @return 列信息
     */
	@Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
		List<GenTableColumn> columns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
		for (GenTableColumn genTableColumn : columns) {
			GenUtils.dbColumn2Java(genTableColumn);
		}
		return columns;
    }
	
    /**
     * 新增业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
	@Override
	public int insertGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.insertGenTableColumn(genTableColumn);
	}
	
	/**
     * 修改业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
	@Override
	public int updateGenTableColumn(GenTableColumn genTableColumn)
	{
	    return genTableColumnMapper.updateGenTableColumn(genTableColumn);
	}

	/**
     * 删除业务字段对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGenTableColumnByIds(String ids)
	{
		return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
	}
}

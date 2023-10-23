package com.spirit.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spirit.common.core.domain.entity.SysDictData;
import com.spirit.common.core.domain.entity.SysDictType;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.report.domain.RiJmDict;
import com.spirit.report.domain.RiJmDictItem;
import com.spirit.report.mapper.RiJmDictItemMapper;
import com.spirit.report.mapper.RiJmDictMapper;
import com.spirit.report.service.IRiJmDictService;
import com.spirit.system.service.ISysDictDataService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 报表字典Service业务层处理
 * 
 * @author sunchao
 * @date 2023-05-29
 */
@Service
public class RiJmDictServiceImpl implements IRiJmDictService 
{
    @Autowired
    private RiJmDictMapper riJmDictMapper;
    @Autowired
    private RiJmDictItemMapper riJmDictItemMapper;
    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询报表字典
     * 
     * @param id 报表字典主键
     * @return 报表字典
     */
    @Override
    public RiJmDict selectRiJmDictById(String id)
    {
        return riJmDictMapper.selectRiJmDictById(id);
    }

    /**
     * 查询报表字典列表
     * 
     * @param riJmDict 报表字典
     * @return 报表字典
     */
    @Override
    public List<RiJmDict> selectRiJmDictList(RiJmDict riJmDict)
    {
        return riJmDictMapper.selectRiJmDictList(riJmDict);
    }

    /**
     * 新增报表字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
    @Override
    public int insertRiJmDict(RiJmDict riJmDict)
    {
    	riJmDict.setId(IdUtil.getSnowflakeNextIdStr());
    	riJmDict.setDelFlag(0);
        riJmDict.setCreateBy(SecurityUtils.getUsername());
        riJmDict.setCreateTime(DateUtils.getNowDate());
        return riJmDictMapper.insertRiJmDict(riJmDict);
    }

    /**
     * 修改报表字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
    @Override
    public int updateRiJmDict(RiJmDict riJmDict)
    {
        riJmDict.setUpdateBy(SecurityUtils.getUsername());
        riJmDict.setUpdateTime(DateUtils.getNowDate());
        return riJmDictMapper.updateRiJmDict(riJmDict);
    }

    /**
     * 批量删除报表字典
     * 
     * @param ids 需要删除的报表字典主键
     * @return 结果
     */
    @Override
    public int deleteRiJmDictByIds(String[] ids)
    {
        return riJmDictMapper.deleteRiJmDictByIds(ids);
    }

    /**
     * 删除报表字典信息
     * 
     * @param id 报表字典主键
     * @return 结果
     */
    @Override
    public int deleteRiJmDictById(String id)
    {
        return riJmDictMapper.deleteRiJmDictById(id);
    }

    /**
     * 同步系统数据字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
	@Override
	@Transactional
	public int syncRiJmDict(List<SysDictType> dictTypes) {
		for (SysDictType sysDictType : dictTypes) {
			List<RiJmDict> dicts = riJmDictMapper.selectRiJmDictByDictCode(sysDictType.getDictType());
			if(CollUtil.isNotEmpty(dicts)) {
				for (RiJmDict dict : dicts) {
					riJmDictItemMapper.deleteRiJmDictItemByDictId(dict.getId());
					riJmDictMapper.deletePhysicalRiJmDictById(dict.getId());
				}
			}
			RiJmDict jmDict = this.convert2RiJmDict(sysDictType); 
			riJmDictMapper.insertRiJmDict(jmDict);
			SysDictData query = new SysDictData();
			query.setDictType(sysDictType.getDictType());
			List<SysDictData> dictDatas = sysDictDataService.selectDictDataList(query);
			for (SysDictData dictData : dictDatas) {
				RiJmDictItem dictItem = this.convert2RiJmDictItem(jmDict.getId(), dictData);
				riJmDictItemMapper.insertRiJmDictItem(dictItem);
			}
			
		}
		return dictTypes.size();
	}
	
	private RiJmDict convert2RiJmDict(SysDictType sysDictType) {
		RiJmDict dict = new RiJmDict();
		dict.setId(IdUtil.getSnowflakeNextIdStr());
		dict.setDictCode(sysDictType.getDictType());
		dict.setDictName(sysDictType.getDictName());
		dict.setDescription(sysDictType.getRemark());
		dict.setDelFlag(StrUtil.isNotEmpty(sysDictType.getStatus()) ? Integer.parseInt(sysDictType.getStatus()) : 0);
		dict.setCreateBy(SecurityUtils.getUsername());
		dict.setCreateTime(DateUtils.getNowDate());
		return dict;
	}
	
	private RiJmDictItem convert2RiJmDictItem(String dictId, SysDictData sysDictData) {
		RiJmDictItem item = new RiJmDictItem();
		item.setId(IdUtil.getSnowflakeNextIdStr());
		item.setItemText(sysDictData.getDictLabel());
		item.setItemValue(sysDictData.getDictValue());
		item.setDictId(dictId);
		item.setSortOrder(sysDictData.getDictSort().intValue());
		item.setDescription(sysDictData.getRemark());
		item.setCreateBy(SecurityUtils.getUsername());
		item.setCreateTime(DateUtils.getNowDate());
		return item;
	}
}

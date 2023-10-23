package com.spirit.report.service.impl;

import java.util.List;
import com.spirit.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.report.mapper.RiJmDictItemMapper;
import com.spirit.report.domain.RiJmDictItem;
import com.spirit.report.service.IRiJmDictItemService;

import cn.hutool.core.util.IdUtil;

/**
 * dictService业务层处理
 * 
 * @author sunchao
 * @date 2023-05-29
 */
@Service
public class RiJmDictItemServiceImpl implements IRiJmDictItemService 
{
    @Autowired
    private RiJmDictItemMapper riJmDictItemMapper;

    /**
     * 查询dict
     * 
     * @param id dict主键
     * @return dict
     */
    @Override
    public RiJmDictItem selectRiJmDictItemById(String id)
    {
        return riJmDictItemMapper.selectRiJmDictItemById(id);
    }

    /**
     * 查询dict列表
     * 
     * @param riJmDictItem dict
     * @return dict
     */
    @Override
    public List<RiJmDictItem> selectRiJmDictItemList(RiJmDictItem riJmDictItem)
    {
        return riJmDictItemMapper.selectRiJmDictItemList(riJmDictItem);
    }

    /**
     * 新增dict
     * 
     * @param riJmDictItem dict
     * @return 结果
     */
    @Override
    public int insertRiJmDictItem(RiJmDictItem riJmDictItem)
    {
    	riJmDictItem.setId(IdUtil.getSnowflakeNextIdStr());
        riJmDictItem.setCreateBy(SecurityUtils.getUsername());
        riJmDictItem.setCreateTime(DateUtils.getNowDate());
        return riJmDictItemMapper.insertRiJmDictItem(riJmDictItem);
    }

    /**
     * 修改dict
     * 
     * @param riJmDictItem dict
     * @return 结果
     */
    @Override
    public int updateRiJmDictItem(RiJmDictItem riJmDictItem)
    {
        riJmDictItem.setUpdateBy(SecurityUtils.getUsername());
        riJmDictItem.setUpdateTime(DateUtils.getNowDate());
        return riJmDictItemMapper.updateRiJmDictItem(riJmDictItem);
    }

    /**
     * 批量删除dict
     * 
     * @param ids 需要删除的dict主键
     * @return 结果
     */
    @Override
    public int deleteRiJmDictItemByIds(String[] ids)
    {
        return riJmDictItemMapper.deleteRiJmDictItemByIds(ids);
    }

    /**
     * 删除dict信息
     * 
     * @param id dict主键
     * @return 结果
     */
    @Override
    public int deleteRiJmDictItemById(String id)
    {
        return riJmDictItemMapper.deleteRiJmDictItemById(id);
    }
}

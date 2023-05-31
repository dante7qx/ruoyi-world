package com.risun.report.service;

import java.util.List;
import com.risun.report.domain.RiJmDictItem;

/**
 * dictService接口
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public interface IRiJmDictItemService 
{
    /**
     * 查询dict
     * 
     * @param id dict主键
     * @return dict
     */
    public RiJmDictItem selectRiJmDictItemById(String id);

    /**
     * 查询dict列表
     * 
     * @param riJmDictItem dict
     * @return dict集合
     */
    public List<RiJmDictItem> selectRiJmDictItemList(RiJmDictItem riJmDictItem);

    /**
     * 新增dict
     * 
     * @param riJmDictItem dict
     * @return 结果
     */
    public int insertRiJmDictItem(RiJmDictItem riJmDictItem);

    /**
     * 修改dict
     * 
     * @param riJmDictItem dict
     * @return 结果
     */
    public int updateRiJmDictItem(RiJmDictItem riJmDictItem);

    /**
     * 批量删除dict
     * 
     * @param ids 需要删除的dict主键集合
     * @return 结果
     */
    public int deleteRiJmDictItemByIds(String[] ids);

    /**
     * 删除dict信息
     * 
     * @param id dict主键
     * @return 结果
     */
    public int deleteRiJmDictItemById(String id);
}

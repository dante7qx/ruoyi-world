package com.risun.report.service;

import java.util.List;

import com.risun.common.core.domain.entity.SysDictType;
import com.risun.report.domain.RiJmDict;

/**
 * 报表字典Service接口
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public interface IRiJmDictService 
{
    /**
     * 查询报表字典
     * 
     * @param id 报表字典主键
     * @return 报表字典
     */
    public RiJmDict selectRiJmDictById(String id);

    /**
     * 查询报表字典列表
     * 
     * @param riJmDict 报表字典
     * @return 报表字典集合
     */
    public List<RiJmDict> selectRiJmDictList(RiJmDict riJmDict);

    /**
     * 新增报表字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
    public int insertRiJmDict(RiJmDict riJmDict);

    /**
     * 修改报表字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
    public int updateRiJmDict(RiJmDict riJmDict);

    /**
     * 批量删除报表字典
     * 
     * @param ids 需要删除的报表字典主键集合
     * @return 结果
     */
    public int deleteRiJmDictByIds(String[] ids);

    /**
     * 删除报表字典信息
     * 
     * @param id 报表字典主键
     * @return 结果
     */
    public int deleteRiJmDictById(String id);
    
    /**
     * 同步系统数据字典
     * 
     * @param riJmDict 报表字典
     * @return 结果
     */
    public int syncRiJmDict(List<SysDictType> sysDicts);
}

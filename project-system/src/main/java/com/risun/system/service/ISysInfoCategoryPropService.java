package com.risun.system.service;

import java.util.List;

import com.risun.system.domain.SysInfoCategoryProp;

/**
 * 信息栏目属性Service接口
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public interface ISysInfoCategoryPropService {
    /**
     * 查询信息栏目属性
     * 
     * @param propId 信息栏目属性主键
     * @return 信息栏目属性
     */
    SysInfoCategoryProp selectSysInfoCategoryPropByPropId(Long propId);

    /**
     * 查询信息栏目属性列表
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 信息栏目属性集合
     */
    List<SysInfoCategoryProp> selectSysInfoCategoryPropList(SysInfoCategoryProp sysInfoCategoryProp);

    /**
     * 新增信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    int insertSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp);

    /**
     * 修改信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    int updateSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp);

    /**
     * 批量删除信息栏目属性
     * 
     * @param propIds 需要删除的信息栏目属性主键集合
     * @return 结果
     */
    int deleteSysInfoCategoryPropByPropIds(Long[] propIds);

    /**
     * 删除信息栏目属性信息
     * 
     * @param propId 信息栏目属性主键
     * @return 结果
     */
    int deleteSysInfoCategoryPropByPropId(Long propId);
    
    /**
     * 检查栏目下是否有栏目属性
     * 
     * @param categoryId
     * @return
     */
    boolean hasChildSysInfoCategoryPropByCategoryId(Long categoryId);
}

package com.spirit.system.mapper;

import java.util.List;

import com.spirit.system.domain.SysInfoCategoryProp;

/**
 * 信息栏目属性Mapper接口
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public interface SysInfoCategoryPropMapper {
    /**
     * 查询信息栏目属性
     * 
     * @param propId 信息栏目属性主键
     * @return 信息栏目属性
     */
    public SysInfoCategoryProp selectSysInfoCategoryPropByPropId(Long propId);

    /**
     * 查询信息栏目属性列表
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 信息栏目属性集合
     */
    public List<SysInfoCategoryProp> selectSysInfoCategoryPropList(SysInfoCategoryProp sysInfoCategoryProp);

    /**
     * 新增信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    public int insertSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp);
    
    /**
     * 修改信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    public int updateSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp);
    
    /**
     * 删除信息栏目属性
     * 
     * @param propId 信息栏目属性主键
     * @return 结果
     */
    public int deleteSysInfoCategoryPropByPropId(Long propId);

    /**
     * 批量删除信息栏目属性
     * 
     * @param propIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInfoCategoryPropByPropIds(Long[] propIds);
    
    /**
     * 检查栏目下是否有栏目属性
     * 
     * @param categoryId
     * @return
     */
    public boolean hasChildSysInfoCategoryPropByCategoryId(Long categoryId);
}

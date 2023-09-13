package com.risun.system.service;

import java.util.List;

import com.risun.system.domain.SysInfoCategory;

/**
 * 信息栏目Service接口
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public interface ISysInfoCategoryService {
    /**
     * 查询信息栏目
     * 
     * @param categoryId 信息栏目主键
     * @return 信息栏目
     */
    SysInfoCategory selectSysInfoCategoryByCategoryId(Long categoryId);

    /**
     * 查询信息栏目列表
     * 
     * @param sysInfoCategory 信息栏目
     * @return 信息栏目集合
     */
    List<SysInfoCategory> selectSysInfoCategoryList(SysInfoCategory sysInfoCategory);

    /**
     * 新增信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    int insertSysInfoCategory(SysInfoCategory sysInfoCategory);

    /**
     * 修改信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    int updateSysInfoCategory(SysInfoCategory sysInfoCategory);

    /**
     * 批量删除信息栏目
     * 
     * @param categoryIds 需要删除的信息栏目主键集合
     * @return 结果
     */
    int deleteSysInfoCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除信息栏目信息
     * 
     * @param categoryId 信息栏目主键
     * @return 结果
     */
    int deleteSysInfoCategoryByCategoryId(Long categoryId);
    
    /**
     * 检查是否含有子栏目
     * 
     * @param categoryId
     * @return
     */
    boolean hasChildSysInfoCategoryByCategoryId(Long categoryId);
    
}

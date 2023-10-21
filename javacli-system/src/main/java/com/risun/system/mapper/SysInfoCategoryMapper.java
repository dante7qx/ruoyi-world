package com.risun.system.mapper;

import java.util.List;
import com.risun.system.domain.SysInfoCategory;

/**
 * 信息栏目Mapper接口
 * 
 * @author sunchao
 * @date 2023-08-29
 */
public interface SysInfoCategoryMapper {
    /**
     * 查询信息栏目
     * 
     * @param categoryId 信息栏目主键
     * @return 信息栏目
     */
    public SysInfoCategory selectSysInfoCategoryByCategoryId(Long categoryId);

    /**
     * 查询信息栏目列表
     * 
     * @param sysInfoCategory 信息栏目
     * @return 信息栏目集合
     */
    public List<SysInfoCategory> selectSysInfoCategoryList(SysInfoCategory sysInfoCategory);

    /**
     * 新增信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    public int insertSysInfoCategory(SysInfoCategory sysInfoCategory);
    
    /**
     * 修改信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    public int updateSysInfoCategory(SysInfoCategory sysInfoCategory);
    
    /**
     * 删除信息栏目
     * 
     * @param categoryId 信息栏目主键
     * @return 结果
     */
    public int deleteSysInfoCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除信息栏目
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInfoCategoryByCategoryIds(Long[] categoryIds);
    
    /**
     * 检查是否含有子栏目
     * 
     * @param categoryId
     * @return
     */
    public boolean hasChildSysInfoCategoryByCategoryId(Long categoryId);
}

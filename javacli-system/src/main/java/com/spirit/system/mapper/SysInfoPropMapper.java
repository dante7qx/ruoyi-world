package com.spirit.system.mapper;

import java.util.List;

import com.spirit.system.domain.SysInfoProp;

/**
 * 信息属性Mapper接口
 * 
 * @author sunchao
 * @date 2023-08-30
 */
public interface SysInfoPropMapper {
    /**
     * 查询信息属性
     * 
     * @param propId 信息属性主键
     * @return 信息属性
     */
    public SysInfoProp selectSysInfoPropByPropId(Long propId);

    /**
     * 查询信息属性列表
     * 
     * @param sysInfoProp 信息属性
     * @return 信息属性集合
     */
    public List<SysInfoProp> selectSysInfoPropList(SysInfoProp sysInfoProp);

    /**
     * 新增信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    public int insertSysInfoProp(SysInfoProp sysInfoProp);
    
    /**
     * 修改信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    public int updateSysInfoProp(SysInfoProp sysInfoProp);
    
    /**
     * 删除信息属性
     * 
     * @param propId 信息属性主键
     * @return 结果
     */
    public int deleteSysInfoPropByPropId(Long propId);

    /**
     * 批量删除信息属性
     * 
     * @param propIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInfoPropByPropIds(Long[] propIds);
    
    /**
     * 根据信息发布Id删除信息属性
     * 
     * @param infoId
     * @return
     */
    public int deleteSysInfoPropByInfoId(Long infoId);
    
    /**
     * 指定栏目属性Id下是否有信息属性
     * 
     * @param categroyPropIds
     * @return
     */
    public boolean hasSysInfoPropByCategoryPropId(Long[] categroyPropIds);
}

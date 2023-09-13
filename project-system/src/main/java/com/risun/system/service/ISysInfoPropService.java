package com.risun.system.service;

import java.util.List;

import com.risun.system.domain.SysInfoProp;

/**
 * 信息属性Service接口
 * 
 * @author sunchao
 * @date 2023-08-30
 */
public interface ISysInfoPropService {
    /**
     * 查询信息属性
     * 
     * @param propId 信息属性主键
     * @return 信息属性
     */
    SysInfoProp selectSysInfoPropByPropId(Long propId);

    /**
     * 查询信息属性列表
     * 
     * @param sysInfoProp 信息属性
     * @return 信息属性集合
     */
    List<SysInfoProp> selectSysInfoPropList(SysInfoProp sysInfoProp);

    /**
     * 新增信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    int insertSysInfoProp(SysInfoProp sysInfoProp);

    /**
     * 修改信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    int updateSysInfoProp(SysInfoProp sysInfoProp);

    /**
     * 批量删除信息属性
     * 
     * @param propIds 需要删除的信息属性主键集合
     * @return 结果
     */
    int deleteSysInfoPropByPropIds(Long[] propIds);

    /**
     * 删除信息属性信息
     * 
     * @param propId 信息属性主键
     * @return 结果
     */
    int deleteSysInfoPropByPropId(Long propId);
    
    /**
     * 指定栏目属性Id下是否有信息属性
     * 
     * @param categroyPropIds
     * @return
     */
    boolean hasSysInfoPropByCategoryPropId(Long[] categroyPropIds);
    
}

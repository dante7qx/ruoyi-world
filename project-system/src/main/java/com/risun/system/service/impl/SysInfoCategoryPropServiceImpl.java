package com.risun.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysInfoCategoryProp;
import com.risun.system.mapper.SysInfoCategoryPropMapper;
import com.risun.system.service.ISysInfoCategoryPropService;

/**
 * 信息栏目属性Service业务层处理
 * 
 * @author sunchao
 * @date 2023-08-29
 */
@Service
public class SysInfoCategoryPropServiceImpl implements ISysInfoCategoryPropService {
    @Autowired
    private SysInfoCategoryPropMapper sysInfoCategoryPropMapper;

    /**
     * 查询信息栏目属性
     * 
     * @param propId 信息栏目属性主键
     * @return 信息栏目属性
     */
    @Override
    public SysInfoCategoryProp selectSysInfoCategoryPropByPropId(Long propId) {
        return sysInfoCategoryPropMapper.selectSysInfoCategoryPropByPropId(propId);
    }

    /**
     * 查询信息栏目属性列表
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 信息栏目属性
     */
    @Override
    public List<SysInfoCategoryProp> selectSysInfoCategoryPropList(SysInfoCategoryProp sysInfoCategoryProp) {
        return sysInfoCategoryPropMapper.selectSysInfoCategoryPropList(sysInfoCategoryProp);
    }

    /**
     * 新增信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    @Override
    public int insertSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp) {
        sysInfoCategoryProp.setCreateBy(SecurityUtils.getUsername());
        sysInfoCategoryProp.setCreateTime(DateUtils.getNowDate());
        return sysInfoCategoryPropMapper.insertSysInfoCategoryProp(sysInfoCategoryProp);
    }

    /**
     * 修改信息栏目属性
     * 
     * @param sysInfoCategoryProp 信息栏目属性
     * @return 结果
     */
    @Override
    public int updateSysInfoCategoryProp(SysInfoCategoryProp sysInfoCategoryProp) {
        sysInfoCategoryProp.setUpdateBy(SecurityUtils.getUsername());
        sysInfoCategoryProp.setUpdateTime(DateUtils.getNowDate());
        return sysInfoCategoryPropMapper.updateSysInfoCategoryProp(sysInfoCategoryProp);
    }

    /**
     * 批量删除信息栏目属性
     * 
     * @param propIds 需要删除的信息栏目属性主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoCategoryPropByPropIds(Long[] propIds) {
        return sysInfoCategoryPropMapper.deleteSysInfoCategoryPropByPropIds(propIds);
    }

    /**
     * 删除信息栏目属性信息
     * 
     * @param propId 信息栏目属性主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoCategoryPropByPropId(Long propId) {
        return sysInfoCategoryPropMapper.deleteSysInfoCategoryPropByPropId(propId);
    }

    /**
     * 检查栏目下是否有栏目属性
     * 
     * @param categoryId
     * @return
     */
    @Override
    public boolean hasChildSysInfoCategoryPropByCategoryId(Long categoryId) {
    	return sysInfoCategoryPropMapper.hasChildSysInfoCategoryPropByCategoryId(categoryId);
    }
}

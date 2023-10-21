package com.spirit.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.common.exception.ServiceException;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.domain.SysInfoCategory;
import com.spirit.system.mapper.SysInfoCategoryMapper;
import com.spirit.system.service.ISysInfoCategoryService;

import cn.hutool.core.util.StrUtil;

/**
 * 信息栏目Service业务层处理
 * 
 * @author sunchao
 * @date 2023-08-29
 */
@Service
public class SysInfoCategoryServiceImpl implements ISysInfoCategoryService {
    @Autowired
    private SysInfoCategoryMapper sysInfoCategoryMapper;

    /**
     * 查询信息栏目
     * 
     * @param categoryId 信息栏目主键
     * @return 信息栏目
     */
    @Override
    public SysInfoCategory selectSysInfoCategoryByCategoryId(Long categoryId) {
        return sysInfoCategoryMapper.selectSysInfoCategoryByCategoryId(categoryId);
    }

    /**
     * 查询信息栏目列表
     * 
     * @param sysInfoCategory 信息栏目
     * @return 信息栏目
     */
    @Override
    public List<SysInfoCategory> selectSysInfoCategoryList(SysInfoCategory sysInfoCategory) {
        return sysInfoCategoryMapper.selectSysInfoCategoryList(sysInfoCategory);
    }

    /**
     * 新增信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    @Override
    public int insertSysInfoCategory(SysInfoCategory sysInfoCategory) {
    	SysInfoCategory category = sysInfoCategoryMapper.selectSysInfoCategoryByCategoryId(sysInfoCategory.getParentId());
    	if(category != null && category.getDisabled()) {
    		throw new ServiceException("栏目已停用，不允许新增");
    	}
    	
    	if(category != null && StrUtil.isNotEmpty(category.getAncestors())) {
    		sysInfoCategory.setAncestors(category.getAncestors() + "," + sysInfoCategory.getParentId());
    	} else {
    		sysInfoCategory.setAncestors(sysInfoCategory.getParentId().toString());
    	}
    	
        sysInfoCategory.setCreateBy(SecurityUtils.getUsername());
        sysInfoCategory.setCreateTime(DateUtils.getNowDate());
        return sysInfoCategoryMapper.insertSysInfoCategory(sysInfoCategory);
    }

    /**
     * 修改信息栏目
     * 
     * @param sysInfoCategory 信息栏目
     * @return 结果
     */
    @Override
    public int updateSysInfoCategory(SysInfoCategory sysInfoCategory) {
        sysInfoCategory.setUpdateBy(SecurityUtils.getUsername());
        sysInfoCategory.setUpdateTime(DateUtils.getNowDate());
        return sysInfoCategoryMapper.updateSysInfoCategory(sysInfoCategory);
    }

    /**
     * 批量删除信息栏目
     * 
     * @param categoryIds 需要删除的信息栏目主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoCategoryByCategoryIds(Long[] categoryIds) {
        return sysInfoCategoryMapper.deleteSysInfoCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除信息栏目信息
     * 
     * @param categoryId 信息栏目主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoCategoryByCategoryId(Long categoryId) {
        return sysInfoCategoryMapper.deleteSysInfoCategoryByCategoryId(categoryId);
    }

    /**
     * 检查是否含有子栏目
     * 
     * @param categoryId
     * @return
     */
    @Override
    public boolean hasChildSysInfoCategoryByCategoryId(Long categoryId) {
    	return sysInfoCategoryMapper.hasChildSysInfoCategoryByCategoryId(categoryId);
    }
    
}

package com.spirit.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.system.domain.SysInfoProp;
import com.spirit.system.mapper.SysInfoPropMapper;
import com.spirit.system.service.ISysInfoPropService;

/**
 * 信息属性Service业务层处理
 * 
 * @author sunchao
 * @date 2023-08-30
 */
@Service
public class SysInfoPropServiceImpl implements ISysInfoPropService {
    @Autowired
    private SysInfoPropMapper sysInfoPropMapper;

    /**
     * 查询信息属性
     * 
     * @param propId 信息属性主键
     * @return 信息属性
     */
    @Override
    public SysInfoProp selectSysInfoPropByPropId(Long propId) {
        return sysInfoPropMapper.selectSysInfoPropByPropId(propId);
    }

    /**
     * 查询信息属性列表
     * 
     * @param sysInfoProp 信息属性
     * @return 信息属性
     */
    @Override
    public List<SysInfoProp> selectSysInfoPropList(SysInfoProp sysInfoProp) {
        return sysInfoPropMapper.selectSysInfoPropList(sysInfoProp);
    }

    /**
     * 新增信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    @Override
    public int insertSysInfoProp(SysInfoProp sysInfoProp) {
        return sysInfoPropMapper.insertSysInfoProp(sysInfoProp);
    }

    /**
     * 修改信息属性
     * 
     * @param sysInfoProp 信息属性
     * @return 结果
     */
    @Override
    public int updateSysInfoProp(SysInfoProp sysInfoProp) {
        return sysInfoPropMapper.updateSysInfoProp(sysInfoProp);
    }

    /**
     * 批量删除信息属性
     * 
     * @param propIds 需要删除的信息属性主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoPropByPropIds(Long[] propIds) {
        return sysInfoPropMapper.deleteSysInfoPropByPropIds(propIds);
    }

    /**
     * 删除信息属性信息
     * 
     * @param propId 信息属性主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoPropByPropId(Long propId) {
        return sysInfoPropMapper.deleteSysInfoPropByPropId(propId);
    }
    
    /**
     * 指定栏目属性Id下是否有信息属性
     * 
     * @param categroyPropIds
     * @return
     */
    @Override
    public boolean hasSysInfoPropByCategoryPropId(Long[] categroyPropIds) {
    	return sysInfoPropMapper.hasSysInfoPropByCategoryPropId(categroyPropIds);
    }

}

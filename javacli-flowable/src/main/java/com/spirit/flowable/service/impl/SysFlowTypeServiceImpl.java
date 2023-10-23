package com.spirit.flowable.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.flowable.domain.SysFlowType;
import com.spirit.flowable.mapper.SysFlowTypeMapper;
import com.spirit.flowable.service.ISysFlowTypeService;

import cn.hutool.core.collection.CollUtil;;

/**
 * 流程类型Service业务层处理
 * 
 * @author sunchao
 * @date 2023-02-27
 */
@Service
public class SysFlowTypeServiceImpl implements ISysFlowTypeService 
{
    @Autowired
    private SysFlowTypeMapper sysFlowTypeMapper;

    /**
     * 查询流程类型
     * 
     * @param typeId 流程类型主键
     * @return 流程类型
     */
    @Override
    public SysFlowType selectSysFlowTypeByTypeId(Long typeId)
    {
        return sysFlowTypeMapper.selectSysFlowTypeByTypeId(typeId);
    }

    /**
     * 查询流程类型列表
     * 
     * @param sysFlowType 流程类型
     * @return 流程类型
     */
    @Override
    public List<SysFlowType> selectSysFlowTypeList(SysFlowType sysFlowType)
    {
        return sysFlowTypeMapper.selectSysFlowTypeList(sysFlowType);
    }
    
    /**
     * 查询当前用户有权限新建的流程类型
     * 
     * @param sysFlowType
     * @return
     */
    @Override
    public Map<String, Set<String>> selectLoginUserSysFlowTypeMap() {
    	SysFlowType query = new SysFlowType();
    	query.setLoginUserId(SecurityUtils.getUserId());
    	List<SysFlowType> list = sysFlowTypeMapper.selectSysFlowTypeList(query);
    	return list.stream().collect(groupingBy(SysFlowType::getFlowCategory, LinkedHashMap::new, mapping(SysFlowType::getTypeName, toSet())));
    }
    
    /**
     * 根据流程类型查询当前用户所属的流程定义
     * 
     * @param flowType，数据字典 sys_process_type
     * @return
     */
    @Override
    public SysFlowType selectLoginUserSysFlowType(String flowType) {
    	SysFlowType result = null;
    	SysFlowType query = new SysFlowType();
    	query.setTypeName(flowType);
    	query.setLoginUserId(SecurityUtils.getUserId());
    	List<SysFlowType> list = sysFlowTypeMapper.selectSysFlowTypeList(query);
    	if(CollUtil.isNotEmpty(list)) {
    		list.sort((p1, p2) -> Long.compare(p2.getOrderNum(), p1.getOrderNum()));
    		result = list.get(0);
    	}
    	return result;
    }

    /**
     * 新增流程类型
     * 
     * @param sysFlowType 流程类型
     * @return 结果
     */
    @Override
    public int insertSysFlowType(SysFlowType sysFlowType)
    {
        sysFlowType.setCreateBy(SecurityUtils.getUsername());
        sysFlowType.setCreateTime(DateUtils.getNowDate());
        return sysFlowTypeMapper.insertSysFlowType(sysFlowType);
    }

    /**
     * 修改流程类型
     * 
     * @param sysFlowType 流程类型
     * @return 结果
     */
    @Override
    public int updateSysFlowType(SysFlowType sysFlowType)
    {
        sysFlowType.setUpdateBy(SecurityUtils.getUsername());
        sysFlowType.setUpdateTime(DateUtils.getNowDate());
        return sysFlowTypeMapper.updateSysFlowType(sysFlowType);
    }

    /**
     * 批量删除流程类型
     * 
     * @param typeIds 需要删除的流程类型主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowTypeByTypeIds(Long[] typeIds)
    {
        return sysFlowTypeMapper.deleteSysFlowTypeByTypeIds(typeIds);
    }

    /**
     * 删除流程类型信息
     * 
     * @param typeId 流程类型主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowTypeByTypeId(Long typeId)
    {
        return sysFlowTypeMapper.deleteSysFlowTypeByTypeId(typeId);
    }
}

package com.risun.flowable.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.risun.flowable.domain.SysFlowType;

/**
 * 流程类型Service接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface ISysFlowTypeService 
{
    /**
     * 查询流程类型
     * 
     * @param typeId 流程类型主键
     * @return 流程类型
     */
    public SysFlowType selectSysFlowTypeByTypeId(Long typeId);

    /**
     * 查询流程类型列表
     * 
     * @param sysFlowType 流程类型
     * @return 流程类型集合
     */
    public List<SysFlowType> selectSysFlowTypeList(SysFlowType sysFlowType);
    
    /**
     * 查询当前用户有权限新建的流程类型
     * 
     * @param sysFlowType
     * @return
     */
    public Map<String, Set<String>> selectLoginUserSysFlowTypeMap();
    
    /**
     * 根据流程类型查询当前用户所属的流程定义
     * 
     * @param flowType，数据字典 sys_process_type
     * @return
     */
    public SysFlowType selectLoginUserSysFlowType(String flowType);

    /**
     * 新增流程类型
     * 
     * @param sysFlowType 流程类型
     * @return 结果
     */
    public int insertSysFlowType(SysFlowType sysFlowType);

    /**
     * 修改流程类型
     * 
     * @param sysFlowType 流程类型
     * @return 结果
     */
    public int updateSysFlowType(SysFlowType sysFlowType);

    /**
     * 批量删除流程类型
     * 
     * @param typeIds 需要删除的流程类型主键集合
     * @return 结果
     */
    public int deleteSysFlowTypeByTypeIds(Long[] typeIds);

    /**
     * 删除流程类型信息
     * 
     * @param typeId 流程类型主键
     * @return 结果
     */
    public int deleteSysFlowTypeByTypeId(Long typeId);
}

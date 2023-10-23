package com.spirit.flowable.mapper;

import java.util.List;
import com.spirit.flowable.domain.SysFlowType;

/**
 * 流程类型Mapper接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface SysFlowTypeMapper 
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
     * 删除流程类型
     * 
     * @param typeId 流程类型主键
     * @return 结果
     */
    public int deleteSysFlowTypeByTypeId(Long typeId);

    /**
     * 批量删除流程类型
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowTypeByTypeIds(Long[] typeIds);
}

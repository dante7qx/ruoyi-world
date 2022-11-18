package com.risun.flowable.mapper;

import java.util.List;
import com.risun.flowable.domain.SysFlowGroup;

/**
 * 流程审批组Mapper接口
 * 
 * @author sunchao
 * @date 2022-11-09
 */
public interface SysFlowGroupMapper 
{
    /**
     * 查询流程审批组
     * 
     * @param groupId 流程审批组主键
     * @return 流程审批组
     */
    public SysFlowGroup selectSysFlowGroupByGroupId(Long groupId);

    /**
     * 查询流程审批组列表
     * 
     * @param sysFlowGroup 流程审批组
     * @return 流程审批组集合
     */
    public List<SysFlowGroup> selectSysFlowGroupList(SysFlowGroup sysFlowGroup);

    /**
     * 新增流程审批组
     * 
     * @param sysFlowGroup 流程审批组
     * @return 结果
     */
    public int insertSysFlowGroup(SysFlowGroup sysFlowGroup);

    /**
     * 修改流程审批组
     * 
     * @param sysFlowGroup 流程审批组
     * @return 结果
     */
    public int updateSysFlowGroup(SysFlowGroup sysFlowGroup);

    /**
     * 删除流程审批组
     * 
     * @param groupId 流程审批组主键
     * @return 结果
     */
    public int deleteSysFlowGroupByGroupId(Long groupId);

    /**
     * 批量删除流程审批组
     * 
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowGroupByGroupIds(Long[] groupIds);
}

package com.spirit.flowable.mapper;

import java.util.List;
import com.spirit.flowable.domain.SysFlowAssign;

/**
 * 流程任务转办Mapper接口
 * 
 * @author sunchao
 * @date 2022-11-17
 */
public interface SysFlowAssignMapper 
{
    /**
     * 查询流程任务转办
     * 
     * @param flowAssignId 流程任务转办主键
     * @return 流程任务转办
     */
    public SysFlowAssign selectSysFlowAssignByFlowAssignId(Long flowAssignId);

    /**
     * 查询流程任务转办列表
     * 
     * @param sysFlowAssign 流程任务转办
     * @return 流程任务转办集合
     */
    public List<SysFlowAssign> selectSysFlowAssignList(SysFlowAssign sysFlowAssign);

    /**
     * 新增流程任务转办
     * 
     * @param sysFlowAssign 流程任务转办
     * @return 结果
     */
    public int insertSysFlowAssign(SysFlowAssign sysFlowAssign);

    /**
     * 修改流程任务转办
     * 
     * @param sysFlowAssign 流程任务转办
     * @return 结果
     */
    public int updateSysFlowAssign(SysFlowAssign sysFlowAssign);

    /**
     * 删除流程任务转办
     * 
     * @param flowAssignId 流程任务转办主键
     * @return 结果
     */
    public int deleteSysFlowAssignByFlowAssignId(Long flowAssignId);
    
    /**
     * 根据流程实例Id删除任务转办
     * 
     * @param procInstId
     * @return
     */
    public int deleteSysFlowAssignByProcInstId(String procInstId);

    /**
     * 批量删除流程任务转办
     * 
     * @param flowAssignIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowAssignByFlowAssignIds(Long[] flowAssignIds);
}
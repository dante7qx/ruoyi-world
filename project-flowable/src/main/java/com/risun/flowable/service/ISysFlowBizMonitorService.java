package com.risun.flowable.service;

import java.util.List;
import com.risun.flowable.domain.SysFlowBizMonitor;

/**
 * 业务流程监控Service接口
 * 
 * @author sunchao
 * @date 2022-11-11
 */
public interface ISysFlowBizMonitorService 
{
    /**
     * 查询业务流程监控
     * 
     * @param monitorId 业务流程监控主键
     * @return 业务流程监控
     */
    public SysFlowBizMonitor selectSysFlowBizMonitorByMonitorId(Long monitorId);
    
    /**
     * 根据业务Uid查询业务流程监控
     * 
     * @param bizUid
     * @return
     */
    public SysFlowBizMonitor selectSysFlowBizMonitorByBizUid(String bizUid);

    /**
     * 查询业务流程监控列表
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 业务流程监控集合
     */
    public List<SysFlowBizMonitor> selectSysFlowBizMonitorList(SysFlowBizMonitor sysFlowBizMonitor);

    /**
     * 新增业务流程监控
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 结果
     */
    public int insertSysFlowBizMonitor(SysFlowBizMonitor sysFlowBizMonitor);

    /**
     * 修改业务流程监控
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 结果
     */
    public int updateSysFlowBizMonitor(SysFlowBizMonitor sysFlowBizMonitor);

    /**
     * 批量删除业务流程监控
     * 
     * @param monitorIds 需要删除的业务流程监控主键集合
     * @return 结果
     */
    public int deleteSysFlowBizMonitorByMonitorIds(Long[] monitorIds);

    /**
     * 删除业务流程监控信息
     * 
     * @param monitorId 业务流程监控主键
     * @return 结果
     */
    public int deleteSysFlowBizMonitorByMonitorId(Long monitorId);
    
    /**
     * 根据bizUid删除业务流程监控
     * 
     * @param bizUid
     * @return
     */
    public int deleteSysFlowBizMonitorByBizUid(String bizUid);
    
    /**
     * 根据流程实例Id删除业务流程监控
     * 
     * @param procInstId
     * @return
     */
    public int deleteSysFlowBizMonitorByProcInstId(String procInstId);
}

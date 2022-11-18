package com.risun.flowable.service.impl;

import java.util.List;

import com.risun.common.utils.DateUtils;
import com.risun.flowable.domain.SysFlowBizMonitor;
import com.risun.flowable.mapper.SysFlowBizMonitorMapper;
import com.risun.flowable.service.ISysFlowBizMonitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务流程监控Service业务层处理
 * 
 * @author sunchao
 * @date 2022-11-11
 */
@Service
public class SysFlowBizMonitorServiceImpl implements ISysFlowBizMonitorService 
{
    @Autowired
    private SysFlowBizMonitorMapper sysFlowBizMonitorMapper;

    /**
     * 查询业务流程监控
     * 
     * @param monitorId 业务流程监控主键
     * @return 业务流程监控
     */
    @Override
    public SysFlowBizMonitor selectSysFlowBizMonitorByMonitorId(Long monitorId)
    {
        return sysFlowBizMonitorMapper.selectSysFlowBizMonitorByMonitorId(monitorId);
    }

    /**
     * 根据业务Uid查询业务流程监控
     * 
     * @param bizUid
     * @return
     */
    @Override
    public SysFlowBizMonitor selectSysFlowBizMonitorByBizUid(String bizUid) {
    	return sysFlowBizMonitorMapper.selectSysFlowBizMonitorBybizUid(bizUid);
    }
    
    /**
     * 查询业务流程监控列表
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 业务流程监控
     */
    @Override
    public List<SysFlowBizMonitor> selectSysFlowBizMonitorList(SysFlowBizMonitor sysFlowBizMonitor)
    {
        return sysFlowBizMonitorMapper.selectSysFlowBizMonitorList(sysFlowBizMonitor);
    }

    /**
     * 新增业务流程监控
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 结果
     */
    @Override
    public int insertSysFlowBizMonitor(SysFlowBizMonitor sysFlowBizMonitor)
    {
//        sysFlowBizMonitor.setCreateBy(SecurityUtils.getUsername());
        sysFlowBizMonitor.setCreateTime(DateUtils.getNowDate());
        return sysFlowBizMonitorMapper.insertSysFlowBizMonitor(sysFlowBizMonitor);
    }

    /**
     * 修改业务流程监控
     * 
     * @param sysFlowBizMonitor 业务流程监控
     * @return 结果
     */
    @Override
    public int updateSysFlowBizMonitor(SysFlowBizMonitor sysFlowBizMonitor)
    {
//        sysFlowBizMonitor.setUpdateBy(SecurityUtils.getUsername());
        sysFlowBizMonitor.setUpdateTime(DateUtils.getNowDate());
        return sysFlowBizMonitorMapper.updateSysFlowBizMonitor(sysFlowBizMonitor);
    }

    /**
     * 批量删除业务流程监控
     * 
     * @param monitorIds 需要删除的业务流程监控主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowBizMonitorByMonitorIds(Long[] monitorIds)
    {
        return sysFlowBizMonitorMapper.deleteSysFlowBizMonitorByMonitorIds(monitorIds);
    }

    /**
     * 删除业务流程监控信息
     * 
     * @param monitorId 业务流程监控主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowBizMonitorByMonitorId(Long monitorId)
    {
        return sysFlowBizMonitorMapper.deleteSysFlowBizMonitorByMonitorId(monitorId);
    }
    
    /**
     * 根据bizUid删除业务流程监控
     * 
     * @param bizUid
     * @return
     */
    @Override
    public int deleteSysFlowBizMonitorByBizUid(String bizUid) {
    	return sysFlowBizMonitorMapper.deleteSysFlowBizMonitorByBizUid(bizUid);
    }
    
    /**
     * 根据流程实例Id删除业务流程监控
     * 
     * @param procInstId
     * @return
     */
    @Override
    public int deleteSysFlowBizMonitorByProcInstId(String procInstId) {
    	return sysFlowBizMonitorMapper.deleteSysFlowBizMonitorByProcInstId(procInstId);
    }
}

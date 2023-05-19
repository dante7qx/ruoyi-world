package com.risun.report.mapper;

import java.util.List;

import com.risun.common.core.domain.entity.SysMenu;
import com.risun.report.domain.RiJmReport;
import com.risun.report.domain.RiJmReportMenu;

/**
 * 报表列表Mapper接口
 * 
 * @author sunchao
 * @date 2023-05-18
 */
public interface RiJmReportMapper 
{
    /**
     * 查询报表列表
     * 
     * @param id 报表列表主键
     * @return 报表列表
     */
    public RiJmReport selectRiJmReportById(String id);

    /**
     * 查询报表列表列表
     * 
     * @param jimuReport 报表列表
     * @return 报表列表集合
     */
    public List<RiJmReport> selectRiJmReportList(RiJmReport jimuReport);

    /**
     * 新增报表列表
     * 
     * @param jimuReport 报表列表
     * @return 结果
     */
    public int insertRiJmReport(RiJmReport jimuReport);

    /**
     * 修改报表列表
     * 
     * @param jimuReport 报表列表
     * @return 结果
     */
    public int updateRiJmReport(RiJmReport jimuReport);

    /**
     * 删除报表列表
     * 
     * @param id 报表列表主键
     * @return 结果
     */
    public int deleteRiJmReportById(String id);

    /**
     * 批量删除报表列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRiJmReportByIds(String[] ids);
    
    /**
     * 根据reportId查询报表菜单
     * 
     * @param reportId
     * @return
     */
    public List<RiJmReportMenu> selectRiJmReportMenuByReportId(String[] reportIds);
    
    /**
     * 新增报表菜单
     * 
     * @param riJmReportMenu
     * @return
     */
    public int insertRiJmReportMenu(RiJmReportMenu riJmReportMenu);
    
    /**
     * 批量删除报表菜单
     * 
     * @param ids
     * @return
     */
    public int deleteRiJmReportMenuByReportId(List<Long> ids);
    
    /**
     * 新增报表菜单
     * 
     * @param sysMenu
     * @return
     */
    public int insertMenuByReport(SysMenu sysMenu);
    
    /**
     * 删除报表数据字段
     * 
     * @param reportId
     * @return
     */
    public int deleteRiJmReportDbFieldByReportId(String reportId);
    
    /**
     * 删除报表数据
     * 
     * @param reportId
     * @return
     */
    public int deleteRiJmReportDbByReportId(String reportId);
    
    /**
     * 删除报表分享
     * 
     * @param reportId
     * @return
     */
    public int deleteRiJmReportShareByReportId(String reportId);
    
}

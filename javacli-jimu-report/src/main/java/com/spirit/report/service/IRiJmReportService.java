package com.spirit.report.service;

import java.util.List;
import com.spirit.report.domain.RiJmReport;

/**
 * 报表列表Service接口
 * 
 * @author sunchao
 * @date 2023-05-18
 */
public interface IRiJmReportService 
{
    /**
     * 查询报表列表
     * 
     * @param id 报表列表主键
     * @return 报表列表
     */
    public RiJmReport selectJimuReportById(String id);

    /**
     * 查询报表列表列表
     * 
     * @param jimuReport 报表列表
     * @return 报表列表集合
     */
    public List<RiJmReport> selectJimuReportList(RiJmReport jimuReport);

    /**
     * 新增报表菜单
     * 
     * @param reportIds 报表Ids
     * @return 结果
     */
    public int insertJimuReportMenu(RiJmReport jimuReport);
    
    /**
     * 取消报表菜单
     * 
     * @param jimuReport
     * @return
     */
    public int cancelJimuReportMenu(String[] ids);

    /**
     * 修改报表列表
     * 
     * @param jimuReport 报表列表
     * @return 结果
     */
    public int updateJimuReport(RiJmReport jimuReport);

    /**
     * 批量删除报表列表
     * 
     * @param ids 需要删除的报表列表主键集合
     * @return 结果
     */
    public int deleteJimuReportByIds(String[] ids);
    
    /**
     * 添加部门报表权限
     * 
     * @param deptId
     * @param reportIds
     * @return
     */
    public int setupDeptAcl(Long deptId, String[] reportIds);
    
    /**
     * 移除部门报表权限
     * 
     * @param deptId
     * @param reportIds
     * @return
     */
    public int removeDeptAcl(Long deptId, String[] reportIds);

}

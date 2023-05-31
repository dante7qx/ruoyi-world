package com.risun.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.risun.report.domain.RiJmReportDeptAcl;

/**
 * 积木报部门权限Mapper接口
 * 
 * @author sunchao
 * @date 2023-05-29
 */
public interface RiJmReportDeptAclMapper 
{
    /**
     * 查询积木报部门权限
     * 
     * @param id 积木报部门权限主键
     * @return 积木报部门权限
     */
    public RiJmReportDeptAcl selectRiJmReportDeptAclById(Long id);

    /**
     * 查询积木报部门权限列表
     * 
     * @param riJmReportDeptAcl 积木报部门权限
     * @return 积木报部门权限集合
     */
    public List<RiJmReportDeptAcl> selectRiJmReportDeptAclList(RiJmReportDeptAcl riJmReportDeptAcl);

    /**
     * 新增积木报部门权限
     * 
     * @param riJmReportDeptAcl 积木报部门权限
     * @return 结果
     */
    public int insertRiJmReportDeptAcl(RiJmReportDeptAcl riJmReportDeptAcl);

    /**
     * 修改积木报部门权限
     * 
     * @param riJmReportDeptAcl 积木报部门权限
     * @return 结果
     */
    public int updateRiJmReportDeptAcl(RiJmReportDeptAcl riJmReportDeptAcl);

    /**
     * 删除积木报部门权限
     * 
     * @param id 积木报部门权限主键
     * @return 结果
     */
    public int deleteRiJmReportDeptAclById(Long id);

    /**
     * 批量删除积木报部门权限
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRiJmReportDeptAclByIds(Long[] ids);
    
    /**
     * 根据reportId删除积木报部门权限
     * 
     * @param reportId
     * @return
     */
    public int deleteRiJmReportDeptAclByReportId(String reportId);
    
    /**
     * 根据deptId删除积木报部门权限
     * 
     * @param deptId
     * @return
     */
    public int deleteRiJmReportDeptAclByDeptId(@Param("deptId") Long deptId, @Param("reportIds") String[] reportIds);
    
}

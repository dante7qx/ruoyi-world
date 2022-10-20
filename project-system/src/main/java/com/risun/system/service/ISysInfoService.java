package com.risun.system.service;

import java.util.List;
import com.risun.system.domain.SysInfo;

/**
 * 信息发布Service接口
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public interface ISysInfoService 
{
    /**
     * 查询信息发布
     * 
     * @param infoId 信息发布主键
     * @return 信息发布
     */
    public SysInfo selectSysInfoByInfoId(Long infoId);

    /**
     * 查询信息发布列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布集合
     */
    public List<SysInfo> selectSysInfoList(SysInfo sysInfo);

    /**
     * 新增信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    public int insertSysInfo(SysInfo sysInfo);

    /**
     * 修改信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    public int updateSysInfo(SysInfo sysInfo);

    /**
     * 批量删除信息发布
     * 
     * @param infoIds 需要删除的信息发布主键集合
     * @return 结果
     */
    public int deleteSysInfoByInfoIds(Long[] infoIds);

    /**
     * 删除信息发布信息
     * 
     * @param infoId 信息发布主键
     * @return 结果
     */
    public int deleteSysInfoByInfoId(Long infoId);
    
    /**
     * 批量审批
     * 
     * @param sysInfo
     * @return
     */
    public int batchApproval(SysInfo sysInfo);
    
    /**
     * 置顶信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    public int setInfoTop(SysInfo sysInfo);
    
    /**
     * 停用（启用）信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    public int setInfoDisabled(SysInfo sysInfo);
    
    /**
     * 设置（取消）匿名访问信息发布
     * 
     * @param sysInfo
     * @return
     */
    public int setAnonymousSysInfo(SysInfo sysInfo);
    
    /**
     * 设置访问范围信息发布
     * 
     * @param sysInfo
     * @return
     */
    public int setRangeSysInfo(SysInfo sysInfo);
    
    /**
     * 根据信息Id获取访问范围
     * 
     * @param infoId
     * @return
     */
    public Long[] selectInfoRangeByInfoId(Long infoId);
}

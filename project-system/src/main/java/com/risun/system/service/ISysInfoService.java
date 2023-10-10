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
    SysInfo selectSysInfoByInfoId(Long infoId);
    
    /**
     * 查询信息发布4浏览详情
     * 
     * @param infoId 信息发布主键
     * @return 信息发布
     */
    SysInfo selectSysInfo4ViewByInfoId(Long infoId);

    /**
     * 查询信息发布列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布集合
     */
    List<SysInfo> selectSysInfoList(SysInfo sysInfo);
    
    /**
     * 查询信息发布浏览列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布集合
     */
    List<SysInfo> selectSysInfoList4View(SysInfo sysInfo);

    /**
     * 新增信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    int insertSysInfo(SysInfo sysInfo);

    /**
     * 修改信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    int updateSysInfo(SysInfo sysInfo);

    /**
     * 批量删除信息发布
     * 
     * @param infoIds 需要删除的信息发布主键集合
     * @return 结果
     */
    int deleteSysInfoByInfoIds(Long[] infoIds);

    /**
     * 删除信息发布信息
     * 
     * @param infoId 信息发布主键
     * @return 结果
     */
    int deleteSysInfoByInfoId(Long infoId);
    
    /**
     * 批量审批
     * 
     * @param sysInfo
     * @return
     */
    int batchApproval(SysInfo sysInfo);
    
    /**
     * 置顶信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    int setInfoTop(SysInfo sysInfo);
    
    /**
     * 停用（启用）信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    int setInfoDisabled(SysInfo sysInfo);
    
    /**
     * 撤销已发布信息
     * 
     * @param sysInfo
     * @return
     */
    int withdrawSysInfo(SysInfo sysInfo);
    
    /**
     * 设置（取消）评论信息发布
     * 
     * @param sysInfo
     * @return
     */
    int setCommentSysInfo(SysInfo sysInfo);
    
    /**
     * 设置（取消）匿名访问信息发布
     * 
     * @param sysInfo
     * @return
     */
    int setAnonymousSysInfo(SysInfo sysInfo);
    
    /**
     * 设置访问范围信息发布
     * 
     * @param sysInfo
     * @return
     */
    int setRangeSysInfo(SysInfo sysInfo);
    
    /**
     * 根据信息Id获取访问范围
     * 
     * @param infoId
     * @return
     */
    Long[] selectInfoRangeByInfoId(Long infoId);
    
    /**
     * 指定栏目Id下是否有信息发布
     * 
     * @param categoryId
     * @return
     */
    boolean hasSysInfoByCategoryId(Long categoryId);
    
    /**
     * 更新信息发布浏览量
     * 
     * @param infoId
     * @param viewIP
     * @return
     */
    void updateSysInfoViewCount(Long infoId, String viewIP);
    
    /**
     * 收藏
     * 
     * @param infoId
     * @return
     */
    int updateSysInfoFavorCount(Long infoId);
    
    /**
     * 点赞
     * 
     * @param infoId
     * @return
     */
    int updateSysInfoIncreasePraiseCount(Long infoId);
    
    /**
     * 取消点赞
     * 
     * @param infoId
     * @return
     */
    int updateSysInfoDecreasePraiseCount(Long infoId);
}

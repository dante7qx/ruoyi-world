package com.risun.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.risun.system.domain.SysInfo;
import com.risun.system.domain.SysInfoView;

/**
 * 信息发布Mapper接口
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public interface SysInfoMapper 
{
    /**
     * 查询信息发布
     * 
     * @param infoId 信息发布主键
     * @return 信息发布
     */
    public SysInfo selectSysInfoByInfoId(Long infoId);
    
    /**
     * 查询信息发布4浏览详情
     * 
     * @param sysInfo
     * @return 信息发布
     */
    public SysInfo selectSysInfo4ViewByInfoId(SysInfo sysInfo);

    /**
     * 查询信息发布列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布集合
     */
    public List<SysInfo> selectSysInfoList(SysInfo sysInfo);
    
    /**
     * 查询信息发布浏览列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布集合
     */
    public List<SysInfo> selectSysInfoList4View(SysInfo sysInfo);
    
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
     * 删除信息发布
     * 
     * @param infoId 信息发布主键
     * @return 结果
     */
    public int deleteSysInfoByInfoId(Long infoId);

    /**
     * 批量删除信息发布
     * 
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInfoByInfoIds(Long[] infoIds);
    
    /**
     * 取消置顶
     * 
     * @return
     */
    public int cancelSysInfoTop();
    
    /**
     * 停用（启用）信息发布
     * 
     * @param sysInfo
     * @return
     */
    public int disabledSysInfo(SysInfo sysInfo);
    
    /**
     * 设置（取消）匿名访问信息发布
     * 
     * @param sysInfo
     * @return
     */
    public int setAnonymousSysInfo(SysInfo sysInfo);
    
    /**
     * 指定栏目Id下是否有信息发布
     * 
     * @param categoryId
     * @return
     */
    public boolean hasSysInfoByCategoryId(Long categoryId);
    
    /**
     * 删除非当前天的访问记录
     * 
     * @param viewDate (yyyy-MM-dd)
     * @return
     */
    @InterceptorIgnore(blockAttack = "true")
    public int deletePrevSysInfoView(String viewDate);
    
    /**
     * 更新信息发布浏览数
     * 
     * @param infoId
     * @return
     */
    public int updateSysInfoViewCount(Long infoId);
    
    /**
     * 新增信息发布浏览记录
     * 
     * @param sysInfoView
     * @return
     */
    public int insertSysInfoView(SysInfoView sysInfoView);
    
    /**
     * 查询信息发布浏览记录
     * 
     * @param sysInfoView
     * @return
     */
    public SysInfoView selectSysInfoViewByInfoView(SysInfoView sysInfoView);
    
    /**
     * 评论设置
     * 
     * @param infoIds
     * @param commentable 
     * @return
     */
    public int updateSysInfoCommentable(@Param("infoIds") Long[] infoIds, @Param("commentable") Integer commentable);
    
    /**
     * 收藏
     * 
     * @param infoId
     * @return
     */
    public int updateSysInfoFavorCount(Long infoId);
    
    /**
     * 点赞
     * 
     * @param infoId
     * @return
     */
    public int updateSysInfoIncreasePraiseCount(Long infoId);
    
    /**
     * 取消点赞
     * 
     * @param infoId
     * @return
     */
    public int updateSysInfoDecreasePraiseCount(Long infoId);
    
}

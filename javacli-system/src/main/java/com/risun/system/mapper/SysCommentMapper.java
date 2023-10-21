package com.risun.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.risun.system.domain.SysComment;

/**
 * 留言管理Mapper接口
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public interface SysCommentMapper 
{
    /**
     * 查询留言管理
     * 
     * @param commentId 留言管理主键
     * @return 留言管理
     */
    public SysComment selectSysCommentByCommentId(Long commentId);

    /**
     * 查询留言管理列表
     * 
     * @param sysComment 留言管理
     * @return 留言管理集合
     */
    public List<SysComment> selectSysCommentList(SysComment sysComment);

    /**
     * 根据业务模块和业务Id查询评论
     * 
     * @param bizModel
     * @param bizId
     * @return
     */
    public List<SysComment> selectSysCommentByBizModelAndId(@Param("bizModel") String bizModel, @Param("bizId") Long bizId);
    
    /**
     * 新增留言管理
     * 
     * @param sysComment 留言管理
     * @return 结果
     */
    public int insertSysComment(SysComment sysComment);

    /**
     * 修改留言管理
     * 
     * @param sysComment 留言管理
     * @return 结果
     */
    public int updateSysComment(SysComment sysComment);

    /**
     * 删除留言管理
     * 
     * @param commentId 留言管理主键
     * @return 结果
     */
    public int deleteSysCommentByCommentId(Long commentId);

    /**
     * 批量删除留言管理
     * 
     * @param commentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCommentByCommentIds(Long[] commentIds);
}

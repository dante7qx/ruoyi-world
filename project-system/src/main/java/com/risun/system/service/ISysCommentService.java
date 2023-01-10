package com.risun.system.service;

import java.util.List;

import com.risun.system.domain.SysComment;

/**
 * 留言管理Service接口
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public interface ISysCommentService 
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
    public List<SysComment> selectSysCommentByBizModelAndId(String bizModel, Long bizId);
    
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
     * 批量删除留言管理
     * 
     * @param commentIds 需要删除的留言管理主键集合
     * @return 结果
     */
    public int deleteSysCommentByCommentIds(Long[] commentIds);

    /**
     * 删除留言管理信息
     * 
     * @param commentId 留言管理主键
     * @return 结果
     */
    public int deleteSysCommentByCommentId(Long commentId);
    
    /**
     * 删除指定业务的留言管理信息
     * 
     * @param commentId 留言管理主键
     * @return 结果
     */
    public int deleteSysCommentByBizModelAndId(String bizModel, Long bizId);
    
    /**
     * 用户评论点赞
     * 
     * @param commentId
     * @return
     */
    public int userLike(Long commentId);
}

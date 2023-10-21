package com.spirit.system.mapper;

import java.util.List;
import com.spirit.system.domain.SysCommentLike;

/**
 * 留言点赞Mapper接口
 * 
 * @author sunchao
 * @date 2023-01-09
 */
public interface SysCommentLikeMapper 
{
    /**
     * 查询留言点赞
     * 
     * @param likeId 留言点赞主键
     * @return 留言点赞
     */
    public SysCommentLike selectSysCommentLikeByLikeId(Long likeId);
    
    /**
     * 查询留言点赞列表
     * 
     * @param sysCommentLike 留言点赞
     * @return 留言点赞集合
     */
    public List<SysCommentLike> selectSysCommentLikeList(SysCommentLike sysCommentLike);

    /**
     * 新增留言点赞
     * 
     * @param sysCommentLike 留言点赞
     * @return 结果
     */
    public int insertSysCommentLike(SysCommentLike sysCommentLike);

    /**
     * 修改留言点赞
     * 
     * @param sysCommentLike 留言点赞
     * @return 结果
     */
    public int updateSysCommentLike(SysCommentLike sysCommentLike);

    /**
     * 删除留言点赞
     * 
     * @param likeId 留言点赞主键
     * @return 结果
     */
    public int deleteSysCommentLikeByLikeId(Long likeId);
    
    /**
     * 批量删除留言点赞
     * 
     * @param likeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCommentLikeByLikeIds(Long[] likeIds);
    
    /**
     * 根据commentId删除留言点赞
     * 
     * @param commentId 评论Id
     * @return 结果
     */
    public int deleteSysCommentLikeByCommentId(Long commentId);
    
    /**
     * 根据commentIds删除留言点赞
     * 
     * @param commentIds 评论Id数组
     * @return 结果
     */
    public int deleteSysCommentLikeByCommentIds(Long[] commentIds);
    
}

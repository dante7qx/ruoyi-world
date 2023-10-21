package com.spirit.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spirit.system.domain.SysCommentReply;

/**
 * 留言评论回复Mapper接口
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public interface SysCommentReplyMapper 
{
    /**
     * 查询留言评论回复
     * 
     * @param replyId 留言评论回复主键
     * @return 留言评论回复
     */
    public SysCommentReply selectSysCommentReplyByReplyId(Long replyId);

    /**
     * 查询留言评论回复列表
     * 
     * @param sysCommentReply 留言评论回复
     * @return 留言评论回复集合
     */
    public List<SysCommentReply> selectSysCommentReplyList(SysCommentReply sysCommentReply);
    
    /**
     * 根据commentId查询回复信息
     * 
     * @param sysCommentReply 留言评论回复
     * @return 留言评论回复集合
     */
    public List<SysCommentReply> selectSysCommentReplyByCommentId(Long commentId);
    
    /**
     * 根据commentIds查询回复信息
     * 
     * @param commentIds
     * @return
     */
    public List<SysCommentReply> selectSysCommentReplyByCommentIds(@Param("commentIds") List<Long> commentIds);

    /**
     * 新增留言评论回复
     * 
     * @param sysCommentReply 留言评论回复
     * @return 结果
     */
    public int insertSysCommentReply(SysCommentReply sysCommentReply);

    /**
     * 修改留言评论回复
     * 
     * @param sysCommentReply 留言评论回复
     * @return 结果
     */
    public int updateSysCommentReply(SysCommentReply sysCommentReply);

    /**
     * 删除留言评论回复
     * 
     * @param replyId 留言评论回复主键
     * @return 结果
     */
    public int deleteSysCommentReplyByReplyId(Long replyId);

    /**
     * 批量删除留言评论回复
     * 
     * @param replyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCommentReplyByReplyIds(Long[] replyIds);
    
    /**
     * 删除指定评论的回复信息
     * 
     * @param commentIds
     * @return
     */
    public int deleteSysCommentReplyByCommentIds(@Param("commentIds") List<Long> commentIds);
}

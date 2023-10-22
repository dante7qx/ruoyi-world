package com.spirit.system.service;

import java.util.List;
import com.spirit.system.domain.SysCommentReply;

/**
 * 留言评论回复Service接口
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public interface ISysCommentReplyService 
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
     * 批量删除留言评论回复
     * 
     * @param replyIds 需要删除的留言评论回复主键集合
     * @return 结果
     */
    public int deleteSysCommentReplyByReplyIds(Long[] replyIds);

    /**
     * 删除留言评论回复信息
     * 
     * @param replyId 留言评论回复主键
     * @return 结果
     */
    public int deleteSysCommentReplyByReplyId(Long replyId);
}

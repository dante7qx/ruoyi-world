package com.risun.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysCommentReply;
import com.risun.system.mapper.SysCommentReplyMapper;
import com.risun.system.service.ISysCommentReplyService;

/**
 * 留言评论回复Service业务层处理
 * 
 * @author sunchao
 * @date 2023-01-05
 */
@Service
public class SysCommentReplyServiceImpl implements ISysCommentReplyService 
{
    @Autowired
    private SysCommentReplyMapper sysCommentReplyMapper;

    /**
     * 查询留言评论回复
     * 
     * @param replyId 留言评论回复主键
     * @return 留言评论回复
     */
    @Override
    public SysCommentReply selectSysCommentReplyByReplyId(Long replyId)
    {
        return sysCommentReplyMapper.selectSysCommentReplyByReplyId(replyId);
    }

    /**
     * 查询留言评论回复列表
     * 
     * @param sysCommentReply 留言评论回复
     * @return 留言评论回复
     */
    @Override
    public List<SysCommentReply> selectSysCommentReplyList(SysCommentReply sysCommentReply)
    {
        return sysCommentReplyMapper.selectSysCommentReplyList(sysCommentReply);
    }

    /**
     * 新增留言评论回复
     * 
     * @param sysCommentReply 留言评论回复
     * @return 结果
     */
    @Override
    public int insertSysCommentReply(SysCommentReply sysCommentReply)
    {
    	sysCommentReply.setFromId(SecurityUtils.getUsername());
    	sysCommentReply.setCommentDate(DateUtils.getNowDate());
        return sysCommentReplyMapper.insertSysCommentReply(sysCommentReply);
    }

    /**
     * 修改留言评论回复
     * 
     * @param sysCommentReply 留言评论回复
     * @return 结果
     */
    @Override
    public int updateSysCommentReply(SysCommentReply sysCommentReply)
    {
        return sysCommentReplyMapper.updateSysCommentReply(sysCommentReply);
    }

    /**
     * 批量删除留言评论回复
     * 
     * @param replyIds 需要删除的留言评论回复主键
     * @return 结果
     */
    @Override
    public int deleteSysCommentReplyByReplyIds(Long[] replyIds)
    {
        return sysCommentReplyMapper.deleteSysCommentReplyByReplyIds(replyIds);
    }

    /**
     * 删除留言评论回复信息
     * 
     * @param replyId 留言评论回复主键
     * @return 结果
     */
    @Override
    public int deleteSysCommentReplyByReplyId(Long replyId)
    {
        return sysCommentReplyMapper.deleteSysCommentReplyByReplyId(replyId);
    }
}

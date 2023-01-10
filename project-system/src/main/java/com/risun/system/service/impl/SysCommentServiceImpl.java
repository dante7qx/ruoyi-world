package com.risun.system.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysComment;
import com.risun.system.domain.SysCommentLike;
import com.risun.system.domain.SysCommentReply;
import com.risun.system.mapper.SysCommentLikeMapper;
import com.risun.system.mapper.SysCommentMapper;
import com.risun.system.mapper.SysCommentReplyMapper;
import com.risun.system.service.ISysCommentService;

import cn.hutool.core.collection.CollUtil;

/**
 * 留言管理Service业务层处理
 * 
 * @author sunchao
 * @date 2023-01-05
 */
@Service
public class SysCommentServiceImpl implements ISysCommentService {
	@Autowired
	private SysCommentMapper sysCommentMapper;
	@Autowired
	private SysCommentReplyMapper sysCommentReplyMapper;
	@Autowired
	private SysCommentLikeMapper sysCommentLikeMapper;
	
	/**
	 * 查询留言管理
	 * 
	 * @param commentId 留言管理主键
	 * @return 留言管理
	 */
	@Override
	public SysComment selectSysCommentByCommentId(Long commentId) {
		SysComment comment = sysCommentMapper.selectSysCommentByCommentId(commentId);
		comment.setReply(sysCommentReplyMapper.selectSysCommentReplyByCommentId(commentId));
		return comment;
	}

	/**
	 * 查询留言管理列表
	 * 
	 * @param sysComment 留言管理
	 * @return 留言管理
	 */
	@Override
	public List<SysComment> selectSysCommentList(SysComment sysComment) {
		return sysCommentMapper.selectSysCommentList(sysComment);
	}

	/**
	 * 根据业务模块和业务Id查询评论
	 * 
	 * @param bizModel
	 * @param bizId
	 * @return
	 */
	@Override
	public List<SysComment> selectSysCommentByBizModelAndId(String bizModel, Long bizId) {
		List<SysComment> comments = sysCommentMapper.selectSysCommentByBizModelAndId(bizModel, bizId);
		List<Long> commentIds = comments.stream().map(SysComment::getCommentId).collect(toList());
		if(CollUtil.isNotEmpty(commentIds)) {
			List<SysCommentReply> replys = sysCommentReplyMapper.selectSysCommentReplyByCommentIds(commentIds);
			for (SysComment comment : comments) {
				comment.setReply(replys.stream().filter(r -> r.getCommentId().equals(comment.getCommentId())).collect(toList()));
			}
		}
		return comments;
	}

	/**
	 * 新增留言管理
	 * 
	 * @param sysComment 留言管理
	 * @return 结果
	 */
	@Override
	public int insertSysComment(SysComment sysComment) {
		sysComment.setFromId(SecurityUtils.getUsername());
		sysComment.setCommentDate(DateUtils.getNowDate());
		return sysCommentMapper.insertSysComment(sysComment);
	}

	/**
	 * 修改留言管理
	 * 
	 * @param sysComment 留言管理
	 * @return 结果
	 */
	@Override
	public int updateSysComment(SysComment sysComment) {
		return sysCommentMapper.updateSysComment(sysComment);
	}

	/**
	 * 批量删除留言管理
	 * 
	 * @param commentIds 需要删除的留言管理主键
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysCommentByCommentIds(Long[] commentIds) {
		sysCommentReplyMapper.deleteSysCommentReplyByCommentIds(Arrays.asList(commentIds));
		return sysCommentMapper.deleteSysCommentByCommentIds(commentIds);
	}

	/**
	 * 删除留言管理信息
	 * 
	 * @param commentId 留言管理主键
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysCommentByCommentId(Long commentId) {
		sysCommentReplyMapper.deleteSysCommentReplyByCommentIds(Lists.newArrayList(commentId));
		sysCommentLikeMapper.deleteSysCommentLikeByCommentId(commentId);
		return sysCommentMapper.deleteSysCommentByCommentId(commentId);
	}
	
	/**
     * 删除指定业务的留言管理信息
     * 
     * @param commentId 留言管理主键
     * @return 结果
     */
	@Override
	@Transactional
    public int deleteSysCommentByBizModelAndId(String bizModel, Long bizId) {
    	List<SysComment> comments = sysCommentMapper.selectSysCommentByBizModelAndId(bizModel, bizId);
		List<Long> commentIds = comments.stream().map(SysComment::getCommentId).collect(toList());
		if(CollUtil.isNotEmpty(commentIds)) {
			sysCommentReplyMapper.deleteSysCommentReplyByCommentIds(commentIds);
			sysCommentLikeMapper.deleteSysCommentLikeByCommentIds(commentIds.toArray(new Long[commentIds.size()]));
		}
    	return sysCommentMapper.deleteSysCommentByCommentIds(commentIds.toArray(new Long[commentIds.size()]));
    }
	
	/**
     * 用户评论点赞
     * 
     * @param commentLike
     * @return
     */
    public int userLike(Long commentId) {
    	int result = 0;
    	SysCommentLike commentLike = new SysCommentLike();
    	commentLike.setCommentId(commentId);
    	commentLike.setUserId(SecurityUtils.getUsername());
    	commentLike.setLikeDate(DateUtils.getNowDate());
    	List<SysCommentLike> commentLikes = sysCommentLikeMapper.selectSysCommentLikeList(commentLike);
    	if(CollUtil.isNotEmpty(commentLikes)) {
    		// 取消点赞
    		result = sysCommentLikeMapper.deleteSysCommentLikeByLikeId(commentLikes.get(0).getLikeId());
//    		result = 1;
    	} else {
    		result = sysCommentLikeMapper.insertSysCommentLike(commentLike);
    	}
    	return result;
    }
	
}

package com.risun.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.core.domain.BaseEntity;

/**
 * 留言点赞对象 sys_comment_like
 * 
 * @author sunchao
 * @date 2023-01-09
 */
public class SysCommentLike extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 点赞id */
	private Long likeId;

	/** 评论id */
	private Long commentId;

	/** 评论者id */
	private String userId;

	/** 点赞时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date likeDate;
	
	/** 点赞（1 点赞 0 取消） */
	private Boolean likeFlag;

	public void setLikeId(Long likeId) {
		this.likeId = likeId;
	}

	public Long getLikeId() {
		return likeId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setLikeFlag(Boolean likeFlag) {
		this.likeFlag = likeFlag;
	}

	public Boolean getLikeFlag() {
		return likeFlag;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("likeId", getLikeId())
				.append("userId", getUserId())
				.append("likeFlag", getLikeFlag())
				.append("likeDate", getLikeDate())
				.toString();
	}
}

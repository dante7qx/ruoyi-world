package com.spirit.system.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 留言管理对象 sys_comment
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public class SysComment extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 评论id */
	private Long commentId;

	/** 业务模块 */
	@Excel(name = "业务模块")
	private String bizModel;

	/** 业务id */
	@Excel(name = "业务id")
	private Long bizId;

	/** 评论者id */
	private String fromId;

	/** 评论者昵称 */
	@Excel(name = "评论者")
	private String fromName;

	/** 评论者头像 */
	private String fromAvatar;

	/** 评论时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Excel(name = "评论时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date commentDate;

	/** 评论内容 */
	@Excel(name = "评论内容")
	private String content;

	/** 评论图片 */
	@Excel(name = "评论图片")
	private String imgUrl;
	
	/** 评论缩略图 */
	private String thumbUrl;

	/** 评论视频 */
	@Excel(name = "评论视频")
	private String videoUrl;

	/** 回复 */
	private List<SysCommentReply> reply;
	
	/** 点赞数 */
	private int likeNum;

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setBizModel(String bizModel) {
		this.bizModel = bizModel;
	}

	public String getBizModel() {
		return bizModel;
	}

	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	public Long getBizId() {
		return bizId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromId() {
		return fromId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAvatar() {
		return fromAvatar;
	}

	public void setFromAvatar(String fromAvatar) {
		this.fromAvatar = fromAvatar;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public List<SysCommentReply> getReply() {
		return reply;
	}

	public void setReply(List<SysCommentReply> reply) {
		this.reply = reply;
	}

	public int getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("commentId", getCommentId())
				.append("bizModel", getBizModel()).append("bizId", getBizId()).append("fromId", getFromId())
				.append("commentDate", getCommentDate()).append("content", getContent()).append("imgUrl", getImgUrl())
				.append("videoUrl", getVideoUrl()).toString();
	}
}

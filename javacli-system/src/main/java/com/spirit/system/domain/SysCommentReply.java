package com.spirit.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.spirit.common.annotation.Excel;
import com.spirit.common.core.domain.BaseEntity;

/**
 * 留言评论回复对象 sys_comment_reply
 * 
 * @author sunchao
 * @date 2023-01-05
 */
public class SysCommentReply extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 回复id */
	private Long replyId;

	/** 父评论id */
	@Excel(name = "父评论id")
	private Long commentId;

	/** 评论者id */
	@Excel(name = "评论者id")
	private String fromId;

	/** 评论者昵称 */
	@Excel(name = "评论者")
	private String fromName;

	/** 评论者头像 */
	private String fromAvatar;

	/** 被评论者id */
	@Excel(name = "被评论者id")
	private String toId;

	/** 被评论者昵称 */
	@Excel(name = "评论者")
	private String toName;

	/** 被评论者头像 */
	private String toAvatar;

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

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getCommentId() {
		return commentId;
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

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getToId() {
		return toId;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToAvatar() {
		return toAvatar;
	}

	public void setToAvatar(String toAvatar) {
		this.toAvatar = toAvatar;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("replyId", getReplyId())
				.append("commentId", getCommentId()).append("fromId", getFromId()).append("toId", getToId())
				.append("commentDate", getCommentDate()).append("content", getContent()).append("imgUrl", getImgUrl())
				.append("videoUrl", getVideoUrl()).toString();
	}
}

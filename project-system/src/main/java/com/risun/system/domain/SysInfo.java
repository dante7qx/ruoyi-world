package com.risun.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.risun.common.annotation.Excel;
import com.risun.common.core.domain.BaseEntity;

/**
 * 信息发布对象 sys_info
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public class SysInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 草稿箱 */
	public static final String DRAFT_STATUS = "0";
	/** 待审批 */
	public static final String DSP_STATUS = "1";
	/** 已发布 */
	public static final String PUBLISH_STATUS = "2";

	/** 信息id */
	private Long infoId;

	/** 标题 */
	@Excel(name = "标题")
	private String title;

	/** 副标题 */
	@Excel(name = "副标题")
	private String subTitle;

	/** 封面 */
	@Excel(name = "封面")
	private String cover;

	/** 简介 */
	@Excel(name = "简介")
	private String summary;

	/** 内容 */
	@Excel(name = "内容")
	private String content;

	/** 栏目Id */
	private Long categoryId;

	/** 栏目名称 */
	@Excel(name = "栏目")
	private String categoryName;

	/** 作者 */
	@Excel(name = "作者")
	private String author;

	/** 来源 */
	@Excel(name = "来源")
	private String source;

	/** 是否置顶 */
	@Excel(name = "是否置顶")
	private Integer setTop;

	/** 是否匿名访问 */
	@Excel(name = "是否匿名访问")
	private Integer anonymous;

	/** 发布时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
	private Date publishTime;
	
	/** 是否可评论 */
	private Integer commentable;
	
	/** 浏览数 */
	@Excel(name = "浏览数")
	private Integer viewCount;
	
	/** 点赞数 */
	@Excel(name = "点赞数")
	private Integer praiseCount;
	
	/** 收藏数 */
	@Excel(name = "收藏数")
	private Integer favorCount;
	

	/** 状态（0: 草稿，1: 待发布审批，2:已发布） */
	@Excel(name = "状态", readConverterExp = "0=草稿，1=待发布审批，2=已发布，3=关闭")
	private String status;

	/** 是否停用 */
	@Excel(name = "是否停用")
	private Integer disabled;

	/** 审批信息id */
	private Long[] ids;
	/** 审批意见 */
	private String comment;
	/** 审批通过 */
	private Boolean approval = Boolean.FALSE;
	/** 访问范围部门id */
	private Long[] rangeDeptIds;
	/** 登录用户部门 */
	private Long loginDeptId;

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSetTop(Integer setTop) {
		this.setTop = setTop;
	}

	public Integer getSetTop() {
		return setTop;
	}

	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getAnonymous() {
		return anonymous;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public Integer getCommentable() {
		return commentable;
	}

	public void setCommentable(Integer commentable) {
		this.commentable = commentable;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getFavorCount() {
		return favorCount;
	}

	public void setFavorCount(Integer favorCount) {
		this.favorCount = favorCount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getApproval() {
		return approval;
	}

	public void setApproval(Boolean approval) {
		this.approval = approval;
	}

	public Long[] getRangeDeptIds() {
		return rangeDeptIds;
	}

	public void setRangeDeptIds(Long[] rangeDeptIds) {
		this.rangeDeptIds = rangeDeptIds;
	}

	public Long getLoginDeptId() {
		return loginDeptId;
	}

	public void setLoginDeptId(Long loginDeptId) {
		this.loginDeptId = loginDeptId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("infoId", getInfoId())
			.append("title", getTitle())
			.append("cover", getCover())
			.append("category", getCategoryName())
			.append("setTop", getSetTop())
			.append("anonymous", getAnonymous())
			.append("publishTime", getPublishTime())
			.append("status", getStatus())
			.append("createBy", getCreateBy())
			.append("createTime", getCreateTime())
			.append("updateBy", getUpdateBy())
			.append("updateTime", getUpdateTime())
			.toString();
	}
}

package com.risun.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.risun.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户密码修改记录对象 sys_user_pwd_modify
 * 
 * @author sunchao
 * @date 2022-10-28
 */
public class SysUserPwdModify extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long userId;

	/** 更新者 */
	private String modifyBy;

	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("modifyBy", getModifyBy()).append("modifyTime", getModifyTime()).toString();
	}
}

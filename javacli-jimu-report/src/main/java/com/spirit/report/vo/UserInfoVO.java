package com.spirit.report.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * 报表设计 - 用户信息
 * 
 * @author dante
 *
 */
@Data
public class UserInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 用户姓名 */
	private String userNickName;
	
	/** 是否管理员 */
	private Boolean isAdmin;
	
	/** 是否报表专员 */
	private Boolean isReportSpecialist;
	
}

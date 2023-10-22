package com.spirit.system.service;

/**
 * 项目健康指数和客户活跃度感知
 * 
 * @author dante
 * 
 */
public interface ISysMonitor4RisunService {
	
	/**
	 * 每日用户访问数
	 * 
	 * @param queryDate
	 * @return
	 */
	public int selectSysUserVisitCount(String queryDate);
	
	/**
	 * 每日新用户新增数
	 * 
	 * @param queryDate
	 * @return
	 */
	public int selectSysUserIncreaseCount(String queryDate);
	
}

package com.spirit.system.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * 项目健康指数和客户活跃度感知
 * 
 * @author dante
 */
public interface SysMonitor4RisunMapper {
	
	/**
	 * 每日用户访问数
	 * 
	 * @param queryDate
	 * @return
	 */
	@Select("select count(info_id) from sys_logininfor where status = 0 and replace(date(login_time),'-','') = #{queryDate} and msg = '登录成功' group by user_name")
	public Integer selectSysUserVisitCount(String queryDate);
	
	@Select("select count(user_id) from sys_user where replace(date(create_time),'-','') = #{queryDate} group by user_id")
	public Integer selectSysUserIncreaseCount(String queryDate);
}

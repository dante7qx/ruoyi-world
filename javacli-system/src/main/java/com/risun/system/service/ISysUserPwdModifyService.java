package com.risun.system.service;

import java.util.Map;

/**
 * 用户密码修改记录 服务层
 * 
 *  @author dante
 * 
 */
public interface ISysUserPwdModifyService {
	
	/**
	 * 监控用户初始密码修改和定期提示更新
	 */
	public Map<String, Object> monitorLoginUserPassword();
	
}

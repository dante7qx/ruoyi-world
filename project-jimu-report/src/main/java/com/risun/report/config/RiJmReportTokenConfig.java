package com.risun.report.config;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.jeecg.modules.jmreport.api.JmReportTokenServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.risun.common.core.domain.model.LoginUser;
import com.risun.framework.web.service.TokenService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;

/**
 * 自定义报表系统变量
 * 
 * @author dante
 *
 */
@Component
public class RiJmReportTokenConfig implements JmReportTokenServiceI {
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public String getUsername(String token) {
		String username = "";
		LoginUser loginUser = tokenService.getLoginUser(token);
		if(ObjUtil.isNotNull(loginUser)) {
			username = loginUser.getUsername();
		}
		return username;
	}

	@Override
	public Boolean verifyToken(String token) {
		LoginUser loginUser = tokenService.getLoginUser(token);
		if(ObjUtil.isNull(loginUser)) {
			return Boolean.FALSE;
		}
		long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
		return currentTime <= expireTime;
	}
	
	@Override
	public Map<String, Object> getUserInfo(String token) {
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser loginUser = tokenService.getLoginUser(token);
		if(ObjUtil.isNotNull(loginUser)) {
			// 设置账号
			map.put("sysUserId", loginUser.getUserId());
			map.put("sysUserCode", loginUser.getUsername());
			// 设置部门Id
			map.put("sysDeptId", loginUser.getDeptId());
			// 设置当前日期（yyyy-MM-dd）
			map.put("sysDate", LocalDate.now().toString());
			// 设置当前时间（yyyy-MM-dd HH:mm:ss）
			map.put("sysDateTime", DateUtil.date());
		}
		
		return map;
	}

}

package com.risun.system.service.impl;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;
import com.risun.common.constant.UserConstants;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysUserPwdModify;
import com.risun.system.mapper.SysUserPwdModifyMapper;
import com.risun.system.service.ISysConfigService;
import com.risun.system.service.ISysUserPwdModifyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 用户密码修改记录 服务实现
 * 
 * @author dante
 */
@Slf4j
@Service
public class SysUserPwdModifyServiceImpl implements ISysUserPwdModifyService {
	
	@Autowired
	private SysUserPwdModifyMapper sysUserPwdModifyMapper;
	@Autowired
    private ISysConfigService configService;

	/**
	 * 监控用户初始密码修改和定期提示更新
	 */
	@Override
	public Map<String, Object> monitorLoginUserPassword() {
		Map<String, Object> result = Maps.newHashMap();
		SysUser user = SecurityUtils.getLoginUser().getUser();
		
		// 超级管理员不做提示
		if(SecurityUtils.isAdmin(user.getUserId())) {
			return result;
		}
		
		SysUserPwdModify upm = sysUserPwdModifyMapper.selectSysUserPwdModifyByUserId(user.getUserId());
		// 账号初始密码修改
		String needModifyFlag = configService.selectConfigByKey("sys.user.modifyInitPassword");
		if(!UserConstants.NORMAL.equals(needModifyFlag) && ObjectUtil.isNull(upm)) {
			result.put(UserConstants.MODIFY_PASSWORD, Boolean.TRUE);
		}
		// 账号密码更新周期
		String period = configService.selectConfigByKey("sys.user.modifyPasswordPeriod");
		try {
			int modifyPeriod = Integer.parseInt(period);
			Date lastUpdatePwdDate = upm != null ? upm.getModifyTime() : user.getCreateTime();
			// 密码更新间隔天
			long overday = DateUtil.between(lastUpdatePwdDate, DateUtils.getNowDate(), DateUnit.DAY, true);
			if(modifyPeriod > 0 && overday > modifyPeriod) {
				result.put(UserConstants.PROMPT_MODIFY_PASSWORD, overday);
			}
		} catch (Exception e) {
			log.error("系统参数账号密码更新周期设置错误.", e);
		}
		
		return result;
	}
	
}

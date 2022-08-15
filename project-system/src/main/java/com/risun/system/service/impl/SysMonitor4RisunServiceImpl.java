package com.risun.system.service.impl;

import com.risun.system.mapper.SysMonitor4RisunMapper;
import com.risun.system.service.ISysMonitor4RisunService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.ObjectUtil;

/**
 * 项目健康指数和客户活跃度感知
 * 
 * @author dante
 * 
 */
@Service
public class SysMonitor4RisunServiceImpl implements ISysMonitor4RisunService {
	
	@Autowired
	private SysMonitor4RisunMapper sysMonitor4RisunMapper;

	/**
	 * 每日用户访问数
	 */
	@Override
	public int selectSysUserVisitCount(String queryDate) {
		Integer result = sysMonitor4RisunMapper.selectSysUserVisitCount(queryDate);
		return ObjectUtil.isNotNull(result) ? result : 0;
	}
	
	/**
	 * 每日新用户新增数
	 */
	@Override
	public int selectSysUserIncreaseCount(String queryDate) {
		Integer result = sysMonitor4RisunMapper.selectSysUserIncreaseCount(queryDate);
		return ObjectUtil.isNotNull(result) ? result : 0;
	}

}

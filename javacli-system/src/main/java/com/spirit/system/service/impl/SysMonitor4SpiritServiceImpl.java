package com.spirit.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.system.mapper.SysMonitor4SpiritMapper;
import com.spirit.system.service.ISysMonitor4SpiritService;

import cn.hutool.core.util.ObjectUtil;

/**
 * 项目健康指数和客户活跃度感知
 * 
 * @author dante
 * 
 */
@Service
public class SysMonitor4SpiritServiceImpl implements ISysMonitor4SpiritService {
	
	@Autowired
	private SysMonitor4SpiritMapper sysMonitor4SpiritMapper;

	/**
	 * 每日用户访问数
	 */
	@Override
	public int selectSysUserVisitCount(String queryDate) {
		Integer result = sysMonitor4SpiritMapper.selectSysUserVisitCount(queryDate);
		return ObjectUtil.isNotNull(result) ? result : 0;
	}
	
	/**
	 * 每日新用户新增数
	 */
	@Override
	public int selectSysUserIncreaseCount(String queryDate) {
		Integer result = sysMonitor4SpiritMapper.selectSysUserIncreaseCount(queryDate);
		return ObjectUtil.isNotNull(result) ? result : 0;
	}

}

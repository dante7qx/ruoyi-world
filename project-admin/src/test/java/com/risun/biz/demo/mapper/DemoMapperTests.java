package com.risun.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
import com.risun.common.enums.DataSourceType;
import com.risun.framework.datasource.DynamicDataSourceContextHolder;
import com.risun.system.service.ISysConfigService;

import cn.hutool.core.lang.Console;

class DemoMapperTests extends RisunApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	@Autowired
	private ISysConfigService sysConfigService;
	
	@Test
	void selectDemoCount() {
		Console.log(sysConfigService.selectConfigByKey("sys.sms.sendModelID"));
		int count = demoMapper.selectDemoCount();
		assertTrue(count >= 0);
		
	}
	
	/**
	 * 测试手动切换数据源
	 */
	@Test
	void dynaSelectDemoCount() {
		int masterCount = demoMapper.selectDemoCount();
		Console.log("Master 数据源：{}", masterCount);
		// 手动切换数据源
		DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
		int slaveCount = demoMapper.selectDemoCount();
		Console.log("Slave 数据源：{}", slaveCount);
		assertTrue(masterCount >= 0);
	}
	
}

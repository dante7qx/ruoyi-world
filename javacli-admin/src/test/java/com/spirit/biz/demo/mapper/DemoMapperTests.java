package com.spirit.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spirit.SpiritApplicationTests;
import com.spirit.common.enums.DataSourceType;
import com.spirit.framework.datasource.DynamicDataSourceContextHolder;
import com.spirit.system.service.ISysConfigService;

import cn.hutool.core.lang.Console;

class DemoMapperTests extends SpiritApplicationTests {
	
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

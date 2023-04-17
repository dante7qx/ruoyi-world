package com.risun.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
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
	
}

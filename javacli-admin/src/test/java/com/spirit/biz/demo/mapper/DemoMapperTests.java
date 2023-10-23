package com.spirit.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spirit.SpiritApplicationTests;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.system.service.ISysConfigService;

import cn.hutool.core.lang.Console;

class DemoMapperTests extends SpiritApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	@Autowired
	private ISysConfigService sysConfigService;
	
	@Test
	void selectDemoCount() {
		Console.log(sysConfigService.selectConfigByKey(GlobalArgConfigEnum.SEND_MODEL_ID.key()));
		int count = demoMapper.selectDemoCount();
		assertTrue(count >= 0);
		
	}
	
}

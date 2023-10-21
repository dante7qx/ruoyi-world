package com.spirit.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spirit.RisunApplicationTests;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.system.service.ISysConfigService;

import cn.hutool.core.lang.Console;

class DemoMapperTests extends RisunApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	@Autowired
	private ISysConfigService sysConfigService;
	
	@Test
	void selectDemoCount() {
		Console.log(sysConfigService.selectConfigByKey(GlobalArgConfigEnum.SEND_MODEL_ID.key()));
		int count = demoMapper.selectDemoCount();
		Long countPlus = demoMapper.selectCount(null);
		Console.log(count + " == " + countPlus);
		assertTrue(count >= 0);
		
	}
	
}

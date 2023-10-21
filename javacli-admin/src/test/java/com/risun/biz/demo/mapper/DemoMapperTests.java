package com.risun.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
import com.risun.common.enums.GlobalArgConfigEnum;
import com.risun.system.service.ISysConfigService;

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

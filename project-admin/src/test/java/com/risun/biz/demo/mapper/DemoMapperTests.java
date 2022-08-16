package com.risun.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.risun.RisunApplicationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoMapperTests extends RisunApplicationTests {
	
	@Autowired
	private DemoMapper demoMapper;
	
	@Test
	public void selectDemoCount() {
		int count = demoMapper.selectDemoCount();
		assertTrue(count >= 0);
	}
	
}

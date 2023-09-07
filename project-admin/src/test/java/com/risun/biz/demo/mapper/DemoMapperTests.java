package com.risun.biz.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.ibatis.jdbc.SQL;
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
		assertTrue(count >= 0);
		
	}
	
	/**
	 * SQL 语句构建器
	 */
	@Test
	void buildSQL() {
		 String sql = new SQL()
		    .SELECT("P.ID", "A.USERNAME", "A.PASSWORD", "P.FULL_NAME", "D.DEPARTMENT_NAME", "C.COMPANY_NAME")
		    .FROM("PERSON P", "ACCOUNT A")
		    .INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID", "COMPANY C on D.COMPANY_ID = C.ID")
		    .WHERE("P.ID = A.ID", "P.FULL_NAME like #{name}")
		    .ORDER_BY("P.ID", "P.FULL_NAME")
		    .toString();
		 Console.log(sql);
		 assertTrue(true);
	}
	
}

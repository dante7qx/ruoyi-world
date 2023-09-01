package com.risun.system.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.risun.RisunApplicationTests;
import com.risun.common.utils.DateUtils;
import com.risun.system.domain.SysInfoView;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;

class SysInfoMapperTests extends RisunApplicationTests {

	@Autowired
	private SysInfoMapper sysInfoMapper;
	
	@Test
	void selectSysInfoViewByInfoView() {
		String curDateStr = DateUtils.getDate();
    	Date curDate = DateUtil.parseDate(curDateStr);
		SysInfoView infoView = new SysInfoView();
    	infoView.setInfoId(1L);
    	infoView.setViewDate(curDate);
    	infoView.setViewIp("127.0.0.1");
    	
    	SysInfoView existView = sysInfoMapper.selectSysInfoViewByInfoView(infoView);
    	Console.log("====> {}", existView);
    	assertNotNull(existView, "查询有问题，应该有值的。");
	}
	
}

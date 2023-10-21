package com.risun.common;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSON;
import com.risun.RisunApplicationTests;
import com.risun.biz.demo.domain.Demo;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.QrCodeUtils;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class QrCodeUtilsTests extends RisunApplicationTests {
	
	private Demo demo;
	
	@BeforeEach
	public void init() {
		demo = new Demo();
		demo.setDemoId(100L);
		demo.setDemoName("睿阳科技");
		demo.setDemoContent("你好，睿阳科技！");
		demo.setDemoTime(DateUtils.getNowDate());
	}
	
	@Test
	void generate() {
		String qrCodeUrl = QrCodeUtils.generate(JSON.toJSONString(demo));
		log.info("二维码：{}", qrCodeUrl);
	}
	
	@Test
	void generateWithLogo() {
		String logoPath = ClassUtil.getLocationPath(QrCodeUtils.class).replaceAll("common.*", "ui/src/assets/logo/logo.png");
		String qrCodeUrl = QrCodeUtils.generate(JSON.toJSONString(demo), logoPath);
		log.info("Logo二维码：{}", qrCodeUrl);
	}
	
	@Test
	void generateWithLogo2() {
		String logoPath = ClassUtil.getLocationPath(QrCodeUtils.class).replaceAll("common.*", "ui/src/assets/logo/logo.png");
		String qrCodeUrl = QrCodeUtils.generate(JSON.toJSONString(demo), 200, 200, logoPath);
		log.info("Logo二维码：{}", qrCodeUrl);
	}
	
	@Test
	void generateWithLogo3() {
		String logoPath = ClassUtil.getLocationPath(QrCodeUtils.class).replaceAll("common.*", "ui/src/assets/logo/logo.png");
		String qrCodeUrl = QrCodeUtils.generate(JSON.toJSONString(demo), 200, 200, new Color(52, 105, 29), logoPath);
		log.info("Logo二维码：{}", qrCodeUrl);
	}
	
}

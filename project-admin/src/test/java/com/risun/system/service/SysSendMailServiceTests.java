package com.risun.system.service;

import com.risun.RisunApplicationTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SysSendMailServiceTests extends RisunApplicationTests {
	
	@Autowired
	private ISysSendMailService sysSendMailService;
	
	private final String to = "sunchao.0129@163.com";
	private final String cc = "dante7qx@gmail.com";
	private final String bcc = "dante7qx@126.com";
	
	@Test
	public void sendMail() {
		sysSendMailService.sendMail(to, cc, bcc, "测试", "你好，睿阳");
	}
	
}

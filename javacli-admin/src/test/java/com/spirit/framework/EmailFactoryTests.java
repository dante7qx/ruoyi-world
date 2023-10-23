package com.spirit.framework;

import com.spirit.RisunApplicationTests;
import com.spirit.framework.email.EmailFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmailFactoryTests extends RisunApplicationTests {
	
	@Autowired
	private EmailFactory emailFactory;
	
	/**
	 * 邮件接收人
	 */
	private final String to = "test@test.com";
	
	@Test
	void sendSimpleMail() {
		emailFactory.sendSimpleMail(to, "测试简单邮件", "你好，精灵");
		assertTrue(true);
	}
	
	@Test
	void sendMediaMail() {
		emailFactory.sendMediaMail(to, "测试HTML邮件", "sms/demo", Maps.newHashMap("title", "你好，精灵"),
				EmailFactory.ATTACHMENT_URL, 
				"https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg", 
				"https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
				"https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg");
		assertTrue(true);
	}
}

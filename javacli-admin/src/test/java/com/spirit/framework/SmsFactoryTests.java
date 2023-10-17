package com.spirit.framework;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson2.JSONObject;
import com.spirit.SpiritApplicationTests;
import com.spirit.framework.sms.SmsFactory;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SmsFactoryTests extends SpiritApplicationTests {
	
	private final String phoneNumber = "您的手机号码";

	@Autowired
	private SmsFactory smsFactory;
	
	@Test
	void sendSms() {
		log.info("开始发送短信。。。");
		String content = "测试短信（同步）";
		smsFactory.sendSms(phoneNumber, content);
		log.info("短信发送完成。。。");
		ThreadUtil.sleep(7, TimeUnit.SECONDS);
	}
	
	@Test
	void sendSmsAsync() {
		log.info("开始发送短信。。。");
		String content = "测试短信（异步）";
		smsFactory.sendSmsAsync(phoneNumber, content);
		log.info("短信发送完成。。。");
		ThreadUtil.sleep(7, TimeUnit.SECONDS);
	}
	
	@Test
	void invokeExternalSmsInc() {
		String url = "http://sms/app/sendMsg";
		String code = RandomUtil.randomNumbers(6);
		Console.log(code);
		JSONObject json = new JSONObject();
	    //手机号
	    json.put("phone", "18211011254");
	    json.put("modelID", "225130XXX");
	    json.put("content", "#info#=您正在进行身份认证，您的验证码是"+code+"，验证码5分钟之内有效。如非本人操作，请忽略本短信");
		String result = HttpUtil.post(url, json.toString(), 5000);
		log.info(result);
	}
	
	
	
	
}

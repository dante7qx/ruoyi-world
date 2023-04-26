package com.risun.framework;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson2.JSONObject;
import com.risun.RisunApplicationTests;
import com.risun.framework.sms.SmsFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsFactoryTests extends RisunApplicationTests {
	
	private final String phoneNumber = "您的手机号码";

	@Autowired
	private SmsFactory smsFactory;
	
	@Test
	public void sendSms() {
		log.info("开始发送短信。。。");
		String content = "测试短信（同步）";
		smsFactory.sendSms(phoneNumber, content);
		log.info("短信发送完成。。。");
		ThreadUtil.sleep(7, TimeUnit.SECONDS);
	}
	
	@Test
	public void sendSmsAsync() {
		log.info("开始发送短信。。。");
		String content = "测试短信（异步）";
		smsFactory.sendSmsAsync(phoneNumber, content);
		log.info("短信发送完成。。。");
		ThreadUtil.sleep(7, TimeUnit.SECONDS);
	}
	
	@Test
	public void invokeExternalSmsInc() {
		String url = "http://ccgp-ts.risun-tec.cn/ccgp-ts/biz/app/sendMsg";
		String code = RandomUtil.randomNumbers(6);
		Console.log(code);
		JSONObject json = new JSONObject();
	    //手机号
	    json.put("phone", "18211011254");
	    json.put("modelID", "225130XXX");
	    json.put("content", "#info#=您正在进行身份认证，您的验证码是"+code+"，验证码5分钟之内有效。如非本人操作，请忽略本短信");
		String result = HttpUtil.post(url, json.toString(), 5000);
		Console.log(result);
	}
	
	
	/**
	String urls = "http://ccgp-ts.risun-tec.cn/ccgp-ts/biz/app/sendMsg";
    JSONObject json = new JSONObject();
    //手机号
    json.put("phone", imptPerPhone);
    json.put("modelID", "123456");
    //内容
    String code=Long.toString(System.currentTimeMillis()).substring(Long.toString(System.currentTimeMillis()).length()-6, Long.toString(System.currentTimeMillis()).length());
    json.put("content", "#info#=您正在进行身份认证，您的验证码是"+code+"，验证码5分钟之内有效。如非本人操作，请忽略本短信");
    String result = HttpRequest.post(urls)
            .timeout(600000)
            .body(json.toString(), MediaType.APPLICATION_JSON_UTF8_VALUE)
            .execute()
            .body(); 
	 switch (result.split(":")[0]) {
		case "200":
		redisTemplate.opsForValue().set(imptPerPhone, code,5, TimeUnit.MINUTES);
		result="发送成功，请注意查收短信！";
		break;
		default:
		result="发送失败，请检查手机号码是否正确！";
		break;
		}
		 return AjaxResult.success(result);
	*/
	
	
}

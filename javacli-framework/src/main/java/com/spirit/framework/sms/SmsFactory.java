package com.spirit.framework.sms;

import com.alibaba.fastjson2.JSONObject;
import com.spirit.common.constant.Constants;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.domain.SysSmsLog;
import com.spirit.system.mapper.SysSmsLogMapper;
import com.spirit.system.service.ISysConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.hutool.http.HttpUtil;

/**
 * 短信发送工厂
 * 
 * @author dante
 */
@Component
public class SmsFactory {
	
	@Autowired
	private ISysConfigService sysConfigService;
	@Autowired
    private SysSmsLogMapper sysSmsLogMapper;
	
	/** 外部短信接口URL */
	private static final String EXTERNAL_SMS_URL = "http://ccgp-ts.spirit-tec.cn/ccgp-ts/biz/app/sendMsg";
	
	/** 短信内容前缀 */
	private static final String SMS_PREFIX = "#info#=";
	
	/** 短信发送成功编码 */
	private static final String SMS_SEND_SUCCESS_CODE = "200";
	
	/**
	 * 发送短信
	 * 
	 * @param phoneNumber
	 * @param content
	 */
	public void sendSms(String phoneNumber, String content) {
		String modelID = sysConfigService.selectConfigByKey(GlobalArgConfigEnum.SEND_MODEL_ID.key());
		Assert.hasText(modelID, "短信发送标识码不可为空");
		Assert.hasText(phoneNumber, "短信接收手机号不可为空");
		Assert.hasText(content, "短信发送内容不可为空");
		JSONObject json = new JSONObject();
	    json.put("phone", phoneNumber);
	    json.put("modelID", modelID);
	    json.put("content", SMS_PREFIX.concat(content));
		String result = HttpUtil.post(EXTERNAL_SMS_URL, json.toString(), 5000);
		SysSmsLog sysSmsLog = buildSysSmsLog(phoneNumber, content);
		if(!SMS_SEND_SUCCESS_CODE.equals(result.split(":")[0])) {
			sysSmsLog.setStatus(Constants.FAIL);
			sysSmsLog.setSendLog(result);
		} 
		sysSmsLogMapper.insertSysSmsLog(sysSmsLog);
	}
	
	/**
	 * 发送短信（异步）
	 * 
	 * @param phoneNumber
	 * @param content
	 */
	@Async("threadPoolTaskExecutor")
	public void sendSmsAsync(String phoneNumber, String content) {
		sendSms(phoneNumber, content);
	}
	
	private SysSmsLog buildSysSmsLog(String phoneNumber, String content) {
		SysSmsLog log = new SysSmsLog();
		log.setSendTo(phoneNumber);
		log.setContent(content);
		log.setSendDate(DateUtils.getNowDate());
		log.setSendLog("发送成功");
		log.setStatus(Constants.SUCCESS);
		try {
			log.setCreateBy(SecurityUtils.getUsername());
		} catch (Exception e) {
			log.setCreateBy("系统自动");
		}
	    log.setCreateTime(DateUtils.getNowDate());
		return log;
	}
	
}

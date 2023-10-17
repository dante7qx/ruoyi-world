package com.spirit.framework.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.spirit.common.constant.Constants;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.domain.SysSmsLog;
import com.spirit.system.mapper.SysSmsLogMapper;
import com.spirit.system.service.ISysConfigService;

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
	
	/** 短信发送成功编码 */
	private static final String SMS_SEND_SUCCESS_CODE = "200";
	
	/**
	 * 发送短信 TODO
	 *  
	 * @param phoneNumber
	 * @param content
	 */
	public void sendSms(String phoneNumber, String content) {
		String modelID = sysConfigService.selectConfigByKey(GlobalArgConfigEnum.SEND_MODEL_ID.key());
		Assert.hasText(modelID, "短信发送标识码不可为空");
		Assert.hasText(phoneNumber, "短信接收手机号不可为空");
		Assert.hasText(content, "短信发送内容不可为空");
		
		String result = "发送结果";
		
		
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

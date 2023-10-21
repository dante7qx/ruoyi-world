package com.risun.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统参数设置枚举类
 * 
 * @author dante
 *
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GlobalArgConfigEnum {

	CAPTCHA_ENABLED("sys.account.captchaEnabled", "账号自助-验证码开关"),
	REGISTER_USER("sys.account.registerUser", "账号自助-是否开启用户注册功能"), 
	SEND_MODEL_ID("sys.sms.sendModelID", "短信发送标识码"),
	MODIFY_INIT_PASSWORD("sys.user.modifyInitPassword", "用户管理-账号初始密码修改"),
	MODIFY_PASSWORD_PERIOD("sys.user.modifyPasswordPeriod", "用户管理-账号密码更新周期"),
	INIT_PASSWORD("sys.user.initPassword", "用户管理-账号初始密码"), 
	BASE_URL("sys.visit.baseurl", "系统访问地址");

	private final String key;
	private final String name;

	public String key() {
		return this.key;
	}

}

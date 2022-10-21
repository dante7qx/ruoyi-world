package com.risun.framework.web.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.risun.common.constant.CacheConstants;
import com.risun.common.constant.Constants;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.core.domain.model.LoginBody;
import com.risun.common.core.domain.model.LoginUser;
import com.risun.common.core.redis.RedisCache;
import com.risun.common.exception.ServiceException;
import com.risun.common.exception.user.CaptchaException;
import com.risun.common.exception.user.CaptchaExpireException;
import com.risun.common.exception.user.UserException;
import com.risun.common.exception.user.UserPasswordNotMatchException;
import com.risun.common.exception.user.UserPhoneNotFoundException;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.MessageUtils;
import com.risun.common.utils.ServletUtils;
import com.risun.common.utils.StringUtils;
import com.risun.common.utils.ip.IpUtils;
import com.risun.common.utils.sign.RsaUtils;
import com.risun.framework.manager.AsyncManager;
import com.risun.framework.manager.factory.AsyncFactory;
import com.risun.framework.security.context.AuthenticationContextHolder;
import com.risun.framework.sms.SmsFactory;
import com.risun.system.service.ISysConfigService;
import com.risun.system.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService {
	
	/** 账号密码登录 */
	private static final String SYS_LOGIN_TYPE_UNAME = "uname";
	
	/** 短信登录 */
	private static final String SYS_LOGIN_TYPE_SMS = "sms";
	
	/** 短信登录验证码缓存Key */
	private static final String SYS_SMS_LOGIN_KEY = "_SYS_SMS_LOGIN_KEY";
	
	@Autowired
	private TokenService tokenService;

	@Resource
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysConfigService configService;
	
	@Autowired
	private SmsFactory smsFactory;
	
	public String login(LoginBody loginBody) {
		String result = "";
		if(SYS_LOGIN_TYPE_UNAME.equals(loginBody.getLoginType())) {	
			result = this.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
		} else if(SYS_LOGIN_TYPE_SMS.equals(loginBody.getLoginType())) {
			result = this.loginBySms(loginBody.getUserPhone(), loginBody.getSmsCode());
		}
		return result;
	}

	/**
	 * 登录验证
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public String login(String username, String password, String code, String uuid) {
		boolean captchaEnabled = configService.selectCaptchaEnabled();
		// 验证码开关
		if (captchaEnabled) {
			validateCaptcha(username, code, uuid);
		}
		return this.login(username, password);
	}
	
	/**
	 * 短信验证码登录 
	 * 
	 * @param userPhone
	 * @param smsCode
	 * @return
	 * @throws Exception 
	 */
	public String loginBySms(String userPhone, String smsCode) {
		Assert.hasText(userPhone, "手机号不能为空");
		Assert.hasText(smsCode, "验证码不能为空");
		String cacheKey = userPhone.concat(SYS_SMS_LOGIN_KEY);
		String cacheSmsCode = redisCache.getCacheObject(cacheKey);
		if(!StringUtils.equalsIgnoreCase(smsCode, cacheSmsCode)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(userPhone, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		} 
		SysUser sysUser = userService.selectUserByPhonenumber(userPhone);
		return this.login(sysUser.getUserName(), sysUser.getPassword());
	}
	
	private String login(String username, String password) {
		// 用户验证
		Authentication authentication = null;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, RsaUtils.decryptByPrivateKey(password));
			AuthenticationContextHolder.setContext(authenticationToken);
			// 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
			authentication = authenticationManager
					.authenticate(authenticationToken);
		} catch (Exception e) {
			if (e instanceof BadCredentialsException) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
						MessageUtils.message("user.password.not.match")));
				throw new UserPasswordNotMatchException();
			} else {
				AsyncManager.me()
						.execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
				throw new ServiceException(e.getMessage());
			}
		}
		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
				MessageUtils.message("user.login.success")));
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		recordLoginInfo(loginUser.getUserId());
		// 生成token
		return tokenService.createToken(loginUser);
	}

	/**
	 * 校验验证码
	 * 
	 * @param username 用户名
	 * @param code     验证码
	 * @param uuid     唯一标识
	 * @return 结果
	 */
	public void validateCaptcha(String username, String code, String uuid) {
		String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
		String captcha = redisCache.getCacheObject(verifyKey);
		redisCache.deleteObject(verifyKey);
		if (captcha == null) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.expire")));
			throw new CaptchaExpireException();
		}
		if (!code.equalsIgnoreCase(captcha)) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
	}

	/**
	 * 记录登录信息
	 *
	 * @param userId 用户ID
	 */
	public void recordLoginInfo(Long userId) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
		sysUser.setLoginDate(DateUtils.getNowDate());
		userService.updateUserProfile(sysUser);
	}
	
	/**
	 * 发送短信验证码
	 * 
	 * @param userPhone
	 * @return
	 */
	public void sendSmsCode(String userPhone) {
		Assert.hasText(userPhone, "手机号不能为空");
		SysUser sysUser = userService.selectUserByPhonenumber(userPhone);
		if(ObjectUtil.isNull(sysUser)) {
			throw new UserPhoneNotFoundException();
		} else if(Constants.DEL_FLAG.equals(sysUser.getDelFlag())) {
			throw new UserException("user.password.delete", null);
		} else if(Constants.LOCK_FLAG.equals(sysUser.getStatus())) {
			throw new UserException("user.blocked", null);
		}
		String cacheKey = userPhone.concat(SYS_SMS_LOGIN_KEY);
		String smsCode = redisCache.getCacheObject(cacheKey);
		if(StringUtils.isEmpty(smsCode)) {
			smsCode = RandomUtil.randomNumbers(6);
			String content = "您正在进行身份认证，您的验证码是" + smsCode + "，验证码" + Constants.CAPTCHA_EXPIRATION + "分钟之内有效。如非本人操作，请忽略本短信";
			smsFactory.sendSms(userPhone, content);
			redisCache.setCacheObject(cacheKey, smsCode, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
		}
	}
}

package com.spirit.web.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.spirit.common.config.RisunConfig;
import com.spirit.common.constant.CacheConstants;
import com.spirit.common.constant.Constants;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.redis.RedisCache;
import com.spirit.common.utils.uuid.IdUtils;
import com.spirit.framework.web.service.SysLoginService;
import com.spirit.system.service.ISysConfigService;

/**
 * 验证码操作处理
 * 
 * @author ruoyi
 */
@RestController
public class CaptchaController {
	@Resource(name = "captchaProducer")
	private Producer captchaProducer;

	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private ISysConfigService configService;
	
	@Autowired
	private SysLoginService sysLoginService;
	
	private static final String MATH_ = "math";
	private static final String CHAR_ = "char";

	/**
	 * 生成验证码
	 */
	@GetMapping("/captchaImage")
	public AjaxResult getCode(HttpServletResponse response) throws IOException {
		AjaxResult ajax = AjaxResult.success();
		boolean captchaEnabled = configService.selectCaptchaEnabled();
		ajax.put("captchaEnabled", captchaEnabled);
		if (!captchaEnabled) {
			return ajax;
		}

		// 保存验证码信息
		String uuid = IdUtils.simpleUUID();
		String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

		String capStr = null, code = null;
		BufferedImage image = null;

		// 生成验证码
		String captchaType = RisunConfig.getCaptchaType();
		if (MATH_.equals(captchaType)) {
			String capText = captchaProducerMath.createText();
			capStr = capText.substring(0, capText.lastIndexOf("@"));
			code = capText.substring(capText.lastIndexOf("@") + 1);
			image = captchaProducerMath.createImage(capStr);
		} else if (CHAR_.equals(captchaType)) {
			capStr = code = captchaProducer.createText();
			image = captchaProducer.createImage(capStr);
		}

		redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
		// 转换流信息写出
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", os);
		} catch (IOException e) {
			return AjaxResult.error(e.getMessage());
		}

		ajax.put("uuid", uuid);
		ajax.put("img", DatatypeConverter.printBase64Binary(os.toByteArray()));
		return ajax;
	}
	
	/**
     * 发送短信验证码
     * 
     * @return
     */
    @PostMapping("/sendLoginSmsCode/{userPhone}")
    public AjaxResult sendSmsCode(@PathVariable String userPhone) {
    	sysLoginService.sendSmsCode(userPhone);
    	return AjaxResult.success();
    }
}

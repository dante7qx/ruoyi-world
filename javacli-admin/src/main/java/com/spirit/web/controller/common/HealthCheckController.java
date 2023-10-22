package com.spirit.web.controller.common;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spirit.common.annotation.Anonymous;
import com.spirit.common.config.SpiritConfig;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.utils.wordfilter.SensitiveWordUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 健康检查
 * 
 * @author dante
 *
 */
@Slf4j
@RestController
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class HealthCheckController {
	
	@Autowired
	private SpiritConfig SpiritConfig;
	
	@PostConstruct
	@SuppressWarnings("static-access")
	public void init() {
		String path = SpiritConfig.getProfile() + "/SensitiveWord1.txt";
		log.info("加载敏感词文件 - {}", path);
		SensitiveWordUtil.loadWordFromFile(path);
	}
	
	/**
	 * 健康检查
	 * 
	 * @return
	 */
	@Anonymous
	@GetMapping("/health_check")
	public String healthCheck() {
		return "UP";
	}
	
	/**
	 * 刷新敏感词文件
	 * 
	 * @return
	 */
	@Anonymous
	@GetMapping("/refresh_sensitive_word")
	public AjaxResult refreshSensitiveWord() {
		String path = SpiritConfig.getProfile() + "/SensitiveWord.txt";
		log.info("重新加载敏感词文件 - {}", path);
		SensitiveWordUtil.loadWordFromFile(path);
		return AjaxResult.success();
	}
	
}

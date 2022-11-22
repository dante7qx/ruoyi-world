package com.risun.web.controller.common;

import com.risun.common.annotation.Anonymous;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 * 
 * @author dante
 *
 */
@RestController
@CrossOrigin(originPatterns = "*",maxAge = 3600)
public class HealthCheckController {
	
	@Anonymous
	@GetMapping("/health_check")
	public String healthCheck() {
		return "UP";
	}
	
}

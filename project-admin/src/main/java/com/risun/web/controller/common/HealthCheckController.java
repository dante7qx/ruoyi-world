package com.risun.web.controller.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Anonymous;

/**
 * 健康检查
 * 
 * @author dante
 *
 */
@RestController
public class HealthCheckController {
	
	@Anonymous
	@GetMapping("/health_check")
	public String healthCheck() {
		return "UP";
	}
	
}

package com.risun.web.controller.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查
 * 
 * @author dante
 *
 */
@RestController
public class HealthCheckController {
	
	@GetMapping("/health_check")
	public String healthCheck() {
		return "UP";
	}
	
}

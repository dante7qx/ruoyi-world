package com.spirit.web.controller.monitor;

import com.spirit.common.annotation.Anonymous;
import com.spirit.system.service.ISysMonitor4SpiritService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.ReUtil;

/**
 * 项目健康指数和客户活跃度感知
 * 
 * @author dante
 */
@RestController
@RequestMapping("/Spirit/monitor")
public class SysMonitor4SpiritController {
	
	@Autowired
	private ISysMonitor4SpiritService sysMonitor4SpiritService;
	
	/**
	 * 每日用户访问数
	 * 
	 * @param queryDate
	 * @return
	 */
	@Anonymous
	@PostMapping("/user_visit_count/{queryDate}")
	public int userVisitCount(@PathVariable String queryDate) {
		Assert.hasText(queryDate, "查询日期不能为空，格式必须为 yyyyMMdd");
		Assert.isTrue(matchArgFormat(queryDate), "格式必须为 yyyyMMdd");
		return sysMonitor4SpiritService.selectSysUserVisitCount(queryDate);
	}
	
	/**
	 * 每日新用户新增数
	 * 
	 * @param queryDate
	 * @return
	 */
	@Anonymous
	@PostMapping("/user_increase_count/{queryDate}")
	public int userIncreaseCount(@PathVariable String queryDate) {
		Assert.hasText(queryDate, "查询日期不能为空，格式必须为 yyyyMMdd");
		Assert.isTrue(matchArgFormat(queryDate), "格式必须为 yyyyMMdd");
		return sysMonitor4SpiritService.selectSysUserIncreaseCount(queryDate);
	}
	
	/**
	 * 每日核心业务数据新增数
	 * 
	 * @param queryDate
	 * @return
	 */
	@Anonymous
	@PostMapping("/business_increase_count/{queryDate}")
	public int businessIncreaseCount(@PathVariable String queryDate) {
		Assert.hasText(queryDate, "查询日期不能为空，格式必须为 yyyyMMdd");
		Assert.isTrue(matchArgFormat(queryDate), "格式必须为 yyyyMMdd");
		return 0;
	}
	
	private boolean matchArgFormat(String queryDate) {
		return ReUtil.isMatch("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)", queryDate);
	}
}

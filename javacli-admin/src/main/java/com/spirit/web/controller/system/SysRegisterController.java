package com.spirit.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.domain.model.RegisterBody;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.framework.web.service.SysRegisterService;
import com.spirit.system.service.ISysConfigService;

import cn.hutool.core.util.StrUtil;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RestController
public class SysRegisterController extends BaseController {
	private static final String TRUE_ = "true";
	
	@Autowired
	private SysRegisterService registerService;

	@Autowired
	private ISysConfigService configService;

	@PostMapping("/register")
	public AjaxResult register(@RequestBody RegisterBody user) {
		if (!(TRUE_.equals(configService.selectConfigByKey(GlobalArgConfigEnum.REGISTER_USER.key())))) {
			return error("当前系统没有开启注册功能！");
		}
		String msg = registerService.register(user);
		return StrUtil.isEmpty(msg) ? success() : error(msg);
	}
}

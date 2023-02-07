package com.risun.mq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.mq.domain.SysInnerMqException;
import com.risun.mq.service.ISysInnerMqExceptionService;

/**
 * 内部消息队列异常消息Controller
 * 
 * @author sunchao
 * @date 2023-02-06
 */
@RestController
@RequestMapping("/monitor/innermq")
public class SysInnerMqExceptionController extends BaseController {
	@Autowired
	private ISysInnerMqExceptionService sysInnerMqExceptionService;

	/**
	 * 查询内部消息队列异常消息列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:innermq:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysInnerMqException sysInnerMqException) {
		startPage();
		List<SysInnerMqException> list = sysInnerMqExceptionService.selectSysInnerMqExceptionList(sysInnerMqException);
		return getDataTable(list);
	}

	/**
	 * 删除内部消息队列异常消息
	 */
	@PreAuthorize("@ss.hasPermi('monitor:innermq:remove')")
	@Log(title = "内部消息队列异常消息", businessType = BusinessType.DELETE)
	@PostMapping("/del/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(sysInnerMqExceptionService.deleteSysInnerMqExceptionByIds(ids));
	}
}

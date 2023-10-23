package com.spirit.flowable.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.enums.BusinessType;
import com.spirit.flowable.demo.domain.FlowDemo;
import com.spirit.flowable.demo.service.IFlowDemoService;

/**
 * 业务流程示例Controller
 * 
 * @author sunchao
 * @date 2023-03-02
 */
@RestController
@RequestMapping("/flowdemo/flowdemo")
public class FlowDemoController extends BaseController {
	@Autowired
	private IFlowDemoService flowDemoService;

	/**
	 * 获取业务流程示例详细信息
	 */
	@GetMapping(value = "/uid/{uid}")
	public AjaxResult getInfo(@PathVariable("uid") String uid) {
		return AjaxResult.success(flowDemoService.selectFlowDemoByUid(uid));
	}

	/**
	 * 提交业务流程示例
	 */
	@PostMapping("/commit")
	public AjaxResult commit(@RequestBody FlowDemo flowDemo) {
		return toAjax(flowDemoService.commitFlowDemo(flowDemo));
	}

	/**
	 * 删除业务流程示例
	 */
	@PreAuthorize("@ss.hasPermi('flowdemo:flowdemo:remove')")
	@Log(title = "业务流程示例", businessType = BusinessType.DELETE)
	@PostMapping("/del/{demoIds}")
	public AjaxResult remove(@PathVariable Long[] demoIds) {
		return toAjax(flowDemoService.deleteFlowDemoByDemoIds(demoIds));
	}
}

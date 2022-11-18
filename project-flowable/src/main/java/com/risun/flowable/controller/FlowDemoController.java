package com.risun.flowable.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.flowable.domain.FlowDemo;
import com.risun.flowable.service.IFlowDemoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务流程示例Controller
 * 
 * @author sunchao
 * @date 2022-11-11
 */
@RestController
@RequestMapping("/flowable/demo")
public class FlowDemoController extends BaseController {
	@Autowired
	private IFlowDemoService flowDemoService;

	/**
	 * 查询业务流程示例列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(FlowDemo flowDemo) {
		startPage();
		flowDemo.setLeaveUserId(getUserId());
		List<FlowDemo> list = flowDemoService.selectFlowDemoList(flowDemo);
		return getDataTable(list);
	}

	/**
	 * 导出业务流程示例列表
	 */
	@PostMapping("/export")
	public void export(HttpServletResponse response, FlowDemo flowDemo) {
		List<FlowDemo> list = flowDemoService.selectFlowDemoList(flowDemo);
		ExcelUtil<FlowDemo> util = new ExcelUtil<FlowDemo>(FlowDemo.class);
		util.exportExcel(response, list, "业务流程示例数据");
	}

	/**
	 * 获取业务流程示例详细信息
	 */
	@GetMapping(value = "/{demoId}")
	public AjaxResult getInfo(@PathVariable("demoId") Long demoId) {
		return AjaxResult.success(flowDemoService.selectFlowDemoByDemoId(demoId));
	}

	/**
	 * 新增业务流程示例
	 */
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody FlowDemo flowDemo) {
		return toAjax(flowDemoService.insertFlowDemo(flowDemo));
	}

	/**
	 * 修改业务流程示例
	 */
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody FlowDemo flowDemo) {
		return toAjax(flowDemoService.updateFlowDemo(flowDemo));
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
	@PostMapping("/del/{demoIds}")
	public AjaxResult remove(@PathVariable Long[] demoIds) {
		return toAjax(flowDemoService.deleteFlowDemoByDemoIds(demoIds));
	}
}

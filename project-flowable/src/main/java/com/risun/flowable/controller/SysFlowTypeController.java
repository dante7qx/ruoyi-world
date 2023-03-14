package com.risun.flowable.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.flowable.domain.SysFlowType;
import com.risun.flowable.service.ISysFlowTypeService;

/**
 * 流程类型Controller
 * 
 * @author sunchao
 * @date 2023-02-27
 */
@RestController
@RequestMapping("/flowable/type")
public class SysFlowTypeController extends BaseController {
	@Autowired
	private ISysFlowTypeService sysFlowTypeService;

	/**
	 * 查询流程类型列表
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysFlowType sysFlowType) {
		startPage();
		List<SysFlowType> list = sysFlowTypeService.selectSysFlowTypeList(sysFlowType);
		return getDataTable(list);
	}

	/**
	 * 导出流程类型列表
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:export')")
	@Log(title = "流程类型", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysFlowType sysFlowType) {
		List<SysFlowType> list = sysFlowTypeService.selectSysFlowTypeList(sysFlowType);
		ExcelUtil<SysFlowType> util = new ExcelUtil<SysFlowType>(SysFlowType.class);
		util.exportExcel(response, list, "流程类型数据");
	}

	/**
	 * 获取流程类型详细信息
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:query')")
	@GetMapping(value = "/{typeId}")
	public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
		return AjaxResult.success(sysFlowTypeService.selectSysFlowTypeByTypeId(typeId));
	}

	/**
	 * 新增流程类型
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:add')")
	@Log(title = "流程类型", businessType = BusinessType.INSERT)
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody SysFlowType sysFlowType) {
		return toAjax(sysFlowTypeService.insertSysFlowType(sysFlowType));
	}

	/**
	 * 修改流程类型
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:edit')")
	@Log(title = "流程类型", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody SysFlowType sysFlowType) {
		return toAjax(sysFlowTypeService.updateSysFlowType(sysFlowType));
	}

	/**
	 * 删除流程类型
	 */
	@PreAuthorize("@ss.hasPermi('flowable:type:remove')")
	@Log(title = "流程类型", businessType = BusinessType.DELETE)
	@PostMapping("/del/{typeIds}")
	public AjaxResult remove(@PathVariable Long[] typeIds) {
		return toAjax(sysFlowTypeService.deleteSysFlowTypeByTypeIds(typeIds));
	}
}

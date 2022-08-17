package com.risun.web.controller.monitor;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.system.domain.SysSmsLog;
import com.risun.system.service.ISysSmsLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信日志Controller
 * 
 * @author sunchao
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/monitor/smslog")
public class SysSmsLogController extends BaseController {
	@Autowired
	private ISysSmsLogService sysSmsLogService;

	/**
	 * 查询短信日志列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:smslog:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysSmsLog sysSmsLog) {
		startPage();
		List<SysSmsLog> list = sysSmsLogService.selectSysSmsLogList(sysSmsLog);
		return getDataTable(list);
	}

	/**
	 * 导出短信日志列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:smslog:export')")
	@Log(title = "短信日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysSmsLog sysSmsLog) {
		List<SysSmsLog> list = sysSmsLogService.selectSysSmsLogList(sysSmsLog);
		ExcelUtil<SysSmsLog> util = new ExcelUtil<SysSmsLog>(SysSmsLog.class);
		util.exportExcel(response, list, "短信日志数据");
	}

	/**
	 * 获取短信日志详细信息
	 */
	@PreAuthorize("@ss.hasPermi('monitor:smslog:query')")
	@GetMapping(value = "/{smsId}")
	public AjaxResult getInfo(@PathVariable("smsId") Long smsId) {
		return AjaxResult.success(sysSmsLogService.selectSysSmsLogBySmsId(smsId));
	}

	/**
	 * 删除短信日志
	 */
	@PreAuthorize("@ss.hasPermi('monitor:smslog:remove')")
	@Log(title = "短信日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{smsIds}")
	public AjaxResult remove(@PathVariable Long[] smsIds) {
		return toAjax(sysSmsLogService.deleteSysSmsLogBySmsIds(smsIds));
	}
}

package com.risun.web.controller.monitor;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.system.domain.SysEmailLog;
import com.risun.system.service.ISysEmailLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件日志Controller
 * 
 * @author sunchao
 * @date 2022-08-16
 */
@RestController
@RequestMapping("/monitor/emaillog")
public class SysEmailLogController extends BaseController {
	@Autowired
	private ISysEmailLogService sysEmailLogService;

	/**
	 * 查询邮件日志列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:emaillog:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysEmailLog sysEmailLog) {
		startPage();
		List<SysEmailLog> list = sysEmailLogService.selectSysEmailLogList(sysEmailLog);
		return getDataTable(list);
	}

	/**
	 * 导出邮件日志列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:emaillog:export')")
	@Log(title = "邮件日志", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysEmailLog sysEmailLog) {
		List<SysEmailLog> list = sysEmailLogService.selectSysEmailLogList(sysEmailLog);
		ExcelUtil<SysEmailLog> util = new ExcelUtil<SysEmailLog>(SysEmailLog.class);
		util.exportExcel(response, list, "邮件日志数据");
	}

	/**
	 * 获取邮件日志详细信息
	 */
	@PreAuthorize("@ss.hasPermi('monitor:emaillog:query')")
	@GetMapping(value = "/{emailId}")
	public AjaxResult getInfo(@PathVariable("emailId") Long emailId) {
		return AjaxResult.success(sysEmailLogService.selectSysEmailLogByEmailId(emailId));
	}

	
	/**
     * 删除邮件日志
     */
    @PreAuthorize("@ss.hasPermi('monitor:emaillog:remove')")
    @Log(title = "邮件日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{emailIds}")
    public AjaxResult remove(@PathVariable Long[] emailIds)
    {
        return toAjax(sysEmailLogService.deleteSysEmailLogByEmailIds(emailIds));
    }

}

package com.risun.web.controller.monitor;

import java.util.List;

import com.risun.common.core.controller.BaseController;
import com.risun.common.core.page.TableDataInfo;
import com.risun.system.domain.SysApprovalLog;
import com.risun.system.service.ISysApprovalLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审批日志Controller
 * 
 * @author sunchao
 * @date 2022-10-24
 */
@RestController
@RequestMapping("/monitor/approvallog")
public class SysApprovalLogController extends BaseController {
	@Autowired
	private ISysApprovalLogService sysApprovalLogService;

	/**
	 * 查询审批日志列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:approvallog:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysApprovalLog sysApprovalLog) {
		startPage();
		List<SysApprovalLog> list = sysApprovalLogService.selectSysApprovalLogList(sysApprovalLog);
		return getDataTable(list);
	}

}

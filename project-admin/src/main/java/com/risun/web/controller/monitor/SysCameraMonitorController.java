package com.risun.web.controller.monitor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.google.common.collect.Maps;
import com.risun.common.annotation.Log;
import com.risun.common.constant.Constants;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.system.domain.SysCameraMonitor;
import com.risun.system.service.ISysCameraMonitorService;
import com.risun.system.service.ISysConfigService;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;

/**
 * 视频监控Controller
 * 
 * @author sunchao
 * @date 2023-09-25
 */
@RestController
@RequestMapping("/monitor/camera")
public class SysCameraMonitorController extends BaseController {
	@Autowired
	private ISysCameraMonitorService sysCameraMonitorService;
	@Autowired
	private ISysConfigService sysConfigService;
	
	private final String MONITOR_SERVER_KEY = "sys.monitor.camera.server";

	/**
	 * 查询视频监控列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:list')")
	@GetMapping("/list")
	public AjaxResult list(SysCameraMonitor sysCameraMonitor) {
		List<SysCameraMonitor> list = sysCameraMonitorService.selectSysCameraMonitorList(sysCameraMonitor);
		Map<String, Object> result = Maps.newHashMap();
		result.put("data", list);
		result.put("server", Constants.WEBSCOKET.concat(sysConfigService.selectConfigByKey(MONITOR_SERVER_KEY)));
		return AjaxResult.success(result);
	}

	/**
	 * 导出视频监控列表
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:export')")
	@Log(title = "视频监控", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysCameraMonitor sysCameraMonitor) {
		List<SysCameraMonitor> list = sysCameraMonitorService.selectSysCameraMonitorList(sysCameraMonitor);
		ExcelUtil<SysCameraMonitor> util = new ExcelUtil<SysCameraMonitor>(SysCameraMonitor.class);
		util.exportExcel(response, list, "视频监控数据");
	}

	/**
	 * 获取视频监控详细信息
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:query')")
	@GetMapping(value = "/{monitorId}")
	public AjaxResult getInfo(@PathVariable("monitorId") Long monitorId) {
		return AjaxResult.success(sysCameraMonitorService.selectSysCameraMonitorByMonitorId(monitorId));
	}

	/**
	 * 新增视频监控
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:add')")
	@Log(title = "视频监控", businessType = BusinessType.INSERT)
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody SysCameraMonitor sysCameraMonitor) {
		return toAjax(sysCameraMonitorService.insertSysCameraMonitor(sysCameraMonitor));
	}

	/**
	 * 修改视频监控
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:edit')")
	@Log(title = "视频监控", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody SysCameraMonitor sysCameraMonitor) {
		return toAjax(sysCameraMonitorService.updateSysCameraMonitor(sysCameraMonitor));
	}

	/**
	 * 删除视频监控
	 */
	@PreAuthorize("@ss.hasPermi('monitor:camera:remove')")
	@Log(title = "视频监控", businessType = BusinessType.DELETE)
	@PostMapping("/del/{monitorIds}")
	public AjaxResult remove(@PathVariable Long[] monitorIds) {
		if (sysCameraMonitorService.hasChildSysCameraMonitorByMonitorId(monitorIds[0])) {
			return error("存在子监控,不允许删除");
		}
		return toAjax(sysCameraMonitorService.deleteSysCameraMonitorByMonitorIds(monitorIds));
	}
	
	/**
	 * 视频解析
	 * 
	 * @param sysCameraMonitor
	 * @return
	 */
	@PostMapping("/play")
	public AjaxResult play(@RequestBody SysCameraMonitor sysCameraMonitor) {
		String monitorServer = sysConfigService.selectConfigByKey(MONITOR_SERVER_KEY);
		Assert.hasText(sysCameraMonitor.getRtspUrl(), "RTSP地址不能为空！");
		Assert.hasText(monitorServer, "视频监控服务器地址不能为空！");
		String resultJSON = HttpUtil.post(Constants.HTTP.concat(monitorServer)
			.concat("/stream/play"), MapUtil.of("url", sysCameraMonitor.getRtspUrl()), 2000);
		Map<String, Object> result = JSON.parseObject(resultJSON, new TypeReference<Map<String, Object>>(){});
		int code = Integer.parseInt(result.get("code").toString());
		if(code == 0) {
			return AjaxResult.success(result.get("data"));
		} else {
			return AjaxResult.error(result.get("msg").toString());
		}
		
	}
}

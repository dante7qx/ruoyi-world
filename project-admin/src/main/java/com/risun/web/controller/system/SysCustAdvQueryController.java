package com.risun.web.controller.system;

import java.util.List;

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
import com.risun.system.domain.SysCustAdvQuery;
import com.risun.system.domain.SysCustAdvQueryCond;
import com.risun.system.service.ISysCustAdvQueryCondService;
import com.risun.system.service.ISysCustAdvQueryService;

/**
 * 自定义高级查询模板Controller
 * 
 * @author sunchao
 * @date 2023-08-02
 */
@RestController
@RequestMapping("/system/caq")
public class SysCustAdvQueryController extends BaseController {
	@Autowired
	private ISysCustAdvQueryService sysCustAdvQueryService;
	@Autowired
	private ISysCustAdvQueryCondService sysCustAdvQueryCondService;

	/**
	 * 查询自定义高级查询模板列表
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysCustAdvQuery sysCustAdvQuery) {
		startPage();
		List<SysCustAdvQuery> list = sysCustAdvQueryService.selectSysCustAdvQueryList(sysCustAdvQuery);
		return getDataTable(list);
	}

	/**
	 * 获取自定义高级查询模板详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:query')")
	@GetMapping("/{queryId}")
	public AjaxResult getInfo(@PathVariable("queryId") Long queryId) {
		return AjaxResult.success(sysCustAdvQueryService.selectSysCustAdvQueryByQueryId(queryId));
	}

	/**
	 * 获取自定义高级查询模板条件
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:query')")
	@PostMapping("/cond/{queryId}")
	public AjaxResult getCond(@PathVariable("queryId") Long queryId) {
		return AjaxResult.success(sysCustAdvQueryCondService.selectSysCustAdvQueryCondByQueryId(queryId));
	}
	
	/**
	 * 获取已配置的自定义高级查询模板条件
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:query')")
	@PostMapping("/cond_setup/{queryId}")
	public AjaxResult getCondSetup(@PathVariable("queryId") Long queryId) {
		return AjaxResult.success(sysCustAdvQueryCondService.selectSysCustAdvQueryCondByQueryId(queryId, true));
	}

	/**
	 * 新增自定义高级查询模板
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:add')")
	@Log(title = "自定义高级查询模板", businessType = BusinessType.INSERT)
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody SysCustAdvQuery sysCustAdvQuery) {
		sysCustAdvQueryService.insertSysCustAdvQuery(sysCustAdvQuery);
		return AjaxResult.success(sysCustAdvQuery);
	}

	/**
	 * 修改自定义高级查询模板
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:edit')")
	@Log(title = "自定义高级查询模板", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody SysCustAdvQuery sysCustAdvQuery) {
		return toAjax(sysCustAdvQueryService.updateSysCustAdvQuery(sysCustAdvQuery));
	}

	/**
	 * 删除自定义高级查询模板
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:remove')")
	@Log(title = "自定义高级查询模板", businessType = BusinessType.DELETE)
	@PostMapping("/del/{queryIds}")
	public AjaxResult remove(@PathVariable Long[] queryIds) {
		return toAjax(sysCustAdvQueryService.deleteSysCustAdvQueryByQueryIds(queryIds));
	}
	
	/**
	 * 新增自定义高级查询模板
	 */
	@PreAuthorize("@ss.hasPermi('system:caq:add')")
	@Log(title = "自定义高级查询模板", businessType = BusinessType.INSERT)
	@PostMapping("/add_conds/{queryId}")
	public AjaxResult addCond(@PathVariable Long queryId, @RequestBody List<SysCustAdvQueryCond> conds) {
		return toAjax(sysCustAdvQueryCondService.batchInsertSysCustAdvQueryCond(queryId, conds));
	}

}

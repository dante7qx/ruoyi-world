package com.spirit.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.common.utils.ip.IpUtils;
import com.spirit.common.utils.poi.ExcelUtil;
import com.spirit.system.domain.SysInfo;
import com.spirit.system.domain.SysInfoProp;
import com.spirit.system.service.ISysInfoPropService;
import com.spirit.system.service.ISysInfoService;

import cn.hutool.core.util.ObjectUtil;

/**
 * 信息发布Controller
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/system/info")
public class SysInfoController extends BaseController {
	@Autowired
	private ISysInfoService sysInfoService;
	@Autowired
	private ISysInfoPropService sysInfoPropService;

	/**
	 * 查询信息发布列表
	 */
	@PreAuthorize("@ss.hasPermi('system:info:list')")
	@GetMapping("/list")
	public TableDataInfo list(SysInfo sysInfo) {
		startPage();
		List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
		return getDataTable(list);
	}

	/**
	 * 查询待审批信息发布列表
	 */
	@PreAuthorize("@ss.hasAnyRoles('info_mgr, admin')")
	@GetMapping("/list_approval")
	public TableDataInfo listApproval(SysInfo sysInfo) {
		Assert.isTrue(SysInfo.DSP_STATUS.equals(sysInfo.getStatus()), "只能获取待审批的信息记录！");
		startPage();
		List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
		return getDataTable(list);
	}

	/**
	 * 查询信息发布浏览列表
	 */
	@GetMapping("/list_view")
	public TableDataInfo list4View(SysInfo sysInfo) {
		startPage();
		List<SysInfo> list = sysInfoService.selectSysInfoList4View(sysInfo);
		return getDataTable(list);
	}

	/**
	 * 导出信息发布列表
	 */
	@PreAuthorize("@ss.hasPermi('system:info:export')")
	@Log(title = "信息发布", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysInfo sysInfo) {
		List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
		ExcelUtil<SysInfo> util = new ExcelUtil<SysInfo>(SysInfo.class);
		util.exportExcel(response, list, "信息发布数据");
	}

	/**
	 * 获取信息发布详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:info:query')")
	@GetMapping(value = "/{infoId}")
	public AjaxResult getInfo(@PathVariable("infoId") Long infoId) {
		return AjaxResult.success(sysInfoService.selectSysInfoByInfoId(infoId));
	}

	/**
	 * 获取信息发布详细信息4浏览
	 */
	@PostMapping(value = "/view/{infoId}")
	public AjaxResult getInfo4View(@PathVariable("infoId") Long infoId, HttpServletRequest request) {
		SysInfo sysInfo = sysInfoService.selectSysInfo4ViewByInfoId(infoId);
		if(ObjectUtil.isNotNull(sysInfo)) {
			// 更新浏览数
			sysInfoService.updateSysInfoViewCount(infoId, IpUtils.getIpAddr(request));
		}
		return AjaxResult.success(sysInfo);
	}

	/**
	 * 新增信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:add')")
	@Log(title = "信息发布", businessType = BusinessType.INSERT)
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.insertSysInfo(sysInfo));
	}

	/**
	 * 修改信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.updateSysInfo(sysInfo));
	}

	/**
	 * 删除信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:remove')")
	@Log(title = "信息发布", businessType = BusinessType.DELETE)
	@PostMapping("/del/{infoIds}")
	public AjaxResult remove(@PathVariable Long[] infoIds) {
		return toAjax(sysInfoService.deleteSysInfoByInfoIds(infoIds));
	}

	/**
	 * 批量审批
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "批量审批", businessType = BusinessType.APPROVAL)
	@PostMapping("/batch_approval")
	public AjaxResult batchApproval(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.batchApproval(sysInfo));
	}

	/**
	 * 置顶信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "置顶信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/set_top")
	public AjaxResult setInfoTop(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.setInfoTop(sysInfo));
	}
	
	/**
	 * 撤销已发布信息
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "撤销已发布信息", businessType = BusinessType.UPDATE)
	@PostMapping("/batch_withdraw")
	public AjaxResult batchWithdraw(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.withdrawSysInfo(sysInfo));
	}

	/**
	 * 停用（启用）信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "停用（启用）信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/set_disabled")
	public AjaxResult setInfoDisabled(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.setInfoDisabled(sysInfo));
	}

	/**
	 * 设置（取消）评论信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "设置（取消）评论信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/set_commantable")
	public AjaxResult setInfoComment(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.setCommentSysInfo(sysInfo));
	}
	
	/**
	 * 设置（取消）匿名访问信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "设置（取消）匿名访问信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/set_anonymous")
	public AjaxResult setInfoAnonymous(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.setAnonymousSysInfo(sysInfo));
	}

	/**
	 * 设置访问范围信息发布
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "设置访问范围信息发布", businessType = BusinessType.UPDATE)
	@PostMapping("/set_range")
	public AjaxResult setInfoRange(@RequestBody SysInfo sysInfo) {
		return toAjax(sysInfoService.setRangeSysInfo(sysInfo));
	}

	/**
	 * 获取指定信息Id的访问范围
	 */
	@PostMapping("/info_range/{infoId}")
	public AjaxResult getInfoRange(@PathVariable Long infoId) {
		return AjaxResult.success(sysInfoService.selectInfoRangeByInfoId(infoId));
	}
	
	/**
	 * 收藏信息发布
	 * 
	 * @param infoId
	 * @return
	 */
	@PostMapping("/favor_info/{infoId}")
	public AjaxResult favorInfo(@PathVariable Long infoId) {
		return AjaxResult.success(sysInfoService.updateSysInfoFavorCount(infoId));
	}
	
	/**
	 * 点赞（取消点赞）信息发布
	 * 
	 * @param infoId
	 * @return
	 */
	@PostMapping("/praise_info/{infoId}/{isPraise}")
	public AjaxResult praiseInfo(@PathVariable Long infoId, @PathVariable Boolean isPraise) {
		int result = 1;
		if(isPraise) {
			result = sysInfoService.updateSysInfoIncreasePraiseCount(infoId);
		} else {
			result = sysInfoService.updateSysInfoDecreasePraiseCount(infoId);
		}
		return AjaxResult.success(result);
	}

	/**
	 * 查询信息属性列表
	 */
	@PreAuthorize("@ss.hasPermi('system:info:list')")
	@GetMapping("/list_prop")
	public TableDataInfo listProp(SysInfoProp sysInfoProp) {
		Assert.notNull(sysInfoProp.getInfoId(), "只能查询执行信息Id的信息属性列表！");
		startPage();
		List<SysInfoProp> list = sysInfoPropService.selectSysInfoPropList(sysInfoProp);
		return getDataTable(list);
	}

	/**
	 * 获取信息属性详细信息
	 */
	@PreAuthorize("@ss.hasPermi('system:info:query')")
	@GetMapping(value = "/prop/{propId}")
	public AjaxResult getPropInfo(@PathVariable("propId") Long propId) {
		return AjaxResult.success(sysInfoPropService.selectSysInfoPropByPropId(propId));
	}

	/**
	 * 新增信息属性
	 */
	@PreAuthorize("@ss.hasPermi('system:info:add')")
	@Log(title = "信息属性", businessType = BusinessType.INSERT)
	@PostMapping("/insert_prop")
	public AjaxResult addProp(@RequestBody SysInfoProp sysInfoProp) {
		return toAjax(sysInfoPropService.insertSysInfoProp(sysInfoProp));
	}

	/**
	 * 修改信息属性
	 */
	@PreAuthorize("@ss.hasPermi('system:info:edit')")
	@Log(title = "信息属性", businessType = BusinessType.UPDATE)
	@PostMapping("/update_prop")
	public AjaxResult editProp(@RequestBody SysInfoProp sysInfoProp) {
		return toAjax(sysInfoPropService.updateSysInfoProp(sysInfoProp));
	}

	/**
	 * 删除信息属性
	 */
	@PreAuthorize("@ss.hasPermi('system:info:remove')")
	@Log(title = "信息属性", businessType = BusinessType.DELETE)
	@PostMapping("/del_prop/{propIds}")
	public AjaxResult removeProp(@PathVariable Long[] propIds) {
		return toAjax(sysInfoPropService.deleteSysInfoPropByPropIds(propIds));
	}

}
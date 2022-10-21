package com.risun.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.system.domain.SysInfo;
import com.risun.system.service.ISysInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 信息发布Controller
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/system/info")
public class SysInfoController extends BaseController
{
    @Autowired
    private ISysInfoService sysInfoService;

    /**
     * 查询信息发布列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysInfo sysInfo)
    {
        startPage();
        List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
        return getDataTable(list);
    }
    
    /**
     * 查询待审批信息发布列表
     */
    @PreAuthorize("@ss.hasAnyRoles('info_mgr, admin')")
    @GetMapping("/list_approval")
    public TableDataInfo listApproval(SysInfo sysInfo)
    {
    	Assert.isTrue(SysInfo.DSP_STATUS.equals(sysInfo.getStatus()), "只能获取待审批的信息记录！");
        startPage();
        List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
        return getDataTable(list);
    }
    
    /**
     * 查询信息发布浏览列表
     */
    @GetMapping("/list_view")
    public TableDataInfo list4View(SysInfo sysInfo)
    {
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
    public void export(HttpServletResponse response, SysInfo sysInfo)
    {
        List<SysInfo> list = sysInfoService.selectSysInfoList(sysInfo);
        ExcelUtil<SysInfo> util = new ExcelUtil<SysInfo>(SysInfo.class);
        util.exportExcel(response, list, "信息发布数据");
    }

    /**
     * 获取信息发布详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return AjaxResult.success(sysInfoService.selectSysInfoByInfoId(infoId));
    }

    /**
     * 新增信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "信息发布", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.insertSysInfo(sysInfo));
    }

    /**
     * 修改信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.updateSysInfo(sysInfo));
    }

    /**
     * 删除信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "信息发布", businessType = BusinessType.DELETE)
    @PostMapping("/del/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(sysInfoService.deleteSysInfoByInfoIds(infoIds));
    }
    
    /**
     * 批量审批
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "批量审批", businessType = BusinessType.APPROVAL)
    @PostMapping("/batch_approval")
    public AjaxResult batchApproval(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.batchApproval(sysInfo));
    }
    
    /**
     * 置顶信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "置顶信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/set_top")
    public AjaxResult setInfoTop(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.setInfoTop(sysInfo));
    }
    
    /**
     * 停用（启用）信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "停用（启用）信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/set_disabled")
    public AjaxResult setInfoDisabled(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.setInfoDisabled(sysInfo));
    }
    
    /**
     * 设置（取消）匿名访问信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "停用（启用）信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/set_anonymous")
    public AjaxResult setInfoAnonymous(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.setAnonymousSysInfo(sysInfo));
    }
    
    /**
     * 设置访问范围信息发布
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "设置访问范围信息发布", businessType = BusinessType.UPDATE)
    @PostMapping("/set_range")
    public AjaxResult setInfoRange(@RequestBody SysInfo sysInfo)
    {
        return toAjax(sysInfoService.setRangeSysInfo(sysInfo));
    }
    
    /**
     * 获取指定信息Id的访问范围
     */
    @PostMapping("/info_range/{infoId}")
    public AjaxResult getInfoRange(@PathVariable Long infoId)
    {
        return AjaxResult.success(sysInfoService.selectInfoRangeByInfoId(infoId));
    }
    
    
}
package com.risun.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysMenu;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.report.domain.RiJmReport;
import com.risun.report.service.IRiJmReportService;
import com.risun.system.service.ISysMenuService;

/**
 * 报表列表Controller
 * 
 * @author sunchao
 * @date 2023-05-18
 */
@RestController
@RequestMapping("/jimureport/jimureport")
public class RiJmReportController extends BaseController
{
    @Autowired
    private IRiJmReportService jimuReportService;
    @Autowired
    private ISysMenuService menuService;

    /**
     * 查询报表列表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RiJmReport jimuReport)
    {
        startPage();
        List<RiJmReport> list = jimuReportService.selectJimuReportList(jimuReport);
        return getDataTable(list);
    }

    /**
     * 获取报表列表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(jimuReportService.selectJimuReportById(id));
    }

    /**
     * 根据过滤条件查询菜单信息
     */
    @PostMapping("/listMenu/{menuName}")
    public AjaxResult listMenu(@PathVariable String menuName)
    {
    	SysMenu menu = new SysMenu();
    	menu.setMenuName(menuName);
        return AjaxResult.success(menuService.selectMenuList(menu, getUserId()));
    }

    /**
     * 设置报表菜单
     */
    @Log(title = "报表列表", businessType = BusinessType.UPDATE)
    @PostMapping("/setupMenu")
    public AjaxResult setupMenu(@RequestBody RiJmReport jimuReport)
    {
        return toAjax(jimuReportService.insertJimuReportMenu(jimuReport));
    }
    
    /**
     * 取消报表菜单
     */
    @Log(title = "报表列表", businessType = BusinessType.UPDATE)
    @PostMapping("/cancelMenu/{ids}")
    public AjaxResult setupMenu(@PathVariable String[] ids)
    {
        return toAjax(jimuReportService.cancelJimuReportMenu(ids));
    }
    
    /**
     * 修改报表列表
     */
    @Log(title = "报表列表", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody RiJmReport jimuReport)
    {
        return toAjax(jimuReportService.updateJimuReport(jimuReport));
    }

    /**
     * 删除报表列表
     */
    @Log(title = "报表列表", businessType = BusinessType.DELETE)
    @PostMapping("/del/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(jimuReportService.deleteJimuReportByIds(ids));
    }
    
    /**
     * 设置部门权限
     */
    @Log(title = "报表列表", businessType = BusinessType.UPDATE)
    @PostMapping("/setupDeptAcl/{deptId}/{ids}")
    public AjaxResult setupDeptAcl(@PathVariable Long deptId, @PathVariable String[] ids)
    {
    	Assert.notNull(deptId, "部门Id不能为空！");
    	Assert.noNullElements(ids, "报表不能为空！");
        return toAjax(jimuReportService.setupDeptAcl(deptId, ids));
    }
    
    /**
     * 移除部门权限
     */
    @Log(title = "报表列表", businessType = BusinessType.UPDATE)
    @PostMapping("/removeDeptAcl/{deptId}/{ids}")
    public AjaxResult removeDeptAcl(@PathVariable Long deptId, @PathVariable String[] ids)
    {
    	Assert.notNull(deptId, "部门Id不能为空！");
    	Assert.noNullElements(ids, "报表不能为空！");
        return toAjax(jimuReportService.removeDeptAcl(deptId, ids));
    }
}

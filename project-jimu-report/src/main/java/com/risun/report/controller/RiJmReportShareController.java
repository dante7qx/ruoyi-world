package com.risun.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.risun.report.domain.RiJmReportShare;
import com.risun.report.service.IRiJmReportShareService;

/**
 * 报表预览Controller
 * 
 * @author sunchao
 * @date 2023-05-19
 */
@RestController
@RequestMapping("/jimureport/share")
public class RiJmReportShareController extends BaseController
{
    @Autowired
    private IRiJmReportShareService riJmReportShareService;

    /**
     * 查询报表预览列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RiJmReportShare riJmReportShare)
    {
        startPage();
        List<RiJmReportShare> list = riJmReportShareService.selectRiJmReportShareList(riJmReportShare);
        return getDataTable(list);
    }

    /**
     * 导出报表预览列表
     */
    @Log(title = "报表预览", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RiJmReportShare riJmReportShare)
    {
        List<RiJmReportShare> list = riJmReportShareService.selectRiJmReportShareList(riJmReportShare);
        ExcelUtil<RiJmReportShare> util = new ExcelUtil<RiJmReportShare>(RiJmReportShare.class);
        util.exportExcel(response, list, "报表预览数据");
    }

    /**
     * 获取报表预览详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(riJmReportShareService.selectRiJmReportShareById(id));
    }

    /**
     * 新增报表预览
     */
    @Log(title = "报表预览", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody RiJmReportShare riJmReportShare)
    {
        return toAjax(riJmReportShareService.insertRiJmReportShare(riJmReportShare));
    }

    /**
     * 修改报表预览
     */
    @Log(title = "报表预览", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody RiJmReportShare riJmReportShare)
    {
        return toAjax(riJmReportShareService.updateRiJmReportShare(riJmReportShare));
    }

    /**
     * 删除报表预览
     */
    @Log(title = "报表预览", businessType = BusinessType.DELETE)
    @PostMapping("/del/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(riJmReportShareService.deleteRiJmReportShareByIds(ids));
    }
}

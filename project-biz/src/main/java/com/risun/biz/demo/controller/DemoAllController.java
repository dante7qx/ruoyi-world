package com.risun.biz.demo.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.risun.biz.demo.domain.DemoAll;
import com.risun.biz.demo.service.IDemoAllService;
import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;

/**
 * 全部业务功能Controller
 * 
 * @author sunchao
 * @date 2023-09-08
 */
@RestController
@RequestMapping("/biz/demoall")
public class DemoAllController extends BaseController {
    @Autowired
    private IDemoAllService demoAllService;

    /**
     * 查询全部业务功能列表
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:list')")
    @GetMapping("/list")
    public TableDataInfo list(DemoAll demoAll) {
        startPage();
        List<DemoAll> list = demoAllService.selectDemoAllList(demoAll);
        return getDataTable(list);
    }

    /**
     * 获取全部业务功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:query')")
    @GetMapping(value = "/{demoId}")
    public AjaxResult getInfo(@PathVariable("demoId") Long demoId) {
        return AjaxResult.success(demoAllService.selectDemoAllByDemoId(demoId));
    }

    /**
     * 新增全部业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:add')")
    @Log(title = "全部业务功能", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody DemoAll demoAll) {
        return toAjax(demoAllService.insertDemoAll(demoAll));
    }

    /**
     * 修改全部业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:edit')")
    @Log(title = "全部业务功能", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody DemoAll demoAll) {
        return toAjax(demoAllService.updateDemoAll(demoAll));
    }

    /**
     * 删除全部业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:remove')")
    @Log(title = "全部业务功能", businessType = BusinessType.DELETE)
    @PostMapping("/del/{demoIds}")
    public AjaxResult remove(@PathVariable Long[] demoIds) {
        return toAjax(demoAllService.deleteDemoAllByDemoIds(demoIds));
    }
    
    /**
     * 导入全部业务功能
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:import')")
    @Log(title = "全部业务功能", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<DemoAll> util = new ExcelUtil<DemoAll>(DemoAll.class);
        List<DemoAll> demoAllList = util.importExcel(file.getInputStream());
        String message = demoAllService.importDemoAll(demoAllList);
        return AjaxResult.success(message);
    }
    
    /**
     * 下载导入模板
     * 
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<DemoAll> util = new ExcelUtil<DemoAll>(DemoAll.class);
        util.importTemplateExcel(response, "全部业务功能模板");
    }
    
    /**
     * 导出全部业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoall:export')")
    @Log(title = "全部业务功能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DemoAll demoAll) {
        List<DemoAll> list = demoAllService.selectDemoAllList(demoAll);
        ExcelUtil<DemoAll> util = new ExcelUtil<DemoAll>(DemoAll.class);
        util.exportExcel(response, list, "全部业务功能数据");
    }
}

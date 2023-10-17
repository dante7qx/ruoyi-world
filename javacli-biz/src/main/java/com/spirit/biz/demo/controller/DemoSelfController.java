package com.spirit.biz.demo.controller;

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

import com.spirit.biz.demo.domain.DemoSelf;
import com.spirit.biz.demo.service.IDemoSelfService;
import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.common.utils.poi.ExcelUtil;

/**
 * 私有业务功能Controller
 * 
 * @author sunchao
 * @date 2023-09-08
 */
@RestController
@RequestMapping("/biz/demoself")
public class DemoSelfController extends BaseController {
    @Autowired
    private IDemoSelfService demoSelfService;

    /**
     * 查询私有业务功能列表
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:list')")
    @GetMapping("/list")
    public TableDataInfo list(DemoSelf demoSelf) {
        startPage();
        List<DemoSelf> list = demoSelfService.selectDemoSelfList(demoSelf);
        return getDataTable(list);
    }

    /**
     * 获取私有业务功能详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:query')")
    @GetMapping(value = "/{demoId}")
    public AjaxResult getInfo(@PathVariable("demoId") Long demoId) {
        return AjaxResult.success(demoSelfService.selectDemoSelfByDemoId(demoId));
    }

    /**
     * 新增私有业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:add')")
    @Log(title = "私有业务功能", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody DemoSelf demoSelf) {
        return toAjax(demoSelfService.insertDemoSelf(demoSelf));
    }

    /**
     * 修改私有业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:edit')")
    @Log(title = "私有业务功能", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody DemoSelf demoSelf) {
        return toAjax(demoSelfService.updateDemoSelf(demoSelf));
    }

    /**
     * 删除私有业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:remove')")
    @Log(title = "私有业务功能", businessType = BusinessType.DELETE)
    @PostMapping("/del/{demoIds}")
    public AjaxResult remove(@PathVariable Long[] demoIds) {
        return toAjax(demoSelfService.deleteDemoSelfByDemoIds(demoIds));
    }
    
    /**
     * 导入私有业务功能
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:import')")
    @Log(title = "私有业务功能", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<DemoSelf> util = new ExcelUtil<DemoSelf>(DemoSelf.class);
        List<DemoSelf> demoSelfList = util.importExcel(file.getInputStream());
        String message = demoSelfService.importDemoSelf(demoSelfList);
        return AjaxResult.success(message);
    }
    
    /**
     * 下载导入模板
     * 
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<DemoSelf> util = new ExcelUtil<DemoSelf>(DemoSelf.class);
        util.importTemplateExcel(response, "私有业务功能模板");
    }
    
    /**
     * 导出私有业务功能
     */
    @PreAuthorize("@ss.hasPermi('biz:demoself:export')")
    @Log(title = "私有业务功能", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DemoSelf demoSelf) {
        List<DemoSelf> list = demoSelfService.selectDemoSelfList(demoSelf);
        ExcelUtil<DemoSelf> util = new ExcelUtil<DemoSelf>(DemoSelf.class);
        util.exportExcel(response, list, "私有业务功能数据");
    }
}

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

import com.google.common.collect.Lists;
import com.risun.biz.demo.domain.Demo;
import com.risun.biz.demo.service.IDemoService;
import com.risun.common.annotation.CustomizeAdvancedSearch;
import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.common.utils.wordfilter.SensitiveWordUtil;

import cn.hutool.core.date.DateUtil;

/**
 * 业务Controller
 * 
 * @author sunchao
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/biz/demo")
public class DemoController extends BaseController
{
    @Autowired
    private IDemoService demoService;

    /**
     * 查询业务列表
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:list')")
    @GetMapping("/list")
    @CustomizeAdvancedSearch
    public TableDataInfo list(Demo demo)
    {
        startPage();
        List<Demo> list = demoService.selectDemoList(demo);
        return getDataTable(list);
    }

    /**
     * 导出业务列表
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:export')")
    @Log(title = "业务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Demo demo)
    {
        List<Demo> list = demoService.selectDemoList(demo);
        ExcelUtil<Demo> util = new ExcelUtil<>(Demo.class);
        util.exportExcel(response, list, "业务数据", "业务数据报表");
    }

    /**
     * 获取业务详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:query')")
    @GetMapping(value = "/{demoId}")
    public AjaxResult getInfo(@PathVariable("demoId") Long demoId)
    {
        return AjaxResult.success(demoService.selectDemoByDemoId(demoId));
    }
    
    /**
     * 新增业务
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:add')")
    @Log(title = "业务", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody Demo demo)
    {
    	if(SensitiveWordUtil.allowed(demo)) {
    		return toAjax(demoService.insertDemo(demo));
    	} else {
    		return AjaxResult.error("新增内容包含非法字符，请检查后再尝试！");
    	}
        
    }

    /**
     * 修改业务
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:edit')")
    @Log(title = "业务", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody Demo demo)
    {
    	demo.setDemoName(SensitiveWordUtil.filter(demo.getDemoName()));
        return toAjax(demoService.updateDemo(demo));
    }

    /**
     * 删除业务
     */
    @PreAuthorize("@ss.hasPermi('biz:demo:remove')")
    @Log(title = "业务", businessType = BusinessType.DELETE)
	@PostMapping("/del/{demoIds}")
    public AjaxResult remove(@PathVariable Long[] demoIds)
    {
        return toAjax(demoService.deleteDemoByDemoIds(demoIds));
    }
    
    /**
	 * 批量新增业务
	 */
	@PreAuthorize("@ss.hasPermi('biz:demo:add')")
	@Log(title = "批量新增业务", businessType = BusinessType.INSERT)
	@PostMapping("/insertBatch")
	public AjaxResult addBatch() {
		List<Demo> demos = Lists.newArrayList();
		for (int i = 0; i < 200; i++) {
			Demo demo = new Demo();
			demo.setDemoName("测试数据" + i);
			demo.setDemoTime(DateUtil.date());
			demo.setCreateBy(getUsername());
			demo.setCreateTime(DateUtil.date());
			demos.add(demo);
		}
		return toAjax(demoService.batchInsertDemo(demos));
	}

}

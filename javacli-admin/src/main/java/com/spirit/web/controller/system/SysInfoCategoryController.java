package com.spirit.web.controller.system;

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

import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.common.utils.poi.ExcelUtil;
import com.spirit.system.domain.SysInfoCategory;
import com.spirit.system.domain.SysInfoCategoryProp;
import com.spirit.system.service.ISysInfoCategoryPropService;
import com.spirit.system.service.ISysInfoCategoryService;
import com.spirit.system.service.ISysInfoPropService;
import com.spirit.system.service.ISysInfoService;

/**
 * 信息栏目Controller
 * 
 * @author sunchao
 * @date 2023-08-29
 */
@RestController
@RequestMapping("/system/infocategory")
public class SysInfoCategoryController extends BaseController {
    @Autowired
    private ISysInfoCategoryService sysInfoCategoryService;
    @Autowired
    private ISysInfoCategoryPropService sysInfoCategoryPropService;
    @Autowired
    private ISysInfoService sysInfoService;
    @Autowired
    private ISysInfoPropService sysInfoPropService;

    /**
     * 查询信息栏目列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public AjaxResult list(SysInfoCategory sysInfoCategory) {
        List<SysInfoCategory> list = sysInfoCategoryService.selectSysInfoCategoryList(sysInfoCategory);
        return AjaxResult.success(list);
    }

    /**
     * 获取信息栏目详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId) {
        return AjaxResult.success(sysInfoCategoryService.selectSysInfoCategoryByCategoryId(categoryId));
    }

    /**
     * 新增信息栏目
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "信息栏目", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody SysInfoCategory sysInfoCategory) {
    	try {
    		return toAjax(sysInfoCategoryService.insertSysInfoCategory(sysInfoCategory));
    	} catch (Exception e) {
			return error("栏目键是惟一的，不允许重复！");
		}
    }

    /**
     * 修改信息栏目
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "信息栏目", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysInfoCategory sysInfoCategory) {
        try {
    		return toAjax(sysInfoCategoryService.updateSysInfoCategory(sysInfoCategory));
    	} catch (Exception e) {
			return error("栏目键是惟一的，不允许重复！");
		}
    }

    /**
     * 删除信息栏目
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "信息栏目", businessType = BusinessType.DELETE)
    @PostMapping("/del/{categoryIds}")
	public AjaxResult remove(@PathVariable Long[] categoryIds) {
		if (sysInfoCategoryService.hasChildSysInfoCategoryByCategoryId(categoryIds[0])) {
			return error("存在子栏目,不允许删除");
		}
		if (sysInfoCategoryPropService.hasChildSysInfoCategoryPropByCategoryId(categoryIds[0])) {
			return error("存在栏目属性,不允许删除");
		}
		if (sysInfoService.hasSysInfoByCategoryId(categoryIds[0])) {
			return error("存在信息发布记录,不允许删除");
		}
		return toAjax(sysInfoCategoryService.deleteSysInfoCategoryByCategoryIds(categoryIds));
	}

    /**
     * 导出信息栏目
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "信息栏目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysInfoCategory sysInfoCategory) {
        List<SysInfoCategory> list = sysInfoCategoryService.selectSysInfoCategoryList(sysInfoCategory);
        ExcelUtil<SysInfoCategory> util = new ExcelUtil<SysInfoCategory>(SysInfoCategory.class);
        util.exportExcel(response, list, "信息栏目数据");
    }
    
    /**
     * 查询信息栏目属性列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list_prop")
    public TableDataInfo listProp(SysInfoCategoryProp sysInfoCategoryProp) {
        startPage();
        List<SysInfoCategoryProp> list = sysInfoCategoryPropService.selectSysInfoCategoryPropList(sysInfoCategoryProp);
        return getDataTable(list);
    }

    /**
     * 获取信息栏目属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/prop/{propId}")
    public AjaxResult getProp(@PathVariable("propId") Long propId) {
        return AjaxResult.success(sysInfoCategoryPropService.selectSysInfoCategoryPropByPropId(propId));
    }

    /**
     * 新增信息栏目属性
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "信息栏目属性", businessType = BusinessType.INSERT)
    @PostMapping("/insert_prop")
    public AjaxResult addProp(@RequestBody SysInfoCategoryProp sysInfoCategoryProp) {
        return toAjax(sysInfoCategoryPropService.insertSysInfoCategoryProp(sysInfoCategoryProp));
    }

    /**
     * 修改信息栏目属性
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "信息栏目属性", businessType = BusinessType.UPDATE)
    @PostMapping("/update_prop")
    public AjaxResult editProp(@RequestBody SysInfoCategoryProp sysInfoCategoryProp) {
        return toAjax(sysInfoCategoryPropService.updateSysInfoCategoryProp(sysInfoCategoryProp));
    }

    /**
     * 删除信息栏目属性
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "信息栏目属性", businessType = BusinessType.DELETE)
    @PostMapping("/del_prop/{propIds}")
    public AjaxResult removeProp(@PathVariable Long[] propIds) {
    	if (sysInfoPropService.hasSysInfoPropByCategoryPropId(propIds)) {
    		return error("存在信息属性记录,不允许删除");
    	}
        return toAjax(sysInfoCategoryPropService.deleteSysInfoCategoryPropByPropIds(propIds));
    }
    
}

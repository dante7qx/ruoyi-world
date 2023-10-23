package com.spirit.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.domain.entity.SysDictType;
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.report.domain.RiJmDict;
import com.spirit.report.service.IRiJmDictService;

/**
 * 报表字典Controller
 * 
 * @author sunchao
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/jimureport/dict")
public class RiJmDictController extends BaseController
{
    @Autowired
    private IRiJmDictService riJmDictService;

    /**
     * 查询报表字典列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RiJmDict riJmDict)
    {
        startPage();
        List<RiJmDict> list = riJmDictService.selectRiJmDictList(riJmDict);
        return getDataTable(list);
    }


    /**
     * 获取报表字典详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(riJmDictService.selectRiJmDictById(id));
    }

    /**
     * 新增报表字典
     */
    @Log(title = "报表字典", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody RiJmDict riJmDict)
    {
        return toAjax(riJmDictService.insertRiJmDict(riJmDict));
    }

    /**
     * 修改报表字典
     */
    @Log(title = "报表字典", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody RiJmDict riJmDict)
    {
        return toAjax(riJmDictService.updateRiJmDict(riJmDict));
    }

    /**
     * 删除报表字典
     */
    @Log(title = "报表字典", businessType = BusinessType.DELETE)
    @PostMapping("/del/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(riJmDictService.deleteRiJmDictByIds(ids));
    }
    
    /**
     * 同步系统数据字典
     */
    @Log(title = "报表字典", businessType = BusinessType.UPDATE)
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody List<SysDictType> sysDicts)
    {
        return toAjax(riJmDictService.syncRiJmDict(sysDicts));
    }
}

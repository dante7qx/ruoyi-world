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
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.report.domain.RiJmDictItem;
import com.spirit.report.service.IRiJmDictItemService;

/**
 * dictController
 * 
 * @author sunchao
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/jimureport/dictitem")
public class RiJmDictItemController extends BaseController
{
    @Autowired
    private IRiJmDictItemService riJmDictItemService;

    /**
     * 查询dict列表
     */
    @GetMapping("/list")
    public TableDataInfo list(RiJmDictItem riJmDictItem)
    {
        startPage();
        List<RiJmDictItem> list = riJmDictItemService.selectRiJmDictItemList(riJmDictItem);
        return getDataTable(list);
    }

    /**
     * 获取dict详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(riJmDictItemService.selectRiJmDictItemById(id));
    }

    /**
     * 新增dict
     */
    @Log(title = "dict", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody RiJmDictItem riJmDictItem)
    {
        return toAjax(riJmDictItemService.insertRiJmDictItem(riJmDictItem));
    }

    /**
     * 修改dict
     */
    @Log(title = "dict", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody RiJmDictItem riJmDictItem)
    {
        return toAjax(riJmDictItemService.updateRiJmDictItem(riJmDictItem));
    }

    /**
     * 删除dict
     */
    @Log(title = "dict", businessType = BusinessType.DELETE)
    @PostMapping("/del/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(riJmDictItemService.deleteRiJmDictItemByIds(ids));
    }
}

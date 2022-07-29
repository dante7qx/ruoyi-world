package com.risun.biz.biz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.enums.BusinessType;
import com.risun.biz.biz.domain.Biz;
import com.risun.biz.biz.service.IBizService;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.common.core.page.TableDataInfo;

/**
 * 业务管理Controller
 * 
 * @author sunchao
 * @date 2022-07-29
 */
@RestController
@RequestMapping("/biz/biz")
public class BizController extends BaseController
{
    @Autowired
    private IBizService bizService;

    /**
     * 查询业务管理列表
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:list')")
    @GetMapping("/list")
    public TableDataInfo list(Biz biz)
    {
        startPage();
        List<Biz> list = bizService.selectBizList(biz);
        return getDataTable(list);
    }

    /**
     * 导出业务管理列表
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:export')")
    @Log(title = "业务管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Biz biz)
    {
        List<Biz> list = bizService.selectBizList(biz);
        ExcelUtil<Biz> util = new ExcelUtil<Biz>(Biz.class);
        util.exportExcel(response, list, "业务管理数据");
    }

    /**
     * 获取业务管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:query')")
    @GetMapping(value = "/{bizId}")
    public AjaxResult getInfo(@PathVariable("bizId") Long bizId)
    {
        return AjaxResult.success(bizService.selectBizByBizId(bizId));
    }

    /**
     * 新增业务管理
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:add')")
    @Log(title = "业务管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Biz biz)
    {
        return toAjax(bizService.insertBiz(biz));
    }

    /**
     * 修改业务管理
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:edit')")
    @Log(title = "业务管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Biz biz)
    {
        return toAjax(bizService.updateBiz(biz));
    }

    /**
     * 删除业务管理
     */
    @PreAuthorize("@ss.hasPermi('biz:biz:remove')")
    @Log(title = "业务管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bizIds}")
    public AjaxResult remove(@PathVariable Long[] bizIds)
    {
        return toAjax(bizService.deleteBizByBizIds(bizIds));
    }
}

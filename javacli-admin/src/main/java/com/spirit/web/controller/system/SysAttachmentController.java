package com.spirit.web.controller.system;

import java.util.List;

import com.spirit.common.annotation.Log;
import com.spirit.common.core.controller.BaseController;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.page.TableDataInfo;
import com.spirit.common.enums.BusinessType;
import com.spirit.system.domain.SysAttachment;
import com.spirit.system.service.ISysAttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务附件Controller
 * 
 * @author sunchao
 * @date 2022-10-14
 */
@RestController
@RequestMapping("/system/attachment")
public class SysAttachmentController extends BaseController
{
    @Autowired
    private ISysAttachmentService sysAttachmentService;

    /**
     * 查询业务附件列表
     */
    @PreAuthorize("@ss.hasPermi('system:attachment:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAttachment sysAttachment)
    {
        startPage();
        List<SysAttachment> list = sysAttachmentService.selectSysAttachmentList(sysAttachment);
        return getDataTable(list);
    }

    /**
     * 获取业务附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:attachment:query')")
    @GetMapping(value = "/{attachId}")
    public AjaxResult getInfo(@PathVariable("attachId") Long attachId)
    {
        return AjaxResult.success(sysAttachmentService.selectSysAttachmentByAttachId(attachId));
    }
    
    /**
     * 获取业务附件详细信息
     */
    @PostMapping(value = "/by_url")
    public AjaxResult getInfoByFileUrl(@RequestBody SysAttachment sysAttachment)
    {
        return AjaxResult.success(sysAttachmentService.selectSysAttachmentByFileUrl(sysAttachment.getFileUrl()));
    }

    /**
     * 删除业务附件
     */
    @PreAuthorize("@ss.hasPermi('system:attachment:remove')")
    @Log(title = "业务附件", businessType = BusinessType.DELETE)
    @PostMapping("/del/{attachIds}")
    public AjaxResult remove(@PathVariable Long[] attachIds)
    {
        return toAjax(sysAttachmentService.deleteSysAttachmentByAttachIds(attachIds));
    }
}

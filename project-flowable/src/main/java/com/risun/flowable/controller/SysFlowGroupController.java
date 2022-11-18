package com.risun.flowable.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.flowable.domain.SysFlowGroup;
import com.risun.flowable.domain.SysFlowGroupUser;
import com.risun.flowable.service.ISysFlowGroupService;
import com.risun.flowable.service.ISysFlowGroupUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程审批组Controller
 * 
 * @author sunchao
 * @date 2022-11-09
 */
@RestController
@RequestMapping("/flowable/group")
public class SysFlowGroupController extends BaseController
{
    @Autowired
    private ISysFlowGroupService sysFlowGroupService;
    @Autowired ISysFlowGroupUserService sysFlowGroupUserService;

    /**
     * 查询流程审批组列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysFlowGroup sysFlowGroup)
    {
        startPage();
        List<SysFlowGroup> list = sysFlowGroupService.selectSysFlowGroupList(sysFlowGroup);
        List<SysFlowGroupUser> groupUsers = sysFlowGroupUserService.selectSysFlowGroupUserList(new SysFlowGroupUser());
        
        for (SysFlowGroup group : list) {
			group.setChildren(groupUsers
					.stream()
					.filter(u -> u.getGroupId().equals(group.getGroupId()))
					.map(u -> new SysFlowGroup(u.getId(), u.getUserName(), u.getNickName(), u.getCreateBy(), u.getCreateTime(), Boolean.TRUE))
					.collect(Collectors.toList()));
		}
        return getDataTable(list);
    }

    /**
     * 导出流程审批组列表
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:export')")
    @Log(title = "流程审批组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysFlowGroup sysFlowGroup)
    {
        List<SysFlowGroup> list = sysFlowGroupService.selectSysFlowGroupList(sysFlowGroup);
        ExcelUtil<SysFlowGroup> util = new ExcelUtil<SysFlowGroup>(SysFlowGroup.class);
        util.exportExcel(response, list, "流程审批组数据");
    }

    /**
     * 获取流程审批组详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        return AjaxResult.success(sysFlowGroupService.selectSysFlowGroupByGroupId(groupId));
    }

    /**
     * 新增流程审批组
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:add')")
    @Log(title = "流程审批组", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody SysFlowGroup sysFlowGroup)
    {
        return toAjax(sysFlowGroupService.insertSysFlowGroup(sysFlowGroup));
    }

    /**
     * 修改流程审批组
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:edit')")
    @Log(title = "流程审批组", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysFlowGroup sysFlowGroup)
    {
        return toAjax(sysFlowGroupService.updateSysFlowGroup(sysFlowGroup));
    }

    /**
     * 删除流程审批组
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:remove')")
    @Log(title = "流程审批组", businessType = BusinessType.DELETE)
    @PostMapping("/del/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(sysFlowGroupService.deleteSysFlowGroupByGroupIds(groupIds));
    }
    
    /**
     * 删除流程审批组用户
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:remove')")
    @PostMapping("/del_user/{groupUserId}")
    public AjaxResult removeUser(@PathVariable Long groupUserId)
    {
        return toAjax(sysFlowGroupUserService.deleteSysFlowGroupUserById(groupUserId));
    }
    
    /**
     * 分配用户
     */
    @PreAuthorize("@ss.hasPermi('flowable:group:edit')")
    @PostMapping("/allocate")
    public AjaxResult allocateUser(@RequestBody SysFlowGroup sysFlowGroup)
    {
        return toAjax(sysFlowGroupService.allocateUser(sysFlowGroup.getGroupId(), sysFlowGroup.getUserIds()));
    }
    
    
}

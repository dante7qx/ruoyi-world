package com.risun.web.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Log;
import com.risun.common.constant.Constants;
import com.risun.common.constant.UserConstants;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysMobileMenu;
import com.risun.common.enums.BusinessType;
import com.risun.system.service.ISysMobileMenuService;

import cn.hutool.core.util.StrUtil;

/**
 * 移动菜单权限Controller
 * 
 * @author sunchao
 * @date 2023-09-12
 */
@RestController
@RequestMapping("/system/mobile/menu")
public class SysMobileMenuController extends BaseController {
    @Autowired
    private ISysMobileMenuService sysMobileMenuService;

    /**
     * 查询移动菜单权限列表
     */
    @PreAuthorize("@ss.hasPermi('system:mobile:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMobileMenu menu) {
        List<SysMobileMenu> menus = sysMobileMenuService.selectSysMobileMenuList(menu, getUserId());
        return AjaxResult.success(menus);
    }

    /**
     * 获取移动菜单权限详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:mobile:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable("menuId") Long menuId) {
        return AjaxResult.success(sysMobileMenuService.selectSysMobileMenuByMenuId(menuId));
    }
    
    /**
     * 获取移动菜单下拉树列表
     */
    @GetMapping(value = "/treeselect")
    public AjaxResult treeselect(SysMobileMenu menu) {
    	List<SysMobileMenu> menus = sysMobileMenuService.selectSysMobileMenuList(menu, getUserId());
        return AjaxResult.success(sysMobileMenuService.buildMenuTreeSelect(menus));
    }
    
    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        List<SysMobileMenu> menus = sysMobileMenuService.selectSysMobileMenuList(getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", sysMobileMenuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", sysMobileMenuService.buildMenuTreeSelect(menus));
        return ajax;
    }
    

    /**
     * 新增移动菜单权限
     */
    @PreAuthorize("@ss.hasPermi('system:mobile:menu:add')")
    @Log(title = "移动菜单权限", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    public AjaxResult add(@RequestBody SysMobileMenu menu) {
		if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
				&& !StrUtil.startWithAny(menu.getPagePath(), Constants.HTTP, Constants.HTTPS)) {
			return error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		}
		return toAjax(sysMobileMenuService.insertSysMobileMenu(menu));
    }

    /**
     * 修改移动菜单权限
     */
    @PreAuthorize("@ss.hasPermi('system:mobile:menu:edit')")
    @Log(title = "移动菜单权限", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody SysMobileMenu menu) {
		if (UserConstants.YES_FRAME.equals(menu.getIsFrame())
				&& !StrUtil.startWithAny(menu.getPagePath(), Constants.HTTP, Constants.HTTPS)) {
			return error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
		} else if (menu.getMenuId()
			.equals(menu.getParentId())) {
			return error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
		}
        return toAjax(sysMobileMenuService.updateSysMobileMenu(menu));
    }

    /**
     * 删除移动菜单权限
     */
    @PreAuthorize("@ss.hasPermi('system:mobile:menu:remove')")
    @Log(title = "移动菜单权限", businessType = BusinessType.DELETE)
    @PostMapping("/del/{menuIds}")
    public AjaxResult remove(@PathVariable Long menuId) {
    	if (sysMobileMenuService.hasChildByMenuId(menuId))
        {
            return error("存在子菜单,不允许删除");
        }
        if (sysMobileMenuService.checkMenuExistRole(menuId))
        {
            return error("菜单已分配,不允许删除");
        }
        return toAjax(sysMobileMenuService.deleteSysMobileMenuByMenuId(menuId));
    }
    
}

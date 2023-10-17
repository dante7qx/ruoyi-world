package com.spirit.web.controller.system;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spirit.common.constant.Constants;
import com.spirit.common.core.domain.AjaxResult;
import com.spirit.common.core.domain.entity.SysMenu;
import com.spirit.common.core.domain.entity.SysMobileMenu;
import com.spirit.common.core.domain.entity.SysUser;
import com.spirit.common.core.domain.model.LoginBody;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.framework.web.service.SysLoginService;
import com.spirit.framework.web.service.SysPermissionService;
import com.spirit.system.service.ISysMenuService;
import com.spirit.system.service.ISysMobileMenuService;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;
    
    @Autowired
    private ISysMobileMenuService mobileMenuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
//        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        String token = loginService.login(loginBody);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
    
    /**
     * 获取登录用户移动端菜单
     * 
     * @return 路由信息
     */
    @PostMapping("/getMobileMemus")
    public AjaxResult getMobileMenus() {
        List<SysMobileMenu> menus = mobileMenuService.selectMenuTreeByUserId(SecurityUtils.getUserId());
        return AjaxResult.success(menus);
    }
}

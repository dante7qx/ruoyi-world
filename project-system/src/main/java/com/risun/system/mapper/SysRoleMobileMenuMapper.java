package com.risun.system.mapper;

import java.util.List;

import com.risun.system.domain.SysRoleMobileMenu;

/**
 * 角色和移动菜单关联Mapper接口
 * 
 * @author sunchao
 * @date 2023-09-12
 */
public interface SysRoleMobileMenuMapper {
	
	/**
     * 查询菜单使用数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    public int checkMobileMenuExistRole(Long menuId);
    
    /**
     * 批量新增角色和移动菜单关联
     * 
     * @param sysRoleMobileMenus
     * @return
     */
    public int batchInsertSysRoleMobileMenu(List<SysRoleMobileMenu> sysRoleMobileMenus);

    /**
     * 删除角色和移动菜单关联
     * 
     * @param roleId 角色和移动菜单关联主键
     * @return 结果
     */
    public int deleteSysRoleMobileMenuByRoleId(Long roleId);

    /**
     * 批量删除角色和移动菜单关联
     * 
     * @param roleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysRoleMobileMenuByRoleIds(Long[] roleIds);
}

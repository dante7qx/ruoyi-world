package com.spirit.system.service;

import java.util.List;
import java.util.Set;

import com.spirit.common.core.domain.TreeSelect;
import com.spirit.common.core.domain.entity.SysMobileMenu;

/**
 * 移动菜单权限Service接口
 * 
 * @author sunchao
 * @date 2023-09-12
 */
public interface ISysMobileMenuService {
	
	/**
     * 根据用户查询系统移动菜单列表
     * 
     * @param userId 用户ID
     * @return 移动菜单集合
     */
    List<SysMobileMenu> selectSysMobileMenuList(Long userId);
	
	/**
     * 查询移动菜单权限列表
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 移动菜单权限集合
     */
    List<SysMobileMenu> selectSysMobileMenuList(SysMobileMenu menu, Long userId);
    
    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(Long userId);
    
    /**
     * 根据角色ID查询权限
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询菜单树信息
     * 
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMobileMenu> selectMenuTreeByUserId(Long userId);
	
    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    List<Long> selectMenuListByRoleId(Long roleId);
    
    /**
     * 构建前端所需要树结构
     * 
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<SysMobileMenu> buildMenuTree(List<SysMobileMenu> menus);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildMenuTreeSelect(List<SysMobileMenu> menus);
    
    /**
     * 查询移动菜单权限
     * 
     * @param menuId 移动菜单权限主键
     * @return 移动菜单权限
     */
    SysMobileMenu selectSysMobileMenuByMenuId(Long menuId);
    
    /**
     * 是否存在菜单子节点
     * 
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean hasChildByMenuId(Long menuId);

    /**
     * 查询菜单是否存在角色
     * 
     * @param menuId 菜单ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkMenuExistRole(Long menuId);


    /**
     * 新增移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    int insertSysMobileMenu(SysMobileMenu sysMobileMenu);

    /**
     * 修改移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    int updateSysMobileMenu(SysMobileMenu sysMobileMenu);

    /**
     * 删除移动菜单权限信息
     * 
     * @param menuId 移动菜单权限主键
     * @return 结果
     */
    int deleteSysMobileMenuByMenuId(Long menuId);
    
}

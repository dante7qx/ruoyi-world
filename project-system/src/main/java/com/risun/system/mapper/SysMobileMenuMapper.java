package com.risun.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.risun.common.core.domain.entity.SysMobileMenu;

/**
 * 移动菜单权限Mapper接口
 * 
 * @author sunchao
 * @date 2023-09-12
 */
public interface SysMobileMenuMapper {
    /**
     * 查询移动菜单权限
     * 
     * @param menuId 移动菜单权限主键
     * @return 移动菜单权限
     */
    public SysMobileMenu selectSysMobileMenuByMenuId(Long menuId);

    /**
     * 查询移动菜单权限列表
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 移动菜单权限集合
     */
    public List<SysMobileMenu> selectSysMobileMenuList(SysMobileMenu sysMobileMenu);
    
    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMobileMenu> selectSysMobileMenuListByUserId(SysMobileMenu menu);

    /**
     * 根据用户所有权限
     *
     * @return 权限列表
     */
    public List<String> selectSysMobileMenuPerms();
    
    /**
     * 根据角色ID查询权限
     * 
     * @param roleId 角色ID
     * @return 权限列表
     */
    public List<String> selectSysMobileMenuPermsByRoleId(Long roleId);
    
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectSysMobileMenuPermsByUserId(Long userId);
    
    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMobileMenu> selectSysMobileMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMobileMenu> selectSysMobileMenuTreeByUserId(Long userId);
    
    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     * @return 选中菜单列表
     */
    public List<Long> selectSysMobileMenuListByRoleId(@Param("roleId") Long roleId, @Param("menuCheckStrictly") boolean menuCheckStrictly);
    
    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    public int hasChildByMobileMenuId(Long menuId);
    
    /**
     * 新增移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    public int insertSysMobileMenu(SysMobileMenu sysMobileMenu);
    
    /**
     * 修改移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    public int updateSysMobileMenu(SysMobileMenu sysMobileMenu);
    
    /**
     * 删除移动菜单权限
     * 
     * @param menuId 移动菜单权限主键
     * @return 结果
     */
    public int deleteSysMobileMenuByMenuId(Long menuId);

}

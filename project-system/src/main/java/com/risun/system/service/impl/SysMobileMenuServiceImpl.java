package com.risun.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.core.domain.TreeSelect;
import com.risun.common.core.domain.entity.SysMobileMenu;
import com.risun.common.core.domain.entity.SysRole;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.mapper.SysMobileMenuMapper;
import com.risun.system.mapper.SysRoleMapper;
import com.risun.system.mapper.SysRoleMobileMenuMapper;
import com.risun.system.service.ISysMobileMenuService;

import cn.hutool.core.util.StrUtil;

/**
 * 移动菜单权限Service业务层处理
 * 
 * @author sunchao
 * @date 2023-09-12
 */
@Service
public class SysMobileMenuServiceImpl implements ISysMobileMenuService {
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";
	
    @Autowired
    private SysMobileMenuMapper sysMobileMenuMapper;
    
    @Autowired
    private SysRoleMapper roleMapper;
    
    @Autowired
    private SysRoleMobileMenuMapper roleMenuMapper;
    
    /**
     * 根据用户查询系统移动菜单列表
     * 
     * @param userId 用户ID
     * @return 移动菜单集合
     */
    public List<SysMobileMenu> selectSysMobileMenuList(Long userId) {
    	return selectSysMobileMenuList(new SysMobileMenu(), userId);
    }

    /**
     * 查询移动菜单权限
     * 
     * @param menuId 移动菜单权限主键
     * @return 移动菜单权限
     */
    @Override
    public SysMobileMenu selectSysMobileMenuByMenuId(Long menuId) {
        return sysMobileMenuMapper.selectSysMobileMenuByMenuId(menuId);
    }

    /**
     * 查询移动菜单权限列表
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 移动菜单权限
     */
    @Override
    public List<SysMobileMenu> selectSysMobileMenuList(SysMobileMenu sysMobileMenu, Long userId) {
    	List<SysMobileMenu> menuList = null;
    	// 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId))
        {
            menuList = sysMobileMenuMapper.selectSysMobileMenuList(sysMobileMenu);
        }
        else
        {
        	sysMobileMenu.getParams().put("userId", userId);
            menuList = sysMobileMenuMapper.selectSysMobileMenuListByUserId(sysMobileMenu);
        }
        return menuList;
    }
    
    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
	public Set<String> selectMenuPermsByUserId(Long userId) {
		List<String> perms = sysMobileMenuMapper.selectSysMobileMenuPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StrUtil.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim()
					.split(",")));
			}
		}
		return permsSet;
	}
    
	/**
	 * 根据角色ID查询权限
	 * 
	 * @param roleId 角色ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectMenuPermsByRoleId(Long roleId) {
		List<String> perms = sysMobileMenuMapper.selectSysMobileMenuPermsByRoleId(roleId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StrUtil.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim()
					.split(",")));
			}
		}
		return permsSet;
	}
	
	/**
     * 根据用户ID查询菜单
     * 
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMobileMenu> selectMenuTreeByUserId(Long userId)
    {
        List<SysMobileMenu> menus = null;
        if (SecurityUtils.isAdmin(userId))
        {
            menus = sysMobileMenuMapper.selectSysMobileMenuTreeAll();
        }
        else
        {
            menus = sysMobileMenuMapper.selectSysMobileMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }
    
    /**
     * 根据角色ID查询菜单树信息
     * 
     * @param roleId 角色ID
     * @return 选中菜单列表
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return sysMobileMenuMapper.selectSysMobileMenuListByRoleId(roleId, role.isMenuCheckStrictly());
    }
    
    /**
     * 构建前端所需要树结构
     * 
     * @param menus 菜单列表
     * @return 树结构列表
     */
	@Override
	public List<SysMobileMenu> buildMenuTree(List<SysMobileMenu> menus) {
		List<SysMobileMenu> returnList = new ArrayList<>();
		List<Long> tempList = new ArrayList<Long>();
		for (SysMobileMenu dept : menus) {
			tempList.add(dept.getMenuId());
		}
		for (Iterator<SysMobileMenu> iterator = menus.iterator(); iterator.hasNext();) {
			SysMobileMenu menu = (SysMobileMenu) iterator.next();
			// 如果是顶级节点, 遍历该父节点的所有子节点
			if (!tempList.contains(menu.getParentId())) {
				recursionFn(menus, menu);
				returnList.add(menu);
			}
		}
		if (returnList.isEmpty()) {
			returnList = menus;
		}
		return returnList;
	}
	
	/**
     * 构建前端所需要下拉树结构
     * 
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
	@Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMobileMenu> menus) {
		List<SysMobileMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }
	
	/**
     * 是否存在菜单子节点
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
	@Override
	public boolean hasChildByMenuId(Long menuId) {
		int result = sysMobileMenuMapper.hasChildByMobileMenuId(menuId);
		return result > 0;
	}

	/**
	 * 查询菜单使用数量
	 * 
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public boolean checkMenuExistRole(Long menuId) {
		return roleMenuMapper.checkMobileMenuExistRole(menuId) > 0;
	}

    /**
     * 新增移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    @Override
    public int insertSysMobileMenu(SysMobileMenu sysMobileMenu) {
        sysMobileMenu.setCreateBy(SecurityUtils.getUsername());
        sysMobileMenu.setCreateTime(DateUtils.getNowDate());
        return sysMobileMenuMapper.insertSysMobileMenu(sysMobileMenu);
    }

    /**
     * 修改移动菜单权限
     * 
     * @param sysMobileMenu 移动菜单权限
     * @return 结果
     */
    @Override
    public int updateSysMobileMenu(SysMobileMenu sysMobileMenu) {
        sysMobileMenu.setUpdateBy(SecurityUtils.getUsername());
        sysMobileMenu.setUpdateTime(DateUtils.getNowDate());
        return sysMobileMenuMapper.updateSysMobileMenu(sysMobileMenu);
    }

    /**
     * 删除移动菜单权限信息
     * 
     * @param menuId 移动菜单权限主键
     * @return 结果
     */
    @Override
    public int deleteSysMobileMenuByMenuId(Long menuId) {
        return sysMobileMenuMapper.deleteSysMobileMenuByMenuId(menuId);
    }
    
	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	private List<SysMobileMenu> getChildPerms(List<SysMobileMenu> list, int parentId) {
		List<SysMobileMenu> returnList = new ArrayList<>();
		for (Iterator<SysMobileMenu> iterator = list.iterator(); iterator.hasNext();) {
			SysMobileMenu t = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId() == parentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * 
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<SysMobileMenu> list, SysMobileMenu t) {
		// 得到子节点列表
		List<SysMobileMenu> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysMobileMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysMobileMenu> getChildList(List<SysMobileMenu> list, SysMobileMenu t) {
		List<SysMobileMenu> tlist = new ArrayList<>();
		Iterator<SysMobileMenu> it = list.iterator();
		while (it.hasNext()) {
			SysMobileMenu n = (SysMobileMenu) it.next();
			if (n.getParentId()
				.longValue() == t.getMenuId()
					.longValue()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysMobileMenu> list, SysMobileMenu t) {
		return getChildList(list, t).size() > 0;
	}
   
}

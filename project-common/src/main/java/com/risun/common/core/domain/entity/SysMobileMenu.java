package com.risun.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.core.domain.BaseEntity;

/**
 * 移动菜单权限对象 sys_mobile_menu
 * 
 * @author sunchao
 * @date 2023-09-12
 */
public class SysMobileMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单类型 */
    private String menuType;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    private Long orderNum;

    /** 菜单地址 */
    private String pagePath;

    /** 初始图标地址 */
    private String iconPath;

    /** 选中图标地址 */
    private String selectedIconPath;

    /** 路由参数 */
    private String queryParam;

    /** 菜单Icon */
    private String iconType;

    /** 菜单Icon尺寸 */
    private String iconSize;

    /** 视图样式 */
    private String viewClass;

    /** 图片src */
    private String imgSrc;

    /** 是否为外链（0是 1否） */
    private Integer isFrame;

    /** 菜单状态（0显示 1隐藏） */
    private Integer status;

    /** 权限标识 */
    private String perms;
    
    /** 子菜单 */
    private List<SysMobileMenu> children = new ArrayList<>();

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return menuId;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }
    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() {
        return orderNum;
    }
    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public String getPagePath() {
        return pagePath;
    }
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
    public void setSelectedIconPath(String selectedIconPath) {
        this.selectedIconPath = selectedIconPath;
    }

    public String getSelectedIconPath() {
        return selectedIconPath;
    }
    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryParam() {
        return queryParam;
    }
    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getIconType() {
        return iconType;
    }
    public void setIconSize(String iconSize) {
        this.iconSize = iconSize;
    }

    public String getIconSize() {
        return iconSize;
    }
    public void setViewClass(String viewClass) {
        this.viewClass = viewClass;
    }

    public String getViewClass() {
        return viewClass;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }
    public void setIsFrame(Integer isFrame) {
        this.isFrame = isFrame;
    }

    public Integer getIsFrame() {
        return isFrame;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuType() {
        return menuType;
    }
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPerms() {
        return perms;
    }
    
    public List<SysMobileMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMobileMenu> children) {
		this.children = children;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("menuId", getMenuId())
            .append("menuName", getMenuName())
            .append("menuType", getMenuType())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("pagePath", getPagePath())
            .append("iconPath", getIconPath())
            .append("selectedIconPath", getSelectedIconPath())
            .append("queryParam", getQueryParam())
            .append("iconType", getIconType())
            .append("iconSize", getIconSize())
            .append("viewClass", getViewClass())
            .append("imgSrc", getImgSrc())
            .append("isFrame", getIsFrame())
            .append("status", getStatus())
            .append("perms", getPerms())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

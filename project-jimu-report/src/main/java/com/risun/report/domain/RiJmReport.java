package com.risun.report.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.risun.common.core.domain.BaseEntity;

/**
 * 报表列表对象 jimu_report
 * 
 * @author sunchao
 * @date 2023-05-18
 */
public class RiJmReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 说明 */
    private String note;

    /** 状态 */
    private String status;

    /** 类型 */
    private String type;

    /** json字符串 */
    private String jsonStr;

    /** 请求地址 */
    private String apiUrl;

    /** 缩略图 */
    private String thumb;

    /** 删除标识0-正常,1-已删除 */
    private Integer delFlag;

    /** 请求方法0-get,1-post */
    private String apiMethod;

    /** 请求编码 */
    private String apiCode;

    /** 是否是模板 0-是,1-不是 */
    private Integer template;

    /** 浏览次数 */
    private Long viewCount;

    /** css增强 */
    private String cssStr;

    /** js增强 */
    private String jsStr;

    /** 多租户标识 */
    private String tenantId;
    
    /** 批量操作Id */
    private String[] ids;
    
    /** 菜单Id */
    private Long menuId;
    
    /** 上级菜单Id */
    private Long parentMenuId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setJsonStr(String jsonStr) 
    {
        this.jsonStr = jsonStr;
    }

    public String getJsonStr() 
    {
        return jsonStr;
    }
    public void setApiUrl(String apiUrl) 
    {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() 
    {
        return apiUrl;
    }
    public void setThumb(String thumb) 
    {
        this.thumb = thumb;
    }

    public String getThumb() 
    {
        return thumb;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public void setApiMethod(String apiMethod) 
    {
        this.apiMethod = apiMethod;
    }

    public String getApiMethod() 
    {
        return apiMethod;
    }
    public void setApiCode(String apiCode) 
    {
        this.apiCode = apiCode;
    }

    public String getApiCode() 
    {
        return apiCode;
    }
    public void setTemplate(Integer template) 
    {
        this.template = template;
    }

    public Integer getTemplate() 
    {
        return template;
    }
    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }
    public void setCssStr(String cssStr) 
    {
        this.cssStr = cssStr;
    }

    public String getCssStr() 
    {
        return cssStr;
    }
    public void setJsStr(String jsStr) 
    {
        this.jsStr = jsStr;
    }

    public String getJsStr() 
    {
        return jsStr;
    }
    public void setTenantId(String tenantId) 
    {
        this.tenantId = tenantId;
    }

    public String getTenantId() 
    {
        return tenantId;
    }
    
    public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("note", getNote())
            .append("status", getStatus())
            .append("type", getType())
            .append("jsonStr", getJsonStr())
            .append("apiUrl", getApiUrl())
            .append("thumb", getThumb())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("apiMethod", getApiMethod())
            .append("apiCode", getApiCode())
            .append("template", getTemplate())
            .append("viewCount", getViewCount())
            .append("cssStr", getCssStr())
            .append("jsStr", getJsStr())
            .append("tenantId", getTenantId())
            .toString();
    }
}

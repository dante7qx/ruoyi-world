package com.risun.report.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * 报表菜单实体类
 * 
 * @author dante
 *
 */
@Data
public class RiJmReportMenu implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;
    
    /** 报表Id */
    private String reportId;
    
    /** 菜单Id */
    private Long menuId;
    
}

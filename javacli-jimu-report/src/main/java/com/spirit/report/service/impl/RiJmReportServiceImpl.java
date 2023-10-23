package com.spirit.report.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spirit.common.core.domain.entity.SysMenu;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.report.domain.RiJmReport;
import com.spirit.report.domain.RiJmReportDeptAcl;
import com.spirit.report.domain.RiJmReportMenu;
import com.spirit.report.mapper.RiJmReportDeptAclMapper;
import com.spirit.report.mapper.RiJmReportMapper;
import com.spirit.report.service.IRiJmReportService;
import com.spirit.system.mapper.SysMenuMapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;

/**
 * 报表列表Service业务层处理
 * 
 * @author sunchao
 * @date 2023-05-18
 */
@Service
public class RiJmReportServiceImpl implements IRiJmReportService {
	@Autowired
	private RiJmReportMapper jimuReportMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private RiJmReportDeptAclMapper jmReportDeptAclMapper;
	
	private static final String MENU_PATH_PREFIX = "jmview";
	private static final String MENU_PATH_COMPONENT = "jimureport/view";

	/**
	 * 查询报表列表
	 * 
	 * @param id 报表列表主键
	 * @return 报表列表
	 */
	@Override
	public RiJmReport selectJimuReportById(String id) {
		return jimuReportMapper.selectRiJmReportById(id);
	}

	/**
	 * 查询报表列表列表
	 * 
	 * @param jimuReport 报表列表
	 * @return 报表列表
	 */
	@Override
	public List<RiJmReport> selectJimuReportList(RiJmReport jimuReport) {
		return jimuReportMapper.selectRiJmReportList(jimuReport);
	}

	/**
	 * 新增报表菜单
	 * 
	 * @param reportIds
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertJimuReportMenu(RiJmReport jimuReport) {
		String[] reportIds = jimuReport.getIds();
		List<RiJmReportMenu> reportMenus = jimuReportMapper.selectRiJmReportMenuByReportId(reportIds);
		if(CollUtil.isNotEmpty(reportMenus)) {
			for (RiJmReportMenu reportMenu : reportMenus) {
				sysMenuMapper.deleteMenuById(reportMenu.getMenuId());
			}
			List<Long> reportMenuIds = reportMenus.stream().map(RiJmReportMenu::getId).collect(toList());
			jimuReportMapper.deleteRiJmReportMenuByReportId(reportMenuIds);
		}
		int i = 10;
		for (String reportId : reportIds) {
			RiJmReport report = jimuReportMapper.selectRiJmReportById(reportId);
			SysMenu menu = new SysMenu();
			menu.setMenuName(report.getName());
			menu.setParentId(jimuReport.getParentMenuId());
			menu.setOrderNum(++i);
			menu.setPath(MENU_PATH_PREFIX.concat(report.getCode()));
			menu.setComponent(MENU_PATH_COMPONENT);
			menu.setIsFrame("1");
			menu.setIsCache("0");
			menu.setMenuType("C");
			menu.setVisible("0");
			menu.setStatus("0");
			menu.setIcon("documentation");
			menu.setQuery(JSONUtil.toJsonStr(MapUtil.of("id", report.getId())));
			menu.setRemark(report.getName());
			menu.setCreateBy(SecurityUtils.getUsername());
			jimuReportMapper.insertMenuByReport(menu);
			RiJmReportMenu reportMenu = new RiJmReportMenu();
			reportMenu.setMenuId(menu.getMenuId());
			reportMenu.setReportId(reportId);
			jimuReportMapper.insertRiJmReportMenu(reportMenu);
		}
		return i;
	}
	
	/**
     * 取消报表菜单
     * 
     * @param jimuReport
     * @return
     */
	@Override
	@Transactional
    public int cancelJimuReportMenu(String[] ids) {
    	List<RiJmReportMenu> reportMenus = jimuReportMapper.selectRiJmReportMenuByReportId(ids);
    	if(CollUtil.isNotEmpty(reportMenus)) {
    		for (RiJmReportMenu reportMenu : reportMenus) {
        		sysMenuMapper.deleteMenuById(reportMenu.getMenuId());
    		}
        	List<Long> reportMenuIds = reportMenus.stream().map(RiJmReportMenu::getId).collect(toList());
        	jimuReportMapper.deleteRiJmReportMenuByReportId(reportMenuIds);
    	}
    	return ids.length;
    }
	
	/**
	 * 修改报表列表
	 * 
	 * @param jimuReport 报表列表
	 * @return 结果
	 */
	@Override
	public int updateJimuReport(RiJmReport jimuReport) {
		jimuReport.setUpdateBy(SecurityUtils.getUsername());
		jimuReport.setUpdateTime(DateUtils.getNowDate());
		return jimuReportMapper.updateRiJmReport(jimuReport);
	}

	/**
	 * 批量删除报表列表
	 * 
	 * @param ids 需要删除的报表列表主键
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteJimuReportByIds(String[] ids) {
		for (String reportId : ids) {
			jimuReportMapper.deleteRiJmReportDbFieldByReportId(reportId);
			jimuReportMapper.deleteRiJmReportDbByReportId(reportId);
			jimuReportMapper.deleteRiJmReportShareByReportId(reportId);
			jmReportDeptAclMapper.deleteRiJmReportDeptAclByReportId(reportId);
		}
		
		return jimuReportMapper.deleteRiJmReportByIds(ids);
	}

	/**
     * 设置部门报表权限
     * 
     * @param deptId
     * @param reportIds
     * @return
     */
	@Override
    public int setupDeptAcl(Long deptId, String[] reportIds) {
		for (String reportId : reportIds) {
			RiJmReportDeptAcl deptAcl = new RiJmReportDeptAcl();
			deptAcl.setDeptId(deptId);
			deptAcl.setReportId(reportId);
			jmReportDeptAclMapper.insertRiJmReportDeptAcl(deptAcl);
		}
		return reportIds.length;
    }
	
	/**
     * 移除部门报表权限
     * 
     * @param deptId
     * @param reportIds
     * @return
     */
    public int removeDeptAcl(Long deptId, String[] reportIds) {
    	return jmReportDeptAclMapper.deleteRiJmReportDeptAclByDeptId(deptId, reportIds);
    }
	
}

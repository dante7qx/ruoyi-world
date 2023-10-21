package com.spirit.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.system.domain.SysCameraMonitor;
import com.spirit.system.mapper.SysCameraMonitorMapper;
import com.spirit.system.service.ISysCameraMonitorService;

import cn.hutool.core.util.StrUtil;

/**
 * 视频监控Service业务层处理
 * 
 * @author sunchao
 * @date 2023-09-25
 */
@Service
public class SysCameraMonitorServiceImpl implements ISysCameraMonitorService {
	@Autowired
	private SysCameraMonitorMapper sysCameraMonitorMapper;

	/**
	 * 查询视频监控
	 * 
	 * @param monitorId 视频监控主键
	 * @return 视频监控
	 */
	@Override
	public SysCameraMonitor selectSysCameraMonitorByMonitorId(Long monitorId) {
		return sysCameraMonitorMapper.selectSysCameraMonitorByMonitorId(monitorId);
	}

	/**
	 * 查询视频监控列表
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 视频监控
	 */
	@Override
	public List<SysCameraMonitor> selectSysCameraMonitorList(SysCameraMonitor sysCameraMonitor) {
		return sysCameraMonitorMapper.selectSysCameraMonitorList(sysCameraMonitor);
	}
	
	/**
     * 检查是否含有子监控
     * 
     * @param monitorId
     * @return
     */
	@Override
	public boolean hasChildSysCameraMonitorByMonitorId(Long monitorId) {
		return sysCameraMonitorMapper.hasChildSysCameraMonitorByMonitorId(monitorId);
	}

	/**
	 * 新增视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	@Override
	public int insertSysCameraMonitor(SysCameraMonitor sysCameraMonitor) {
		SysCameraMonitor parentNode = sysCameraMonitorMapper.selectSysCameraMonitorByMonitorId(sysCameraMonitor.getParentId());
		if(parentNode != null && StrUtil.isNotEmpty(parentNode.getAncestors())) {
			sysCameraMonitor.setAncestors(parentNode.getAncestors() + "," + sysCameraMonitor.getParentId());
    	} else {
    		sysCameraMonitor.setAncestors(sysCameraMonitor.getParentId().toString());
    	}
		sysCameraMonitor.setCreateBy(SecurityUtils.getUsername());
		sysCameraMonitor.setCreateTime(DateUtils.getNowDate());
		return sysCameraMonitorMapper.insertSysCameraMonitor(sysCameraMonitor);
	}

	/**
	 * 修改视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	@Override
	public int updateSysCameraMonitor(SysCameraMonitor sysCameraMonitor) {
		sysCameraMonitor.setUpdateBy(SecurityUtils.getUsername());
		sysCameraMonitor.setUpdateTime(DateUtils.getNowDate());
		return sysCameraMonitorMapper.updateSysCameraMonitor(sysCameraMonitor);
	}

	/**
	 * 批量删除视频监控
	 * 
	 * @param monitorIds 需要删除的视频监控主键
	 * @return 结果
	 */
	@Override
	public int deleteSysCameraMonitorByMonitorIds(Long[] monitorIds) {
		return sysCameraMonitorMapper.deleteSysCameraMonitorByMonitorIds(monitorIds);
	}

	/**
	 * 删除视频监控信息
	 * 
	 * @param monitorId 视频监控主键
	 * @return 结果
	 */
	@Override
	public int deleteSysCameraMonitorByMonitorId(Long monitorId) {
		return sysCameraMonitorMapper.deleteSysCameraMonitorByMonitorId(monitorId);
	}
}

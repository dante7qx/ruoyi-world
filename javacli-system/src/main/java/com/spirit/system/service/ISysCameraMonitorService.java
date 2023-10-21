package com.spirit.system.service;

import java.util.List;
import com.spirit.system.domain.SysCameraMonitor;

/**
 * 视频监控Service接口
 * 
 * @author sunchao
 * @date 2023-09-25
 */
public interface ISysCameraMonitorService {
	/**
	 * 查询视频监控
	 * 
	 * @param monitorId 视频监控主键
	 * @return 视频监控
	 */
	SysCameraMonitor selectSysCameraMonitorByMonitorId(Long monitorId);

	/**
	 * 查询视频监控列表
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 视频监控集合
	 */
	List<SysCameraMonitor> selectSysCameraMonitorList(SysCameraMonitor sysCameraMonitor);

	/**
     * 检查是否含有子监控
     * 
     * @param monitorId
     * @return
     */

	boolean hasChildSysCameraMonitorByMonitorId(Long monitorId);
	
	/**
	 * 新增视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	int insertSysCameraMonitor(SysCameraMonitor sysCameraMonitor);

	/**
	 * 修改视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	int updateSysCameraMonitor(SysCameraMonitor sysCameraMonitor);

	/**
	 * 批量删除视频监控
	 * 
	 * @param monitorIds 需要删除的视频监控主键集合
	 * @return 结果
	 */
	int deleteSysCameraMonitorByMonitorIds(Long[] monitorIds);

	/**
	 * 删除视频监控信息
	 * 
	 * @param monitorId 视频监控主键
	 * @return 结果
	 */
	int deleteSysCameraMonitorByMonitorId(Long monitorId);
}

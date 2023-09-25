package com.risun.system.mapper;

import java.util.List;
import com.risun.system.domain.SysCameraMonitor;

/**
 * 视频监控Mapper接口
 * 
 * @author sunchao
 * @date 2023-09-25
 */
public interface SysCameraMonitorMapper {
	/**
	 * 查询视频监控
	 * 
	 * @param monitorId 视频监控主键
	 * @return 视频监控
	 */
	public SysCameraMonitor selectSysCameraMonitorByMonitorId(Long monitorId);

	/**
	 * 查询视频监控列表
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 视频监控集合
	 */
	public List<SysCameraMonitor> selectSysCameraMonitorList(SysCameraMonitor sysCameraMonitor);

	/**
     * 检查是否含有子监控
     * 
     * @param monitorId
     * @return
     */

	public boolean hasChildSysCameraMonitorByMonitorId(Long monitorId);
	
	/**
	 * 新增视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	public int insertSysCameraMonitor(SysCameraMonitor sysCameraMonitor);

	/**
	 * 修改视频监控
	 * 
	 * @param sysCameraMonitor 视频监控
	 * @return 结果
	 */
	public int updateSysCameraMonitor(SysCameraMonitor sysCameraMonitor);

	/**
	 * 删除视频监控
	 * 
	 * @param monitorId 视频监控主键
	 * @return 结果
	 */
	public int deleteSysCameraMonitorByMonitorId(Long monitorId);

	/**
	 * 批量删除视频监控
	 * 
	 * @param monitorIds 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteSysCameraMonitorByMonitorIds(Long[] monitorIds);
}

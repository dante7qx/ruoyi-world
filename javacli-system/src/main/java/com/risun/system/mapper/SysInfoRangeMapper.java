package com.spirit.system.mapper;

import java.util.List;
import com.spirit.system.domain.SysInfoRange;

/**
 * 信息访问范围Mapper接口
 * 
 * @author sunchao
 * @date 2022-10-19
 */
public interface SysInfoRangeMapper 
{
    /**
     * 查询信息访问范围
     * 
     * @param rangeId 信息访问范围主键
     * @return 信息访问范围
     */
    public SysInfoRange selectSysInfoRangeByRangeId(Long rangeId);
    
    /**
     * 根据信息Id查询信息访问范围
     * 
     * @param infoId
     * @return
     */
    public List<SysInfoRange> selectSysInfoRangeByInfoId(Long infoId);

    /**
     * 查询信息访问范围列表
     * 
     * @param sysInfoRange 信息访问范围
     * @return 信息访问范围集合
     */
    public List<SysInfoRange> selectSysInfoRangeList(SysInfoRange sysInfoRange);

    /**
     * 新增信息访问范围
     * 
     * @param sysInfoRange 信息访问范围
     * @return 结果
     */
    public int insertSysInfoRange(SysInfoRange sysInfoRange);

    /**
     * 修改信息访问范围
     * 
     * @param sysInfoRange 信息访问范围
     * @return 结果
     */
    public int updateSysInfoRange(SysInfoRange sysInfoRange);

    /**
     * 删除信息访问范围
     * 
     * @param rangeId 信息访问范围主键
     * @return 结果
     */
    public int deleteSysInfoRangeByRangeId(Long rangeId);

    /**
     * 批量删除信息访问范围
     * 
     * @param rangeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysInfoRangeByRangeIds(Long[] rangeIds);
    
    /**
     * 根据信息发布Id删除信息访问范围
     * 
     * @param rangeId 信息访问范围主键
     * @return 结果
     */
    public int deleteSysInfoRangeByInfoId(Long infoId);
    
    
}

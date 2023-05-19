package com.risun.report.mapper;

import java.util.List;
import com.risun.report.domain.RiJmReportShare;

/**
 * 报表预览Mapper接口
 * 
 * @author sunchao
 * @date 2023-05-19
 */
public interface RiJmReportShareMapper 
{
    /**
     * 查询报表预览
     * 
     * @param id 报表预览主键
     * @return 报表预览
     */
    public RiJmReportShare selectRiJmReportShareById(String id);

    /**
     * 查询报表预览列表
     * 
     * @param riJmReportShare 报表预览
     * @return 报表预览集合
     */
    public List<RiJmReportShare> selectRiJmReportShareList(RiJmReportShare riJmReportShare);

    /**
     * 新增报表预览
     * 
     * @param riJmReportShare 报表预览
     * @return 结果
     */
    public int insertRiJmReportShare(RiJmReportShare riJmReportShare);

    /**
     * 修改报表预览
     * 
     * @param riJmReportShare 报表预览
     * @return 结果
     */
    public int updateRiJmReportShare(RiJmReportShare riJmReportShare);

    /**
     * 删除报表预览
     * 
     * @param id 报表预览主键
     * @return 结果
     */
    public int deleteRiJmReportShareById(String id);

    /**
     * 批量删除报表预览
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRiJmReportShareByIds(String[] ids);
}

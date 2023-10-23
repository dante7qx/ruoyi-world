package com.spirit.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirit.report.domain.RiJmReportShare;
import com.spirit.report.mapper.RiJmReportShareMapper;
import com.spirit.report.service.IRiJmReportShareService;

/**
 * 报表预览Service业务层处理
 * 
 * @author sunchao
 * @date 2023-05-19
 */
@Service
public class RiJmReportShareServiceImpl implements IRiJmReportShareService 
{
    @Autowired
    private RiJmReportShareMapper riJmReportShareMapper;

    /**
     * 查询报表预览
     * 
     * @param id 报表预览主键
     * @return 报表预览
     */
    @Override
    public RiJmReportShare selectRiJmReportShareById(String id)
    {
        return riJmReportShareMapper.selectRiJmReportShareById(id);
    }

    /**
     * 查询报表预览列表
     * 
     * @param riJmReportShare 报表预览
     * @return 报表预览
     */
    @Override
    public List<RiJmReportShare> selectRiJmReportShareList(RiJmReportShare riJmReportShare)
    {
        return riJmReportShareMapper.selectRiJmReportShareList(riJmReportShare);
    }

    /**
     * 新增报表预览
     * 
     * @param riJmReportShare 报表预览
     * @return 结果
     */
    @Override
    public int insertRiJmReportShare(RiJmReportShare riJmReportShare)
    {
        return riJmReportShareMapper.insertRiJmReportShare(riJmReportShare);
    }

    /**
     * 修改报表预览
     * 
     * @param riJmReportShare 报表预览
     * @return 结果
     */
    @Override
    public int updateRiJmReportShare(RiJmReportShare riJmReportShare)
    {
        return riJmReportShareMapper.updateRiJmReportShare(riJmReportShare);
    }

    /**
     * 批量删除报表预览
     * 
     * @param ids 需要删除的报表预览主键
     * @return 结果
     */
    @Override
    public int deleteRiJmReportShareByIds(String[] ids)
    {
        return riJmReportShareMapper.deleteRiJmReportShareByIds(ids);
    }

    /**
     * 删除报表预览信息
     * 
     * @param id 报表预览主键
     * @return 结果
     */
    @Override
    public int deleteRiJmReportShareById(String id)
    {
        return riJmReportShareMapper.deleteRiJmReportShareById(id);
    }
}

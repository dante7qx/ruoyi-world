package com.risun.biz.demo.service.impl;

import java.util.List;
import com.risun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.risun.common.utils.SecurityUtils;
import com.risun.biz.demo.mapper.DemoMapper;
import com.risun.biz.demo.domain.Demo;
import com.risun.biz.demo.service.IDemoService;

/**
 * 业务Service业务层处理
 * 
 * @author sunchao
 * @date 2022-07-30
 */
@Service
public class DemoServiceImpl implements IDemoService 
{
    @Autowired
    private DemoMapper demoMapper;

    /**
     * 查询业务
     * 
     * @param demoId 业务主键
     * @return 业务
     */
    @Override
    public Demo selectDemoByDemoId(Long demoId)
    {
        return demoMapper.selectDemoByDemoId(demoId);
    }

    /**
     * 查询业务列表
     * 
     * @param demo 业务
     * @return 业务
     */
    @Override
    public List<Demo> selectDemoList(Demo demo)
    {
        return demoMapper.selectDemoList(demo);
    }

    /**
     * 新增业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int insertDemo(Demo demo)
    {
        demo.setCreateBy(SecurityUtils.getUsername());
        demo.setCreateTime(DateUtils.getNowDate());
        return demoMapper.insertDemo(demo);
    }

    /**
     * 修改业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int updateDemo(Demo demo)
    {
        demo.setUpdateBy(SecurityUtils.getUsername());
        demo.setUpdateTime(DateUtils.getNowDate());
        return demoMapper.updateDemo(demo);
    }

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoIds(Long[] demoIds)
    {
        return demoMapper.deleteDemoByDemoIds(demoIds);
    }

    /**
     * 删除业务信息
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoId(Long demoId)
    {
        return demoMapper.deleteDemoByDemoId(demoId);
    }
    
    /**
     * 批量新增
     * 
     * @param demos
     * @return
     */
    @Override
    public int batchInsertDemo(List<Demo> demos) {
    	return demoMapper.batchInsertDemo(demos);
    }

}

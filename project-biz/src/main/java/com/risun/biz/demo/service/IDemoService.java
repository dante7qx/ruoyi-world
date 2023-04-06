package com.risun.biz.demo.service;

import java.util.List;
import java.util.Map;

import com.risun.biz.demo.domain.Demo;

/**
 * 业务Service接口
 * 
 * @author sunchao
 * @date 2022-07-30
 */
public interface IDemoService 
{
    /**
     * 查询业务
     * 
     * @param demoId 业务主键
     * @return 业务
     */
    public Demo selectDemoByDemoId(Long demoId);

    /**
     * 查询业务列表
     * 
     * @param demo 业务
     * @return 业务集合
     */
    public List<Demo> selectDemoList(Demo demo);

    /**
     * 新增业务
     * 
     * @param demo 业务
     * @return 结果
     */
    public int insertDemo(Demo demo);

    /**
     * 修改业务
     * 
     * @param demo 业务
     * @return 结果
     */
    public int updateDemo(Demo demo);

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的业务主键集合
     * @return 结果
     */
    public int deleteDemoByDemoIds(Long[] demoIds);

    /**
     * 删除业务信息
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    public int deleteDemoByDemoId(Long demoId);
    
    /**
     * 导出Word
     * 
     * @param demoId
     * @return
     */
    public Map<String, Object> export4Word(Long demoId);
}

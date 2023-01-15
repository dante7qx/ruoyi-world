package com.risun.biz.demo.mapper;

import java.util.List;
import com.risun.biz.demo.domain.Demo;

import org.apache.ibatis.annotations.Select;

/**
 * 业务Mapper接口
 * 
 * @author sunchao
 * @date 2022-07-30
 */
public interface DemoMapper 
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
     * 删除业务
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    public int deleteDemoByDemoId(Long demoId);

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDemoByDemoIds(Long[] demoIds);
    
    /**
     * 获取业务数量
     * 
     * @return
     */
    @Select("select count(demo_id) from t_demo")
    public int selectDemoCount();
}
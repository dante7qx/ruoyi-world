package com.risun.biz.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.yulichang.base.MPJBaseMapper;
import com.risun.biz.demo.domain.Demo;

/**
 * 业务Mapper接口
 * 
 * @author sunchao
 * @date 2022-07-30
 */
public interface DemoMapper extends MPJBaseMapper<Demo>
{
	/**
     * 删除业务
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    public int deleteDemoByDemoId(@Param("demoId") Long demoId, @Param("deleteBy") String deleteBy);

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDemoByDemoIds(@Param("demoIds") Long[] demoIds, @Param("deleteBy") String deleteBy);
    
    /**
     * 获取业务数量
     * 
     * @return
     */
    @Select("select count(demo_id) from t_demo")
    public int selectDemoCount();
    
    /**
     * 批量新增业务
     * 
     * @param demos
     * @return
     */
    public int batchInsertDemo(List<Demo> demos);
    
    /**
     * 批量更新业务
     * 
     * @param demos
     * @return
     */
    public int batchUpdateDemo(List<Demo> demos);
    
    /**
     * 清空数据
     * 
     * @return
     */
    public int clearDemoData();
}

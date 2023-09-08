package com.risun.biz.demo.service;

import java.util.List;

import com.risun.biz.demo.domain.DemoAll;

/**
 * 全部业务功能Service接口
 * 
 * @author sunchao
 * @date 2023-09-08
 */
public interface IDemoAllService {
    /**
     * 查询全部业务功能
     * 
     * @param demoId 全部业务功能主键
     * @return 全部业务功能
     */
    DemoAll selectDemoAllByDemoId(Long demoId);

    /**
     * 查询全部业务功能列表
     * 
     * @param demoAll 全部业务功能
     * @return 全部业务功能集合
     */
    List<DemoAll> selectDemoAllList(DemoAll demoAll);

    /**
     * 新增全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    int insertDemoAll(DemoAll demoAll);

    /**
     * 修改全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    int updateDemoAll(DemoAll demoAll);

    /**
     * 批量删除全部业务功能
     * 
     * @param demoIds 需要删除的全部业务功能主键集合
     * @return 结果
     */
    int deleteDemoAllByDemoIds(Long[] demoIds);

    /**
     * 删除全部业务功能信息
     * 
     * @param demoId 全部业务功能主键
     * @return 结果
     */
    int deleteDemoAllByDemoId(Long demoId);
    
    /**
     * 导入全部业务功能
     * 
     * @param demoAllList 待导入数据
     * @return 结果
     */
    String importDemoAll(List<DemoAll> demoAllList);
}

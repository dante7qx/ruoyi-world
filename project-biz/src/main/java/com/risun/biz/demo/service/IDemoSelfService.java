package com.risun.biz.demo.service;

import java.util.List;

import com.risun.biz.demo.domain.DemoSelf;

/**
 * 私有业务功能Service接口
 * 
 * @author sunchao
 * @date 2023-09-08
 */
public interface IDemoSelfService {
    /**
     * 查询私有业务功能
     * 
     * @param demoId 私有业务功能主键
     * @return 私有业务功能
     */
    DemoSelf selectDemoSelfByDemoId(Long demoId);

    /**
     * 查询私有业务功能列表
     * 
     * @param demoSelf 私有业务功能
     * @return 私有业务功能集合
     */
    List<DemoSelf> selectDemoSelfList(DemoSelf demoSelf);

    /**
     * 新增私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    int insertDemoSelf(DemoSelf demoSelf);

    /**
     * 修改私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    int updateDemoSelf(DemoSelf demoSelf);

    /**
     * 批量删除私有业务功能
     * 
     * @param demoIds 需要删除的私有业务功能主键集合
     * @return 结果
     */
    int deleteDemoSelfByDemoIds(Long[] demoIds);

    /**
     * 删除私有业务功能信息
     * 
     * @param demoId 私有业务功能主键
     * @return 结果
     */
    int deleteDemoSelfByDemoId(Long demoId);
    
    /**
     * 导入私有业务功能
     * 
     * @param demoSelfList 待导入数据
     * @return 结果
     */
    String importDemoSelf(List<DemoSelf> demoSelfList);
}

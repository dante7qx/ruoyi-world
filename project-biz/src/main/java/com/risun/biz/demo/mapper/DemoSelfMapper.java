package com.risun.biz.demo.mapper;

import java.util.List;

import com.risun.biz.demo.domain.DemoSelf;

/**
 * 私有业务功能Mapper接口
 * 
 * @author sunchao
 * @date 2023-09-08
 */
public interface DemoSelfMapper {
    /**
     * 查询私有业务功能
     * 
     * @param demoId 私有业务功能主键
     * @return 私有业务功能
     */
    public DemoSelf selectDemoSelfByDemoId(Long demoId);

    /**
     * 查询私有业务功能列表
     * 
     * @param demoSelf 私有业务功能
     * @return 私有业务功能集合
     */
    public List<DemoSelf> selectDemoSelfList(DemoSelf demoSelf);

    /**
     * 新增私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    public int insertDemoSelf(DemoSelf demoSelf);
    
    /**
     * 批量新增私有业务功能
     * 
     * @param demoSelfs
     * @return
     */
    public int batchInsertDemoSelf(List<DemoSelf> demoSelfs);

    /**
     * 修改私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    public int updateDemoSelf(DemoSelf demoSelf);
    
    /**
     * 批量更新私有业务功能
     * 
     * @param demoSelfs
     * @return
     */
    public int batchUpdateDemoSelf(List<DemoSelf> demoSelfs);

    /**
     * 删除私有业务功能
     * 
     * @param demoId 私有业务功能主键
     * @return 结果
     */
    public int deleteDemoSelfByDemoId(Long demoId);

    /**
     * 批量删除私有业务功能
     * 
     * @param demoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDemoSelfByDemoIds(Long[] demoIds);
}

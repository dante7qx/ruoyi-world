package com.risun.biz.demo.mapper;

import java.util.List;

import com.risun.biz.demo.domain.DemoAll;

/**
 * 全部业务功能Mapper接口
 * 
 * @author sunchao
 * @date 2023-09-08
 */
public interface DemoAllMapper {
    /**
     * 查询全部业务功能
     * 
     * @param demoId 全部业务功能主键
     * @return 全部业务功能
     */
    public DemoAll selectDemoAllByDemoId(Long demoId);

    /**
     * 查询全部业务功能列表
     * 
     * @param demoAll 全部业务功能
     * @return 全部业务功能集合
     */
    public List<DemoAll> selectDemoAllList(DemoAll demoAll);

    /**
     * 新增全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    public int insertDemoAll(DemoAll demoAll);
    
    /**
     * 批量新增全部业务功能
     * 
     * @param demoAlls
     * @return
     */
    public int batchInsertDemoAll(List<DemoAll> demoAlls);

    /**
     * 修改全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    public int updateDemoAll(DemoAll demoAll);
    
    /**
     * 批量更新全部业务功能
     * 
     * @param demoAlls
     * @return
     */
    public int batchUpdateDemoAll(List<DemoAll> demoAlls);

    /**
     * 删除全部业务功能
     * 
     * @param demoId 全部业务功能主键
     * @return 结果
     */
    public int deleteDemoAllByDemoId(Long demoId);

    /**
     * 批量删除全部业务功能
     * 
     * @param demoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDemoAllByDemoIds(Long[] demoIds);
}

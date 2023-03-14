package com.risun.flowable.demo.mapper;

import java.util.List;
import com.risun.flowable.demo.domain.FlowDemo;

/**
 * 业务流程示例Mapper接口
 * 
 * @author sunchao
 * @date 2023-03-02
 */
public interface FlowDemoMapper {
	
	/**
     * 查询业务流程示例
     * 
     * @param uid 业务uid
     * @return 业务流程示例
     */
    public FlowDemo selectFlowDemoByUid(String uid);
	
    /**
     * 查询业务流程示例
     * 
     * @param demoId 业务流程示例主键
     * @return 业务流程示例
     */
    public FlowDemo selectFlowDemoByDemoId(Long demoId);
    

    /**
     * 查询业务流程示例列表
     * 
     * @param flowDemo 业务流程示例
     * @return 业务流程示例集合
     */
    public List<FlowDemo> selectFlowDemoList(FlowDemo flowDemo);

    /**
     * 新增业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    public int insertFlowDemo(FlowDemo flowDemo);

    /**
     * 修改业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    public int updateFlowDemo(FlowDemo flowDemo);

    /**
     * 删除业务流程示例
     * 
     * @param uid
     * @return 结果
     */
    public int deleteFlowDemoByUid(String uid);
    
    /**
     * 删除业务流程示例
     * 
     * @param demoId 业务流程示例主键
     * @return 结果
     */
    public int deleteFlowDemoByDemoId(Long demoId);

    /**
     * 批量删除业务流程示例
     * 
     * @param demoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFlowDemoByDemoIds(Long[] demoIds);
}

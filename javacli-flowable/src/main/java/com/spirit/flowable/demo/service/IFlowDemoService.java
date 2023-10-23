package com.spirit.flowable.demo.service;

import java.util.List;
import com.spirit.flowable.demo.domain.FlowDemo;

/**
 * 业务流程示例Service接口
 * 
 * @author sunchao
 * @date 2023-03-02
 */
public interface IFlowDemoService 
{
	
	/**
     * 查询业务流程示例
     * 
     * @param uid 业务uid
     * @return 业务流程示例
     */
    public FlowDemo selectFlowDemoByUid(String uid);
    
    /**
     * 提交业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    public int commitFlowDemo(FlowDemo flowDemo);
	
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
     * 删除业务流程示例
     * 
     * @param uid
     * @return 结果
     */
    public int deleteFlowDemoByUid(String uid);

    /**
     * 批量删除业务流程示例
     * 
     * @param demoIds 需要删除的业务流程示例主键集合
     * @return 结果
     */
    public int deleteFlowDemoByDemoIds(Long[] demoIds);

    /**
     * 删除业务流程示例信息
     * 
     * @param demoId 业务流程示例主键
     * @return 结果
     */
    public int deleteFlowDemoByDemoId(Long demoId);
}

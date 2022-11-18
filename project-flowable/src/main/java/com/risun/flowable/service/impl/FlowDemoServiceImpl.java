package com.risun.flowable.service.impl;

import java.util.List;

import com.risun.common.constant.BizModelConstants;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.common.constant.ProcessDefKeyConstants;
import com.risun.flowable.domain.FlowDemo;
import com.risun.flowable.domain.vo.StartFlowVo;
import com.risun.flowable.mapper.FlowDemoMapper;
import com.risun.flowable.service.IFlowDemoService;
import com.risun.flowable.service.IFlowInstanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

/**
 * 业务流程示例Service业务层处理
 * 
 * @author sunchao
 * @date 2022-11-11
 */
@Service
public class FlowDemoServiceImpl implements IFlowDemoService 
{
    @Autowired
    private FlowDemoMapper flowDemoMapper;
    @Autowired
    private IFlowInstanceService flowInstanceService;
    
    /**
     * 查询业务流程示例
     * 
     * @param demoId 业务流程示例主键
     * @return 业务流程示例
     */
    @Override
    public FlowDemo selectFlowDemoByDemoId(Long demoId)
    {
        return flowDemoMapper.selectFlowDemoByDemoId(demoId);
    }

    /**
     * 查询业务流程示例列表
     * 
     * @param flowDemo 业务流程示例
     * @return 业务流程示例
     */
    @Override
    public List<FlowDemo> selectFlowDemoList(FlowDemo flowDemo)
    {
        return flowDemoMapper.selectFlowDemoList(flowDemo);
    }

    /**
     * 新增业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    @Override
    public int insertFlowDemo(FlowDemo flowDemo)
    {
    	flowDemo.setLeaveUserId(SecurityUtils.getUserId());
    	flowDemo.setUid(IdUtil.fastUUID());
        flowDemo.setCreateBy(SecurityUtils.getUsername());
        flowDemo.setCreateTime(DateUtils.getNowDate());
        return flowDemoMapper.insertFlowDemo(flowDemo);
    }

    /**
     * 修改业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    @Override
    public int updateFlowDemo(FlowDemo flowDemo)
    {
        flowDemo.setUpdateBy(SecurityUtils.getUsername());
        flowDemo.setUpdateTime(DateUtils.getNowDate());
        return flowDemoMapper.updateFlowDemo(flowDemo);
    }
    
    /**
     * 提交业务流程示例
     * 
     * @param flowDemo 业务流程示例
     * @return 结果
     */
    @Override
    @Transactional
    public int commitFlowDemo(FlowDemo flowDemo) {
    	int result = 0;
    	Long demoId = flowDemo.getDemoId();
    	if(demoId == null) {
    		flowDemo.setLeaveUserId(SecurityUtils.getUserId());
        	flowDemo.setUid(IdUtil.fastUUID());
            flowDemo.setCreateBy(SecurityUtils.getUsername());
            flowDemo.setCreateTime(DateUtils.getNowDate());
            result = flowDemoMapper.insertFlowDemo(flowDemo);
    	} else {
    		flowDemo.setUpdateBy(SecurityUtils.getUsername());
            flowDemo.setUpdateTime(DateUtils.getNowDate());
            result = flowDemoMapper.updateFlowDemo(flowDemo);
    	}
    	// 
        // 启动流程
    	StartFlowVo startFlowVo = new StartFlowVo();
    	startFlowVo.setBizId(flowDemo.getDemoId().toString());
    	startFlowVo.setBizUid(flowDemo.getUid());
    	startFlowVo.setBizModel(BizModelConstants.FLOW_DEMO);
    	// 这里的 days 是你在绘制流程图时，设置的条件参数
    	startFlowVo.addParams("days", DateUtil.betweenDay(flowDemo.getStartTime(), flowDemo.getEndTime(), true) + 1);
    	flowInstanceService.commit(ProcessDefKeyConstants.KEY_FLOW_DEMO, startFlowVo);
    	
    	// 验证多实例（3、4为spuser1、spuser2）时，请打开注释，并将上一行 flowInstanceService.commit(...) 注释
//    	startFlowVo.addParams(ProcessConstants.PROCESS_MULTI_INSTANCE_USER, Lists.newArrayList("3", "4"));
//    	flowInstanceService.commit(ProcessDefKeyConstants.KEY_FLOW_DEMO+"2", startFlowVo);
        return result;
    }
    
    /**
     * 批量删除业务流程示例
     * 
     * @param demoIds 需要删除的业务流程示例主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteFlowDemoByDemoIds(Long[] demoIds) {
    	int result = 0;
    	for (Long demoId : demoIds) {
    		result += deleteFlowDemoByDemoId(demoId);
		}
        return result;
    }

    /**
     * 删除业务流程示例信息
     * 
     * @param demoId 业务流程示例主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteFlowDemoByDemoId(Long demoId) {
    	FlowDemo flowDemo = flowDemoMapper.selectFlowDemoByDemoId(demoId);
    	// 删除流程相关数据
    	if(flowDemo.getFlowMonitor() != null) {
    		flowInstanceService.delete(new String[] {flowDemo.getFlowMonitor().getProcInstId()});
    	}
        return flowDemoMapper.deleteFlowDemoByDemoId(demoId);
    }
}

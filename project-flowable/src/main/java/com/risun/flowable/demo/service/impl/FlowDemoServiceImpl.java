package com.risun.flowable.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.demo.domain.FlowDemo;
import com.risun.flowable.demo.mapper.FlowDemoMapper;
import com.risun.flowable.demo.service.IFlowDemoService;
import com.risun.flowable.domain.SysFlowTrace;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;
import com.risun.flowable.service.ISysFlowTaskService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 业务流程示例Service业务层处理
 * 
 * @author sunchao
 * @date 2023-03-02
 */
@Service
public class FlowDemoServiceImpl implements IFlowDemoService {
	@Autowired
	private FlowDemoMapper flowDemoMapper;
	@Autowired
	private ISysFlowTaskService sysFlowTaskService;
	
	static final String DAYS = "days";

	/**
     * 查询业务流程示例
     * 
     * @param uid 业务uid
     * @return 业务流程示例
     */
    public FlowDemo selectFlowDemoByUid(String uid) {
    	FlowDemo flowDemo = flowDemoMapper.selectFlowDemoByUid(uid);
    	SysFlowTrace trace = sysFlowTaskService.selectSysFlowTraceByBizUid(uid);
    	flowDemo.setTrace(trace);
    	return flowDemo;
    }
	
	/**
	 * 查询业务流程示例
	 * 
	 * @param demoId 业务流程示例主键
	 * @return 业务流程示例
	 */
	@Override
	public FlowDemo selectFlowDemoByDemoId(Long demoId) {
		return flowDemoMapper.selectFlowDemoByDemoId(demoId);
	}

	/**
	 * 查询业务流程示例列表
	 * 
	 * @param flowDemo 业务流程示例
	 * @return 业务流程示例
	 */
	@Override
	public List<FlowDemo> selectFlowDemoList(FlowDemo flowDemo) {
		return flowDemoMapper.selectFlowDemoList(flowDemo);
	}

	/**
	 * 新增业务流程示例
	 * 
	 * @param flowDemo 业务流程示例
	 * @return 结果
	 */
	@Override
	@Transactional
	public int commitFlowDemo(FlowDemo flowDemo) {
		int result = 0;
		String uid = flowDemo.getUid();
		SysFlowTrace trace = flowDemo.getTrace();
		if(StrUtil.isEmpty(uid)) {
			flowDemo.setUid(IdUtil.nanoId());
			flowDemo.setUserId(SecurityUtils.getUserId());
			flowDemo.setCreateBy(SecurityUtils.getUsername());
			flowDemo.setCreateTime(DateUtils.getNowDate());
			result = flowDemoMapper.insertFlowDemo(flowDemo);
		} else {
			flowDemo.setUpdateBy(SecurityUtils.getUsername());
			flowDemo.setUpdateTime(DateUtils.getNowDate());
			result = flowDemoMapper.updateFlowDemo(flowDemo);
		}
		// 审批流程
		if(trace != null && StrUtil.isNotEmpty(trace.getProcInstId())) {
			SysApprovalFlowVo approvalVo = new SysApprovalFlowVo();
			approvalVo.setFlowType(trace.getFlowType());
			approvalVo.setTaskId(trace.getTaskId());
			approvalVo.setTaskDefId(trace.getTaskDefId());
			approvalVo.setBizUid(flowDemo.getUid());
			approvalVo.setBizDesc(this.buildBizDetail(flowDemo));
			approvalVo.setComment(trace.getTaskDefName());
			approvalVo.setApprovalUserId(trace.getApprovalUserId());
			approvalVo.setCommitDate(DateUtils.getNowDate());
			approvalVo.add(DAYS, DateUtil.betweenDay(flowDemo.getStartTime(), flowDemo.getEndTime(), true));
			sysFlowTaskService.approvalFlowTask(approvalVo);
		} else {
			// 启动流程
			SysStartFlowVo startVo = new SysStartFlowVo();
			startVo.setBizUid(flowDemo.getUid());
			startVo.setProcDefKey(trace.getProcDefKey());
			startVo.setFlowType(trace.getFlowType());
			startVo.setApprovalUserId(trace.getApprovalUserId());
			startVo.setBizDesc(this.buildBizDetail(flowDemo));
			startVo.add(DAYS, DateUtil.betweenDay(flowDemo.getStartTime(), flowDemo.getEndTime(), true));
			sysFlowTaskService.startFlowTask(startVo);
		}
		return result;
	}
	
	private String buildBizDetail(FlowDemo flowDemo) {
		SysUser user = SecurityUtils.getLoginUser().getUser();
		String detail = DateUtil.formatDate(flowDemo.getStartTime()) + " ~ "
				+ DateUtil.formatDate(flowDemo.getEndTime());
		return user.getNickName() + "，" + user.getDept().getDeptName() + "，请假申请。" + detail;
	}
	
	/**
     * 删除业务流程示例
     * 
     * @param uid
     * @return 结果
     */
	@Override
    public int deleteFlowDemoByUid(String uid) {
		return flowDemoMapper.deleteFlowDemoByUid(uid);
    }

	/**
	 * 批量删除业务流程示例
	 * 
	 * @param demoIds 需要删除的业务流程示例主键
	 * @return 结果
	 */
	@Override
	public int deleteFlowDemoByDemoIds(Long[] demoIds) {
		return flowDemoMapper.deleteFlowDemoByDemoIds(demoIds);
	}

	/**
	 * 删除业务流程示例信息
	 * 
	 * @param demoId 业务流程示例主键
	 * @return 结果
	 */
	@Override
	public int deleteFlowDemoByDemoId(Long demoId) {
		return flowDemoMapper.deleteFlowDemoByDemoId(demoId);
	}
}

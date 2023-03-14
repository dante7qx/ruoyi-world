package com.risun.flowable.service;

import java.io.InputStream;
import java.util.List;

import com.risun.common.core.domain.AjaxResult;
import com.risun.flowable.domain.SysFlowAssign;
import com.risun.flowable.domain.SysFlowTrace;
import com.risun.flowable.domain.vo.SysFlowApproverVo;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysFlowAssignVo;
import com.risun.flowable.domain.vo.SysFlowTaskQueryVo;
import com.risun.flowable.domain.vo.SysFlowTaskVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;

/**
 * 系统流程服务接口
 * 
 * @author dante
 *
 */
public interface ISysFlowTaskService {
	
	/**
	 * 获取任务审批候选人
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @param starterUserId
	 * @return
	 */
	public SysFlowApproverVo selectFlowApprover(String procDefId, String taskDefId, Long starterUserId);
	
	/**
	 * 发起流程
	 * 
	 * @param startFlowVo
	 */
	public void startFlowTask(SysStartFlowVo startFlowVo);
	

	/**
	 * 审批流程
	 * 
	 * @param approvalFlowVo
	 */
	public int approvalFlowTask(SysApprovalFlowVo approvalFlowVo);
	
	/**
	 * 查询系统流程列表（我的、已办、监控）
	 * 
	 * @param query
	 * @return
	 */
	public List<SysFlowTaskVo> selectSysFlowTaskList(SysFlowTaskQueryVo query);
	
	/**
	 * 查询系统流程待办列表
	 * 
	 * @param query
	 * @return
	 */
	public List<SysFlowTaskVo> selectSysFlowTaskTodoList(SysFlowTaskQueryVo query);
	
	/**
	 * 根据bizUid查询流程跟踪
	 * 
	 * @return
	 */
	public SysFlowTrace selectSysFlowTraceByBizUid(String bizUid);
	
	/**
	 * 撤销流程
	 * 
	 * @param bizUid
	 * @return
	 */
	public int revoke(String bizUid);
	
	/**
	 * 删除流程
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	public int delete(SysFlowTaskVo sysFlowTaskVo);
	
	/**
	 * 删除流程
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	public int delete(SysFlowTaskVo[] sysFlowTaskVo);
	
	/**
	 * 根据流程实例Id删除流程
	 * 
	 * @param procInstId
	 */
	public void deleteFlowByProcInstId(String procInstId);
	
	/**
     * 获取流程过程图
     * 
     * @param processId
     * @return
     */
	public InputStream diagram(String processId);

    /**
     * 获取流程执行过程
     * 
     * @param procInsId
     * @return
     */
	public AjaxResult getFlowViewer(String procInsId);
	
	/**
     * 转办任务
     *
     * @param sysFlowTaskVo
     */
	public void assignTask(SysFlowAssignVo sysFlowAssignVo);
	
	/**
	 * 查询流程转办记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	public List<SysFlowAssign> selectFlowAssignList(String procInsId);
	

}

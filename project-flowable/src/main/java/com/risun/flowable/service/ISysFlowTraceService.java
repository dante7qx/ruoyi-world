package com.risun.flowable.service;

import java.util.List;

import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import com.risun.flowable.domain.SysFlowTrace;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;

/**
 * 流程跟踪Service接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface ISysFlowTraceService 
{
    /**
     * 查询流程跟踪
     * 
     * @param bizUid 业务uid
     * @return 流程跟踪
     */
    public SysFlowTrace selectSysFlowTraceByBizUid(String bizUid);
    
    /**
     * 新增流程跟踪
     * 
     * @param startFlowVo
     * @param procInst
     * @param curTasks
     * @return
     */
    public int insertSysFlowTrace(SysStartFlowVo startFlowVo, ProcessInstance procInst, List<Task> curTasks);


    /**
     * 更新流程跟踪
     * 
     * @param taskVo
     * @param curTasks
     * @return
     */
    public int updateSysFlowTrace(SysApprovalFlowVo taskVo, List<Task> curTasks);
    
    /**
     * 撤销流程
     * 
     * @param sysFlowTrace
     * @return
     */
    public int revokeSysFlowTrace(SysFlowTrace sysFlowTrace);
    
    /**
	 * 删除流程跟踪
	 * 
	 * @param bizUid 业务UID
	 * @return 结果
	 */
	public int deleteSysFlowTraceByBizUid(String bizUid);
    
    
}

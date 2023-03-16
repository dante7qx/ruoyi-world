package com.risun.flowable.service.impl;

import java.util.List;

import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.utils.DateUtils;
import com.risun.flowable.common.enums.SysFlowStatusEnum;
import com.risun.flowable.domain.SysFlowSeq;
import com.risun.flowable.domain.SysFlowTrace;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;
import com.risun.flowable.mapper.SysFlowTraceMapper;
import com.risun.flowable.service.ISysFlowBpmnModelService;
import com.risun.flowable.service.ISysFlowSeqService;
import com.risun.flowable.service.ISysFlowTraceService;

import cn.hutool.core.collection.CollUtil;

/**
 * 流程跟踪Service业务层处理
 * 
 * @author sunchao
 * @date 2023-02-27
 */
@Service
public class SysFlowTraceServiceImpl implements ISysFlowTraceService {
	@Autowired
	private SysFlowTraceMapper sysFlowTraceMapper;
	@Autowired
	private ISysFlowSeqService sysFlowSeqService;
	@Autowired
	private ISysFlowBpmnModelService sysFlowBpmnModelService;
	
	static final String END_NODE = "结束";

	/**
	 * 查询流程跟踪
	 * 
	 * @param bizUid 业务uid
	 * @return 流程跟踪
	 */
	@Override
	public SysFlowTrace selectSysFlowTraceByBizUid(String bizUid) {
		return sysFlowTraceMapper.selectSysFlowTraceByBizUid(bizUid);
	}

	 /**
     * 新增流程跟踪
     * 
     * @param startFlowVo
     * @param procInst
     * @param curTasks
     * @return
     */
	@Override
	public int insertSysFlowTrace(SysStartFlowVo startFlowVo, ProcessInstance procInst, List<Task> curTasks) {
		int result = 0;
		boolean isUpdate = true;
    	SysFlowTrace trace = sysFlowTraceMapper.selectSysFlowTraceByBizUid(procInst.getBusinessKey());
    	if(trace == null) {
    		isUpdate = false;
    		trace = new SysFlowTrace();
    		SysFlowSeq flowSeq = sysFlowSeqService.selectNextSysFlowSeq();
    		trace.setFlowNum(flowSeq.getSeqNum());
    		trace.setFlowDateNum(flowSeq.getDateNum());
    	}
		
    	trace.setBizUid(procInst.getBusinessKey());
    	trace.setBizDesc(startFlowVo.getBizDesc());
    	trace.setTaskDefDesc(startFlowVo.getBizDesc());
    	trace.setFlowType(startFlowVo.getFlowType());
    	trace.setProcDefId(procInst.getProcessDefinitionId());
    	trace.setProcInstId(procInst.getId());
    	trace.setCommitTime(DateUtils.getNowDate());
    	trace.setCommitUserId(startFlowVo.getHandleUserId());
    	trace.setFirstTask(Boolean.FALSE);
		if(CollUtil.isNotEmpty(curTasks)) {
			Task curTask = curTasks.get(0);
			trace.setTaskId(curTask.getId());
			trace.setTaskDefId(curTask.getTaskDefinitionKey());
			trace.setTaskDefName(curTask.getName());
			trace.setTaskDefDesc(curTask.getDescription());
			trace.setFlowStatus(SysFlowStatusEnum.IN_PROGRESS.getType());
		} else {
			trace.setFlowStatus(SysFlowStatusEnum.FINISHED.getType());
		}
		if(isUpdate) {
			result = sysFlowTraceMapper.updateSysFlowTrace(trace);
		} else {
			result = sysFlowTraceMapper.insertSysFlowTrace(trace);
		}
        return result;
	}
	
	/**
     * 更新流程跟踪
     * 
     * @param taskVo
     * @param curTasks
     * @return
     */
	@Override
    public int updateSysFlowTrace(SysApprovalFlowVo taskVo, List<Task> curTasks) {
		int result = 1;
		SysFlowTrace trace = sysFlowTraceMapper.selectSysFlowTraceByBizUid(taskVo.getBizUid());
		trace.setFlowResult(taskVo.getAgree());
		if(CollUtil.isNotEmpty(curTasks)) { 
			Task curTask = curTasks.get(0);
			if(!curTask.getTaskDefinitionKey().equalsIgnoreCase(trace.getTaskDefId())) {
				trace.setTaskId(curTask.getId());
				trace.setTaskDefId(curTask.getTaskDefinitionKey());
				trace.setTaskDefName(curTask.getName());
				trace.setTaskDefDesc(curTask.getDescription());
				UserTask firstUserTask = sysFlowBpmnModelService.getFirstUserTaskOrTaskDef(curTask.getProcessDefinitionId(), null);
				if(firstUserTask != null && firstUserTask.getId().equals(curTask.getTaskDefinitionKey())) {
					trace.setFirstTask(Boolean.TRUE);
				} else {
					trace.setFirstTask(Boolean.FALSE);
				}
			}
			if(taskVo.getCommitDate() != null) {
				trace.setCommitTime(taskVo.getCommitDate());
			}
			trace.setFlowStatus(SysFlowStatusEnum.IN_PROGRESS.getType());
			result = sysFlowTraceMapper.updateSysFlowTrace(trace);
		}  else {
			trace.setTaskId("");
			trace.setTaskDefId("");
			trace.setTaskDefDesc("");
			trace.setTaskDefName(END_NODE);
			trace.setFlowStatus(SysFlowStatusEnum.FINISHED.getType());
			result = sysFlowTraceMapper.updateSysFlowTrace(trace);
		}
		return result;
    }

	/**
	 * 撤销流程
	 */
	@Override
	public int revokeSysFlowTrace(SysFlowTrace sysFlowTrace) {
		return sysFlowTraceMapper.revokeSysFlowTrace(sysFlowTrace);
	}
	
	/**
	 * 删除流程跟踪
	 * 
	 * @param bizUid 业务UID
	 * @return 结果
	 */
	@Override
	public int deleteSysFlowTraceByBizUid(String bizUid) {
		return sysFlowTraceMapper.deleteSysFlowTraceByBizUid(bizUid);
	}
}

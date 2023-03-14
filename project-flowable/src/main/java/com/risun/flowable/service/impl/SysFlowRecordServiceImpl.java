package com.risun.flowable.service.impl;

import java.util.Date;
import java.util.List;

import org.flowable.bpmn.model.UserTask;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.risun.common.utils.DateUtils;
import com.risun.flowable.domain.SysFlowRecord;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysStartFlowVo;
import com.risun.flowable.mapper.SysFlowRecordMapper;
import com.risun.flowable.service.ISysFlowBpmnModelService;
import com.risun.flowable.service.ISysFlowRecordService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * 流程记录Service业务层处理
 * 
 * @author sunchao
 * @date 2023-02-27
 */
@Service
public class SysFlowRecordServiceImpl implements ISysFlowRecordService {
	
	@Autowired
	private SysFlowRecordMapper sysFlowRecordMapper;
	@Autowired
	private ISysFlowBpmnModelService sysFlowBpmnModelService;

	/**
	 * 查询流程记录
	 * 
	 * @param bizUid 流程业务uid
	 * @return 流程记录
	 */
	@Override
	public List<SysFlowRecord> selectSysFlowRecordByBizUid(String bizUid) {
		SysFlowRecord query = new SysFlowRecord();
		query.setBizUid(bizUid);
		return sysFlowRecordMapper.selectSysFlowRecordList(query);
	}

	/**
	 * 查询流程记录列表
	 * 
	 * @param sysFlowRecord 流程记录
	 * @return 流程记录
	 */
	@Override
	public List<SysFlowRecord> selectSysFlowRecordList(SysFlowRecord sysFlowRecord) {
		return sysFlowRecordMapper.selectSysFlowRecordList(sysFlowRecord);
	}

	/**
	 * 新增流程记录
	 * 
	 * @param sysFlowRecord 流程记录
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertSysFlowRecord(SysStartFlowVo startFlowVo, Task handledTask, List<Task> curTasks) {
		int result = 0;
		Date handledTime = DateUtils.getNowDate();
		SysFlowRecord handledRecord = new SysFlowRecord();
		handledRecord.setBizUid(startFlowVo.getBizUid());
		handledRecord.setUserId(startFlowVo.getHandleUserId());
		handledRecord.setStartTime(handledTime);
		handledRecord.setEndTime(handledTime);
		handledRecord.setTaskId(handledTask.getId());
		handledRecord.setTaskDefId(handledTask.getTaskDefinitionKey());
		handledRecord.setTaskDefName(handledTask.getName());
		handledRecord.setTaskDefDesc(handledTask.getDescription());
		handledRecord.setComment(handledTask.getName());
		result = sysFlowRecordMapper.insertSysFlowRecord(handledRecord);

		this.insertTodoSysFlowRecord(startFlowVo.getBizUid(), startFlowVo.getApprovalUserId(), handledTime, curTasks);
		return result;
	}

	/**
	 * 修改流程记录
	 * 
	 * @param sysFlowRecord 流程记录
	 * @return 结果
	 */
	@Override
	public int updateSysFlowRecord(SysApprovalFlowVo taskVo, List<Task> curTasks) {
		int result = 0;
		List<SysFlowRecord> records = this.selectByBizUidAndTaskDefId(taskVo.getBizUid(), taskVo.getTaskDefId());
		if(records.size() > 1) {
			handleMultiTask(taskVo, records);
		} else if(records.size() == 1) {
			updateRecord(taskVo, records.get(0));
		}
		
		if(CollUtil.isNotEmpty(curTasks) && curTasks.stream().allMatch(c -> !c.getTaskDefinitionKey().equals(taskVo.getTaskDefId()))) {
			this.insertTodoSysFlowRecord(taskVo.getBizUid(), taskVo.getApprovalUserId(), DateUtils.getNowDate(), curTasks);
		}
		return result;
	}
	
	/**
	 * 处理多实例任务
	 * 
	 * @param taskVo
	 * @param records
	 */
	private void handleMultiTask(SysApprovalFlowVo taskVo, List<SysFlowRecord> records) {
		Boolean agree = taskVo.getAgree();
		Date handledTime = DateUtils.getNowDate();
		if(agree == null || agree) {
			for (SysFlowRecord record : records) {
				if(record.getUserId().equals(taskVo.getHandleUserId())) {
					updateRecord(taskVo, record);
				}
			}
		} else {
			for (SysFlowRecord record : records) {
				if(record.getUserId().equals(taskVo.getHandleUserId())) {
					updateRecord(taskVo, record);
				} else {
					record.setFlowResult(Boolean.FALSE);
					record.setComment("自动退回");
					record.setEndTime(handledTime);
					sysFlowRecordMapper.updateSysFlowRecord(record);
				}
			}
		}
	}
	
	private void updateRecord(SysApprovalFlowVo taskVo, SysFlowRecord record) {
		record.setTaskId(taskVo.getTaskId());
		record.setComment(taskVo.getComment());
		record.setAttachment(taskVo.getAttachment());
		record.setEndTime(DateUtils.getNowDate());
		if(record.getUserId() == null) {
			record.setUserId(taskVo.getHandleUserId());
		} 
		record.setFlowResult(taskVo.getAgree());
		sysFlowRecordMapper.updateSysFlowRecord(record);
	}
	
	/**
	 * 新增待办记录
	 * 
	 * @param bizUid
	 * @param approvalUserIds
	 * @param handledTime
	 * @param curTasks
	 */
	private void insertTodoSysFlowRecord(String bizUid, Long[] approvalUserIds, Date handledTime, List<Task> curTasks) {
		if (CollUtil.isNotEmpty(curTasks)) {
			for (int i = 0; i < curTasks.size(); i++) {
				Task curTask = curTasks.get(i);
				SysFlowRecord processingRecord = new SysFlowRecord();
				processingRecord.setBizUid(bizUid);
				if (ArrayUtil.isNotEmpty(approvalUserIds)) {
					processingRecord.setUserId(approvalUserIds[i]);
				} else {
					// 设置候选组
					UserTask userTask = sysFlowBpmnModelService.getUserTaskByTaskDefId(curTask.getProcessDefinitionId(), curTask.getTaskDefinitionKey());
					List<String> candidateGroups = userTask.getCandidateGroups();
					if(CollUtil.isNotEmpty(candidateGroups)) {
						processingRecord.setGroupId(Long.parseLong(candidateGroups.get(0)));
					} 
				}
				processingRecord.setStartTime(DateUtil.offsetMillisecond(handledTime, 1));
				processingRecord.setTaskId(curTask.getId());
				processingRecord.setTaskDefId(curTask.getTaskDefinitionKey());
				processingRecord.setTaskDefName(curTask.getName());
				processingRecord.setTaskDefDesc(curTask.getDescription());
				sysFlowRecordMapper.insertSysFlowRecord(processingRecord);
			}
			
		}
	}
	
	/**
	 * 根据业务uid和任务Id获取流程记录
	 * 
	 * @param bizUid
	 * @param taskDefId
	 * @return
	 */
	@Override
	public List<SysFlowRecord> selectByBizUidAndTaskDefId(String bizUid, String taskDefId) {
		SysFlowRecord query = new SysFlowRecord();
		query.setBizUid(bizUid);
		query.setTaskDefId(taskDefId);
		return sysFlowRecordMapper.selectSysFlowRecordBybizUidAndTaskDefId(query);
	}

	/**
	 * 批量删除流程记录
	 * 
	 * @param recordIds 需要删除的流程记录主键
	 * @return 结果
	 */
	@Override
	public int deleteSysFlowRecordByRecordIds(Long[] recordIds) {
		return sysFlowRecordMapper.deleteSysFlowRecordByRecordIds(recordIds);
	}

	/**
	 * 删除流程记录信息
	 * 
	 * @param recordId 流程记录主键
	 * @return 结果
	 */
	@Override
	public int deleteSysFlowRecordByRecordId(Long recordId) {
		return sysFlowRecordMapper.deleteSysFlowRecordByRecordId(recordId);
	}

	/**
	 * 根据业务UID删除流程记录
	 * 
	 * @param bizUid
	 * @return
	 */
	public int deleteSysFlowRecordByBizUid(String bizUid) {
		return sysFlowRecordMapper.deleteSysFlowRecordByBizUid(bizUid);
	}
}

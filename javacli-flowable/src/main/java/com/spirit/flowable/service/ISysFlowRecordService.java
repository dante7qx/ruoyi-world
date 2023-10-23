package com.spirit.flowable.service;

import java.util.List;

import org.flowable.task.api.Task;

import com.spirit.flowable.domain.SysFlowRecord;
import com.spirit.flowable.domain.vo.SysApprovalFlowVo;
import com.spirit.flowable.domain.vo.SysStartFlowVo;

/**
 * 流程记录Service接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface ISysFlowRecordService 
{
    /**
     * 查询流程记录
     * 
     * @param bizUid 流程业务uid
     * @return 流程记录
     */
    public List<SysFlowRecord> selectSysFlowRecordByBizUid(String bizUid);

    /**
     * 查询流程记录列表
     * 
     * @param sysFlowRecord 流程记录
     * @return 流程记录集合
     */
    public List<SysFlowRecord> selectSysFlowRecordList(SysFlowRecord sysFlowRecord);
    
    /**
	 * 根据业务uid和任务Id获取流程记录
	 * 
	 * @param bizUid
	 * @param taskDefId
	 * @return
	 */
	public List<SysFlowRecord> selectByBizUidAndTaskDefId(String bizUid, String taskDefId);

    /**
     * 新增流程记录
     * 
     * @param startFlowVo 流程启动参数
     * @param handledTask 起始任务
     * @param curTasks    待处理任务
     * @return
     */
    public int insertSysFlowRecord(SysStartFlowVo startFlowVo, Task handledTask, List<Task> curTasks);

    /**
     * 修改流程记录
     * 
     * @param taskVo	流程审批参数
     * @param curTasks  待处理任务
     * @return
     */
    public int updateSysFlowRecord(SysApprovalFlowVo taskVo, List<Task> curTasks);

    /**
     * 批量删除流程记录
     * 
     * @param recordIds 需要删除的流程记录主键集合
     * @return 结果
     */
    public int deleteSysFlowRecordByRecordIds(Long[] recordIds);

    /**
     * 删除流程记录信息
     * 
     * @param recordId 流程记录主键
     * @return 结果
     */
    public int deleteSysFlowRecordByRecordId(Long recordId);
    
    /**
     * 根据业务UID删除流程记录
     * 
     * @param bizUid
     * @return
     */
    public int deleteSysFlowRecordByBizUid(String bizUid);
}

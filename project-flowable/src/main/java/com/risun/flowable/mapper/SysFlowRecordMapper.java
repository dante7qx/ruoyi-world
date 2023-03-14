package com.risun.flowable.mapper;

import java.util.List;
import com.risun.flowable.domain.SysFlowRecord;

/**
 * 流程记录Mapper接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface SysFlowRecordMapper 
{
    /**
     * 查询流程记录
     * 
     * @param recordId 流程记录主键
     * @return 流程记录
     */
    public SysFlowRecord selectSysFlowRecordByRecordId(Long recordId);

    /**
     * 查询流程记录列表
     * 
     * @param sysFlowRecord 流程记录
     * @return 流程记录集合
     */
    public List<SysFlowRecord> selectSysFlowRecordList(SysFlowRecord sysFlowRecord);
    
    /**
     * 查询待处理的流程记录
     * 
     * @param sysFlowRecord
     * @return
     */
    public List<SysFlowRecord> selectSysFlowRecordBybizUidAndTaskDefId(SysFlowRecord sysFlowRecord);

    /**
     * 新增流程记录
     * 
     * @param sysFlowRecord 流程记录
     * @return 结果
     */
    public int insertSysFlowRecord(SysFlowRecord sysFlowRecord);

    /**
     * 修改流程记录
     * 
     * @param sysFlowRecord 流程记录
     * @return 结果
     */
    public int updateSysFlowRecord(SysFlowRecord sysFlowRecord);

    /**
     * 删除流程记录
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

    /**
     * 批量删除流程记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowRecordByRecordIds(Long[] recordIds);
}

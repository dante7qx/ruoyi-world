package com.risun.flowable.mapper;

import java.util.List;
import com.risun.flowable.domain.SysFlowSeq;

/**
 * 流程序号Mapper接口
 * 
 * @author sunchao
 * @date 2023-02-27
 */
public interface SysFlowSeqMapper 
{
    /**
     * 查询流程序号
     * 
     * @param id 流程序号主键
     * @return 流程序号
     */
    public SysFlowSeq selectSysFlowSeqById(Long id);

    /**
     * 查询流程序号列表
     * 
     * @param sysFlowSeq 流程序号
     * @return 流程序号集合
     */
    public List<SysFlowSeq> selectSysFlowSeqList(SysFlowSeq sysFlowSeq);

    /**
     * 新增流程序号
     * 
     * @param sysFlowSeq 流程序号
     * @return 结果
     */
    public int insertSysFlowSeq(SysFlowSeq sysFlowSeq);

    /**
     * 修改流程序号
     * 
     * @param sysFlowSeq 流程序号
     * @return 结果
     */
    public int updateSysFlowSeq(SysFlowSeq sysFlowSeq);

    /**
     * 删除流程序号
     * 
     * @param id 流程序号主键
     * @return 结果
     */
    public int deleteSysFlowSeqById(Long id);

    /**
     * 批量删除流程序号
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowSeqByIds(Long[] ids);
}

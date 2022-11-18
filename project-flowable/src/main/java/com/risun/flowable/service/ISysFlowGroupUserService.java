package com.risun.flowable.service;

import java.util.List;

import com.risun.flowable.domain.SysFlowGroupUser;

/**
 * 流程审批组人关联Service接口
 * 
 * @author sunchao
 * @date 2022-11-09
 */
public interface ISysFlowGroupUserService 
{
    /**
     * 查询流程审批组人关联
     * 
     * @param id 流程审批组人关联主键
     * @return 流程审批组人关联
     */
    public SysFlowGroupUser selectSysFlowGroupUserById(Long id);

    /**
     * 查询流程审批组人关联列表
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 流程审批组人关联集合
     */
    public List<SysFlowGroupUser> selectSysFlowGroupUserList(SysFlowGroupUser sysFlowGroupUser);

    /**
     * 新增流程审批组人关联
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 结果
     */
    public int insertSysFlowGroupUser(SysFlowGroupUser sysFlowGroupUser);

    /**
     * 修改流程审批组人关联
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 结果
     */
    public int updateSysFlowGroupUser(SysFlowGroupUser sysFlowGroupUser);

    /**
     * 批量删除流程审批组人关联
     * 
     * @param ids 需要删除的流程审批组人关联主键集合
     * @return 结果
     */
    public int deleteSysFlowGroupUserByIds(Long[] ids);

    /**
     * 删除流程审批组人关联信息
     * 
     * @param id 流程审批组人关联主键
     * @return 结果
     */
    public int deleteSysFlowGroupUserById(Long id);
    
    /**
     * 查询指定用户所属的审批组
     * 
     * @param userId
     * @return
     */
    public List<String> selectGroupByUserId(Long userId);
}

package com.risun.flowable.mapper;

import java.util.List;
import com.risun.flowable.domain.SysFlowGroupUser;

/**
 * 流程审批组人关联Mapper接口
 * 
 * @author sunchao
 * @date 2022-11-09
 */
public interface SysFlowGroupUserMapper 
{
    /**
     * 查询流程审批组人关联
     * 
     * @param id 流程审批组人关联主键
     * @return 流程审批组人关联
     */
    public SysFlowGroupUser selectSysFlowGroupUserById(Long id);
    
    /**
     * 根据groupId查询流程审批组人关联
     * 
     * @param groupId
     * @return
     */
    public List<SysFlowGroupUser> selectSysFlowGroupUserByGroupId(Long groupId);

    /**
     * 查询流程审批组人关联列表
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 流程审批组人关联集合
     */
    public List<SysFlowGroupUser> selectSysFlowGroupUserList(SysFlowGroupUser sysFlowGroupUser);
    
    /**
     * 查询指定用户所属的审批组
     * 
     * @param userId
     * @return
     */
    public List<String> selectGroupByUserId(Long userId);

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
     * 删除流程审批组人关联
     * 
     * @param id 流程审批组人关联主键
     * @return 结果
     */
    public int deleteSysFlowGroupUserById(Long id);
    
    /**
     * 根据groupId删除流程审批组人关联
     * 
     * @param id
     * @return
     */
    public int deleteSysFlowGroupUserByGroupId(Long groupId);

    /**
     * 批量删除流程审批组人关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFlowGroupUserByIds(Long[] ids);
}

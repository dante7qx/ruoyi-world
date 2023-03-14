package com.risun.flowable.service.impl;

import java.util.List;

import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.domain.SysFlowGroup;
import com.risun.flowable.domain.SysFlowGroupUser;
import com.risun.flowable.mapper.SysFlowGroupMapper;
import com.risun.flowable.mapper.SysFlowGroupUserMapper;
import com.risun.flowable.service.ISysFlowGroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程审批组Service业务层处理
 * 
 * @author sunchao
 * @date 2022-11-09
 */
@Service
public class SysFlowGroupServiceImpl implements ISysFlowGroupService 
{
    @Autowired
    private SysFlowGroupMapper sysFlowGroupMapper;
    @Autowired
    private SysFlowGroupUserMapper sysFlowGroupUserMapper;

    /**
     * 查询流程审批组
     * 
     * @param groupId 流程审批组主键
     * @return 流程审批组
     */
    @Override
    public SysFlowGroup selectSysFlowGroupByGroupId(Long groupId)
    {
        return sysFlowGroupMapper.selectSysFlowGroupByGroupId(groupId);
    }

    /**
     * 查询流程审批组列表
     * 
     * @param sysFlowGroup 流程审批组
     * @return 流程审批组
     */
    @Override
    public List<SysFlowGroup> selectSysFlowGroupList(SysFlowGroup sysFlowGroup)
    {
        return sysFlowGroupMapper.selectSysFlowGroupList(sysFlowGroup);
    }

    /**
     * 新增流程审批组
     * 
     * @param sysFlowGroup 流程审批组
     * @return 结果
     */
    @Override
    public int insertSysFlowGroup(SysFlowGroup sysFlowGroup)
    {
        sysFlowGroup.setCreateBy(SecurityUtils.getUsername());
        sysFlowGroup.setCreateTime(DateUtils.getNowDate());
        return sysFlowGroupMapper.insertSysFlowGroup(sysFlowGroup);
    }

    /**
     * 修改流程审批组
     * 
     * @param sysFlowGroup 流程审批组
     * @return 结果
     */
    @Override
    public int updateSysFlowGroup(SysFlowGroup sysFlowGroup)
    {
        sysFlowGroup.setUpdateBy(SecurityUtils.getUsername());
        sysFlowGroup.setUpdateTime(DateUtils.getNowDate());
        return sysFlowGroupMapper.updateSysFlowGroup(sysFlowGroup);
    }

    /**
     * 批量删除流程审批组
     * 
     * @param groupIds 需要删除的流程审批组主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteSysFlowGroupByGroupIds(Long[] groupIds)
    {
    	for (Long groupId : groupIds) {
    		sysFlowGroupUserMapper.deleteSysFlowGroupUserByGroupId(groupId);
		}
        return sysFlowGroupMapper.deleteSysFlowGroupByGroupIds(groupIds);
    }

    /**
     * 删除流程审批组信息
     * 
     * @param groupId 流程审批组主键
     * @return 结果
     */
    @Override
    // TODO: 流程类型关联后不允许删除
    public int deleteSysFlowGroupByGroupId(Long groupId)
    {
        return sysFlowGroupMapper.deleteSysFlowGroupByGroupId(groupId);
    }
    
    /**
     * 分配用户
     * 
     * @param groupId
     * @param userIds
     * @return
     */
    @Override
    public int allocateUser(Long groupId, Long[] userIds) {
    	int result = 1;
    	List<SysFlowGroupUser> groupUsers = sysFlowGroupUserMapper.selectSysFlowGroupUserByGroupId(groupId);
    	for (Long userId : userIds) {
    		if(!groupUsers.stream().anyMatch(g -> g.getUserId().equals(userId))) {
    			SysFlowGroupUser groupUser = new SysFlowGroupUser();
    			groupUser.setGroupId(groupId);
    			groupUser.setUserId(userId);
    			groupUser.setCreateBy(SecurityUtils.getUsername());
    			groupUser.setCreateTime(DateUtils.getNowDate());
    			result += sysFlowGroupUserMapper.insertSysFlowGroupUser(groupUser);
    		}
		}
    	return result;
    }
}

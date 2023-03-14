package com.risun.flowable.service.impl;

import java.util.List;
import com.risun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.risun.common.utils.SecurityUtils;
import com.risun.flowable.mapper.SysFlowGroupUserMapper;
import com.risun.flowable.domain.SysFlowGroupUser;
import com.risun.flowable.service.ISysFlowGroupUserService;

/**
 * 流程审批组人关联Service业务层处理
 * 
 * @author sunchao
 * @date 2022-11-09
 */
@Service
public class SysFlowGroupUserServiceImpl implements ISysFlowGroupUserService 
{
    @Autowired
    private SysFlowGroupUserMapper sysFlowGroupUserMapper;

    /**
     * 查询流程审批组人关联
     * 
     * @param id 流程审批组人关联主键
     * @return 流程审批组人关联
     */
    @Override
    public SysFlowGroupUser selectSysFlowGroupUserById(Long id)
    {
        return sysFlowGroupUserMapper.selectSysFlowGroupUserById(id);
    }

    /**
     * 查询流程审批组人关联列表
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 流程审批组人关联
     */
    @Override
    public List<SysFlowGroupUser> selectSysFlowGroupUserList(SysFlowGroupUser sysFlowGroupUser)
    {
        return sysFlowGroupUserMapper.selectSysFlowGroupUserList(sysFlowGroupUser);
    }
    
    /**
     * 根据groupKey查询流程审批组人关联
     * 
     * @param groupKey
     * @return
     */
    @Override
    public List<SysFlowGroupUser> selectSysFlowGroupUserByGroupKey(String groupKey) {
    	return sysFlowGroupUserMapper.selectSysFlowGroupUserByGroupKey(groupKey);
    }

    /**
     * 新增流程审批组人关联
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 结果
     */
    @Override
    public int insertSysFlowGroupUser(SysFlowGroupUser sysFlowGroupUser)
    {
        sysFlowGroupUser.setCreateBy(SecurityUtils.getUsername());
        sysFlowGroupUser.setCreateTime(DateUtils.getNowDate());
        return sysFlowGroupUserMapper.insertSysFlowGroupUser(sysFlowGroupUser);
    }

    /**
     * 修改流程审批组人关联
     * 
     * @param sysFlowGroupUser 流程审批组人关联
     * @return 结果
     */
    @Override
    public int updateSysFlowGroupUser(SysFlowGroupUser sysFlowGroupUser)
    {
        sysFlowGroupUser.setUpdateBy(SecurityUtils.getUsername());
        sysFlowGroupUser.setUpdateTime(DateUtils.getNowDate());
        return sysFlowGroupUserMapper.updateSysFlowGroupUser(sysFlowGroupUser);
    }

    /**
     * 批量删除流程审批组人关联
     * 
     * @param ids 需要删除的流程审批组人关联主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowGroupUserByIds(Long[] ids)
    {
        return sysFlowGroupUserMapper.deleteSysFlowGroupUserByIds(ids);
    }

    /**
     * 删除流程审批组人关联信息
     * 
     * @param id 流程审批组人关联主键
     * @return 结果
     */
    @Override
    public int deleteSysFlowGroupUserById(Long id)
    {
        return sysFlowGroupUserMapper.deleteSysFlowGroupUserById(id);
    }
    
    /**
     * 查询指定用户所属的审批组
     * 
     * @param userId
     * @return
     */
    @Override
    public List<String> selectGroupByUserId(Long userId) {
    	
    	return sysFlowGroupUserMapper.selectGroupByUserId(userId);
    }
}

package com.risun.flowable.factory;

import java.util.List;

import javax.annotation.Resource;

import com.risun.common.core.domain.entity.SysUser;
import com.risun.flowable.domain.SysFlowGroup;
import com.risun.flowable.domain.dto.FlowHistRecordDto;
import com.risun.flowable.service.ISysFlowGroupService;

import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * flowable 引擎注入封装
 * 
 * @author XuanXuan
 * @date 2021-04-03
 */
@Component
@Getter
public class FlowServiceFactory {
	
	@Resource
    protected RepositoryService repositoryService;

    @Resource
    protected RuntimeService runtimeService;

    @Resource
    protected IdentityService identityService;

    @Resource
    protected TaskService taskService;

    @Autowired
    protected FormService formService;

    @Resource
    protected HistoryService historyService;

    @Resource
    protected ManagementService managementService;

    @Qualifier("processEngine")
    @Resource
    protected ProcessEngine processEngine;
    
    @Autowired
    protected ISysFlowGroupService sysFlowGroupService;
    
    
    protected FlowHistRecordDto buildFlowCandidate(List<SysUser> userList, HistoricActivityInstance histActivity,
			HistoricTaskInstance taskHist, int seq) {
		FlowHistRecordDto vo = new FlowHistRecordDto();
		vo.setSeqNum(seq);
		vo.setFromActivityId(histActivity.getActivityId());
		vo.setFromTaskId(histActivity.getTaskId());
		vo.setFromTask(histActivity.getActivityName());
		vo.setStartDate(DateUtil.formatDateTime(taskHist.getCreateTime()));
		vo.setEndDate(DateUtil.formatDateTime(taskHist.getEndTime()));
		if (StrUtil.isNotEmpty(histActivity.getAssignee())) {
			vo.setUserId(Long.parseLong(histActivity.getAssignee()));
			SysUser sysUser = userList.stream().filter(r -> r.getUserId().equals(vo.getUserId())).distinct().findFirst()
					.orElse(null);
			if (sysUser != null) {
				vo.setUserNickName(sysUser.getNickName());
				vo.setDeptId(sysUser.getRoleId());
				vo.setDeptName(sysUser.getDept().getDeptName());
			}
		}

		return vo;
	}
	
    protected void buildFlowCandidate(FlowHistRecordDto v, IdentityLink identityLink, List<SysUser> userList) {
		String groupId = identityLink.getGroupId();
		String userId = identityLink.getUserId();
		if(StrUtil.isNotEmpty(userId)) {
			v.setUserId(Long.parseLong(userId));
			SysUser sysUser = userList.stream().filter(r -> r.getUserId().equals(v.getUserId())).distinct().findFirst().orElse(null);
			if(sysUser != null) {
				v.setUserNickName(sysUser.getNickName());
				v.setDeptId(sysUser.getRoleId());
				v.setDeptName(sysUser.getDept().getDeptName());
			} 
			
		} else if(StrUtil.isNotEmpty(groupId)) {
			v.setDeptId(Long.parseLong(groupId));
			SysFlowGroup flowGroup = sysFlowGroupService.selectSysFlowGroupByGroupId(Long.parseLong(groupId));
			v.setDeptName(flowGroup.getGroupName());
		}
	}
}

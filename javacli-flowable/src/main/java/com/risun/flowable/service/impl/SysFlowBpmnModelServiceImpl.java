package com.spirit.flowable.service.impl;

import java.util.Collection;
import java.util.List;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.springframework.stereotype.Service;

import com.spirit.flowable.factory.FlowServiceFactory;
import com.spirit.flowable.service.ISysFlowBpmnModelService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 流程模型服务接口实现类
 * 
 * @author dante
 *
 */
@Service
public class SysFlowBpmnModelServiceImpl extends FlowServiceFactory implements ISysFlowBpmnModelService {

	/**
	 * 根据用户任务定义Id获取用户任务模型
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @return
	 */
	@Override
	public UserTask getUserTaskByTaskDefId(String procDefId, String taskDefId) {
		BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
		return (UserTask) bpmnModel.getFlowElement(taskDefId);
	}

	/**
	 * 获取流程第一个用户任务或指定用户任务Id的用户任务
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @return
	 */
	@Override
	public UserTask getFirstUserTaskOrTaskDef(String procDefId, String taskDefId) {
		UserTask userTask = null;
		List<Process> processes = repositoryService.getBpmnModel(procDefId)
			.getProcesses();
		for (Process process : processes) {
			Collection<FlowElement> flowElements = process.getFlowElements();
			if (CollUtil.isNotEmpty(flowElements)) {
				for (FlowElement flowElement : flowElements) {
					if (flowElement instanceof UserTask) {
						userTask = (UserTask) flowElement;
						if (StrUtil.isEmpty(taskDefId) || userTask.getId().equals(taskDefId)) {
							break;
						}
					}
				}
			}
		}
		return userTask;
	}

}

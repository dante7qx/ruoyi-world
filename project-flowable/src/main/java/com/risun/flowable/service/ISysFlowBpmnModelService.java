package com.risun.flowable.service;

import org.flowable.bpmn.model.UserTask;

/**
 * 流程模型服务接口
 * 
 * @author dante
 *
 */
public interface ISysFlowBpmnModelService {
	
	/**
	 * 根据用户任务定义Id获取用户任务模型
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @return
	 */
	public UserTask getUserTaskByTaskDefId(String procDefId, String taskDefId);
	
	/**
	 * 获取流程第一个用户任务或指定用户任务Id的用户任务
	 * 
	 * @param procDefId
	 * @param taskDefId
	 * @return
	 */
	public UserTask getFirstUserTaskOrTaskDef(String procDefId, String taskDefId);
	
}

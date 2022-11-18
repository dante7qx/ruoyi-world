package com.risun.flowable.service;

import java.util.List;

import com.risun.flowable.domain.vo.StartFlowVo;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

/**
 * 流程实例服务类
 * 
 * @author dante
 */
public interface IFlowInstanceService {
	
	/**
     * 根据流程定义Key启动流程实例
     * 
     * @param procDefKey	流程定义Key
     * @param startFlowVo	流程启动参数
     * @return
     */
    public ProcessInstance startProcessInstanceByKey(String procDefKey, StartFlowVo startFlowVo);
    
    /**
     * 业务提交操作
     * 
     * @param procDefKey
     * @param startFlowVo
     */
    public void commit(String procDefKey, StartFlowVo startFlowVo);
	
	/**
	 * 根据流程实例Id查询任务
	 * 
	 * @param instanceId
	 * @return
	 */
	List<Task> queryListByInstanceId(String instanceId);

    /**
     * 激活或挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例ID
     */
    void updateState(Integer state, String instanceId);

    /**
     * 删除流程实例ID
     *
     * @param proInstIds   流程实例ID
     */
    public int delete(String[] proInstIds);

    /**
     * 根据实例ID查询历史实例数据
     *
     * @param processInstanceId
     * @return
     */
    public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId);

}

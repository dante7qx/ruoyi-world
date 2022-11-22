package com.risun.flowable.service;

import java.io.InputStream;
import java.util.List;

import com.risun.common.core.domain.AjaxResult;
import com.risun.flowable.domain.SysFlowAssign;
import com.risun.flowable.domain.dto.FlowAttachmentDto;
import com.risun.flowable.domain.dto.FlowHistRecordDto;
import com.risun.flowable.domain.dto.FlowTaskDto;
import com.risun.flowable.domain.vo.FlowQueryVo;
import com.risun.flowable.domain.vo.FlowTaskVo;

import org.flowable.task.api.Task;

/**
 * @author XuanXuan
 * @date 2021-04-03 14:42
 */
public interface IFlowTaskService {
	
	/**
     * 代办任务列表
     *
     * @param FlowQueryVo  查询参数
     * @return
     */
    public List<FlowTaskDto> todoList(FlowQueryVo queryVo);
    
    /**
     * 已办任务列表
     *
     * @param FlowQueryVo  查询参数
     * @return
     */
    public List<FlowTaskDto> doneList(FlowQueryVo queryVo);


    /**
     * 办结任务列表
     *
     * @param FlowQueryVo  查询参数
     * @return
     */
    public List<FlowTaskDto> finishedList(FlowQueryVo queryVo);
	
	/**
	 * 审批流程任务
	 * 
	 * @param flowTask
	 * @return
	 */
	public int approval(FlowTaskVo flowTask);
	
	/**
     * 流程历史记录
     *
     * @param procInsId 流程实例Id
     * @return
     */
	public List<FlowHistRecordDto> flowRecordList(String procInsId);
	
	/**
	 * 查询流程附件记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	public List<FlowAttachmentDto> selectFlowAttachmentList(String procInsId);
	
	/**
	 * 查询流程转办记录
	 * 
	 * @param procInsId 流程实例Id
	 * @return
	 */
	public List<SysFlowAssign> selectFlowAssignList(String procInsId);

    /**
     * 转办任务
     *
     * @param flowTaskVo 请求实体参数
     */
    void assignTask(FlowTaskVo flowTaskVo);
    
    /**
     * 获取流程过程图
     * 
     * @param processId
     * @return
     */
    InputStream diagram(String processId);

    /**
     * 获取流程执行过程
     * 
     * @param procInsId
     * @return
     */
    AjaxResult getFlowViewer(String procInsId);

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     * @return
     */
    Task getTaskForm(String taskId);

    /**
     * 获取流程变量
     * 
     * @param taskId
     * @return
     */
	public Map<String, Object> processVariablesByTaskId(String taskId);
    
    /**
     * 获取流程变量
     * 
     * @param procInsId
     * @return
     */
	public Map<String, Object> processVariablesByProcInsId(String procInsId);
    
    /**
     * 驳回任务
     *
     * @param flowTaskVo
     */
    void taskReject(FlowTaskVo flowTaskVo);

    /**
     * 获取所有可回退的节点
     *
     * @param flowTaskVo
     * @return
     */
    AjaxResult findReturnTaskList(FlowTaskVo flowTaskVo);

    /**
     * 获取下一节点
     * @param flowTaskVo 任务
     * @return
     */
    AjaxResult getNextFlowNode(FlowTaskVo flowTaskVo);
	
}

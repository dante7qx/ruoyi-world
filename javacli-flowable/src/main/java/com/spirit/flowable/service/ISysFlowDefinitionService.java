package com.spirit.flowable.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.spirit.common.core.domain.AjaxResult;
import com.spirit.flowable.domain.dto.FlowProcDefDto;

/**
 * 流程定义服务类
 * 
 * @author dante
 */
public interface ISysFlowDefinitionService {
	
	/**
	 * 是否存在流程
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	boolean exist(String processDefinitionKey);

    /**
     * 流程定义列表
     *
     * @return 流程定义分页列表数据
     */
    List<FlowProcDefDto> list(FlowProcDefDto flowProcDefDto);
    
    /**
     * 查询所有的最新版流程定义
     * 
     * @return
     */
    public List<FlowProcDefDto> selectLatestProcessDef();
    
    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    void importFile(String name, String category, InputStream in);

    /**
     * 读取xml
     * 
     * @param deployId
     * @return
     */
    AjaxResult readXml(String deployId) throws IOException;

    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    void updateState(Integer state, String deployId);

    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    void delete(String deployId);


    /**
     * 读取图片文件
     * 
     * @param deployId
     * @return
     */
    InputStream readImage(String deployId);
    
    /**
     * 获取流程定义的第一个UserTask的Id
     * 
     * @param procDefId
     * @return
     */
    String getBeginUserTask(String procDefId);
}

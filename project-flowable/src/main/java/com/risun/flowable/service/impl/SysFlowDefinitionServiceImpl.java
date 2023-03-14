package com.risun.flowable.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.risun.common.core.domain.AjaxResult;
import com.risun.common.utils.DateUtils;
import com.risun.flowable.domain.SysForm;
import com.risun.flowable.domain.dto.FlowProcDefDto;
import com.risun.flowable.factory.FlowServiceFactory;
import com.risun.flowable.mapper.SysFlowDeployMapper;
import com.risun.flowable.service.ISysFlowDefinitionService;
import com.risun.flowable.service.ISysDeployFormService;
import com.risun.system.service.ISysDeptService;
import com.risun.system.service.ISysPostService;
import com.risun.system.service.ISysUserService;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.job.api.Job;
import org.springframework.stereotype.Service;

import cn.hutool.core.collection.CollUtil;

@Service
public class SysFlowDefinitionServiceImpl extends FlowServiceFactory implements ISysFlowDefinitionService {
	
	@Resource
    private ISysDeployFormService sysDeployFormService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysDeptService sysDeptService;

    @Resource
    private ISysPostService postService;

    @Resource
    private SysFlowDeployMapper flowDeployMapper;

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public boolean exist(String processDefinitionKey) {
        ProcessDefinitionQuery processDefinitionQuery
                = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey);
        long count = processDefinitionQuery.count();
        return count > 0 ? true : false;
    }


    /**
     * 流程定义列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @return 流程定义分页列表数据
     */
    @Override
    public List<FlowProcDefDto> list(FlowProcDefDto flowProcDefDto) {
        final List<FlowProcDefDto> dataList = flowDeployMapper.selectDeployList(flowProcDefDto);
        // 加载挂表单
        for (FlowProcDefDto procDef : dataList) {
            SysForm sysForm = sysDeployFormService.selectSysDeployFormByDeployId(procDef.getDeploymentId());
            if (Objects.nonNull(sysForm)) {
                procDef.setFormName(sysForm.getFormName());
                procDef.setFormId(sysForm.getFormId());
            }
        }
        return dataList;
    }
    
    /**
     * 查询所有的最新版流程定义
     * 
     * @return
     */
    public List<FlowProcDefDto> selectLatestProcessDef() {
    	return flowDeployMapper.selectLatestProcDefList();
    }


    /**
     * 导入流程文件
     *
     * @param name
     * @param category
     * @param in
     */
    @Override
    public void importFile(String name, String category, InputStream in) {
        Deployment deploy = repositoryService
        		.createDeployment()
        		.addInputStream(name + BPMN_FILE_SUFFIX, in)
        		.name(name)
        		.category(category)
        		.deploy();
        ProcessDefinition definition = repositoryService
        		.createProcessDefinitionQuery()
        		.deploymentId(deploy.getId())
        		.singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public AjaxResult readXml(String deployId) throws IOException {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return AjaxResult.success("", result);
    }

    /**
     * 读取xml
     *
     * @param deployId
     * @return
     */
    @Override
    public InputStream readImage(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        //获得图片流
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //输出为图片
        return diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                Collections.emptyList(),
                Collections.emptyList(),
                "宋体",
                "宋体",
                "宋体",
                null,
                1.0,
                false);

    }


    /**
     * 激活或挂起流程定义
     *
     * @param state    状态
     * @param deployId 流程部署ID
     */
    @Override
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        if (state == 1) {
        	// 激活
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, DateUtils.getNowDate());
        } else if (state == 2) {
        	// 挂起
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, DateUtils.getNowDate());
        }
    }


    /**
     * 删除流程定义
     *
     * @param deployId 流程部署ID act_ge_bytearray 表中 deployment_id值
     */
    @Override
    public void delete(String deployId) {
        // true 允许级联删除 ,不设置会导致数据库外键关联异常
    	ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
    	if(procDef != null) {
    		List<Job> deadLetterJobs = managementService.createDeadLetterJobQuery().processDefinitionId(procDef.getId()).list();
    		if(CollUtil.isNotEmpty(deadLetterJobs)) {
    			for (Job job : deadLetterJobs) {
    				managementService.deleteDeadLetterJob(job.getId());
				}
    		}
    	}
        repositoryService.deleteDeployment(deployId, true);
    }
    
    /**
     * 获取流程定义的第一个UserTask的Id
     * 
     * @param procDefId
     * @return UserTaskId
     */
    @Override
    public String getBeginUserTask(String procDefId) {
    	String userTaskId = "";
    	List<Process> processes = repositoryService.getBpmnModel(procDefId).getProcesses();
    	for (Process process : processes) {
    		Collection<FlowElement> flowElements = process.getFlowElements();
    		if (CollUtil.isNotEmpty(flowElements)) {
    			for (FlowElement flowElement : flowElements) {
    				if (flowElement instanceof UserTask) {
    					userTaskId = flowElement.getId();
    					break;
    				}
    			}
    		}
    	}
    	return userTaskId;
    }

}

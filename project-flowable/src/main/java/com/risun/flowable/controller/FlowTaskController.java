package com.risun.flowable.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.flowable.domain.vo.FlowQueryVo;
import com.risun.flowable.domain.vo.FlowTaskVo;
import com.risun.flowable.service.IFlowTaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Cleanup;

/**
 * <p>工作流任务管理<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/flowable/task")
public class FlowTaskController extends BaseController {
	@Autowired
    private IFlowTaskService flowTaskService;
	
	/**
     * 获取待办列表
     * 
     * @return
     */
    @GetMapping(value = "/todoList")
    public TableDataInfo todoList(FlowQueryVo queryVo) {
    	startPage();
        return getDataTable(flowTaskService.todoList(queryVo));
    }
    
    /**
     * 获取已办任务
     * 
     * @return
     */
    @GetMapping(value = "/doneList")
    public TableDataInfo doneList(FlowQueryVo queryVo) {
    	startPage();
        return getDataTable(flowTaskService.doneList(queryVo));
    }
    
    /**
     * 获取已办任务
     * 
     * @return
     */
    @GetMapping(value = "/finishedList")
    public TableDataInfo finishedList(FlowQueryVo queryVo) {
    	startPage();
        return getDataTable(flowTaskService.finishedList(queryVo));
    }
    
    /**
     * 审批任务
     * 
     * @param flowTaskVo
     * @return
     */
    @PostMapping(value = "/approval")
    public AjaxResult approval(@RequestBody FlowTaskVo flowTaskVo) {
        return toAjax(flowTaskService.approval(flowTaskVo));
    }
    

    /**
     * 转办任务
     * 
     * @param flowTaskVo
     * @return
     */
    @PostMapping(value = "/assign")
    public AjaxResult assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return AjaxResult.success();
    }

    /**
     * 流程记录
     * 
     * @param procInsId 流程实例Id
     * 
     * @return
     */
    @PostMapping(value = "/recordList/{procInsId}")
    public AjaxResult recordList(@PathVariable String procInsId) {
    	return AjaxResult.success(flowTaskService.flowRecordList(procInsId));
    }
    
    /**
     * 流程附件记录
     * 
     * @param procInsId 流程实例Id
     * 
     * @return
     */
    @PostMapping(value = "/attachmentList/{procInsId}")
    public AjaxResult attachmentList(@PathVariable String procInsId) {
    	return AjaxResult.success(flowTaskService.selectFlowAttachmentList(procInsId));
    }
    
    /**
     * 流程转办记录
     * 
     * @param procInsId 流程实例Id
     * 
     * @return
     */
    @PostMapping(value = "/assignList/{procInsId}")
    public AjaxResult assignList(@PathVariable String procInsId) {
    	return AjaxResult.success(flowTaskService.selectFlowAssignList(procInsId));
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) throws IOException {
    	@Cleanup InputStream inputStream = flowTaskService.diagram(processId);
    	@Cleanup OutputStream os = response.getOutputStream();
        BufferedImage image = ImageIO.read(inputStream);
        response.setContentType("image/png");
        if (image != null) {
            ImageIO.write(image, "png", os);
        }
    }

    /**
     * 生成流程过程图
     *
     * @param procInsId 流程实例编号
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public AjaxResult getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }
    
    /**
     * 获取流程变量
     * 
     * @param taskId
     * @return
     */
    @GetMapping(value = "/processVariables/{taskId}")
    public AjaxResult processVariables(@PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }
    
    /**
     * 获取下一节点
     * 
     * @param flowTaskVo
     * @return
     */
    @PostMapping(value = "/nextFlowNode")
    public AjaxResult getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo);
    }
}

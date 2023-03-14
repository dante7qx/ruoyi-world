package com.risun.flowable.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.flowable.domain.vo.SysFlowApproverVo;
import com.risun.flowable.domain.vo.SysApprovalFlowVo;
import com.risun.flowable.domain.vo.SysFlowAssignVo;
import com.risun.flowable.domain.vo.SysFlowTaskQueryVo;
import com.risun.flowable.domain.vo.SysFlowTaskVo;
import com.risun.flowable.service.ISysFlowRecordService;
import com.risun.flowable.service.ISysFlowTaskService;
import com.risun.flowable.service.ISysFlowTypeService;

import lombok.Cleanup;


/**
 * 系统流程相关操作
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/flowable/process")
public class SysFlowTaskController extends BaseController {
	
	@Autowired
	private ISysFlowTypeService sysFlowTypeService;
	@Autowired
	private ISysFlowTaskService sysFlowTaskService;
	@Autowired
	private ISysFlowRecordService sysFlowRecordService;
	
	/**
	 * 查询当前用户有权限新建的流程类型
	 */
	@PostMapping("/new")
	public AjaxResult myNewFlow() {
		return AjaxResult.success(sysFlowTypeService.selectLoginUserSysFlowTypeMap());
	}
	
	/**
	 * 根据流程类型查询当前用户所属的流程定义
	 */
	@PostMapping("/flowDef/{flowType}")
	public AjaxResult getFlowDef(@PathVariable String flowType) {
		return AjaxResult.success(sysFlowTypeService.selectLoginUserSysFlowType(flowType));
	}
	
	/**
	 * 获取流程审批候选人
	 */
	@PostMapping("/approver")
	public AjaxResult getFlowDef(@RequestBody SysFlowApproverVo flowApprover) {
		Assert.hasText(flowApprover.getProcDefId(), "流程定义Id不能为空！");
		return AjaxResult.success(sysFlowTaskService.selectFlowApprover(flowApprover.getProcDefId(), flowApprover.getTaskDefId(), flowApprover.getStarterUserId()));
	}
	
	/**
	 * 待办任务列表
	 */
	@GetMapping("/todolist")
	public TableDataInfo todoList(SysFlowTaskQueryVo queryVo) {
		startPage();
		return getDataTable(sysFlowTaskService.selectSysFlowTaskTodoList(queryVo));
	}
	
	/**
	 * 我的任务列表
	 */
	@GetMapping("/mylist")
	public TableDataInfo myList(SysFlowTaskQueryVo queryVo) {
		startPage();
		queryVo.setStarterUserId(getUserId());
		return getDataTable(sysFlowTaskService.selectSysFlowTaskList(queryVo));
	}
	
	/**
	 * 已办任务列表
	 */
	@GetMapping("/donelist")
	public TableDataInfo doneList(SysFlowTaskQueryVo queryVo) {
		startPage();
		queryVo.setHandledUserId(getUserId());
		return getDataTable(sysFlowTaskService.selectSysFlowTaskList(queryVo));
	}

	/**
	 * 监控任务列表
	 */
	@GetMapping("/monitorlist")
	public TableDataInfo monitorList(SysFlowTaskQueryVo queryVo) {
		startPage();
		queryVo.setStarterUserId(null);
		return getDataTable(sysFlowTaskService.selectSysFlowTaskList(queryVo));
	}
	
	/**
	 * 流程审批
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	@PostMapping("/approval")
	public AjaxResult approvalFlow(@RequestBody SysApprovalFlowVo sysApprovalFlowVo) {
		return AjaxResult.success(sysFlowTaskService.approvalFlowTask(sysApprovalFlowVo));
	} 
	
	/**
     * 转办任务
     * 
     * @param flowTaskVo
     * @return
     */
    @PostMapping(value = "/assign")
    public AjaxResult assign(@RequestBody SysFlowAssignVo sysFlowAssignVo) {
    	sysFlowTaskService.assignTask(sysFlowAssignVo);
        return AjaxResult.success();
    }
	
	/**
	 * 撤销流程
	 * 
	 * @param flowVo
	 * @return
	 */
	@PostMapping("/revoke/{bizUid}")
	public AjaxResult revoke(@PathVariable String bizUid) {
		Assert.hasText(bizUid, "业务Uid不能为空！");
		return AjaxResult.success(sysFlowTaskService.revoke(bizUid));
	} 
	
	/**
	 * 获取流程跟踪
	 * 
	 * @param bizUid
	 * @return
	 */
	@PostMapping("/trace/{bizUid}")
	public AjaxResult getTrace(@PathVariable String bizUid) {
		Assert.hasText(bizUid, "业务Uid不能为空！");
		return AjaxResult.success(sysFlowTaskService.selectSysFlowTraceByBizUid(bizUid));
	}
	
	/**
	 * 删除流程
	 * 
	 * @param sysFlowTaskVo
	 * @return
	 */
	@PostMapping("/del")
	public AjaxResult delFlow(@RequestBody SysFlowTaskVo[] sysFlowTaskVo) {
		return AjaxResult.success(sysFlowTaskService.delete(sysFlowTaskVo));
	} 
	
	/**
	 * 审批记录
	 * 
	 * @param bizUid
	 * @return
	 */
	@PostMapping("/record/{bizUid}")
	public AjaxResult recordList(@PathVariable String bizUid) {
		Assert.hasText(bizUid, "业务Uid不能为空！");
		return AjaxResult.success(sysFlowRecordService.selectSysFlowRecordByBizUid(bizUid));
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
    	return AjaxResult.success(sysFlowTaskService.selectFlowAssignList(procInsId));
    }
	
	/**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response,
                                  @PathVariable("processId") String processId) throws IOException {
    	@Cleanup InputStream inputStream = sysFlowTaskService.diagram(processId);
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
        return sysFlowTaskService.getFlowViewer(procInsId);
    }
	
}

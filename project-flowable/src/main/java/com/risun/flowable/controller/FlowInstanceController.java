package com.risun.flowable.controller;

import com.risun.common.core.domain.AjaxResult;
import com.risun.flowable.service.IFlowInstanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>工作流流程实例管理<p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/flowable/instance")
public class FlowInstanceController {
	@Autowired
    private IFlowInstanceService flowInstanceService;


    /**
     * 激活或挂起流程实例
     * 
     * @param state	1:激活,2:挂起
     * @param instanceId	流程实例ID
     * @return
     */
    @PostMapping(value = "/updateState")
    public AjaxResult updateState(@RequestParam Integer state,
                                  @RequestParam String instanceId) {
        flowInstanceService.updateState(state, instanceId);
        return AjaxResult.success();
    }

    /**
     * 删除流程实例
     * 
     * @param instanceId
     * @param deleteReason
     * @return
     */
    @PostMapping(value = "/del/{proInstIds}")
    public AjaxResult delete(@PathVariable String[] proInstIds) {
        return AjaxResult.success(flowInstanceService.delete(proInstIds));
    }
}

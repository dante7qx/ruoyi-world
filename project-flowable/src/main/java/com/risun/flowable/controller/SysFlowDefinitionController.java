package com.risun.flowable.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysUser;
import com.risun.common.core.page.TableDataInfo;
import com.risun.flowable.domain.SysFlowGroup;
import com.risun.flowable.domain.dto.FlowProcDefDto;
import com.risun.flowable.domain.dto.FlowSaveXmlVo;
import com.risun.flowable.service.ISysFlowDefinitionService;
import com.risun.flowable.service.ISysFlowGroupService;
import com.risun.system.service.ISysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Cleanup;

/**
 * <p>
 * 工作流程定义
 * </p>
 *
 * @author XuanXuan
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/flowable/definition")
public class SysFlowDefinitionController extends BaseController {
	
	@Autowired
    private ISysFlowDefinitionService flowDefinitionService;

    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private ISysFlowGroupService sysFlowGroupService;

    /** 
     * 流程定义列表
     * 
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping(value = "/list")
    public TableDataInfo list(FlowProcDefDto flowProcDefDto) {
    	startPage();
    	return getDataTable(flowDefinitionService.list(flowProcDefDto));
    }
    
    /**
     * 查询所有的最新版流程定义
     * 
     * @return
     */
    @PostMapping(value = "/listLatest")
    public AjaxResult listLatestProcDef() {
    	return AjaxResult.success(flowDefinitionService.selectLatestProcessDef());
    }

    /**
     * 导入流程文件（上传bpmn20的xml文件）
     * 
     * @param name
     * @param category
     * @param file
     * @return
     */
    @PostMapping("/import")
    public AjaxResult importFile(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String category,
                                 MultipartFile file) throws IOException {
    	@Cleanup InputStream in = file.getInputStream();
    	flowDefinitionService.importFile(name, category, in);

        return AjaxResult.success("导入成功");
    }

    /**
     * 读取xml文件
     * 
     * @param deployId
     * @return
     */
    @GetMapping("/readXml/{deployId}")
    public AjaxResult readXml(@PathVariable(value = "deployId") String deployId) {
        try {
            return flowDefinitionService.readXml(deployId);
        } catch (Exception e) {
            return AjaxResult.error("加载xml文件异常");
        }
    }

    /**
     * 读取图片文件
     * 
     * @param deployId
     * @param response
     */
    @GetMapping("/readImage/{deployId}")
    public void readImage(@PathVariable(value = "deployId") String deployId, HttpServletResponse response) throws IOException {
    	@Cleanup OutputStream os = response.getOutputStream();
        BufferedImage image = ImageIO.read(flowDefinitionService.readImage(deployId));
        response.setContentType("image/png");
        if (image != null) {
            ImageIO.write(image, "png", os);
        }
    }

    /**
     * 保存流程设计器内的xml文件
     * 
     * @param vo
     * @return
     */
    @PostMapping("/save")
    public AjaxResult save(@RequestBody FlowSaveXmlVo vo) throws IOException {
    	@Cleanup InputStream in = new ByteArrayInputStream(vo.getXml().getBytes(StandardCharsets.UTF_8));
    	flowDefinitionService.importFile(vo.getName(), vo.getCategory(), in);
        return AjaxResult.success("导入成功");
    }

    /**
     * 激活或挂起流程定义
     * 
     * @param state
     * @param deployId
     * @return
     */
    @PostMapping(value = "/updateState")
    public AjaxResult updateState(@RequestParam Integer state,
                                  @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return AjaxResult.success();
    }

    /**
     * 删除流程
     * 
     * @param deployIds
     * @return
     */
    @PostMapping(value = "/del/{deployIds}")
    public AjaxResult delete(@PathVariable String[] deployIds) {
        for (String deployId : deployIds) {
            flowDefinitionService.delete(deployId);
        }
        return AjaxResult.success();
    }

    /**
     * 指定流程办理人员列表
     * 
     * @param user
     * @return
     */
    @GetMapping("/userList")
    public AjaxResult userList(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return AjaxResult.success(list);
    }

    /**
     * 指定流程办理组列表
     * 
     * @param role
     * @return
     */
    @PostMapping("/groupList")
    public AjaxResult groupList(@RequestBody SysFlowGroup flowGroup) {
        List<SysFlowGroup> list = sysFlowGroupService.selectSysFlowGroupList(flowGroup);
        return AjaxResult.success(list);
    }
    
    /**
     * 获取流程定义的第一个UserTask的Id
     * 
     * @param procDefId
     * @return
     */
    @PostMapping("/beginUserTask/{procDefId}")
    public AjaxResult getBeginUserTask(@PathVariable String procDefId) {
    	return AjaxResult.success(flowDefinitionService.getBeginUserTask(procDefId));
    }
	
}

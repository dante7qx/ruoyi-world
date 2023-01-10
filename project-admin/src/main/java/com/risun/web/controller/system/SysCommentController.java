package com.risun.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.risun.common.annotation.Log;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.BusinessType;
import com.risun.common.utils.poi.ExcelUtil;
import com.risun.system.domain.SysComment;
import com.risun.system.domain.SysCommentReply;
import com.risun.system.service.ISysCommentReplyService;
import com.risun.system.service.ISysCommentService;

/**
 * 留言管理Controller
 * 
 * @author sunchao
 * @date 2023-01-05
 */
@RestController
@RequestMapping("/system/comment")
public class SysCommentController extends BaseController {
	@Autowired
	private ISysCommentService sysCommentService;
	@Autowired
	private ISysCommentReplyService sysCommentReplyService;

	/**
	 * 查询留言管理列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(SysComment sysComment) {
		startPage();
		List<SysComment> list = sysCommentService.selectSysCommentList(sysComment);
		return getDataTable(list);
	}
	
	/**
	 * 获取指定业务的留言信息
	 */
	@PostMapping(value = "/biz/{bizModel}/{bizId}")
	public AjaxResult getInfoByBiz(@PathVariable("bizModel") String bizModel, @PathVariable("bizId") Long bizId) {
		return AjaxResult.success(sysCommentService.selectSysCommentByBizModelAndId(bizModel, bizId));
	}

	/**
	 * 导出留言管理列表
	 */
	@Log(title = "留言管理", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, SysComment sysComment) {
		List<SysComment> list = sysCommentService.selectSysCommentList(sysComment);
		ExcelUtil<SysComment> util = new ExcelUtil<SysComment>(SysComment.class);
		util.exportExcel(response, list, "留言管理数据");
	}
	
	/**
	 * 获取留言管理详细信息
	 */
	@GetMapping(value = "/{commentId}")
	public AjaxResult getInfo(@PathVariable("commentId") Long commentId) {
		return AjaxResult.success(sysCommentService.selectSysCommentByCommentId(commentId));
	}

	/**
	 * 新增留言管理
	 */
	@Log(title = "留言管理", businessType = BusinessType.INSERT)
	@PostMapping("/insert")
	public AjaxResult add(@RequestBody SysComment sysComment) {
		return toAjax(sysCommentService.insertSysComment(sysComment));
	}
	
	/**
	 * 新增留言回复
	 */
	@Log(title = "留言管理", businessType = BusinessType.INSERT)
	@PostMapping("/insertReply")
	public AjaxResult addReply(@RequestBody SysCommentReply sysCommentReply) {
		return toAjax(sysCommentReplyService.insertSysCommentReply(sysCommentReply));
	}

	/**
	 * 修改留言管理
	 */
	@Log(title = "留言管理", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	public AjaxResult edit(@RequestBody SysComment sysComment) {
		return toAjax(sysCommentService.updateSysComment(sysComment));
	}

	/**
	 * 删除留言管理
	 */
	@Log(title = "留言管理", businessType = BusinessType.DELETE)
	@PostMapping("/del/{commentIds}")
	public AjaxResult remove(@PathVariable Long[] commentIds) {
		return toAjax(sysCommentService.deleteSysCommentByCommentIds(commentIds));
	}
	
	/**
	 * 删除指定业务的留言管理
	 */
	@Log(title = "留言管理", businessType = BusinessType.DELETE)
	@PostMapping("/del/{bizModel}/{bizId}")
	public AjaxResult removeBizComment(@PathVariable("bizModel") String bizModel, @PathVariable("bizId") Long bizId) {
		return toAjax(sysCommentService.deleteSysCommentByBizModelAndId(bizModel, bizId));
	}
	
	/**
	 * 删除留言回复
	 */
	@Log(title = "留言管理", businessType = BusinessType.DELETE)
	@PostMapping("/delReply/{replyId}")
	public AjaxResult removeReply(@PathVariable Long replyId) {
		return toAjax(sysCommentReplyService.deleteSysCommentReplyByReplyId(replyId));
	}
	
	/**
	 * 用户评论点赞
	 */
	@PostMapping("/userLike/{commentId}")
	public AjaxResult userLike(@PathVariable Long commentId) {
		return toAjax(sysCommentService.userLike(commentId));
	}
}

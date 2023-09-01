package com.risun.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.risun.common.constant.ApprovalFlowConstats;
import com.risun.common.constant.BizModelConstants;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysApprovalLog;
import com.risun.system.domain.SysInfo;
import com.risun.system.domain.SysInfoRange;
import com.risun.system.domain.SysInfoView;
import com.risun.system.mapper.SysInfoMapper;
import com.risun.system.mapper.SysInfoPropMapper;
import com.risun.system.mapper.SysInfoRangeMapper;
import com.risun.system.service.ISysApprovalLogService;
import com.risun.system.service.ISysInfoService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 信息发布Service业务层处理
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@Service
public class SysInfoServiceImpl implements ISysInfoService {
	@Autowired
	private SysInfoMapper sysInfoMapper;
	@Autowired
	private SysInfoRangeMapper sysInfoRangeMapper;
	@Autowired
	private ISysApprovalLogService sysApprovalLogService;
	@Autowired
	private SysInfoPropMapper sysInfoPropMapper;

	/**
	 * 查询信息发布
	 * 
	 * @param infoId 信息发布主键
	 * @return 信息发布
	 */
	@Override
	public SysInfo selectSysInfoByInfoId(Long infoId) {
		return sysInfoMapper.selectSysInfoByInfoId(infoId);
	}

	/**
	 * 查询信息发布4浏览详情
	 * 
	 * @param infoId 信息发布主键
	 * @return 信息发布
	 */
	@Override
	public SysInfo selectSysInfo4ViewByInfoId(Long infoId) {
		SysInfo sysInfo = new SysInfo();
		sysInfo.setInfoId(infoId);
		sysInfo.setLoginDeptId(SecurityUtils.getDeptId());
		return sysInfoMapper.selectSysInfo4ViewByInfoId(sysInfo);
	}

	/**
	 * 查询信息发布列表
	 * 
	 * @param sysInfo 信息发布
	 * @return 信息发布
	 */
	@Override
	public List<SysInfo> selectSysInfoList(SysInfo sysInfo) {
		return sysInfoMapper.selectSysInfoList(sysInfo);
	}

	/**
	 * 查询信息发布浏览列表
	 * 
	 * @param sysInfo 信息发布
	 * @return 信息发布集合
	 */
	@Override
	public List<SysInfo> selectSysInfoList4View(SysInfo sysInfo) {
		sysInfo.setLoginDeptId(SecurityUtils.getDeptId());
		return sysInfoMapper.selectSysInfoList4View(sysInfo);
	}

	/**
	 * 新增信息发布
	 * 
	 * @param sysInfo 信息发布
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertSysInfo(SysInfo sysInfo) {
		sysInfo.setCreateBy(SecurityUtils.getUsername());
		sysInfo.setCreateTime(DateUtils.getNowDate());
		int result = sysInfoMapper.insertSysInfo(sysInfo);
		if (SysInfo.DSP_STATUS.equals(sysInfo.getStatus())) {
			SysApprovalLog log = this.convert2ApprovalLog(sysInfo);
			log.setComment("提交");
			log.setOperateType(ApprovalFlowConstats.ACTION_TJ);
			sysApprovalLogService.insertSysApprovalLog(log);
		}

		return result;
	}

	/**
	 * 修改信息发布
	 * 
	 * @param sysInfo 信息发布
	 * @return 结果
	 */
	@Override
	@Transactional
	public int updateSysInfo(SysInfo sysInfo) {
		sysInfo.setUpdateBy(SecurityUtils.getUsername());
		sysInfo.setUpdateTime(DateUtils.getNowDate());
		int result = sysInfoMapper.updateSysInfo(sysInfo);
		if (SysInfo.DSP_STATUS.equals(sysInfo.getStatus())) {
			SysApprovalLog log = this.convert2ApprovalLog(sysInfo);
			log.setComment("提交");
			log.setOperateType(ApprovalFlowConstats.ACTION_TJ);
			sysApprovalLogService.insertSysApprovalLog(log);
		}
		return result;
	}

	/**
	 * 批量删除信息发布
	 * 
	 * @param infoIds 需要删除的信息发布主键
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysInfoByInfoIds(Long[] infoIds) {
		for (Long infoId : infoIds) {
			sysInfoPropMapper.deleteSysInfoPropByInfoId(infoId);
			sysInfoRangeMapper.deleteSysInfoRangeByInfoId(infoId);
		}
		return sysInfoMapper.deleteSysInfoByInfoIds(infoIds);
	}

	/**
	 * 删除信息发布信息
	 * 
	 * @param infoId 信息发布主键
	 * @return 结果
	 */
	@Override
	@Transactional
	public int deleteSysInfoByInfoId(Long infoId) {
		sysInfoPropMapper.deleteSysInfoPropByInfoId(infoId);
		sysInfoRangeMapper.deleteSysInfoRangeByInfoId(infoId);
		return sysInfoMapper.deleteSysInfoByInfoId(infoId);
	}

	/**
	 * 批量审批
	 * 
	 * @param sysInfo
	 * @return
	 */
	@Override
	@Transactional
	public int batchApproval(SysInfo sysInfo) {
		Long[] ids = sysInfo.getIds();
		boolean approval = sysInfo.getApproval()
			.booleanValue();
		int result = 0;
		for (Long id : ids) {
			SysInfo info = new SysInfo();
			info.setInfoId(id);
			info.setApproval(approval);
			info.setStatus(approval ? SysInfo.PUBLISH_STATUS : SysInfo.DRAFT_STATUS);
			info.setComment(sysInfo.getComment());
			info.setPublishTime(sysInfo.getPublishTime());
			info.setUpdateBy(SecurityUtils.getUsername());
			info.setUpdateTime(DateUtils.getNowDate());
			result += sysInfoMapper.updateSysInfo(info);
			sysApprovalLogService.insertSysApprovalLog(this.convert2ApprovalLog(info));
		}
		return result;
	}

	/**
	 * 审批日志
	 * 
	 * @param sysInfo
	 * @return
	 */
	private SysApprovalLog convert2ApprovalLog(SysInfo sysInfo) {
		SysApprovalLog approvalLog = new SysApprovalLog();
		approvalLog.setBizModel(BizModelConstants.SYS_INFO);
		approvalLog.setBizId(sysInfo.getInfoId());
		approvalLog.setComment(sysInfo.getComment());
		approvalLog.setOperator(SecurityUtils.getLoginUser().getUser().getNickName());
		approvalLog.setOperateTime(DateUtils.getNowDate());
		if (sysInfo.getApproval().booleanValue()) {
			approvalLog.setOperateType(ApprovalFlowConstats.ACTION_PASS);
		} else {
			approvalLog.setOperateType(ApprovalFlowConstats.ACTION_REJECT);
		}

		return approvalLog;
	}

	/**
	 * 置顶信息发布
	 * 
	 * @param sysInfo 信息发布
	 * @return 结果
	 */
	@Override
	@Transactional
	public int setInfoTop(SysInfo sysInfo) {
		sysInfoMapper.cancelSysInfoTop();
		sysInfo.setSetTop(1);
		sysInfo.setUpdateBy(SecurityUtils.getUsername());
		sysInfo.setUpdateTime(DateUtils.getNowDate());
		return sysInfoMapper.updateSysInfo(sysInfo);
	}

	/**
	 * 停用（启用）信息发布
	 * 
	 * @param sysInfo 信息发布
	 * @return 结果
	 */
	@Override
	@Transactional
	public int setInfoDisabled(SysInfo sysInfo) {
		Long[] ids = sysInfo.getIds();
		int result = 0;
		for (Long id : ids) {
			SysInfo info = new SysInfo();
			info.setInfoId(id);
			info.setDisabled(sysInfo.getDisabled());
			info.setUpdateBy(SecurityUtils.getUsername());
			info.setUpdateTime(DateUtils.getNowDate());
			result += sysInfoMapper.disabledSysInfo(info);
		}
		return result;
	}

	/**
	 * 设置（取消）评论信息发布
	 * 
	 * @param sysInfo
	 * @return
	 */
	@Override
	public int setCommentSysInfo(SysInfo sysInfo) {
		return sysInfoMapper.updateSysInfoCommentable(sysInfo.getIds(), sysInfo.getCommentable());
	}

	/**
	 * 设置（取消）匿名访问信息发布
	 * 
	 * @param sysInfo
	 * @return
	 */
	@Override
	@Transactional
	public int setAnonymousSysInfo(SysInfo sysInfo) {
		Long[] ids = sysInfo.getIds();
		int result = 0;
		for (Long id : ids) {
			SysInfo info = new SysInfo();
			info.setInfoId(id);
			info.setAnonymous(sysInfo.getAnonymous());
			info.setUpdateBy(SecurityUtils.getUsername());
			info.setUpdateTime(DateUtils.getNowDate());
			result += sysInfoMapper.setAnonymousSysInfo(info);
//    		清除访问范围
//    		sysInfoRangeMapper.deleteSysInfoRangeByInfoId(id);
		}
		return result;
	}

	/**
	 * 设置访问范围信息发布（设置后匿名访问被取消）
	 * 
	 * @param sysInfo
	 * @return
	 */
	@Override
	@Transactional
	public int setRangeSysInfo(SysInfo sysInfo) {
		Long[] ids = sysInfo.getIds();
		Long[] deptIds = sysInfo.getRangeDeptIds();
		int result = 0;
		for (Long id : ids) {
			sysInfoRangeMapper.deleteSysInfoRangeByInfoId(id);
			for (Long deptId : deptIds) {
				SysInfoRange range = new SysInfoRange();
				range.setInfoId(id);
				range.setDeptId(deptId);
				sysInfoRangeMapper.insertSysInfoRange(range);
			}
			// 取消匿名访问
			SysInfo info = new SysInfo();
			info.setInfoId(id);
			info.setAnonymous(0);
			info.setUpdateBy(SecurityUtils.getUsername());
			info.setUpdateTime(DateUtils.getNowDate());
			result += sysInfoMapper.setAnonymousSysInfo(info);
		}

		return result;
	}

	/**
	 * 根据信息Id获取访问范围
	 * 
	 * @param infoId
	 * @return
	 */
	public Long[] selectInfoRangeByInfoId(Long infoId) {
		List<SysInfoRange> ranges = sysInfoRangeMapper.selectSysInfoRangeByInfoId(infoId);
		return ranges.stream()
			.map(SysInfoRange::getDeptId)
			.toArray(Long[]::new);
	}

	/**
	 * 指定栏目Id下是否有信息发布
	 * 
	 * @param categoryId
	 * @return
	 */
	@Override
	public boolean hasSysInfoByCategoryId(Long categoryId) {
		return sysInfoMapper.hasSysInfoByCategoryId(categoryId);
	}

	/**
	 * 更新信息发布浏览量
	 * 
	 * @param infoId
	 * @param viewIP
	 * @return
	 */
	@Override
	@Async("threadPoolTaskExecutor")
	public void updateSysInfoViewCount(Long infoId, String viewIP) {
		String curDateStr = DateUtils.getDate();
		Date curDate = DateUtil.parseDate(curDateStr);
		sysInfoMapper.deletePrevSysInfoView(curDateStr);

		SysInfoView infoView = new SysInfoView();
		infoView.setInfoId(infoId);
		infoView.setViewDate(curDate);
		infoView.setViewIp(viewIP);
		SysInfoView existView = sysInfoMapper.selectSysInfoViewByInfoView(infoView);
		if (ObjectUtil.isNull(existView)) {
			sysInfoMapper.insertSysInfoView(infoView);
			sysInfoMapper.updateSysInfoViewCount(infoId);
		}
	}

	/**
	 * 收藏
	 * 
	 * @param infoId
	 * @return
	 */
	@Override
	public int updateSysInfoFavorCount(Long infoId) {
		return sysInfoMapper.updateSysInfoFavorCount(infoId);
	}

	/**
	 * 点赞
	 * 
	 * @param infoId
	 * @return
	 */
	@Override
	public int updateSysInfoIncreasePraiseCount(Long infoId) {
		return sysInfoMapper.updateSysInfoIncreasePraiseCount(infoId);
	}

	/**
	 * 取消点赞
	 * 
	 * @param infoId
	 * @return
	 */
	@Override
	public int updateSysInfoDecreasePraiseCount(Long infoId) {
		return sysInfoMapper.updateSysInfoDecreasePraiseCount(infoId);
	}
}

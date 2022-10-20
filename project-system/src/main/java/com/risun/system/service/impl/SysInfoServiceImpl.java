package com.risun.system.service.impl;

import java.util.List;

import com.risun.common.constant.ApprovalFlowConstats;
import com.risun.common.constant.BizModelConstants;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.system.domain.SysApprovalLog;
import com.risun.system.domain.SysInfo;
import com.risun.system.domain.SysInfoRange;
import com.risun.system.mapper.SysInfoMapper;
import com.risun.system.mapper.SysInfoRangeMapper;
import com.risun.system.service.ISysApprovalLogService;
import com.risun.system.service.ISysInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 信息发布Service业务层处理
 * 
 * @author sunchao
 * @date 2022-10-19
 */
@Service
public class SysInfoServiceImpl implements ISysInfoService 
{
    @Autowired
    private SysInfoMapper sysInfoMapper;
    @Autowired
    private SysInfoRangeMapper sysInfoRangeMapper;
    @Autowired
    private ISysApprovalLogService sysApprovalLogService;

    /**
     * 查询信息发布
     * 
     * @param infoId 信息发布主键
     * @return 信息发布
     */
    @Override
    public SysInfo selectSysInfoByInfoId(Long infoId)
    {
        return sysInfoMapper.selectSysInfoByInfoId(infoId);
    }

    /**
     * 查询信息发布列表
     * 
     * @param sysInfo 信息发布
     * @return 信息发布
     */
    @Override
    public List<SysInfo> selectSysInfoList(SysInfo sysInfo)
    {
        return sysInfoMapper.selectSysInfoList(sysInfo);
    }

    /**
     * 新增信息发布
     * 
     * @param sysInfo 信息发布
     * @return 结果
     */
    @Override
    @Transactional
    public int insertSysInfo(SysInfo sysInfo)
    {
        sysInfo.setCreateBy(SecurityUtils.getUsername());
        sysInfo.setCreateTime(DateUtils.getNowDate());
        int result = sysInfoMapper.insertSysInfo(sysInfo);
        if(SysInfo.DSP_STATUS.equals(sysInfo.getStatus())) {
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
    public int updateSysInfo(SysInfo sysInfo)
    {
        sysInfo.setUpdateBy(SecurityUtils.getUsername());
        sysInfo.setUpdateTime(DateUtils.getNowDate());
        int result = sysInfoMapper.updateSysInfo(sysInfo);
        if(SysInfo.DSP_STATUS.equals(sysInfo.getStatus())) {
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
    public int deleteSysInfoByInfoIds(Long[] infoIds)
    {
        return sysInfoMapper.deleteSysInfoByInfoIds(infoIds);
    }

    /**
     * 删除信息发布信息
     * 
     * @param infoId 信息发布主键
     * @return 结果
     */
    @Override
    public int deleteSysInfoByInfoId(Long infoId)
    {
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
    	boolean approval = sysInfo.getApproval().booleanValue();
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
    	if(sysInfo.getApproval().booleanValue()) {
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
    	return ranges.stream().map(SysInfoRange::getDeptId).toArray(Long[]::new);
    }
}

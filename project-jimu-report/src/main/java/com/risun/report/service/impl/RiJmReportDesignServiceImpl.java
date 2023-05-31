package com.risun.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.risun.common.core.domain.model.LoginUser;
import com.risun.common.utils.SecurityUtils;
import com.risun.framework.web.service.TokenService;
import com.risun.report.domain.RiJmReport;
import com.risun.report.mapper.RiJmReportMapper;
import com.risun.report.service.IRiJmReportDesignService;
import com.risun.report.vo.UserInfoVO;

@Service
public class RiJmReportDesignServiceImpl implements IRiJmReportDesignService {
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RiJmReportMapper riJmReportMapper;
	
	/** 报表专员*/
	private static final String REPORT_SPECIALIST = "report_specialist";

	/**
	 * 获取用户详情
	 * 
	 * 系统管理员和报表专员可以编辑报表
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public UserInfoVO getUserInfo(String token) {
		LoginUser loginUser = tokenService.getLoginUser(token);
		if(loginUser == null || !SecurityUtils.getUserId().equals(loginUser.getUserId())) {
			return null;
		}
		long count = loginUser.getUser().getRoles().stream().filter(r -> REPORT_SPECIALIST.equals(r.getRoleKey())).count();
		UserInfoVO vo = new UserInfoVO();
		vo.setUserNickName(loginUser.getUser().getNickName());
		vo.setIsAdmin(SecurityUtils.isAdmin(loginUser.getUserId()));
		vo.setIsReportSpecialist(count > 0);
		return vo;
	}
	
	/**
     * 查询报表设计列表
     * 
     * @param jimuReport
     * @return
     */
    public List<RiJmReport> selectRiJmReportExcel(RiJmReport jimuReport) {
    	jimuReport.setCreateBy(SecurityUtils.getUsername());
    	jimuReport.setType(jimuReport.getReportType());
    	if(SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
    		jimuReport.setDeptId(null);
    	} else {
    		jimuReport.setDeptId(SecurityUtils.getDeptId());
    	}
    	return riJmReportMapper.selectRiJmReportExcel(jimuReport);
    }

}

package com.risun.report.service;

import java.util.List;

import com.risun.report.domain.RiJmReport;
import com.risun.report.vo.UserInfoVO;

public interface IRiJmReportDesignService {
	
	/**
	 * 获取用户详情
	 * 
	 * @param token
	 * @return
	 */
	public UserInfoVO getUserInfo(String token);
	
	/**
     * 查询报表设计列表
     * 
     * @param jimuReport
     * @return
     */
    public List<RiJmReport> selectRiJmReportExcel(RiJmReport jimuReport);
	
}

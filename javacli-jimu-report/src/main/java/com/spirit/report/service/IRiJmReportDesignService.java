package com.spirit.report.service;

import java.util.List;

import com.spirit.report.domain.RiJmReport;
import com.spirit.report.vo.UserInfoVO;

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

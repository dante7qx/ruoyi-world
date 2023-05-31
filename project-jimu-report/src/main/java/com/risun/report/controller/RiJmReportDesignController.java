package com.risun.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.risun.common.core.controller.BaseController;
import com.risun.common.core.text.Convert;
import com.risun.common.utils.ServletUtils;
import com.risun.report.domain.RiJmReport;
import com.risun.report.service.IRiJmReportDesignService;
import com.risun.report.vo.RP;
import com.risun.report.vo.RPage;
import com.risun.report.vo.UserInfoVO;

/**
 * 报表列表Controller
 * 
 * @author sunchao
 * @date 2023-05-18
 */
@RestController
@RequestMapping("/jmreport/design")
public class RiJmReportDesignController extends BaseController {
	
	@Autowired
	private IRiJmReportDesignService riJmReportDesignService;
	
	/**
	 * 获取用户详情
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/userinfo")
	public RP<UserInfoVO> getUserInfo(@RequestParam("token") String token) {
		return RP.ok(riJmReportDesignService.getUserInfo(token));
	}
	
	/**
     * 查询报表列表
     */
    @GetMapping("/excelQuery")
    public RP<RPage> excelQuery(RiJmReport jimuReport)
    {
    	startRPage();
        jimuReport.setTemplate(0);
        return RP.ok(getTablePage(riJmReportDesignService.selectRiJmReportExcel(jimuReport)));
    }
    
    /**
     * 查询报表模板列表
     */
    @GetMapping("/excelQueryByTemplate")
    public RP<RPage> excelQueryByTemplate(RiJmReport jimuReport)
    {
    	startRPage();
        jimuReport.setTemplate(1);
        return RP.ok(getTablePage(riJmReportDesignService.selectRiJmReportExcel(jimuReport)));
    }
	
    void startRPage() {
    	Integer pageNo = Convert.toInt(ServletUtils.getParameter("pageNo"), 1);
        Integer pageSize = Convert.toInt(ServletUtils.getParameter("pageSize"), 10);
        PageHelper.startPage(pageNo, pageSize);
    }
    
    RPage getTablePage(List<RiJmReport> list) {
    	PageInfo<RiJmReport> pageInfo = new PageInfo<>(list);
        RPage page = new RPage();
        page.setRecords(list);
        page.setPageNo(pageInfo.getSize());
        page.setPageSize(pageInfo.getSize());
        page.setPages(pageInfo.getPages());
        page.setTotal(pageInfo.getTotal());
        return page;
    }
    
}

package com.spirit.report.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 * 
 * @author dante
 */
public class RPage implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// {"success":true,"message":"","code":200,"result":{"pageNo":10,"pageSize":10,"total":1,"pages":1,"records":[]},"timestamp":1685416978378}
	
	/** 单页显示数 */
	private long pageNo;
	
	/** 单页显示数 */
	private long pageSize;
	
	/** 当前页码 */
	private long pages;
	
	/** 总记录数 */
	private long total;

	/** 列表数据 */
	private List<?> records;


	/**
	 * 表格数据对象
	 */
	public RPage() {
	}

	/**
	 * 分页
	 * 
	 * @param list  列表数据
	 * @param total 总记录数
	 */
	public RPage(List<?> list, int total) {
		this.records = list;
		this.total = total;
	}

	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	
}

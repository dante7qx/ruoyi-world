package com.risun.common.core.domain.entity;

import javax.validation.constraints.NotBlank;

import com.risun.common.core.domain.BaseEntity;

/**
 * 通用资源下载请求对象
 * 
 * @author dante
 */
public class SysDownload extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 本地资源（文件的路径）
	 */
	@NotBlank(message = "本地资源不能为空")
	private String resource;
	
	/**
	 * 下载文件名（名称+后缀）
	 */
	@NotBlank(message = "下载文件名不能为空")
	private String fileName;

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "SysDownload [resource=" + resource + ", fileName=" + fileName + "]";
	}
	

}

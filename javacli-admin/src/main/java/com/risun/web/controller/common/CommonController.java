package com.risun.web.controller.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.risun.common.config.RisunConfig;
import com.risun.common.constant.Constants;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.domain.entity.SysDownload;
import com.risun.common.utils.ZipDownloadUtil;
import com.risun.common.utils.file.FileUploadUtils;
import com.risun.common.utils.file.FileUtils;
import com.risun.framework.config.ServerConfig;
import com.risun.system.service.ISysAttachmentService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ServerConfig serverConfig;
	@Autowired
	private ISysAttachmentService sysAttachmentService;
	@Autowired
	private MultipartProperties multipartProp;

	private static final String FILE_DELIMETER = ",";
	
	private static final String THUMBNAIL_PREFIX = "thumbnail_";

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@GetMapping("/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				
				throw new Exception(StrUtil.format("文件名称({})非法，不允许下载。 ", fileName));
			}
			String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			String filePath = RisunConfig.getDownloadPath() + fileName;

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, realFileName);
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * 通用上传请求（单个）
	 */
	@PostMapping("/upload/{bizModel}")
	public AjaxResult uploadFile(MultipartFile file, @PathVariable String bizModel) throws Exception {
		Assert.isTrue(!bizModel.equals("_"), "业务模块不能为空！");
		try {
			// 上传文件路径
			String filePath = RisunConfig.getUploadPath();
			return AjaxResult.success(sysAttachmentService.uploadSysAttachment(filePath, bizModel, file, multipartProp.getMaxFileSize().toBytes()));
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 通用上传请求（多个）
	 */
	@PostMapping("/uploads")
	public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
		try {
			// 上传文件路径
			String filePath = RisunConfig.getUploadPath();
			List<String> urls = new ArrayList<String>();
			List<String> fileNames = new ArrayList<String>();
			List<String> newFileNames = new ArrayList<String>();
			List<String> originalFilenames = new ArrayList<String>();
			for (MultipartFile file : files) {
				// 上传并返回新文件名称
				String fileName = FileUploadUtils.upload(filePath, file, multipartProp.getMaxFileSize().toBytes());
				String url = serverConfig.getUrl() + fileName;
				urls.add(url);
				fileNames.add(fileName);
				newFileNames.add(FileUtils.getName(fileName));
				originalFilenames.add(file.getOriginalFilename());
			}
			AjaxResult ajax = AjaxResult.success();
			ajax.put("urls", CollUtil.join(urls, FILE_DELIMETER));
			ajax.put("fileNames", CollUtil.join(fileNames, FILE_DELIMETER));
			ajax.put("newFileNames", CollUtil.join(newFileNames, FILE_DELIMETER));
			ajax.put("originalFilenames", CollUtil.join(originalFilenames, FILE_DELIMETER));
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}
	
	/**
	 * 本地资源通用下载
	 */
	@GetMapping("/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!FileUtils.checkAllowDownload(resource)) {
				throw new Exception(StrUtil.format("资源文件({})非法，不允许下载。 ", resource));
			}
			// 本地资源路径
			String localPath = RisunConfig.getProfile();
			// 数据库资源地址
			String downloadPath = localPath + StrUtil.subAfter(resource, Constants.RESOURCE_PREFIX, false);
			// 下载名称
			String downloadName = StrUtil.subAfter(downloadPath, "/", true);
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			FileUtils.writeBytes(downloadPath, response.getOutputStream());
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}
	
	/**
	 * 通用上传请求（单个）
	 */
	@PostMapping("/upload_only")
	public AjaxResult uploadOnlyFile(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			String filePath = RisunConfig.getUploadPath();
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(filePath, file, multipartProp.getMaxFileSize().toBytes());
			String url = serverConfig.getUrl() + fileName;
			AjaxResult ajax = AjaxResult.success();
			ajax.put("url", url);
			ajax.put("fileName", fileName);
			ajax.put("newFileName", FileUtils.getName(fileName));
			ajax.put("originalFilename", file.getOriginalFilename());
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 上传图片同时生成缩略图
	 */
	@PostMapping("/upload_img_with_thumb")
	public AjaxResult uploadImgWithThumb(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			String profile = RisunConfig.getProfile();
			String filePath = RisunConfig.getUploadPath();
			// 上传并返回新文件名称
			String fileName = FileUploadUtils.upload(filePath, file, multipartProp.getMaxFileSize().toBytes());
			String url = serverConfig.getUrl() + fileName;
			AjaxResult ajax = AjaxResult.success();
			
			String source = profile.concat(StrUtil.subAfter(fileName, Constants.RESOURCE_PREFIX, false));
			String target = source.replaceAll(FileUtils.getName(fileName), THUMBNAIL_PREFIX.concat(FileUtils.getName(fileName)));
			String thumbnailName = fileName.replaceAll(FileUtils.getName(fileName), THUMBNAIL_PREFIX.concat(FileUtils.getName(fileName)));
			// 生成缩略图
			ImgUtil.scale(new File(source), new File(target), 0.5f);
			ajax.put("url", url);
			ajax.put("fileName", fileName);
			ajax.put("newFileName", FileUtils.getName(fileName));
			ajax.put("originalFilename", file.getOriginalFilename());
			ajax.put("thumbnailName", thumbnailName);
			return ajax;
		} catch (Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 本地资源打包成ZIP并下载
	 */
	@PostMapping("/download/resource2zip")
	public void resource2ZipDownload(@Validated SysDownload sysDownload, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String resource = sysDownload.getResource();
			String downloadName = sysDownload.getFileName();
			if (!FileUtils.checkAllowDownload(downloadName)) {
				throw new Exception(StrUtil.format("资源文件({})非法，不允许下载。 ", downloadName));
			}
			// 本地资源路径
			String localPath = RisunConfig.getProfile();
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			ZipDownloadUtil.zip(localPath, resource, response.getOutputStream());
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}
	
}

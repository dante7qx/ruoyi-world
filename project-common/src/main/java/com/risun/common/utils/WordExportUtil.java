package com.risun.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.risun.common.config.RisunConfig;


/**
 * 导出Word工具类
 * 
 * @author dante
 *
 */
public class WordExportUtil {
	
	/** 表格行循环 */
	public static final String LOOP_TABLE_ROW = "loopRows";
	private static final String DOC_TEMPLATE_DIR = "doc_template";
	
	/**
	 * 导出Word
	 * 
	 * @param dataMap
	 * @param templateName
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void createWord(Map<String, Object> dataMap, String templateName,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		createWord(dataMap, templateName, "", "", response);
	}
	
	/**
	 * 导出Word
	 * 
	 * @param dataMap
	 * @param templateName
	 * @param filePath
	 * @param fileName
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void createWord(Map<String, Object> dataMap, String templateName, String filePath, String fileName,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		String templateDir = RisunConfig.getProfile() + "/" + DOC_TEMPLATE_DIR+ "/";;
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");
		
		// 添加插件
		// 表格行循环
		LoopRowTableRenderPolicy loopRowTablePolicy = new LoopRowTableRenderPolicy();
		Configure config = Configure.builder()
				.useSpringEL()
				.bind(WordExportUtil.LOOP_TABLE_ROW, loopRowTablePolicy)
				.build();

		String templatePath = StringUtils.hasText(filePath) ? templateDir.concat(filePath).concat(templateName) : templateDir.concat(templateName);
		XWPFTemplate template = XWPFTemplate.compile(templatePath, config).render(dataMap);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			template.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			PoitlIOUtils.closeQuietlyMulti(template, out);
		}
	}
	/**
	 * 导出Word
	 *
	 * @param dataMap
	 * @param templateName
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void createWordMoreTable(Map<String, Object> dataMap, String templateName,Configure configure,
								  HttpServletResponse response) throws FileNotFoundException, IOException {
		createWordMoreTable(dataMap, templateName, configure,"", "", response);
	}
	/**
	 * 导出Word
	 *
	 * @param dataMap
	 * @param templateName
	 * @param filePath
	 * @param fileName
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void createWordMoreTable(Map<String, Object> dataMap, String templateName,Configure configure, String filePath, String fileName,
								  HttpServletResponse response) throws FileNotFoundException, IOException {
		String templateDir = RisunConfig.getProfile() + "/" + DOC_TEMPLATE_DIR + "/";;
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");

		String templatePath = StringUtils.hasText(filePath) ? templateDir.concat(filePath).concat(templateName) : templateDir.concat(templateName);
		XWPFTemplate template = XWPFTemplate.compile(templatePath, configure).render(dataMap);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			template.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			PoitlIOUtils.closeQuietlyMulti(template, out);
		}
	}
	
}

package com.spirit.common.utils.poitl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.ddr.poi.html.HtmlRenderPolicy;
import org.springframework.util.StringUtils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.AttachmentRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.spirit.common.config.SpiritConfig;
import com.spirit.common.utils.poitl.plugin.LaTeXPolicy;

import lombok.extern.slf4j.Slf4j;


/**
 * 导出Word工具类
 * 
 * @author dante
 *
 */
@Slf4j
public class WordExportUtil {
	
	private static final String DOC_TEMPLATE_DIR = "doc_template";	// 模板根目录
	
	public static final String LOOP_TABLE_ROW = "loopRows";	// 表格行循环
	public static final String HTML_CONTENT = "html4Word"; // HTML内容
	public static final String ATTACHMENT_WORD = "attachment4Word"; // 附件 - Word
	public static final String ATTACHMENT_EXCEL = "attachment4Excel"; // 附件 - Excel
	
	
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
		String templateDir = SpiritConfig.getProfile() + "/" + DOC_TEMPLATE_DIR+ "/";;
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");
		
		Configure config = Configure.builder()
				.useSpringEL()
				.bind(LOOP_TABLE_ROW, new LoopRowTableRenderPolicy())	// 表格行循环
				.bind(HTML_CONTENT, new HtmlRenderPolicy())	// HTML渲染
				.bind(ATTACHMENT_WORD, new AttachmentRenderPolicy())	// 附件渲染
				.bind(ATTACHMENT_EXCEL, new AttachmentRenderPolicy())	// 附件渲染
				.addPlugin('%', new LaTeXPolicy())	// 添加全局数学公式插件				
				.build();
		
		
		String templatePath = StringUtils.hasText(filePath) ? templateDir.concat(filePath).concat(templateName) : templateDir.concat(templateName);
		XWPFTemplate template = XWPFTemplate.compile(templatePath, config).render(dataMap);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			if(out != null) {
				template.write(out);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if(out != null) {
				out.flush();
				PoitlIOUtils.closeQuietlyMulti(template, out);
				out = null;
			}
			
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
		String templateDir = SpiritConfig.getProfile() + "/" + DOC_TEMPLATE_DIR + "/";;
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
			log.error(e.getMessage(), e);
		} finally {
			if(out != null) {
				out.flush();
				PoitlIOUtils.closeQuietlyMulti(template, out);
				out = null;
			}
		}
	}
	
}

package com.risun.common.utils.poitl.plugin;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import fmath.conversion.c.a;
import lombok.extern.slf4j.Slf4j;



/**
 * 数学公式插件
 * 
 * @author dante
 *
 */
@Slf4j
public class LaTeXPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
		XWPFRun run = ((RunTemplate) eleTemplate).getRun(); 

		// 将 data 转成 OMath 格式
		String mathMlContent = String.valueOf(data);
		if(StrUtil.isEmpty(mathMlContent)) {
			return;
		}
	    CTOMath math = CTOMath.Factory.newInstance();
	    try {
			math.set(CTOMath.Factory.parse(mathML2OOXml(mathMlContent)));
			run.getCTR().set(math);
		} catch (XmlException e) {
			log.error("MathML转换OOXml异常", e);
		}
	}
	
	private String mathML2OOXml(String mathml) {
		String formatMathml = ReUtil.replaceAll(mathml, "<math\\s[^>]*>", "<math>");
		String openXML = a.a(formatMathml);
		String header = "<m:oMathPara xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\">";
		openXML = header + openXML + "</m:oMathPara>";
		return openXML;
	}

}

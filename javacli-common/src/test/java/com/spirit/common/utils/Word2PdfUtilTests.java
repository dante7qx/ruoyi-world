package com.spirit.common.utils;

import org.junit.jupiter.api.Test;

import com.spirit.common.utils.Word2PdfUtil;

public class Word2PdfUtilTests {
	
	@Test
	public void wordToPdf() {
		Word2PdfUtil.wordToPdf("/home/测试.docx", "/home/测试.pdf");
	}
	
}

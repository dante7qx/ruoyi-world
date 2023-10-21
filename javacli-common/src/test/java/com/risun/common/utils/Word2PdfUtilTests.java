package com.risun.common.utils;

import org.junit.jupiter.api.Test;

public class Word2PdfUtilTests {
	
	@Test
	public void wordToPdf() {
		Word2PdfUtil.wordToPdf("/home/测试.docx", "/home/测试.pdf");
	}
	
}

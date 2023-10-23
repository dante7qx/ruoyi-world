package com.spirit.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import lombok.SneakyThrows;

/**
 * Word 转 Pdf 工具类
 * 
 * @author dante
 *
 */
public class Word2PdfUtil {
	
	private static final String licenseStr="<License>\n" +
            "    <Data>\n" +
            "        <Products>\n" +
            "            <Product>Aspose.Total for Java</Product>\n" +
            "            <Product>Aspose.Words for Java</Product>\n" +
            "        </Products>\n" +
            "        <EditionType>Enterprise</EditionType>\n" +
            "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
            "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
            "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
            "    </Data>\n" +
            "    <Signature>\n" +
            "        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=\n" +
            "    </Signature>\n" +
            "</License>";
	
	/**
     * 加载license 用于破解 不生成水印
     *
     * @author yangxiaolong
     */
    @SneakyThrows
    private static void getLicense() {
        InputStream result = new ByteArrayInputStream(licenseStr.getBytes(StandardCharsets.UTF_8));
        License license = new License();
        license.setLicense(result);
    }

    /**
     * word转pdf
     *
     * @param wordPath word文件保存的路径
     * @param pdfPath  转换后pdf文件保存的路径
     * @author yangxiaolong
     */
    @SneakyThrows
    public static void wordToPdf(String wordPath, String pdfPath) {
        getLicense();
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordPath);
            doc.save(os, SaveFormat.PDF);
        }
    }
    
    /**
     * word转pdf
     * 
     * @param wordStream word文件流
     * @param pdfPath 转换后pdf文件保存的路径
     */
    @SneakyThrows
    public static void wordToPdf(InputStream wordStream, String pdfPath) {
        getLicense();
        File file = new File(pdfPath);
        try (FileOutputStream os = new FileOutputStream(file)) {
            Document doc = new Document(wordStream);
            doc.save(os, SaveFormat.PDF);
        }
    }
    
    /**
     * word转pdf
     * 
     * @param wordStream word文件流
     * @param pdfStream 转换后pdf文件输出流
     */
    @SneakyThrows
    public static void wordToPdf(InputStream wordStream, OutputStream pdfStream) {
        getLicense();
        Document doc = new Document(wordStream);
        doc.save(pdfStream, SaveFormat.PDF);
    }
	
}

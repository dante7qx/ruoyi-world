package com.risun.common.utils;

import java.awt.Color;

import com.risun.common.config.RisunConfig;
import com.risun.common.constant.Constants;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;

/**
 * 二维码工具类
 * 
 * @author dante
 *
 */
public class QrCodeUtils {
	
    private static String baseDir = RisunConfig.getProfile();
    
    private static String parentDir = "qrcode";
    
    private static String suffix = ".jpg";
    
	private static int imgWidth = 300;
	
	private static int imgHeight = 300;
	
	private static int margin = 0;
	
	/**
	 * 生成二维码
	 * 
	 * @param content 二维码内容
	 * @return
	 */
	public static String generate(String content) {
		return generate(content, imgWidth, imgHeight);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content 二维码内容
	 * @param foreColor 二维码前置色
	 * @return
	 */
	public static String generate(String content, Color foreColor) {
		return generate(content, imgWidth, imgHeight, foreColor);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content 二维码内容
	 * @param width	  二维码宽度
	 * @param height  二维码高度
	 * @return
	 */
	public static String generate(String content, int width, int height) {
		return generate(content, width, height, null, null);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content	二维码内容
	 * @param width		二维码宽度
	 * @param height	二维码高度
	 * @param foreColor 二维码前置色
	 * @return
	 */
	public static String generate(String content, int width, int height, Color foreColor) {
		return generate(content, imgWidth, imgHeight, foreColor, null);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content	二维码内容
	 * @param logoPath	二维码Logo路径
	 * @return
	 */
	public static String generate(String content, String logoPath) {
		return generate(content, imgWidth, imgHeight, logoPath);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content 二维码内容
	 * @param width	  二维码宽度
	 * @param height  二维码高度
	 * @param logoPath 二维码Logo路径
	 * @return
	 */
	public static String generate(String content, int width, int height, String logoPath) {
		return generate(content, width, height, null, logoPath);
	}
	
	/**
	 * 生成二维码
	 * 
	 * @param content 二维码内容
	 * @param width	  二维码宽度
	 * @param height  二维码高度
	 * @param foreColor 二维码前置色
	 * @param logoPath 二维码Logo路径
	 * @return
	 */
	public static String generate(String content, int width, int height, Color foreColor, String logoPath) {
		String fileName = DateUtils.dateTimeNow() + suffix;
		String filePath = baseDir + "/" + parentDir + "/" + fileName;
		QrConfig config = new QrConfig(width, height);
		config.setMargin(margin);
		if(foreColor != null) {
			config.setForeColor(foreColor);
		}
		if(StrUtil.isNotEmpty(logoPath)) {
			config.setImg(logoPath);
			config.setRatio(5);
			QrCodeUtil.generate(content, config, FileUtil.newFile(filePath));
		} else {
			QrCodeUtil.generate(content, config, FileUtil.newFile(filePath));
		}
		return Constants.RESOURCE_PREFIX + "/" + parentDir + "/" + fileName;
	}
	
}

package com.risun.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.risun.common.constant.Constants;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 资源打包为 ZIP 并下载
 * 
 * @author dante
 *
 */
@Slf4j
public class ZipDownloadUtil {

	public static void zip(String baseUrl, String resource, OutputStream outputStream) {
		String[] resourceArr = resource.split(",");
		int len = resourceArr.length;
		String[] fileNameArr = new String[len];
		InputStream[] insArr = new FileInputStream[len];

		for (int i = 0; i < len; i++) {
			fileNameArr[i] = FileUtil.getName(resourceArr[i]);
			insArr[i] = getFileInputStream(
					baseUrl + StrUtil.subAfter(resourceArr[i], Constants.RESOURCE_PREFIX, false));
		}
		ZipUtil.zip(outputStream, fileNameArr, insArr);
	}

	private static FileInputStream getFileInputStream(String filePath) {
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}
			fis = new FileInputStream(file);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return fis;
	}
}

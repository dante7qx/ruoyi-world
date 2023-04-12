package com.risun.common.utils.file;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.risun.common.config.RisunConfig;
import com.risun.common.constant.Constants;
import com.risun.common.exception.file.FileNameInvalidException;
import com.risun.common.exception.file.FileNameLengthLimitExceededException;
import com.risun.common.exception.file.FileSizeLimitExceededException;
import com.risun.common.exception.file.InvalidExtensionException;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.uuid.Seq;

import cn.hutool.core.util.StrUtil;

/**
 * 文件上传工具类
 *
 * @author ruoyi
 */
public class FileUploadUtils
{
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50L * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = RisunConfig.getProfile();
    
    private static final String FILE_DELIMETER = ",";

    public static void setDefaultBaseDir(String defaultBaseDir)
    {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir()
    {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static final String upload(MultipartFile file, long allowMaxFileSize) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, allowMaxFileSize);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file, long allowMaxFileSize) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, allowMaxFileSize);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static final String upload(String baseDir, MultipartFile file, String[] allowedExtension, long allowMaxFileSize)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException  {
    	if(file.getOriginalFilename().contains(FILE_DELIMETER)) {
    		throw new FileNameInvalidException(FILE_DELIMETER);
    	}
    	
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension, allowMaxFileSize);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file) {
        return StrUtil.format("{}/{}_{}.{}", DateUtils.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), getExtension(file));
    }
    

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = RisunConfig.getProfile().length() + 1;
        String currentDir = uploadDir.substring(dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension, long allowMaxFileSize)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
    	long size = file.getSize();
    	long maxFileSize = allowMaxFileSize > 0 ? allowMaxFileSize : DEFAULT_MAX_SIZE;
        if (size > maxFileSize)
        {
            throw new FileSizeLimitExceededException(maxFileSize / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StrUtil.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }
    
    /**
     * 将文件大小转换为字符串 1024 转换成1kB，1024*1024 转换为MB(保留2为小数)
     * 
     * @param fileSize
     * @return
     */
    public static String convertFileSizeToStr(Long fileSize) {
       if (fileSize <= 0) {
          return "0B";
       } else if (fileSize < 1024) {
          return fileSize + "B";
       } else if (fileSize < 1024 * 1024) {
          float size = Float.parseFloat(String.valueOf(fileSize)) / 1024;
          BigDecimal b = BigDecimal.valueOf(size);
          // 2表示2位 ROUND_HALF_UP表明四舍五入，
          size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
          return size + "KB";
       } else if (fileSize < 1024 * 1024 * 1024) {
          float size = Float.parseFloat(String.valueOf(fileSize)) / (1024 * 1024);
          BigDecimal b = BigDecimal.valueOf(size);
          // 2表示2位 ROUND_HALF_UP表明四舍五入，
          size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
          return size + "MB";
       } else {
          float size = Float.parseFloat(String.valueOf(fileSize)) / (1024 * 1024 * 1024);
          BigDecimal b = BigDecimal.valueOf(size);
          // 2表示2位 ROUND_HALF_UP表明四舍五入，
          size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
          return size + "GB";
       }
    }
}

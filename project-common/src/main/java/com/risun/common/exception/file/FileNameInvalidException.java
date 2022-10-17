package com.risun.common.exception.file;

/**
 * 文件名含有非法字符
 * 
 * @author dante
 */
public class FileNameInvalidException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameInvalidException(String invaildCharacter)
    {
        super("upload.filename.invaild.character", new Object[] { invaildCharacter });
    }
}

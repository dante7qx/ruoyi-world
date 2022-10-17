package com.risun.system.service;

import java.io.IOException;
import java.util.List;

import com.risun.system.domain.SysAttachment;

import org.springframework.web.multipart.MultipartFile;

/**
 * 业务附件Service接口
 * 
 * @author sunchao
 * @date 2022-10-14
 */
public interface ISysAttachmentService 
{
    /**
     * 查询业务附件
     * 
     * @param attachId 业务附件主键
     * @return 业务附件
     */
    public SysAttachment selectSysAttachmentByAttachId(Long attachId);
    
    /**
     * 根据附件URL查询业务附件
     * 
     * @param fileUrl
     * @return
     */
    public SysAttachment selectSysAttachmentByFileUrl(String fileUrl);

    /**
     * 查询业务附件列表
     * 
     * @param sysAttachment 业务附件
     * @return 业务附件集合
     */
    public List<SysAttachment> selectSysAttachmentList(SysAttachment sysAttachment);

    /**
     * 新增业务附件
     * 
     * @param sysAttachment 业务附件
     * @return 结果
     */
    public int insertSysAttachment(SysAttachment sysAttachment);

    /**
     * 修改业务附件
     * 
     * @param sysAttachment 业务附件
     * @return 结果
     */
    public int updateSysAttachment(SysAttachment sysAttachment);

    /**
     * 批量删除业务附件
     * 
     * @param attachIds 需要删除的业务附件主键集合
     * @return 结果
     */
    public int deleteSysAttachmentByAttachIds(Long[] attachIds);

    /**
     * 删除业务附件信息
     * 
     * @param attachId 业务附件主键
     * @return 结果
     */
    public int deleteSysAttachmentByAttachId(Long attachId);
    
    /**
     * 上传业务附件
     * 
     * @param file 业务附件
     * @return SysAttachment
     */
    public SysAttachment uploadSysAttachment(String rootDir, String bizModel, MultipartFile file) throws IOException;
}

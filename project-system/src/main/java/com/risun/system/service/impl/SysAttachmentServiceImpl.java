package com.risun.system.service.impl;

import java.io.IOException;
import java.util.List;

import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.common.utils.file.FileUploadUtils;
import com.risun.system.domain.SysAttachment;
import com.risun.system.mapper.SysAttachmentMapper;
import com.risun.system.service.ISysAttachmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 业务附件Service业务层处理
 * 
 * @author sunchao
 * @date 2022-10-14
 */
@Service
public class SysAttachmentServiceImpl implements ISysAttachmentService {
	
    @Autowired
    private SysAttachmentMapper sysAttachmentMapper;

    /**
     * 查询业务附件
     * 
     * @param attachId 业务附件主键
     * @return 业务附件
     */
    @Override
    public SysAttachment selectSysAttachmentByAttachId(Long attachId)
    {
        return sysAttachmentMapper.selectSysAttachmentByAttachId(attachId);
    }
    
    /**
     * 根据附件URL查询业务附件
     * 
     * @param fileUrl
     * @return
     */
    @Override
    public SysAttachment selectSysAttachmentByFileUrl(String fileUrl) {
    	return sysAttachmentMapper.selectSysAttachmentByFileUrl(fileUrl);
    }	

    /**
     * 查询业务附件列表
     * 
     * @param sysAttachment 业务附件
     * @return 业务附件
     */
    @Override
    public List<SysAttachment> selectSysAttachmentList(SysAttachment sysAttachment)
    {
        return sysAttachmentMapper.selectSysAttachmentList(sysAttachment);
    }

    /**
     * 新增业务附件
     * 
     * @param sysAttachment 业务附件
     * @return 结果
     */
    @Override
    public int insertSysAttachment(SysAttachment sysAttachment)
    {
        sysAttachment.setCreateBy(SecurityUtils.getUsername());
        sysAttachment.setCreateTime(DateUtils.getNowDate());
        return sysAttachmentMapper.insertSysAttachment(sysAttachment);
    }

    /**
     * 修改业务附件
     * 
     * @param sysAttachment 业务附件
     * @return 结果
     */
    @Override
    public int updateSysAttachment(SysAttachment sysAttachment)
    {
        sysAttachment.setUpdateBy(SecurityUtils.getUsername());
        sysAttachment.setUpdateTime(DateUtils.getNowDate());
        return sysAttachmentMapper.updateSysAttachment(sysAttachment);
    }

    /**
     * 批量删除业务附件
     * 
     * @param attachIds 需要删除的业务附件主键
     * @return 结果
     */
    @Override
    public int deleteSysAttachmentByAttachIds(Long[] attachIds)
    {
        return sysAttachmentMapper.deleteSysAttachmentByAttachIds(attachIds);
    }

    /**
     * 删除业务附件信息
     * 
     * @param attachId 业务附件主键
     * @return 结果
     */
    @Override
    public int deleteSysAttachmentByAttachId(Long attachId)
    {
        return sysAttachmentMapper.deleteSysAttachmentByAttachId(attachId);
    }

    /**
     * 上传业务附件
     * 
     * @param file
     */
	@Override
	public SysAttachment uploadSysAttachment(String rootDir, String bizModel, MultipartFile file, long allowMaxFileSize) throws IOException {
		try
        {
			String fileFullName = FileUploadUtils.upload(rootDir, file, allowMaxFileSize);
			SysAttachment attachment = new SysAttachment();
			attachment.setBizModel(bizModel);
			attachment.setFileName(file.getOriginalFilename());
			attachment.setFileUrl(fileFullName);
			attachment.setFileSuffix(FileUploadUtils.getExtension(file));
			attachment.setFileSize(FileUploadUtils.convertFileSizeToStr(file.getSize()));
			attachment.setCreateBy(SecurityUtils.getUsername());
			attachment.setCreateTime(DateUtils.getNowDate());
			sysAttachmentMapper.insertSysAttachment(attachment);
            return attachment;
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
	}
}

package com.risun.biz.demo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepoove.poi.data.AttachmentRenderData;
import com.deepoove.poi.data.AttachmentType;
import com.deepoove.poi.data.Attachments;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.risun.biz.demo.domain.Demo;
import com.risun.biz.demo.mapper.DemoMapper;
import com.risun.biz.demo.service.IDemoService;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;
import com.risun.common.utils.file.FileUtils;
import com.risun.common.utils.file.ImageUtils;
import com.risun.common.utils.poitl.WordExportUtil;
import com.risun.system.service.ISysConfigService;

import cn.hutool.core.util.StrUtil;

/**
 * 业务Service业务层处理
 * 
 * @author sunchao
 * @date 2022-07-30
 */
@Service
public class DemoServiceImpl implements IDemoService 
{
    @Autowired
    private DemoMapper demoMapper;
    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 查询业务
     * 
     * @param demoId 业务主键
     * @return 业务
     */
    @Override
    public Demo selectDemoByDemoId(Long demoId)
    {
        return demoMapper.selectDemoByDemoId(demoId);
    }

    /**
     * 查询业务列表
     * 
     * @param demo 业务
     * @return 业务
     */
    @Override
    public List<Demo> selectDemoList(Demo demo)
    {
        return demoMapper.selectDemoList(demo);
    }

    /**
     * 新增业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int insertDemo(Demo demo)
    {
        demo.setCreateBy(SecurityUtils.getUsername());
        demo.setCreateTime(DateUtils.getNowDate());
        return demoMapper.insertDemo(demo);
    }

    /**
     * 修改业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int updateDemo(Demo demo)
    {
        demo.setUpdateBy(SecurityUtils.getUsername());
        demo.setUpdateTime(DateUtils.getNowDate());
        return demoMapper.updateDemo(demo);
    }

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoIds(Long[] demoIds)
    {
        return demoMapper.deleteDemoByDemoIds(demoIds);
    }

    /**
     * 删除业务信息
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoId(Long demoId)
    {
        return demoMapper.deleteDemoByDemoId(demoId);
    }
    
    /**
     * 导出Word
     * 
     * @param demoId
     * @return
     */
    @Override
    public Map<String, Object> export4Word(Long demoId) {
    	final Map<String, Object> map = Maps.newHashMap();
		Demo data = demoMapper.selectDemoByDemoId(demoId);
		map.put("data", data);
		
		// 富文本内容，系统访问地址请在系统参数中进行设置，参数键为 sys.visit.baseurl
		if(StrUtil.isNotEmpty(data.getDemoContent())) {
			map.put(WordExportUtil.HTML_CONTENT, ImageUtils.replaceImgSrc(data.getDemoContent(), sysConfigService.selectConfigByKey("sys.visit.baseurl")));
		} else {
			map.put(WordExportUtil.HTML_CONTENT, "");
		}
		
		// 多张图片
		String demoImage = data.getDemoImage();
		if(StrUtil.isNotEmpty(demoImage)) {
			List<PictureRenderData> images = Lists.newLinkedList();
			List<String> imageArr = StrUtil.split(demoImage, ",");
			for (String imgPath : imageArr) {
				images.add(Pictures.ofStream(ImageUtils.getFile(imgPath), PictureType.JPEG).size(100, 100).create());
			}
			map.put("demoImg", images);
		} else {
			map.put("demoImg", null);
		}
		
		// 附件（支持Word、Excel）
		String attachment = data.getAttachment();
		if(StrUtil.isNotEmpty(attachment)) {
			String localPath = FileUtils.getLocalPath(attachment);
			AttachmentRenderData attach = Attachments.ofLocal(localPath, AttachmentType.DOCX).create();
			data.setAttachment(FileUtils.getNameNotDateSuffix(attachment));
			map.put(WordExportUtil.ATTACHMENT_WORD, attach);
		} else {
			map.put(WordExportUtil.ATTACHMENT_WORD, null);
		}
		
		// 表格行循环
		List<Demo> demos = demoMapper.selectDemoList(new Demo());
		map.put(WordExportUtil.LOOP_TABLE_ROW, demos);
		return map;
    }
}

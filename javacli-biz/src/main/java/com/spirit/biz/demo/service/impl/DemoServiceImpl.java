package com.spirit.biz.demo.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepoove.poi.data.AttachmentRenderData;
import com.deepoove.poi.data.AttachmentType;
import com.deepoove.poi.data.Attachments;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spirit.biz.demo.domain.Demo;
import com.spirit.biz.demo.mapper.DemoMapper;
import com.spirit.biz.demo.service.IDemoService;
import com.spirit.common.annotation.DesensitizeMethod;
import com.spirit.common.constant.Constants;
import com.spirit.common.enums.GlobalArgConfigEnum;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;
import com.spirit.common.utils.file.FileUtils;
import com.spirit.common.utils.file.ImageUtils;
import com.spirit.common.utils.poitl.WordExportUtil;
import com.spirit.system.service.ISysConfigService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 业务Service业务层处理
 * 
 * @author sunchao
 * @date 2022-07-30
 */
/**
 * 业务Service业务层处理
 * 
 * @author sunchao
 * @date 2023-05-04
 */
@Service
public class DemoServiceImpl implements IDemoService {
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
    public Demo selectDemoByDemoId(Long demoId) {
        return demoMapper.selectById(demoId);
    }

    /**
     * 查询业务列表
     * 
     * @param demo 业务
     * @return 业务
     */
    @Override
    public List<Demo> selectDemoList(Demo demo) {
    	MPJLambdaWrapper<Demo> mpj = new MPJLambdaWrapper<Demo>()
                .selectAll(Demo.class)
                .select("sysrole.role_name")
                .select("syspost.post_name")
                .leftJoin("sys_role sysrole on sysrole.role_id = t.role_id")
                .leftJoin("sys_post syspost on syspost.post_id = t.post_id")
                .eq(Demo::getDelFlag, 0)
                .like(StrUtil.isNotEmpty(demo.getDemoName()), Demo::getDemoName, demo.getDemoName())
                .eq(demo.getDemoTime() != null, Demo::getDemoTime, demo.getDemoTime())
                .eq(StrUtil.isNotEmpty(demo.getDemoImage()), Demo::getDemoImage, demo.getDemoImage())
                .eq(StrUtil.isNotEmpty(demo.getAttachment()), Demo::getAttachment, demo.getAttachment())
                .eq(StrUtil.isNotEmpty(demo.getDemoContent()), Demo::getDemoContent, demo.getDemoContent())
                .like(demo.getRoleId() != null, Demo::getRoleId, demo.getRoleId())
                .like(StrUtil.isNotEmpty(demo.getRoleName()), "sysrole.role_name", demo.getRoleName())
                .like(demo.getPostId() != null, Demo::getPostId, demo.getPostId())
                .like(StrUtil.isNotEmpty(demo.getPostName()), "syspost.post_name", demo.getPostName())
            ;
            return demoMapper.selectList(mpj);
    }

    /**
     * 新增业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int insertDemo(Demo demo) {
        demo.setCreateBy(SecurityUtils.getUsername());
        demo.setCreateTime(DateUtils.getNowDate());
        return demoMapper.insert(demo);
    }

    /**
     * 修改业务
     * 
     * @param demo 业务
     * @return 结果
     */
    @Override
    public int updateDemo(Demo demo) {
        demo.setUpdateBy(SecurityUtils.getUsername());
        demo.setUpdateTime(DateUtils.getNowDate());
        return demoMapper.updateById(demo);
    }

    /**
     * 批量删除业务
     * 
     * @param demoIds 需要删除的业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoIds(Long[] demoIds) {
        return demoMapper.deleteDemoByDemoIds(demoIds, SecurityUtils.getUsername());
    }

    /**
     * 删除业务信息
     * 
     * @param demoId 业务主键
     * @return 结果
     */
    @Override
    public int deleteDemoByDemoId(Long demoId) {
        return demoMapper.deleteDemoByDemoId(demoId, SecurityUtils.getUsername());
    }
    
    /**
     * 批量新增
     * 
     * @param demos
     * @return
     */
    @Override
    @DesensitizeMethod
    public int batchInsertDemo(List<Demo> demos) {
    	return demoMapper.batchInsertDemo(demos);
    }
    
    /**
     * 清空数据
     * 
     * @return
     */
    @Override
    public int clearDemoData() {
    	int result = 1;
    	demoMapper.clearDemoData();
    	return result;
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
		Demo data = demoMapper.selectById(demoId);
		map.put("data", data);
		
		// 富文本内容，系统访问地址请在系统参数中进行设置，参数键为 sys.visit.baseurl
		if(StrUtil.isNotEmpty(data.getDemoContent())) {
			map.put(WordExportUtil.HTML_CONTENT, ImageUtils.replaceImgSrc(data.getDemoContent(), sysConfigService.selectConfigByKey(GlobalArgConfigEnum.BASE_URL.key())));
		} else {
			map.put(WordExportUtil.HTML_CONTENT, "");
		}
		
		// 多张图片
		List<PictureRenderData> images = null;
		String demoImage = data.getDemoImage();
		if(StrUtil.isNotEmpty(demoImage)) {
			images = Lists.newLinkedList();
			List<String> imageArr = StrUtil.split(demoImage, ",");
			for (String imgPath : imageArr) {
				images.add(Pictures.ofStream(ImageUtils.getFile(imgPath), PictureType.JPEG).size(100, 100).create());
			}
		} 
		map.put("demoImg", images);
		// 数学公式
		map.put("fnTrig", "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">  <msup>    <mtext>cos</mtext>    <mrow>      <mn>2</mn>    </mrow>  </msup>  <mfrac>    <mi>&#x3B1;</mi>    <mn>2</mn>  </mfrac>  <mo>=</mo>  <mfrac>    <mrow>      <mn>1</mn>      <mo>+</mo>      <mtext>cos</mtext>      <mi>&#x3B1;</mi>    </mrow>    <mn>2</mn>  </mfrac>  <mi>cos</mi>  <mo data-mjx-texclass=\"NONE\">&#x2061;</mo>  <mi>&#x3B1;</mi>  <mo>+</mo>  <mi>cos</mi>  <mo data-mjx-texclass=\"NONE\">&#x2061;</mo>  <mi>&#x3B2;</mi>  <mo>=</mo>  <mn>2</mn>  <mi>cos</mi>  <mo data-mjx-texclass=\"NONE\">&#x2061;</mo>  <mfrac>    <mrow>      <mi>&#x3B1;</mi>      <mo>+</mo>      <mi>&#x3B2;</mi>    </mrow>    <mn>2</mn>  </mfrac>  <mi>cos</mi>  <mo data-mjx-texclass=\"NONE\">&#x2061;</mo>  <mfrac>    <mrow>      <mi>&#x3B1;</mi>      <mo>&#x2212;</mo>      <mi>&#x3B2;</mi>    </mrow>    <mn>2</mn>  </mfrac></math>");
		
		// 附件（支持Word、Excel）
		AttachmentRenderData attach = null;
		String attachment = data.getAttachment();
		if(StrUtil.isNotEmpty(attachment)) {
			data.setAttachment(FileUtils.getNameNotDateSuffix(attachment));
			String localPath = FileUtils.getLocalPath(attachment);
			String suffix = FileNameUtil.getSuffix(localPath);
			if(Constants.DOCX_SUFFIX.equals(suffix)) {
				attach = Attachments.ofLocal(localPath, AttachmentType.DOCX).create();
			} else if(Constants.XLSX_SUFFIX.equals(suffix)) {
				attach = Attachments.ofLocal(localPath, AttachmentType.XLSX).create();
			}
		}
		map.put(WordExportUtil.ATTACHMENT_WORD, attach);
		
		// 表格行循环
		List<Demo> demos = demoMapper.selectList(null);
		map.put(WordExportUtil.LOOP_TABLE_ROW, demos);
		return map;
    }
    
    /**
     * 导入业务
     * 
     * @param demoList 待导入数据
     * @return 结果
     */
    @Override
    @Transactional
    public String importDemo(List<Demo> demoList) {
    	StringBuilder result = new StringBuilder();
    	if(CollUtil.isEmpty(demoList)) {
    		result.append("导入数据为空！");
    		return result.toString();
    	}
    	
    	// 找出合规的数据 
    	List<Demo> validDemos = demoList;
        validDemos = demoList.stream()
    			.filter(t -> ObjectUtil.isNotEmpty(t.getDemoName()) && ObjectUtil.isNotEmpty(t.getDemoTime()))
    			.collect(toList());
    	int invalidSize = demoList.size() - validDemos.size();
    	
    	
    	// 找出所有已存在的数据（此方法可根据业务进行优化）
    	List<Demo> allDemos = this.selectDemoList(new Demo());
    	// 找出要更新的数据
    	List<Demo> updateDemos = validDemos.stream()
                .filter(t1 -> allDemos.stream().anyMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDemoTime().equals(t2.getDemoTime())))
                .peek(t -> {
                    t.setUpdateBy(SecurityUtils.getUsername());
                    t.setUpdateTime(DateUtils.getNowDate());
                })
                .collect(toList());
    	// 找出新增数据
        List<Demo> insertDemos = validDemos.stream()
                .filter(t1 -> allDemos.stream().noneMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDemoTime().equals(t2.getDemoTime())))
                .peek(t -> {
                    t.setCreateBy(SecurityUtils.getUsername());
                    t.setCreateTime(DateUtils.getNowDate());
                })
                .collect(toList());
        
        if(CollUtil.isNotEmpty(insertDemos)) {
        	demoMapper.batchInsertDemo(insertDemos);
        }
        if(CollUtil.isNotEmpty(updateDemos)) {
        	demoMapper.batchUpdateDemo(updateDemos);
        }
        
        result.append("待导入数据: ").append(demoList.size()).append("条数据，");
        if(invalidSize > 0) {
        	result.append("<br/>无效数据: ").append(invalidSize).append("条数据，");
        }
        
    	result.append("<br/>新增数据: ").append(insertDemos.size()).append("条数据，")
    		.append("<br/>更新数据: ").append(updateDemos.size()).append("条数据。");
        
    	return result.toString();
    }
    
}

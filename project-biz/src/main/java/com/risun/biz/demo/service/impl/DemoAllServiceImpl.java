package com.risun.biz.demo.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.risun.biz.demo.domain.DemoAll;
import com.risun.biz.demo.mapper.DemoAllMapper;
import com.risun.biz.demo.service.IDemoAllService;
import com.risun.common.annotation.DataScope;
import com.risun.common.utils.DateUtils;
import com.risun.common.utils.SecurityUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 全部业务功能Service业务层处理
 * 
 * @author sunchao
 * @date 2023-09-08
 */
@Service
public class DemoAllServiceImpl implements IDemoAllService {
    @Autowired
    private DemoAllMapper demoAllMapper;

    /**
     * 查询全部业务功能
     * 
     * @param demoId 全部业务功能主键
     * @return 全部业务功能
     */
    @Override
    public DemoAll selectDemoAllByDemoId(Long demoId) {
        return demoAllMapper.selectDemoAllByDemoId(demoId);
    }

    /**
     * 查询全部业务功能列表
     * 
     * @param demoAll 全部业务功能
     * @return 全部业务功能
     */
    @Override
    @DataScope(deptAlias = "t")
    public List<DemoAll> selectDemoAllList(DemoAll demoAll) {
        return demoAllMapper.selectDemoAllList(demoAll);
    }

    /**
     * 新增全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    @Override
    public int insertDemoAll(DemoAll demoAll) {
        demoAll.setCreateBy(SecurityUtils.getUsername());
        demoAll.setCreateTime(DateUtils.getNowDate());
        return demoAllMapper.insertDemoAll(demoAll);
    }

    /**
     * 修改全部业务功能
     * 
     * @param demoAll 全部业务功能
     * @return 结果
     */
    @Override
    public int updateDemoAll(DemoAll demoAll) {
        demoAll.setUpdateBy(SecurityUtils.getUsername());
        demoAll.setUpdateTime(DateUtils.getNowDate());
        return demoAllMapper.updateDemoAll(demoAll);
    }

    /**
     * 批量删除全部业务功能
     * 
     * @param demoIds 需要删除的全部业务功能主键
     * @return 结果
     */
    @Override
    public int deleteDemoAllByDemoIds(Long[] demoIds) {
        return demoAllMapper.deleteDemoAllByDemoIds(demoIds);
    }

    /**
     * 删除全部业务功能信息
     * 
     * @param demoId 全部业务功能主键
     * @return 结果
     */
    @Override
    public int deleteDemoAllByDemoId(Long demoId) {
        return demoAllMapper.deleteDemoAllByDemoId(demoId);
    }

    /**
     * 导入全部业务功能
     * 
     * @param demoAllList 待导入数据
     * @return 结果
     */
    @Override
    @Transactional
    public String importDemoAll(List<DemoAll> demoAllList) {
    	StringBuilder result = new StringBuilder();
    	if(CollUtil.isEmpty(demoAllList)) {
    		result.append("导入数据为空！");
    		return result.toString();
    	}
    	
    	// 找出合规的数据 
    	List<DemoAll> validDemoAlls = demoAllList;
        validDemoAlls = demoAllList.stream()
    			.filter(t -> ObjectUtil.isNotEmpty(t.getDemoName()) && ObjectUtil.isNotEmpty(t.getDeptId())  )
    			.collect(toList());
    	int invalidSize = demoAllList.size() - validDemoAlls.size();
    	
        // 找出所有已存在的数据（此方法可根据业务进行优化）
    	List<DemoAll> allDemoAlls = demoAllMapper.selectDemoAllList(new DemoAll());
    	// 找出要更新的数据
    	List<DemoAll> updateDemoAlls = validDemoAlls.stream()
                .filter(t1 -> allDemoAlls.stream().anyMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDeptId().equals(t2.getDeptId())  ))
                .peek(t -> {
                    t.setUpdateBy(SecurityUtils.getUsername());
                    t.setUpdateTime(DateUtils.getNowDate());
                })
                .collect(toList());
    	// 找出新增数据
        List<DemoAll> insertDemoAlls = validDemoAlls.stream()
                .filter(t1 -> allDemoAlls.stream().noneMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDeptId().equals(t2.getDeptId())  ))
                .peek(t -> {
                    t.setCreateBy(SecurityUtils.getUsername());
                    t.setCreateTime(DateUtils.getNowDate());
                })
                .collect(toList());
    	
        if(CollUtil.isNotEmpty(insertDemoAlls)) {
        	demoAllMapper.batchInsertDemoAll(insertDemoAlls);
        }
        if(CollUtil.isNotEmpty(updateDemoAlls)) {
        	demoAllMapper.batchUpdateDemoAll(updateDemoAlls);
        }
        
        result.append("待导入数据: ").append(demoAllList.size()).append("条数据，");
        if(invalidSize > 0) {
        	result.append("<br/>无效数据: ").append(invalidSize).append("条数据，");
        }
        
    	result.append("<br/>新增数据: ").append(insertDemoAlls.size()).append("条数据，")
    		.append("<br/>更新数据: ").append(updateDemoAlls.size()).append("条数据。");
        
    	return result.toString();
    }
}

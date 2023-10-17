package com.spirit.biz.demo.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spirit.biz.demo.domain.DemoSelf;
import com.spirit.biz.demo.mapper.DemoSelfMapper;
import com.spirit.biz.demo.service.IDemoSelfService;
import com.spirit.common.annotation.DataScope;
import com.spirit.common.utils.DateUtils;
import com.spirit.common.utils.SecurityUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * 私有业务功能Service业务层处理
 * 
 * @author sunchao
 * @date 2023-09-08
 */
@Service
public class DemoSelfServiceImpl implements IDemoSelfService {
    @Autowired
    private DemoSelfMapper demoSelfMapper;

    /**
     * 查询私有业务功能
     * 
     * @param demoId 私有业务功能主键
     * @return 私有业务功能
     */
    @Override
    public DemoSelf selectDemoSelfByDemoId(Long demoId) {
        return demoSelfMapper.selectDemoSelfByDemoId(demoId);
    }

    /**
     * 查询私有业务功能列表
     * 
     * @param demoSelf 私有业务功能
     * @return 私有业务功能
     */
    @Override
    @DataScope(deptAlias = "t")
    public List<DemoSelf> selectDemoSelfList(DemoSelf demoSelf) {
        return demoSelfMapper.selectDemoSelfList(demoSelf);
    }

    /**
     * 新增私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    @Override
    public int insertDemoSelf(DemoSelf demoSelf) {
        demoSelf.setCreateBy(SecurityUtils.getUsername());
        demoSelf.setCreateTime(DateUtils.getNowDate());
        return demoSelfMapper.insertDemoSelf(demoSelf);
    }

    /**
     * 修改私有业务功能
     * 
     * @param demoSelf 私有业务功能
     * @return 结果
     */
    @Override
    public int updateDemoSelf(DemoSelf demoSelf) {
        demoSelf.setUpdateBy(SecurityUtils.getUsername());
        demoSelf.setUpdateTime(DateUtils.getNowDate());
        return demoSelfMapper.updateDemoSelf(demoSelf);
    }

    /**
     * 批量删除私有业务功能
     * 
     * @param demoIds 需要删除的私有业务功能主键
     * @return 结果
     */
    @Override
    public int deleteDemoSelfByDemoIds(Long[] demoIds) {
        return demoSelfMapper.deleteDemoSelfByDemoIds(demoIds);
    }

    /**
     * 删除私有业务功能信息
     * 
     * @param demoId 私有业务功能主键
     * @return 结果
     */
    @Override
    public int deleteDemoSelfByDemoId(Long demoId) {
        return demoSelfMapper.deleteDemoSelfByDemoId(demoId);
    }

    /**
     * 导入私有业务功能
     * 
     * @param demoSelfList 待导入数据
     * @return 结果
     */
    @Override
    @Transactional
    public String importDemoSelf(List<DemoSelf> demoSelfList) {
    	StringBuilder result = new StringBuilder();
    	if(CollUtil.isEmpty(demoSelfList)) {
    		result.append("导入数据为空！");
    		return result.toString();
    	}
    	
    	// 找出合规的数据 
    	List<DemoSelf> validDemoSelfs = demoSelfList;
        validDemoSelfs = demoSelfList.stream()
    			.filter(t -> ObjectUtil.isNotEmpty(t.getDemoName()) && ObjectUtil.isNotEmpty(t.getDeptId())  )
    			.collect(toList());
    	int invalidSize = demoSelfList.size() - validDemoSelfs.size();
    	
        // 找出所有已存在的数据（此方法可根据业务进行优化）
    	List<DemoSelf> allDemoSelfs = demoSelfMapper.selectDemoSelfList(new DemoSelf());
    	// 找出要更新的数据
    	List<DemoSelf> updateDemoSelfs = validDemoSelfs.stream()
                .filter(t1 -> allDemoSelfs.stream().anyMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDeptId().equals(t2.getDeptId())  ))
                .peek(t -> {
                    t.setUpdateBy(SecurityUtils.getUsername());
                    t.setUpdateTime(DateUtils.getNowDate());
                })
                .collect(toList());
    	// 找出新增数据
        List<DemoSelf> insertDemoSelfs = validDemoSelfs.stream()
                .filter(t1 -> allDemoSelfs.stream().noneMatch(t2 -> t1.getDemoName().equals(t2.getDemoName()) && t1.getDeptId().equals(t2.getDeptId())  ))
                .peek(t -> {
                    t.setCreateBy(SecurityUtils.getUsername());
                    t.setCreateTime(DateUtils.getNowDate());
                })
                .collect(toList());
    	
        if(CollUtil.isNotEmpty(insertDemoSelfs)) {
        	demoSelfMapper.batchInsertDemoSelf(insertDemoSelfs);
        }
        if(CollUtil.isNotEmpty(updateDemoSelfs)) {
        	demoSelfMapper.batchUpdateDemoSelf(updateDemoSelfs);
        }
        
        result.append("待导入数据: ").append(demoSelfList.size()).append("条数据，");
        if(invalidSize > 0) {
        	result.append("<br/>无效数据: ").append(invalidSize).append("条数据，");
        }
        
    	result.append("<br/>新增数据: ").append(insertDemoSelfs.size()).append("条数据，")
    		.append("<br/>更新数据: ").append(updateDemoSelfs.size()).append("条数据。");
        
    	return result.toString();
    }
}

package com.risun.biz.biz.service.impl;

import java.util.List;
import com.risun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.risun.common.utils.SecurityUtils;
import com.risun.biz.biz.mapper.BizMapper;
import com.risun.biz.biz.domain.Biz;
import com.risun.biz.biz.service.IBizService;

/**
 * 业务管理Service业务层处理
 * 
 * @author sunchao
 * @date 2022-07-29
 */
@Service
public class BizServiceImpl implements IBizService 
{
    @Autowired
    private BizMapper bizMapper;

    /**
     * 查询业务管理
     * 
     * @param bizId 业务管理主键
     * @return 业务管理
     */
    @Override
    public Biz selectBizByBizId(Long bizId)
    {
        return bizMapper.selectBizByBizId(bizId);
    }

    /**
     * 查询业务管理列表
     * 
     * @param biz 业务管理
     * @return 业务管理
     */
    @Override
    public List<Biz> selectBizList(Biz biz)
    {
        return bizMapper.selectBizList(biz);
    }

    /**
     * 新增业务管理
     * 
     * @param biz 业务管理
     * @return 结果
     */
    @Override
    public int insertBiz(Biz biz)
    {
        biz.setCreateBy(SecurityUtils.getUsername());
        biz.setCreateTime(DateUtils.getNowDate());
        return bizMapper.insertBiz(biz);
    }

    /**
     * 修改业务管理
     * 
     * @param biz 业务管理
     * @return 结果
     */
    @Override
    public int updateBiz(Biz biz)
    {
        biz.setUpdateBy(SecurityUtils.getUsername());
        biz.setUpdateTime(DateUtils.getNowDate());
        return bizMapper.updateBiz(biz);
    }

    /**
     * 批量删除业务管理
     * 
     * @param bizIds 需要删除的业务管理主键
     * @return 结果
     */
    @Override
    public int deleteBizByBizIds(Long[] bizIds)
    {
        return bizMapper.deleteBizByBizIds(bizIds);
    }

    /**
     * 删除业务管理信息
     * 
     * @param bizId 业务管理主键
     * @return 结果
     */
    @Override
    public int deleteBizByBizId(Long bizId)
    {
        return bizMapper.deleteBizByBizId(bizId);
    }
}

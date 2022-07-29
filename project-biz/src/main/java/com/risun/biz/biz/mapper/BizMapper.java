package com.risun.biz.biz.mapper;

import java.util.List;
import com.risun.biz.biz.domain.Biz;

/**
 * 业务管理Mapper接口
 * 
 * @author sunchao
 * @date 2022-07-29
 */
public interface BizMapper 
{
    /**
     * 查询业务管理
     * 
     * @param bizId 业务管理主键
     * @return 业务管理
     */
    public Biz selectBizByBizId(Long bizId);

    /**
     * 查询业务管理列表
     * 
     * @param biz 业务管理
     * @return 业务管理集合
     */
    public List<Biz> selectBizList(Biz biz);

    /**
     * 新增业务管理
     * 
     * @param biz 业务管理
     * @return 结果
     */
    public int insertBiz(Biz biz);

    /**
     * 修改业务管理
     * 
     * @param biz 业务管理
     * @return 结果
     */
    public int updateBiz(Biz biz);

    /**
     * 删除业务管理
     * 
     * @param bizId 业务管理主键
     * @return 结果
     */
    public int deleteBizByBizId(Long bizId);

    /**
     * 批量删除业务管理
     * 
     * @param bizIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizByBizIds(Long[] bizIds);
}

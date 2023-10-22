package com.spirit.system.mapper;

import com.spirit.system.domain.SysUserPwdModify;

/**
 * 用户密码修改记录Mapper接口
 * 
 * @author sunchao
 * @date 2022-10-28
 */
public interface SysUserPwdModifyMapper 
{
    /**
     * 查询用户密码修改记录
     * 
     * @param userId 用户密码修改记录主键
     * @return 用户密码修改记录
     */
    public SysUserPwdModify selectSysUserPwdModifyByUserId(Long userId);

    /**
     * 新增用户密码修改记录
     * 
     * @param sysUserPwdModify 用户密码修改记录
     * @return 结果
     */
    public int insertSysUserPwdModify(SysUserPwdModify sysUserPwdModify);

    /**
     * 修改用户密码修改记录
     * 
     * @param sysUserPwdModify 用户密码修改记录
     * @return 结果
     */
    public int updateSysUserPwdModify(SysUserPwdModify sysUserPwdModify);

    
}

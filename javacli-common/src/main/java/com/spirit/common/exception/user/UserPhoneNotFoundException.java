package com.spirit.common.exception.user;

/**
 * 用户手机号不正确或不符合规范异常类
 * 
 * @author ruoyi
 */
public class UserPhoneNotFoundException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPhoneNotFoundException()
    {
        super("user.phone.not.exists", null);
    }
}

package com.risun.common.enums;

import lombok.Getter;

/**
 * 脱敏数据类型枚举
 */
@Getter
public enum DesensitizeType {

	/** 姓名 */
    NAME,
    
    /** 用户Id */
    USER_ID,

    /** 身份证号 */
    ID_CARD,

    /** 手机号 */
    PHONE,
    
    /** 固定电话 */
    FIX_PHONE,

    /** 邮箱 */
    EMAIL,

    /** 账号 */
    ACCOUNT,

    /** 密码 */
    PASSWORD,
    
    /** 银行卡 */
    BANK_CARD,
    
    /** 自定义（此项需设置脱敏的范围）*/
    CUSTOMER,
}

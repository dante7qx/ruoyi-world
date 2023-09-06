package com.risun.common.enums;

import com.risun.common.annotation.DesensitizeField;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 脱敏数据类型枚举
 */
@Getter
public enum DesensitizeType {
	
	
	/** 加密入库 */
	DB {
		/**
		 * 单独处理
		 */
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return "";
		}
	},

	/** 姓名 */
    NAME {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.chineseName(source);
		}
	},
    
    /** 用户Id */
    USER_ID {
		@Override
		public Long desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.userId();
		}
	},

    /** 身份证号 */
    ID_CARD {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.idCardNum(source, 3, 3);
		}
	},

    /** 手机号 */
    PHONE {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.mobilePhone(source);
		}
	},
    
    /** 固定电话 */
    FIX_PHONE {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.fixedPhone(source);
		}
	},

    /** 邮箱 */
    EMAIL {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.email(source);
		}
	},

    /** 账号 */
    ACCOUNT {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return StrUtil.hide(source, 2, source.length());
		}
	},

    /** 密码 */
    PASSWORD {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.password(source);
		}
	},
    
    /** 银行卡 */
    BANK_CARD {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			return DesensitizedUtil.bankCard(source);
		}
	},
    
    /** 自定义（此项需设置脱敏的范围）*/
    CUSTOMER {
		@Override
		public String desensitive(String source, DesensitizeField annotation) {
			int len = source.length();
			int prefixLen = annotation.prefixLen();
			int suffixLen = annotation.suffixLen();
			String symbol = annotation.symbol();
			return CharSequenceUtil.hide(source, prefixLen, len - suffixLen).replaceAll("\\*", symbol);
		}
	};
	
	public abstract Object desensitive(String source, DesensitizeField annotation);
	
}


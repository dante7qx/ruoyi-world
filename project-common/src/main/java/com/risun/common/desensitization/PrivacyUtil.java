package com.risun.common.desensitization;

import com.risun.common.utils.StringUtils;

public class PrivacyUtil {

    /**
     * 隐藏手机号中间四位
     */
    public static String hidePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 隐藏邮箱
     */
    public static String hideEmail(String email) {
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 隐藏身份证
     */
    public static String hideIDCard(String idCard) {
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    /**
     * 【中文姓名】只显示第一个汉字，其他隐藏为星号，比如：任**
     */
    public static String hideChineseName(String chineseName) {
        if (chineseName == null) {
            return null;
        }
        return desValue(chineseName, 1, 0, "*");
    }


    /**
     * 字符串进行脱敏操作
     * @param origin 脱敏字符串
     * @return
     */
    public static String desValue(String origin){
        return desValue(origin,1,1,"*");
    }

    /**
     * 字符串进行脱敏操作
     * @param origin 脱敏字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @return
     */
    public static String desValue(String origin, int prefixNoMaskLen){
        return desValue(origin,prefixNoMaskLen,1,"*");
    }

    /**
     * 对字符串进行脱敏操作
     * @param origin          原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixNoMaskLen,int suffixNoMaskLen){
        return desValue(origin,prefixNoMaskLen,suffixNoMaskLen,"*");
    }
    /**
     * 对字符串进行脱敏操作
     * @param origin          原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr         用于遮罩的字符串, 如'*'
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (StringUtils.isBlank(origin)) {
            return null;
        }
        
        String originTrim = origin.trim();
        
        if(prefixNoMaskLen < 0) prefixNoMaskLen = 0;

        if(suffixNoMaskLen < 0) suffixNoMaskLen = 0;

        if(StringUtils.isBlank(maskStr)){
            maskStr = "*";
        }

        if(prefixNoMaskLen > originTrim.length()){
            return originTrim;
        }

        StringBuilder sb = new StringBuilder();
        int n = originTrim.length();
        int suffixLen = n - suffixNoMaskLen - 1;
        for (int i = 0; i < n; i++) {
            if (i < prefixNoMaskLen) {
                sb.append(originTrim.charAt(i));
                continue;
            }
            if (i > suffixLen) {
                sb.append(originTrim.charAt(i));
                continue;
            }
            sb.append(maskStr);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
		System.out.println(desValue("在Hutool群友的强烈要求下", 7, 1, "#"));
	}
    
    
}

package com.spirit.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.spirit.common.enums.DesensitizeType;

/**
 * 敏感数据脱敏注解 - 作用于Domain实体层
 * 
 * @author dante
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DesensitizeField {
	
	DesensitizeType type();
	
	/**
     * 前置不需要打码的长度
     */
    int prefixLen() default 1;

    /**
     * 后置不需要打码的长度
     */
    int suffixLen() default 1;

    /**
     * 默认隐藏字符
     */
    String symbol() default "*";
}

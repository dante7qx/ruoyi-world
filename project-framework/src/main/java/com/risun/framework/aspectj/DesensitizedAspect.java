package com.risun.framework.aspectj;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.risun.common.annotation.DesensitizeField;
import com.risun.common.annotation.DesensitizeMethod;
import com.risun.common.core.domain.AjaxResult;
import com.risun.common.core.page.TableDataInfo;
import com.risun.common.enums.DesensitizeType;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 敏感数据脱敏处理
 * 
 * @author dante
 */
@Aspect
@Component
public class DesensitizedAspect {

	private static final String SUPER_PARAMS = "params";

	@Around(value = "@annotation(desensitizeMethod)")
	public Object paramCheck(ProceedingJoinPoint joinPoint, DesensitizeMethod desensitizeMethod) throws Throwable {
		Object proceed = joinPoint.proceed();
		Object execute = execute(proceed);
		return execute;

	}

	/**
	 * 判断是否为基本类型：包括包装类型
	 *
	 * @param clazz 字段类型
	 * @return true：是; false：不是
	 */
	private boolean isPrimite(Class<?> clazz) {
		return clazz.isPrimitive() || clazz == String.class || clazz == Integer.class || clazz == Long.class
				|| clazz == Short.class || clazz == Float.class || clazz == Double.class || clazz == Date.class
				|| clazz == Boolean.class;
	}

	private Object execute(Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException {
		Class<?> clazz = obj instanceof Field ? ((Field) obj).getType() : obj.getClass();
		if (isPrimite(clazz)) {
			return obj;
		}
		if (clazz == AjaxResult.class) {
			AjaxResult result = (AjaxResult) obj;
			Object data = result.get(AjaxResult.DATA_TAG);
			executeClassField(data.getClass(), data);
		} else if(clazz == TableDataInfo.class) {
			TableDataInfo tableData = (TableDataInfo) obj;
			List<?> list = (List<?>) tableData.getRows();
			if (CollUtil.isNotEmpty(list)) {
				for (Object listVal : list) {
					executeField(listVal.getClass(), listVal, false);
				}
			}
		} else if (clazz == ArrayList.class || clazz == LinkedList.class) {
			List<?> list = (List<?>) obj;
			if (CollUtil.isNotEmpty(list)) {
				for (Object listVal : list) {
					executeField(listVal.getClass(), listVal, false);
				}
			}
		} else if (clazz == HashSet.class || clazz == LinkedHashSet.class || clazz == TreeSet.class) {
			Set<?> set = (Set<?>) obj;
			if (CollUtil.isNotEmpty(set)) {
				for (Object setVal : set) {
					executeField(setVal.getClass(), setVal, false);
				}
			}
		} else if (clazz == HashMap.class || clazz == LinkedHashMap.class || clazz == TreeMap.class) {
			executeMapField(obj);
		} else {
			executeClassField(clazz, obj);
		}

		return obj;
	}

	@SuppressWarnings("unchecked")
	private void executeMapField(Object obj)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Map<String, Object> map = (Map<String, Object>) obj;
		for (Object val : map.values()) {
			Class<?> valClazz = val.getClass();
			if (!isPrimite(valClazz)) {
				executeField(valClazz, val, false);
			}
		}
	}

	private void executeClassField(Class<?> dataClass, Object data) {
		try {
			executeField(dataClass, data, true);
			Class<?> superClazz = dataClass.getSuperclass();
			if (superClazz != null) {
				
				Field mapField = superClazz.getDeclaredField(SUPER_PARAMS);
				mapField.setAccessible(true);
				Object paramsObj = mapField.get(data);
				if (paramsObj != null) {
					executeMapField(paramsObj);
				}
			}
		} catch (Exception e) {
		}
		
	}

	private void executeField(Class<?> dataClass, Object data, boolean recursion)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field[] declaredFields = dataClass.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			declaredField.setAccessible(true);
			if (isPrimite(declaredField.getType())) {
				DesensitizeField annotation = declaredField.getAnnotation(DesensitizeField.class);
				if (annotation != null) {
					declaredField.set(data, desensitive(data, declaredField, annotation));
				}
			} else if (recursion) {
				if (declaredField.get(data) != null) {
					declaredField.set(data, execute(declaredField.get(data)));
				}
			}
		}
	}

	/**
	 * 数据脱敏处理
	 * 
	 * @param data
	 * @param field
	 * @param annotation
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Object desensitive(Object data, Field field, DesensitizeField annotation)
			throws IllegalArgumentException, IllegalAccessException {
		Object desensitiveVal = null;
		Object fieldVal = field.get(data);
		if (ObjectUtil.isNull(fieldVal)) {
			return fieldVal;
		}
		DesensitizeType type = annotation.type();
		switch (type) {
			case NAME:
				desensitiveVal = DesensitizedUtil.chineseName(fieldVal.toString());
				break;
			case USER_ID:
				desensitiveVal = DesensitizedUtil.userId();
				break;
			case ID_CARD:
				desensitiveVal = DesensitizedUtil.idCardNum(fieldVal.toString(), 3, 3);
				break;
			case PHONE:
				desensitiveVal = DesensitizedUtil.mobilePhone(fieldVal.toString());
				break;
			case FIX_PHONE:
				desensitiveVal = DesensitizedUtil.fixedPhone(fieldVal.toString());
				break;
			case EMAIL:
				desensitiveVal = DesensitizedUtil.email(fieldVal.toString());
				break;
			case ACCOUNT:
				desensitiveVal = StrUtil.hide(fieldVal.toString(), 2, fieldVal.toString().length());
				break;
			case PASSWORD:
				desensitiveVal = DesensitizedUtil.password(fieldVal.toString());
				break;
			case CUSTOMER:
				int len = fieldVal.toString().length();
				int prefixLen = annotation.prefixLen();
				int suffixLen = annotation.suffixLen();
				String symbol = annotation.symbol();
				desensitiveVal = StrUtil.hide(fieldVal.toString(), prefixLen, len - suffixLen).replaceAll("\\*",
						symbol);
				break;
			default:
				break;
		}
		return desensitiveVal;
	}

}

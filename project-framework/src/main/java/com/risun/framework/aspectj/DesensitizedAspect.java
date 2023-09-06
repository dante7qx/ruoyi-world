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
import java.util.Objects;
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
import com.risun.common.exception.ServiceException;
import com.wxtool.ChinaCipher;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 敏感数据脱敏处理
 * 
 * @author dante
 */
@Slf4j
@Aspect
@Component
public class DesensitizedAspect {

	private static final String SUPER_PARAMS = "params";
	private static ChinaCipher chinaCipher = new ChinaCipher();
	
	/**
	 * 数据脱敏环绕切面
	 * 
	 * 1、入参加密，加密参数必须是第一个，只支持字符串字段加密
	 * 
	 * @param joinPoint
	 * @param desensitizeMethod
	 * @return
	 */
	@Around("@annotation(desensitizeMethod)")
	public Object paramCheck(ProceedingJoinPoint joinPoint, DesensitizeMethod desensitizeMethod)  {
		try {
			executeArgs(joinPoint.getArgs()[0]);
			return executeResult(joinPoint.proceed());
		} catch (Throwable e) {
			log.error("敏感数据脱敏处理错误", e);
			throw new ServiceException("敏感数据脱敏处理错误！");
		}

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
	
	/**
	 * 加密落库必须是String类型
	 * 
	 * @param clazz
	 * @return
	 */
	private boolean isString(Class<?> clazz) {
		return clazz == String.class;
	}

	/**
	 * 入参切面处理
	 * 
	 * @param joinPoint
	 * @param desensitizeMethod
	 */
	protected Object executeArgs(Object obj) throws Exception {
		if (ObjectUtil.isNull(obj)) {
			return obj;
		}
		Class<?> clazz = obj instanceof Field ? ((Field) obj).getType() : obj.getClass();
		if (isPrimite(clazz)) {
			return obj;
		} 
		if (clazz == ArrayList.class || clazz == LinkedList.class) {
			executeListField(obj, true);
		} else if (clazz == HashSet.class || clazz == LinkedHashSet.class || clazz == TreeSet.class) {
			executeSetField(obj, true);
		} else if (clazz == HashMap.class || clazz == LinkedHashMap.class || clazz == TreeMap.class) {
			executeMapField(obj, true);
		} else {
			executeArgField(clazz, obj, true);
		}
		return obj;
	}
	
	
	private void executeArgField(Class<?> dataClass, Object data, boolean recursion) throws Exception {
		Field[] declaredFields = dataClass.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			declaredField.setAccessible(true);
			if (isString(declaredField.getType())) {
				DesensitizeField annotation = declaredField.getAnnotation(DesensitizeField.class);
				if (annotation != null && DesensitizeType.DB.equals(annotation.type())) {
					Object fieldVal = declaredField.get(data);
					if (ObjectUtil.isNotNull(fieldVal)) {
						declaredField.set(data, chinaCipher.SM4EncDefault(fieldVal.toString()));
					}
				}
			} else if (!isPrimite(declaredField.getType()) && recursion) {
				if (declaredField.get(data) != null) {
					declaredField.set(data, executeArgs(declaredField.get(data)));
				}
			}
		}
	}
	
	
	/**
	 * 结果切面处理
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private Object executeResult(Object obj) throws Exception {
		Class<?> clazz = obj instanceof Field ? ((Field) obj).getType() : obj.getClass();
		if (isPrimite(clazz)) {
			return obj;
		}
		if (clazz == AjaxResult.class) {
			AjaxResult result = (AjaxResult) obj;
			Object data = result.get(AjaxResult.DATA_TAG);
			if(Objects.isNull(data)) return null;
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
			executeListField(obj, false);
		} else if (clazz == HashSet.class || clazz == LinkedHashSet.class || clazz == TreeSet.class) {
			executeSetField(obj, false);
		} else if (clazz == HashMap.class || clazz == LinkedHashMap.class || clazz == TreeMap.class) {
			executeMapField(obj, false);
		} else {
			executeClassField(clazz, obj);
		}

		return obj;
	}
	
	private void executeListField(Object obj, boolean isInputArgs) throws Exception {
		List<?> list = (List<?>) obj;
		if (CollUtil.isNotEmpty(list)) {
			for (Object listVal : list) {
				if(isInputArgs) {
					executeArgField(listVal.getClass(), listVal, false);
				} else {
					executeField(listVal.getClass(), listVal, false);
				}
			}
		}
	}
	
	private void executeSetField(Object obj, boolean isInputArgs) throws Exception {
		Set<?> set = (Set<?>) obj;
		if (CollUtil.isNotEmpty(set)) {
			for (Object setVal : set) {
				if(isInputArgs) {
					executeArgField(setVal.getClass(), setVal, false);
				} else {
					executeField(setVal.getClass(), setVal, false);
				}
				
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void executeMapField(Object obj, boolean isInputArgs) throws Exception {
		Map<String, Object> map = (Map<String, Object>) obj;
		for (Object val : map.values()) {
			Class<?> valClazz = val.getClass();
			if (!isPrimite(valClazz)) {
				if(isInputArgs) {
					executeArgField(valClazz, val, false);
				} else {
					executeField(valClazz, val, false);
				}
				
			}
		}
	}
	
	private void executeClassField(Class<?> dataClass, Object data) throws Exception {
		executeField(dataClass, data, true);
		Class<?> superClazz = dataClass.getSuperclass();
		if (superClazz != null) {
			Field mapField = superClazz.getDeclaredField(SUPER_PARAMS);
			mapField.setAccessible(true);
			Object paramsObj = mapField.get(data);
			if (paramsObj != null) {
				executeMapField(paramsObj, false);
			}
		}
	}

	private void executeField(Class<?> dataClass, Object data, boolean recursion) throws Exception {
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
					declaredField.set(data, executeResult(declaredField.get(data)));
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
	 * @throws Exception
	 */
	private Object desensitive(Object data, Field field, DesensitizeField annotation) throws Exception {
		Object desensitiveVal = null;
		Object fieldVal = field.get(data);
		if (ObjectUtil.isNull(fieldVal)) {
			return fieldVal;
		}
		DesensitizeType type = annotation.type();
		switch (type) {
			case DB:
				desensitiveVal = isString(field.getType()) ? chinaCipher.SM4DecDefault(fieldVal.toString()) : fieldVal;
				break;
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
				desensitiveVal = CharSequenceUtil.hide(fieldVal.toString(), prefixLen, len - suffixLen).replaceAll("\\*", symbol);
				break;
			default:
				break;
		}
		return desensitiveVal;
	}

}

package com.risun.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

/**
 * 时间工具类
 * 
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 获取当前Date型日期
	 * 
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return Date.from(Instant.now());
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return DateUtil.format(date, format);
	}

	public static final Date dateTime(final String format, final String ts) {
		return DateUtil.parse(ts, format);
	}
	
	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		return DateUtil.format(Date.from(Instant.now()), "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		return DateUtil.format(Date.from(Instant.now()), "yyyyMMdd");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算相差天数
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		return Integer.parseInt(DateUtil.between(date1, date2, DateUnit.DAY) + ""); 
	}
	
	/**
	 * 获取指定年的所有月份
	 * 
	 * getYearMonth(2022, "-")
	 * getYearMonth(2022, "年", "月")
	 * 
	 * @param year
	 * @param spliter
	 * @return
	 */
	public static List<String> getYearMonth(int year, String... spliter) {
		List<String> yearMonth = Lists.newArrayList();
		for (int i = 1; i <= 12; i++) {
			yearMonth.add(year + spliter[0] + (i < 10 ? "0" + i : i) + (spliter.length == 2 ? spliter[1] : ""));
		}
		return yearMonth;
	}
	
	/**
	 * 计算两个时间差（天、时、分、秒）
	 */
	public static String getDatePoor(Date startDate, Date endDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		StringBuffer result = new StringBuffer();
		boolean hasPrev = checkShowDiffStr(Integer.parseInt(day + ""), false, "天", result);
	    hasPrev = checkShowDiffStr(Integer.parseInt(hour + ""), hasPrev, "小时", result);
	    hasPrev = checkShowDiffStr(Integer.parseInt(min + ""), hasPrev, "分钟", result);
	    checkShowDiffStr(Integer.parseInt(sec + ""), hasPrev, "秒", result);
		return result.toString();
	}
	
	/**
	 * 计算两个时间差（年、月、天、时、分、秒）
	 */
	public static String getFullDatePoor(Date startDate, Date endDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		Period between = Period.between(toLocalDate(startDate), toLocalDate(endDate));
		long diff = endDate.getTime() - startDate.getTime();
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
	    long sec = diff % nd % nh % nm / ns;
	    StringBuffer result = new StringBuffer();
	    boolean hasPrev = checkShowDiffStr(Math.abs(between.getYears()), false, "年", result);
	    hasPrev = checkShowDiffStr(Math.abs(between.getMonths()), hasPrev, "月", result);
	    hasPrev = checkShowDiffStr(Math.abs(between.getDays()), hasPrev, "天", result);
	    hasPrev = checkShowDiffStr(Integer.parseInt(hour + ""), hasPrev, "小时", result);
	    hasPrev = checkShowDiffStr(Integer.parseInt(min + ""), hasPrev, "分钟", result);
	    checkShowDiffStr(Integer.parseInt(sec + ""), hasPrev, "秒", result);
		return result.toString();
	}
	
	/**
	 * 增加 LocalDateTime ==> Date
	 */
	public static Date toDate(LocalDateTime temporalAccessor) {
		ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	/**
	 * 增加 LocalDate ==> Date
	 */
	public static Date toDate(LocalDate temporalAccessor) {
		LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}
	
	/**
	 * Date ==> LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	/**
	 * Date ==> LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private static boolean checkShowDiffStr(int result, boolean hasPrev, String unit, StringBuffer buffer) {
		boolean flag = false;
		if(result > 0 || (hasPrev && result == 0)) {
			flag = true;
			buffer.append(result + unit);
	    }
		return flag;
	}
	
}

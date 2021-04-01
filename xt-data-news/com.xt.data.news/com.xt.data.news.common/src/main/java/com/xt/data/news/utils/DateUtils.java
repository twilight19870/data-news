package com.xt.data.news.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author lyc
 * @date 2018/8/23.
 */
public class DateUtils {

    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /** 时间格式(HH:mm:ss) */
    public final static String TIME_PATTERN = "HH:mm:ss";
    /** 日期名称 **/
    public static final String[] dateNameArr = new String[] {"前天", "昨天", "今天", "明天", "后天"};
    /**  中国周 **/
    public static final int[] weeksSn = new int[] {0,7,1,2,3,4,5,6};
    /** 日期名称 **/
    private static Map<String,String> dateNames;
    /** 日期名称缓存结束Time **/
    private static long dateNameEndTime = 0L;

    /** 时间格式(ss mm HH) */
    public final static String TIME_PATTERN_2 = "ss mm HH";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(java.time.LocalDate date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 当前时间格式化
     * @param pattern
     * @return
     */
    public static String format(String pattern) {
    	if(pattern==null) {
    		return null;
    	}
        return format(new Date(), pattern);
    }

    /**
     * 时间格式化,默认：HH:mm:ss
     * @param time
     * @return
     */
    public static String format(java.time.LocalTime time, String pattern) {
    	if(time==null) {
    		return null;
    	}
    	return time.format(java.time.format.DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 时间格式化,默认：ss mm HH
     * @param time
     * @return
     */
    public static String format2(java.time.LocalTime time) {
        if(time==null) {
            return null;
        }
        return time.format(java.time.format.DateTimeFormatter.ofPattern(TIME_PATTERN_2));
    }

    /**
     * 时间格式化,默认：HH:mm:ss
     * @param time
     * @return
     */
    public static String format(java.time.LocalTime time) {
    	if(time==null) {
    		return null;
    	}
    	return format(time, TIME_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param localDate
     * @param pattern
     * @return
     */
    public static String format(java.time.LocalDate localDate, String pattern) {
    	Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
    	return format(Date.from(instant), pattern);
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }


    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, Integer days) {
    	if(date==null) {
    		return null;
    	}
    	if(days==null) {
    		days = 0;
    	}
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
    	if(date==null) {
    		return null;
    	}
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    /**
     * 当天
     * @return
     */
    public static Date sameDay() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }

    /**
     * 明天
     * @return
     */
    public static Date tomorrow() {
    	return tomorrow(1);
    }
    /**
     * 明天
     * @return
     */
    public static Date tomorrow(int day) {
    	Date date = sameDay();
    	date.setDate(date.getDate()+day);
    	return date;
    }

    /**
     * 是否明天
     * @return
     */
    public static Boolean hasTomorrow(Date date) {
    	Date tomorrow = tomorrow();

    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);

    	Date trunc = cal.getTime();
    	trunc.setDate(date.getDate()+1);

    	return tomorrow.getTime()==trunc.getTime();
    }

    /**
     * 昨天
     * @param day
     * @return
     */
    public static Date yesterday() {
    	return yesterday(1);
    }

    /**
     * 昨天
     * @return
     */
    public static Date yesterday(int day) {
    	Date date = sameDay();
    	date.setDate(date.getDate()-day);
    	return date;
    }

    /**
     * 加小时
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
    	if(date==null || hour==0) {
    		return date;
    	}
    	Date r = new Date(date.getTime() + hour * 3600000L);
    	return r;
    }

    /**
     * 今日去时分秒
     * @param day
     * @return
     */
    public static Date today() {
    	return dayFirst(new Date());
    }

    /**
     * 日期去时分秒
     * @param day
     * @return
     */
    public static Date dayFirst() {
    	return dayFirst(new Date());
    }

    /**
     * 日期去时分秒
     * @param day
     * @return
     */
    public static Date dayFirst(Date day) {
    	if(day==null) {
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(day);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }

    /**
     * 当前时间
     * @return
     */
    public static Date currDateTime() {
    	return new Date();
    }

    /**
     * 当前时间
     * @return
     */
    public static String currDateTimeStr() {
    	return format(new Date(), DATE_TIME_PATTERN);
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static Date currDate() {
    	return dayFirst(new Date());
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static Date currDate(int days) {
    	Date date = dayFirst(new Date());
    	return addDateDays(date, days);
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static String currDateStr() {
    	return format(dayFirst(new Date()), DATE_PATTERN);
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static String currDateStr(String pattern) {
    	return format(dayFirst(new Date()), pattern);
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static String currDateTimeStr(String pattern) {
    	return format(new Date(), pattern);
    }

    /**
     * 当前日期(去时分秒)
     * @return
     */
    public static String currDateStr(int days) {
    	return format(currDate(days), DATE_PATTERN);
    }

    /**
     * 日期去时分秒
     * @param day
     * @return
     */
    public static Date dayLast(Date day) {
    	if(day==null) {
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(day);
    	cal.set(Calendar.HOUR_OF_DAY, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
    	cal.set(Calendar.MILLISECOND, 999);
    	return cal.getTime();
    }

    /**
     * 日期在day天内
     * @param date
     * @param day
     * @return
     */
    public static boolean withinDays(Date date, int day) {
    	if(date==null) {
			return false;
		}
		long l = date.getTime();
		if(l>DateUtils.yesterday(day).getTime() &&  l < DateUtils.tomorrow().getTime()) {
			return true;
		}
		return false;
    }

    /**
     * 日期在day天内
     * @param date
     * @param day
     * @return
     */
    public static boolean withinDaysGt(Date date, int day) {
    	if(date==null) {
			return false;
		}
		long l = date.getTime();
		if(l>DateUtils.yesterday(day).getTime() ) {
			return true;
		}
		return false;
    }

    /**
     * 两个日期之间多少天
     * @param begin
     * @param end
     * @param isToday 包含当天？
     * @return
     */
    public static int betweenDay(Date begin, Date end, boolean isToday) {
    	if(begin==null || end==null) {
    		return 0;
    	}
    	Date a = dayFirst(begin);
    	Date b = dayFirst(end);
    	int d = (int) ((b.getTime()-a.getTime())/86400000L);
    	return d + (d<0?(isToday?-1:0):(isToday?1:0));
    }

    /**
     * 是否在时间之间
     * @param time
     * @param begin
     * @param end
     * @return
     */
    public static boolean between(Date time, Date begin, Date end) {
    	if(begin==null || end==null || time==null) {
    		return false;
    	}
    	return time.getTime()>=begin.getTime() && time.getTime()<=end.getTime();
    }

    /**
     * 是否在时间之间
     * @param begin
     * @param end
     * @return
     */
    public static boolean between(Date begin, Date end) {
    	return between(new Date(), begin, end);
    }

    /**
     * 日期与当日相隔天数
     * @param date
     * @return
     */
    public static int days(Date date) {
    	return betweenDay(date, new Date(), true);
    }

    /**
     * 获取时间
     * @param time
     * @return
     */
    public static LocalTime parseTime(String timeStr) {
    	if(timeStr==null || "".equals(timeStr)) {
    		return null;
    	}
    	LocalTime time = LocalTime.parse(timeStr);
    	return time;
    }

    /**
     * 与当前时间比较
     * @param timeStr
     * @return
     */
    public static int compareTime(String timeStr) {
    	LocalTime time = parseTime(timeStr);
    	if(time==null) {
    		return 1;
    	}
    	return LocalTime.now().compareTo(time);
    }

    /**
     * 时间间隔
     * @param beginStr
     * @param endStr
     * @param minutes
     * @return
     */
    public static List<LocalTime> timeList(String beginStr, String endStr, Integer minutes){
    	List<LocalTime> r = new ArrayList();
    	minutes = minutes==null?null:minutes;
    	LocalTime beginTime = (beginStr==null||"".equals(beginStr))?null:LocalTime.parse(beginStr);
    	LocalTime endTime = (endStr==null||"".equals(endStr))?null:LocalTime.parse(endStr);
    	return timeList(beginTime, endTime, minutes);
    }

    /**
     * 时间间隔
     * @param beginStr
     * @param endStr
     * @param minutes
     * @return
     */
    public static List<LocalTime> timeList(LocalTime beginTime, LocalTime endTime, Integer minutes){
    	List<LocalTime> r = new ArrayList();
    	minutes = minutes==null?60:minutes;
    	beginTime = (beginTime==null)?LocalTime.parse("00:00:00"):beginTime;
    	endTime = (endTime==null)?LocalTime.parse("23:59:59"):endTime;
    	minutes = minutes<=0? 60: minutes;

    	int i = beginTime.getHourOfDay();
    	int j = endTime.getHourOfDay();
    	while(i<=j) {
    		r.add(beginTime);
    		beginTime = beginTime.plusMinutes(minutes);
    		i++;
    		if(beginTime.compareTo(endTime)>=0) {
    			break;
    		}
    	}
    	return r;
    }

    /**
     * 时间间隔
     * @param beginStr
     * @param endStr
     * @param minutes
     * @return
     */
    public static List<LocalTime[]> timesList(String beginStr, String endStr, Integer minutes){
    	List<LocalTime[]> r = new ArrayList();
    	minutes = minutes==null?60:minutes;
    	LocalTime beginTime = (beginStr==null||"".equals(beginStr))?LocalTime.parse("00:00:00"):LocalTime.parse(beginStr);
    	LocalTime endTime = (endStr==null||"".equals(endStr))?LocalTime.parse("23:59:59"):LocalTime.parse(endStr);
    	List<LocalTime> times = timeList(beginTime, endTime, minutes);
    	for(LocalTime time : times) {
    		LocalTime time2 = time.plusMinutes(minutes);
    		r.add(new LocalTime[] {time, time2.compareTo(endTime)>0?endTime:time2});
    	}
    	return r;
    }

    /**
     * 时间间隔
     * @param beginStr
     * @param endStr
     * @param minutes
     * @param pattern
     * @return
     */
    public static List<String> timeList(String beginStr, String endStr, Integer minutes, String pattern){
    	List<String> r = new ArrayList();
    	minutes = minutes==null?60:minutes;
    	pattern = (pattern==null||"".equals(pattern))?"HH:mm":pattern;
    	List<LocalTime> times = timeList(beginStr, endStr, minutes);
    	for(LocalTime time : times) {
    		r.add(time.toString(pattern));
    	}
    	return r;
    }

    /**
     * 时间间隔
     * @param beginStr
     * @param endStr
     * @param minutes
     * @param pattern
     * @return
     */
    public static List<String[]> timesList(String beginStr, String endStr, Integer minutes, String pattern){
    	List<String[]> r = new ArrayList();
    	minutes = minutes==null?60:minutes;
    	pattern = (pattern==null||"".equals(pattern))?"HH:mm":pattern;
    	List<LocalTime[]> times = timesList(beginStr, endStr, minutes);
    	for(LocalTime[] time : times) {
    		r.add(new String[] {time[0].toString(pattern), time[1].toString(pattern)});
    	}
    	return r;
    }

    /**
     * 日期名称
     * @return
     */
    public static Map<String,String> dateNames(){
    	//过期清空
    	if(dateNameEndTime!=0L && System.currentTimeMillis() >= dateNameEndTime) {
    		dateNames = null;
    	}
    	if(dateNames==null) {
    		dateNames = new LinkedHashMap();
    		Date tomorrow = tomorrow();
        	dateNames.put(format(yesterday()), dateNameArr[0]);
        	dateNames.put(format(yesterday(2)), dateNameArr[1]);
        	dateNames.put(format(today()), dateNameArr[2]);
        	dateNames.put(format(tomorrow), dateNameArr[3]);
        	dateNames.put(format(tomorrow(2)), dateNameArr[4]);
        	dateNameEndTime = tomorrow.getTime();
    	}
    	return dateNames;
    }

    /**
     * 格式化日期名称(没有返回原日期)
     * @param dateStr
     * @return
     */
    public static String formatDateName(String dateStr) {
    	if(dateStr==null) {
    		return null;
    	}
    	Map<String,String> names = dateNames();
    	String r = names.get(dateStr);
    	return r==null?dateStr:r;
    }

    /**
     * 格式化日期名称(没有返回原日期)
     * @param date
     * @return
     */
    public static String formatDateName(Date date) {
    	if(date==null) {
    		return null;
    	}
    	return formatDateName(format(date));
    }

    /**
     * 格式化日期名称(没有返回原日期)
     * @param date
     * @return
     */
    public static String formatDateName(java.time.LocalDate date) {
    	if(date==null) {
    		return null;
    	}
    	return formatDateName(format(date));
    }

    /**
     * 日期转时间
     * @param date
     * @return
     */
    public static java.time.LocalTime valueOf(Date date) {
    	if(date==null) {
    		return null;
    	}
    	return java.time.LocalTime.of(date.getHours(), date.getMinutes(), date.getSeconds());
    }

    /**
     * 解析日期
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
    	if(date==null || "".equals(date)) {
    		return null;
    	}
    	try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
    }

    /**
     * 解析日期
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
    	return parse(date, DATE_PATTERN);
    }

    /**
     * 解析日期时间
     * @param date
     * @return
     */
    public static Date parseDateTime(String date) {
    	return parse(date, DATE_TIME_PATTERN);
    }

    /**
     * 星期几 1-7
     * @return
     */
    public static int week() {
    	Calendar c = Calendar.getInstance();
    	int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
    	return week;
    }

    /**
     * 本周第一天
     * @return
     */
    public static Date weekFirstDate() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
    	return addDateDays(c.getTime(), -week+1);
    }

    /**
     * 本月第一天
     * @return
     */
    public static Date monthFirstDate() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DAY_OF_MONTH, 1);
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
    	return c.getTime();
    }

    /**
     * 上月第一天
     * @return
     */
    public static Date beforeMonthFirstDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
        return c.getTime();
    }

    /**
     * 上周周第一天
     * @return
     */
    public static Date prevWeekFirstDate() {
    	Date date = weekFirstDate();
    	return addDateDays(date, -7);
    }

    /**
     * 下周周周第一天
     * @return
     */
    public static Date nextWeekFirstDate() {
    	Date date = weekLastDate();
    	return addDateDays(date, +1);
    }

    /**
     * 本周最后一天
     * @return
     */
    public static Date weekLastDate() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
    	return addDateDays(c.getTime(), 7-week);
    }


    /**
     * 本月最后一天
     * @return
     */
    public static Date monthLastDate() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	int value = c.getActualMaximum(Calendar.DAY_OF_MONTH);
    	c.set(Calendar.DAY_OF_MONTH, value);
    	return c.getTime();
    }

    /**
     * 上月最后一天
     * @return
     */
    public static Date beforeMonthLastDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int value = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, value);
        return c.getTime();
    }

    /**
     * 上周最后一天
     * @return
     */
    public static Date prevWeekLastDate() {
    	Date date = weekFirstDate();
    	return addDateDays(date, -1);
    }

    /**
     * 下周最后一天
     * @return
     */
    public static Date nextWeekLastDate() {
    	Date date = weekLastDate();
    	return addDateDays(date, 7);
    }

    /**
     * 本周第一天与最后一天
     * @return
     */
    public static Date[] weekDates() {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	int week = weeksSn[c.get(Calendar.DAY_OF_WEEK)];
    	return new Date[] {addDateDays(c.getTime(), -week+1), addDateDays(c.getTime(), 7-week)};
    }

    /**
     * 上周第一天与最后一天
     * @return
     */
    public static Date[] prevEeekDates() {
    	Date date = weekFirstDate();
    	return new Date[] {addDateDays(date, -7), addDateDays(date, -1)};
    }

    /**
     * 下周第一天与最后一天
     * @return
     */
    public static Date[] nextEeekDates() {
    	Date date = weekLastDate();
    	return new Date[] {addDateDays(date, 1), addDateDays(date, 7)};
    }

    /**
     * 本月第一天最后一天
     * @return
     */
    public static Date[] monthDates() {
    	Date begin = currDate();
    	begin.setDate(1);
    	Date end = new Date(begin.getTime());
    	end.setMonth(end.getMonth()+1);
    	return new Date[] {begin, end};
    }

    public static Date[] quarterDates() {
    	return null;
    }

    public static Date[] yearDates() {
    	return null;
    }

}

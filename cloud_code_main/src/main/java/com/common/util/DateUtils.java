package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 */
public final class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文简写 如：12月01日
     * 如：9月12日
     */
    public static String FORMAT_SHORTEST_CN = "M月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 判断两个日期距离的天数
     */
    public static int countDays(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 获取最近一个月内的日期
     */
    public static List<String> getMonthlyDay() {
        Date date = new Date();
        Date lastMonth = DateUtils.addMonth(date, -1);
        List<String> dayList = new ArrayList<String>();
        int interval = DateUtils.countDays(lastMonth, date);
        for (int i = 0; i <= interval; i++) {
            String dayStr = DateUtils.format(DateUtils.addDay(lastMonth, i), DateUtils.FORMAT_SHORT);
            dayList.add(dayStr.substring(dayStr.lastIndexOf("-") + 1));
        }
        return dayList;
    }
    
    /**
    
    * 判断时间是否在时间段内 *
     
    * @param date
     
    * 当前时间 yyyy-MM-dd HH:mm:ss
     
    * @param strDateBegin
     
    * 开始时间 00:00:00
     
    * @param strDateEnd
     
    * 结束时间 00:05:00
     
    * @return
     
    */
     
    public static boolean isInDate(Date date, String strDateBegin,
     
    String strDateEnd) {
     
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     
    String strDate = sdf.format(date);
     
    // 截取当前时间时分秒
     
    int strDateH = Integer.parseInt(strDate.substring(11, 13));
     
    int strDateM = Integer.parseInt(strDate.substring(14, 16));
     
    int strDateS = Integer.parseInt(strDate.substring(17, 19));
     
    // 截取开始时间时分秒
     
    int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
     
    int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
     
    int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
     
    // 截取结束时间时分秒
     
    int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
     
    int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
     
    int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
     
    if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
     
    // 当前时间小时数在开始时间和结束时间小时数之间
     
    if (strDateH > strDateBeginH && strDateH < strDateEndH) {
     
    return true;
     
    // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
     
    } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
     
    && strDateM <= strDateEndM) {
     
    return true;
     
    // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
     
    } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
     
    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
     
    return true;
     
    }
     
    // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
     
    else if (strDateH >= strDateBeginH && strDateH == strDateEndH
     
    && strDateM <= strDateEndM) {
     
    return true;
     
    // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
     
    } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
     
    && strDateM == strDateEndM && strDateS <= strDateEndS) {
     
    return true;
     
    } else {
     
    return false;
     
    }
     
    } else {
     
    return false;
     
    }
     
    }
    
    /* 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s){
        String res = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
		try {
			date = simpleDateFormat.parse(s);
			long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return res;
    }
}

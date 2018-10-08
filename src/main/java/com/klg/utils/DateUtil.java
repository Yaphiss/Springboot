package com.klg.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by yaphiss on 2018/9/19.
 */
public class DateUtil {

    private static final String FORMAT = "yyyy-MM-dd";

    /**
     * 日期格式化
     * @param date 日期
     * @param formatStr 格式化样式
     * @return "yyyy-MM-dd"
     */
    public static String format (Date date, String formatStr) {
        formatStr = formatStr.isEmpty() ? "yyyy-MM-dd" : formatStr;
        date = dateInit(date);
        return new SimpleDateFormat(formatStr).format(date);
    }

    /**
     * 获取当天的开始时间
     * @return Date
     */
    public static Date getTodayStart(){
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前天的结束时间
     * @return Date
     */
    public static Date getTodayEnd(){
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取指定日期的开始时间
     * @param date 日期
     * @return
     */
    public static Date getDateStart(Date date){
        if (null == date) return getTodayEnd();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的结束时间
     * @param date 日期
     * @return
     */
    public static Date getDateEnd(Date date){
        if (null == date) return getTodayEnd();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }

    /**
     * 日期字符串转时间格式
     * @param dateStr 日期格式字符串
     * @return
     * @throws Exception
     */
    public static Date dateStrChangeToDate(String dateStr) throws Exception {
        if (StringUtil.isStrEmpty(dateStr)) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        return dateFormat.parse(dateStr);
    }

    private static Date dateInit(Date date){
        return date = null == date ? new Date() : date;
    }
}

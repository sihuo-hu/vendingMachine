package com.royal.admin.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 根据传入星期 及 时间返回Date
     * @param week 1-7
     * @param time HH:mm:ss
     * @return
     */
    public static Date getWeekDate(int week, String time) {
        int date = DateUtils.getWeekOfDate (null);
        date = 1 - date;
        String startStr = DateUtils.getFormatDate (DateUtils.getDateBeforeOrAfter (date+week-1)) + " " + time;
        return DateUtils.StringToDate (startStr);
    }

    /**
     * 判断传入时间是否在当前时间加减多少秒的区间内
     *
     * @param date    格式必须是yyyy-MM-dd HH:mm:ss
     * @param seconds 正负皆可,正表示当前时间往前推，负表示当前时间往后推
     * @return
     */
    public static boolean isBetween(Date date, int seconds) {
        if (date != null) {
            Date time = date;
            long begin, end;
            if (seconds > 0) {
                end = System.currentTimeMillis ();
                begin = end - seconds * 1000;
            } else {
                begin = System.currentTimeMillis ();
                end = begin - seconds * 1000;
            }
            return time.getTime () >= begin && time.getTime () <= end;
        }
        return false;
    }


    /**
     * 定义时间日期显示格式
     */
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";
    private final static String TIME_FORMAT_SERIES = "yyyyMMddHHmmss";


    /**
     * * 获取指定日期是星期几
     * <p>
     * 参数为null时表示获取当前日期是星期几
     *
     * @param date
     * @return
     */

    public static int getWeekOfDate(Date date) {

        int[] weekOfDays = {7, 1, 2, 3, 4, 5, 6};

        Calendar calendar = Calendar.getInstance ();

        if (date != null) {

            calendar.setTime (date);

        }

        int w = calendar.get (Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {

            w = 0;

        }

        return weekOfDays[w];

    }


    /**
     * 取得当前系统时间，返回java.util.Date类型
     *
     * @return java.util.Date 返回服务器当前系统时间
     * @see Date
     */
    public static Date getCurrDate() {
        return new Date ();
    }

    /**
     * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
     *
     * @param currDate 要格式化的日期
     * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
     * @see #getFormatDate(Date, String)
     */
    public static String getFormatDate(Date currDate) {
        return getFormatDate (currDate, DATE_FORMAT);
    }


    /**
     * 根据格式得到格式化后的日期
     *
     * @param currDate 要格式化的日期
     * @param format   日期格式，如yyyy-MM-dd
     * @return String 返回格式化后的日期，格式由参数<code>format</code>
     * 定义，如yyyy-MM-dd，如2006-02-15
     * @see SimpleDateFormat#format(Date)
     */
    public static String getFormatDate(Date currDate, String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat (format);
            return dtFormatdB.format (currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat (DATE_FORMAT);
            return dtFormatdB.format (currDate);
        }
    }

    /**
     * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @param currDate 要格式化的时间
     * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     * @see #getFormatDateTime(Date, String)
     */
    public static String getFormatDateTime(Date currDate) {
        return getFormatDateTime (currDate, TIME_FORMAT);
    }


    /**
     * 根据格式得到格式化后的时间
     *
     * @param currDate 要格式化的时间
     * @param format   时间格式，如yyyy-MM-dd HH:mm:ss
     * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
     * @see SimpleDateFormat#format(Date)
     */
    public static String getFormatDateTime(Date currDate, String format) {
        SimpleDateFormat dtFormatdB = null;
        try {
            dtFormatdB = new SimpleDateFormat (format);
            return dtFormatdB.format (currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat (TIME_FORMAT);
            return dtFormatdB.format (currDate);
        }
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyyMMddHHmmss，如20060215152345
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyyMMddHHmmss，如20060215152345
     * @see #getFormatDateTime(Date)
     */
    public static String getCurrDateTimeSeries() {
        return getFormatDateTime (getCurrDate (),TIME_FORMAT_SERIES);
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
     * 15:23:45
     * @see #getFormatDateTime(Date)
     */
    public static String getCurrDateTimeStr() {
        return getFormatDateTime (getCurrDate ());
    }

    /**
     * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
     *
     * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
     * 15:23:45
     * @see #getFormatDateTime(Date, String)
     */
    public static String getCurrDateTimeStr_CN() {
        return getFormatDateTime (getCurrDate (), TIME_FORMAT_CN);
    }

    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回系统当前日期的前或者后几天
     * @see Calendar#add(int, int)
     */
    public static Date getDateBeforeOrAfter(int iDate) {
        Calendar cal = Calendar.getInstance ();
        cal.add (Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime ();
    }

    /**
     * 获得指定分钟后的时间
     * @param curDate
     * @param minute
     * @return
     */
    public static Date getDateBeforeOrAfterMinute(Date curDate, int minute) {
        Calendar cal = Calendar.getInstance ();
        cal.setTime (curDate);
        cal.add (Calendar.MINUTE, minute);
        return cal.getTime ();
    }

       /**
     * String(yyyy-MM-dd HH:mm:ss) 转 Date
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String time) {

        Date date = new Date ();
        // 注意format的格式要与日期String的格式相匹配
        DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse (time);
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return date;
    }

    /**
     * 日期比较
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static boolean compare_date(Date DATE1, Date DATE2) {
        try {
            if (DATE1.getTime () > DATE2.getTime ()) {
                return true;
            } else if (DATE1.getTime () < DATE2.getTime ()) {
                return false;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace ();
        }
        return false;
    }

    /**
     * 判断time是否在start，end之内
     *
     * @param time  指定日期
     * @param start 开始日期
     * @param end   结束日期
     * @return
     */
    public static boolean belongCalendar(Date time, Date start, Date end) {
        Calendar date = Calendar.getInstance ();
        date.setTime (time);

        Calendar c_start = Calendar.getInstance ();
        c_start.setTime (start);

        Calendar c_end = Calendar.getInstance ();
        c_end.setTime (end);

        if (date.after (c_start) && date.before (c_end)) {
            return true;
        } else {
            return false;
        }
    }

}

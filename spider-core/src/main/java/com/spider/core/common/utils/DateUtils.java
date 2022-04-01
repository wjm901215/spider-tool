/**
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.spider.core.common.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期处理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
    /** 时间格式(yyyy-MM-dd) */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /** 时间格式(yyyy-MM-dd HH:mm:ss) */
    private static final String sdf0reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,}$";
    private static final String sdf1reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    private static final String sdf2reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";
    private static final String sdf3reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    private static final String sdf4reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";
    private static final String sdf5reg = "^\\d{8}$";
    private static final String sdf6reg = "^\\d{14}$";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String pattern1 = "yyyy-MM-dd";
    public static final String pattern2 = "yyyy-MM-dd HH:mm:ss";
    public static final String pattern3 = "yyyy/MM/dd";
    public static final String pattern4 = "yyyy/MM/dd HH:mm:ss";
    public static final String pattern5 = "yyMMddHHmmss";
    public static final String pattern6 = "HH:mm";
    public static final String pattern7 = "yyyyMMddHHmmss";
    public static final String pattern8 = "yyyyMMdd";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期转换
     * @param str
     * @return
     */
    public static Date parse(String str) {

        SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_TIME_PATTERN);
        SimpleDateFormat sdf2 = new SimpleDateFormat(pattern1);
        SimpleDateFormat sdf3 = new SimpleDateFormat(pattern4);
        SimpleDateFormat sdf4 = new SimpleDateFormat(pattern3);
        SimpleDateFormat sdf5 = new SimpleDateFormat(pattern8);
        SimpleDateFormat sdf6 = new SimpleDateFormat(pattern7);
        Date date = null;
        Pattern p0 = Pattern.compile(sdf0reg);
        Matcher m0 = p0.matcher(str);
        Pattern p1 = Pattern.compile(sdf1reg);
        Matcher m1 = p1.matcher(str);
        Pattern p2 = Pattern.compile(sdf2reg);
        Matcher m2 = p2.matcher(str);
        Pattern p3 = Pattern.compile(sdf3reg);
        Matcher m3 = p3.matcher(str);
        Pattern p4 = Pattern.compile(sdf4reg);
        Matcher m4 = p4.matcher(str);
        Pattern p5 = Pattern.compile(sdf5reg);
        Matcher m5 = p5.matcher(str);
        Pattern p6 = Pattern.compile(sdf6reg);
        Matcher m6 = p6.matcher(str);
        try {
            if (m0.matches()) {
                date = sdf1.parse(str);
            } else if (m1.matches()) {
                date = sdf1.parse(str);
            } else if (m2.matches()) {
                date = sdf2.parse(str);
            } else if (m3.matches()) {
                date = sdf3.parse(str);
            } else if (m4.matches()) {
                date = sdf4.parse(str);
            } else if (m5.matches()) {
                date = sdf5.parse(str);
            } else if (m6.matches()) {
                date = sdf6.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static Date currentDate() {
        return new Date();
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
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
    public static Date addDateDays(Date date, int days) {
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
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day) {
        return getStartOfDay(day, Calendar.getInstance());
    }

    public static Date getStartOfMonth(Date day) {
        Calendar cal = Calendar.getInstance();
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
        return getStartOfDay(cal.getTime(), cal);
    }

    public static Date getStartOfYear(Date day) {
        Calendar cal = Calendar.getInstance();
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.MONTH, cal.getMinimum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
        return getStartOfDay(cal.getTime(), cal);
    }

    /**
     * Returns a Date set to the first possible millisecond of the day, just
     * after midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getStartOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * Returns a Date set to the last possible millisecond of the day, just
     * before midnight. If a null day is passed in, a new Date is created.
     * midnight (00m 00h 00s)
     */
    public static Date getEndOfDay(Date day) {
        return getEndOfDay(day, Calendar.getInstance());
    }

    public static Date getEndOfMonth(Date day) {
        Calendar cal = Calendar.getInstance();
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getEndOfYear(Date day) {
        Calendar cal = Calendar.getInstance();
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.MONTH, cal.getMaximum(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * 获取当月最后一毫秒的时刻
     *
     * @param time
     * @return
     */
    public static Date getEndingTimeOfYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.MONTH, calendar.getMaximum(Calendar.MONTH));
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 获取当年最后一毫秒的时刻
     *
     * @param time
     * @return
     */
    public static Date getEndingTimeOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }


    /**
     * 获取当月第一毫秒的时刻
     *
     * @param time
     * @return
     */
    public static Date getBeginTimeOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }


    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss  (yyyy-MM-dd'T'HH:mm:ss) TO  yyyy-MM-dd HH:mm:ss
     *
     * @throws ParseException
     */
    public static Date dealDateFormat(String oldDateStr) {
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(oldDateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            DateFormat df2 = new SimpleDateFormat(DATE_TIME_PATTERN);
            return df2.parse(df2.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 获取当年第一毫秒的时刻
     *
     * @param time
     * @return
     */
    public static Date getBeginTimeOfYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.MONTH, calendar.getMinimum(Calendar.MONTH));
        calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回当前分钟数,最小秒数
     * after midnight. If a null day is passed in, a new Date is created.
     */
    public static Date getMinuteFormat(Date day) {
        if (day == null)
            day = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * 日期加分钟数
     *
     * @param fullDate 日期
     * @param minutes  分钟
     * @return 日期
     */
    public static Date addMinutesDate(Date fullDate, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fullDate);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 日期减分钟数
     *
     * @param fullDate 日期
     * @param minute   分钟
     * @return 日期
     */
    public static Date subMinuteFullDate(Date fullDate, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fullDate);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minute);
        return calendar.getTime();
    }

    /**
     * 日期减天数
     *
     * @param date 日期
     * @param day  天数
     * @return 日期
     */
    public static Date subDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - day);
        return calendar.getTime();
    }

    /**
     * 获取两个时间相差秒数
     * (time1 - time2)/1000
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getTwoTimeDiffSecond(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return 0;
        }
        long microsecond = time1.getTime() - time2.getTime();
        return microsecond / 1000;
    }

    public static Date getEndOfDayWithoutMill(Date day) {
        Calendar cal = Calendar.getInstance();
        if (day == null)
            day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Timestamp getCurrentDateTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取两个时间相差分钟数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long getTwoTimeDiffMin(Date time1, Date time2) {
        long diff = getTwoTimeDiffSecond(time1, time2) / 60;
        if (diff > 0) {
            return diff;
        } else {
            return 0;
        }
    }
}


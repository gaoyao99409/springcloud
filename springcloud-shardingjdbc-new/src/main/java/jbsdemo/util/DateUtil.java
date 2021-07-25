package jbsdemo.util;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class DateUtil {
    public static final SimpleDateFormat SHORTSDF = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat LONGSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SHORTSDFUTC = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat LONGSDFUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat LOCALSDF = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    public static final SimpleDateFormat SHORTSUTC = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YEAR_MONTH_DAY_HOUR_MINUTE = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
    public static final String PUSH_MESSAGE_FORMAT = "MM月dd日 HH:mm";
    public static final String[] STR = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};// 字符串数组

    private static Pattern NUM_PATTERN = Pattern.compile("[0-9]*");

    /**
     * 时间格式化（到日期）
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            newDate = shortsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到日期）
     *
     * @param date
     * @return
     */
    public static String formatDateToMonth(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("MM");
            newDate = shortsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到秒）
     */
    public static String formatTime(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    /**
     * 时间格式化（到分）
     */
    public static String formatTimeMin(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static String formatLoaclTime(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat localsdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            newDate = localsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /*
     * 根据时间转换问候语 早上：5:00 —— 8:59 上午：9:00 ——10:59 中午：11:00——12:59 下午：13:00——18:59
     * 晚上：19:00——23:59 凌晨：24:00—— 4:59
     */
    public static String getGreetings() {
        String greetings = "早上好";
        String nowdate1 = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());// 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            Date d1 = sdf.parse("5:00:00");
            Date d2 = sdf.parse("8:59:59");

            Date d3 = sdf.parse("9:00:00");
            Date d4 = sdf.parse("10:59:59");

            Date d5 = sdf.parse("11:00:00");
            Date d6 = sdf.parse("12:59:59");

            Date d7 = sdf.parse("13:00:00");
            Date d8 = sdf.parse("18:59:59");

            Date d9 = sdf.parse("19:00:00");
            Date d10 = sdf.parse("23:59:59");

            Date d11 = sdf.parse("24:00:00");
            Date d12 = sdf.parse("4:59:59");
            Date nowdate = sdf.parse(nowdate1);
            if (nowdate.after(d12) && nowdate.before(d3)) {
                greetings = "早上好";
            } else if (nowdate.after(d2) && nowdate.before(d5)) {
                greetings = "上午好";
            } else if (nowdate.after(d4) && nowdate.before(d7)) {
                greetings = "中午好";
            } else if (nowdate.after(d6) && nowdate.before(d9)) {
                greetings = "下午好";
            } else if (nowdate.after(d8) && nowdate.before(d11)) {
                greetings = "晚上好";
            } else if (nowdate.after(d10) && nowdate.before(d1)) {
                greetings = "凌晨好";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return greetings;
    }

    /**
     * 时间格式化（到秒）
     *
     * @param ticks 时间刻度
     * @return
     */
    public static String formatTime(Long ticks) {
        String newDate = "";
        if (ticks == null) {
            return newDate;
        }
        try {
            Date date = new Date(ticks);
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static Date string2Date(String s, int type) {
        if (s == null) {
            return null;
        }
        Calendar cal = null;
        String[] a = s.split("-| |:");
        try {
            if (a.length >= 3) {
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.valueOf(a[0]));
                cal.set(Calendar.MONTH, Integer.valueOf(a[1]) - 1);
                cal.set(Calendar.DATE, Integer.valueOf(a[2]));
            }
            if (type == 0) {
                if (a.length >= 5) {
                    cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(a[3]));
                    cal.set(Calendar.MINUTE, Integer.valueOf(a[4]));
                    if (a.length == 6) {
                        cal.set(Calendar.SECOND, Integer.valueOf(a[5]));
                    }
                } else {
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                }
            } else if (type == 1) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
            } else if (type == 2) {
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
            }
        } catch (Exception e) {

        }
        if (cal != null) {
            return cal.getTime();
        }
        return null;
    }

    public static Date stringDateMin(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDateToHHMM(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDateToHHMMSS(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm:ss");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDateTohhmmss(String s) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("hh:mm:ss");
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDate(String s) {
        Date date = null;

        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = longsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static Date stringDate(String s, String format) {
        Date date = null;

        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat(format);
            date = shortsdf.parse(s);
        } catch (ParseException e) {
        }
        return date;
    }

    public static String stringDateToHHMM(Date date) {
        if (date != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm");
            return shortsdf.format(date);
        }
        return null;
    }

    public static String stringDateToHHMMSS(Date date) {
        if (date != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("HH:mm:ss");
            return shortsdf.format(date);
        }
        return null;
    }

    /**
     * 以短格式格式化时间,实例：2010-09-19
     *
     * @param time 时间刻度
     * @return 格式化后的时间
     * @author zhengrunjin @ 2010-09-19
     */
    public static String stringDateShortFormat(Long time) {
        if (time != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            return shortsdf.format(new Date(time));
        }
        return null;
    }

    public static String stringDate(Long l) {
        if (l != null) {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return longsdf.format(new Date(l));
        }
        return null;
    }

    public static String stringDateShortFormatUTC(Long time) {
        if (time != null) {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            shortsdf.setTimeZone(TimeZone.getDefault());
            return shortsdf.format(new Date(time * 1000));
        }
        return null;
    }

    public static String stringDateUTC(Long l) {
        if (l != null) {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            longsdf.setTimeZone(TimeZone.getDefault());
            return longsdf.format(new Date(l * 1000));
        }
        return "";
    }

    public static String stringDateUTC(Long l, String format) {
        if (l != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            df.setTimeZone(TimeZone.getDefault());
            return df.format(new Date(l * 1000));
        }
        return "";
    }

    /**
     * 返回于指定日期间隔一定天数的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getSpecDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
        return calendar.getTime();
    }

    public static boolean after(Date date1, Date date2) {
        Calendar dc1 = Calendar.getInstance();
        dc1.setTime(date1);
        Calendar dc2 = Calendar.getInstance();
        dc2.setTime(date2);
        return dc1.after(dc2);
    }

    public static boolean before(Date date1, Date date2) {
        Calendar dc1 = Calendar.getInstance();
        dc1.setTime(date1);
        Calendar dc2 = Calendar.getInstance();
        dc2.setTime(date2);
        return dc1.before(dc2);
    }

    // 日期转换
    public static java.sql.Date getBeforeAfterDate(String datestr, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date olddate = null;
        try {
            df.setLenient(false);
            olddate = new java.sql.Date(df.parse(datestr).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("日期转换错误");
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(olddate);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);

        int newDay = day1 + day;

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, newDay);

        return new java.sql.Date(cal.getTimeInMillis());
    }

    // 日期转换
    public static Date getBeforeAfterDate(Date date, int day) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);

        int newDay = day1 + day;

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, newDay);

        return new Date(cal.getTimeInMillis());
    }

    // 参数日期+小时数得到新日期
    // type：Calendar枚举类型   YEAR = 1;MONTH = 2;HOUR = 10;MINUTE = 12;SECOND = 13;
    public static Date getNewDate(Date d, int num, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(type, num);

        return calendar.getTime();
    }

    /**
     * 两个日期相差的天数,只精确到天
     * 满24小时算一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffDays(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 相差天数 满24小时算一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int diffDays(long time1, long time2) {
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 两个日期时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getDistanceTime(Date date1, Date date2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hour + ":" + min + ":" + sec;
    }

    /**
     * 两个日期相差的天数,只精确到年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffYear(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int time1 = cal.get(Calendar.YEAR);
        cal.setTime(date2);
        int time2 = cal.get(Calendar.YEAR);
        int betweenYear = time2 - time1;
        return betweenYear;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 两个日期相差的月数,只精确到月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffMonth(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        int time1 = cal.get(Calendar.MONTH);
        cal.setTime(date2);
        int time2 = cal.get(Calendar.MONTH);
        int betweenMonth = time2 - time1 + (diffYear(date1, date2) * 12);
        return betweenMonth;
    }

    /**
     * 两个日期相差的天数,只精确到天
     * 以 0点 为界 只要踌日期就算一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Integer diffDay(Date date1, Date date2) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date shortDate1 = dateFormat.parse(dateFormat.format(date1));
            Date shortDate2 = dateFormat.parse(dateFormat.format(date2));
            long betweenDays = (shortDate2.getTime() - shortDate1.getTime()) / (24 * 3600 * 1000);
            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回一天的结束时间
     *
     * @param d
     * @return
     */
    public static String getEndTimeOfDays(String d) {
        return d.trim() + " 23:59:59";
    }

    /**
     * 返回一天的结束时间
     *
     * @param d
     * @return
     */
    public static Date getEndTimeOfDays(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }


    /**
     * 获取一天最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static String getEarliestOfDay(String date) {
        return date.trim() + " 00:00:00";
    }

    /**
     * 获取一个月最早的时间
     *
     * @param date
     * @return
     */
    public static String getEarliestOfMonth(String date) {

        return formatDate(stringDateMin(date), "yyyy-MM") + "-01 00:00:00";
    }

    /**
     * 去掉日期的时间部分
     *
     * @param d
     * @return
     */
    public static String formatDateStr(String d) {
        try {
            SimpleDateFormat shortsdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return shortsdf.format(longsdf.parse(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取过几分钟的时间
     *
     * @param d
     * @param minute
     * @return
     */
    public static Date addMinutes(Date d, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取过几秒钟的时间
     */
    public static Date addSecond(Date d, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 获得过几小时的时间
     *
     * @param d     需要计算的时间类型
     * @param hours 小时数
     * @return
     */
    public static Date addHours(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 获得过几天的时间
     *
     * @param d
     * @param days 天数
     * @return
     */
    public static Date addDays(Date d, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获得前几小时的时间
     *
     * @param d     需要计算的时间类型
     * @param hours 小时数
     * @return
     */
    public static Date beforeHours(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, -hours);
        return calendar.getTime();
    }

    public static String formatDate(Date date, String format) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            newDate = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static Date formatDate(String strDate, String format) {
        Date date = null;
        if (strDate == null) {
            return date;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            date = df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 保留小时和分钟
     *
     * @param date
     * @return
     */
    public static String formatDateToHourAndMin(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public static String getDate(String datetime) {
        if (datetime == null) {
            return null;
        }
        if (datetime.indexOf(" ") == -1) {
            return datetime;
        }
        return datetime.trim().split(" ")[0];
    }

    /**
     * 获得HH:mm:ss格式的时间字符串
     *
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static String getTimeStr(int hours, int minutes, int seconds) {
        StringBuffer timeStr = new StringBuffer();
        if (hours < 10) {
            timeStr.append("0" + hours);
        } else {
            timeStr.append(hours);
        }
        timeStr.append(":");
        if (minutes < 10) {
            timeStr.append("0" + minutes);
        } else {
            timeStr.append(minutes);
        }
        timeStr.append(":");
        if (seconds < 10) {
            timeStr.append("0" + seconds);
        } else {
            timeStr.append(seconds);
        }

        return timeStr.toString();
    }

    /**
     * 获取时间为0点的某天的Date对象
     *
     * @param offset
     * @return
     */
    public static Date getZeroOfDay(int offset) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), (cal.get(Calendar.DAY_OF_MONTH) + offset), 0, 0, 0);
        return cal.getTime();
    }

    /**
     * 获取时间为24点的某天的Date对象
     *
     * @param offset
     * @return
     */
    public static Date get24OfDay(int offset) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), (cal.get(Calendar.DAY_OF_MONTH) + offset), 23, 59, 59);
        return cal.getTime();
    }

    /**
     * 供图片使用（缓存）
     *
     * @return
     */
    public static Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Date formatDate(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat(format);
        String newDate = df.format(calendar.getTime());
        Date date = null;
        try {
            date = df.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取一天最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getEarliestOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取一天最晚的时间点 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLatestOfDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当周最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getEarliestOfWeek(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当周最晚的时间点 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLatestOfWeek(Date date) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //周日
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当月最早的时间点 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getEarliestOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int firstDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        c.set(Calendar.DAY_OF_MONTH, firstDay);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当月最晚的时间点 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLatestOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取季度最晚的时间点 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getLatestOfQuarter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int remainder = (c.get(Calendar.MONTH) + 1) % 3;
        int month = remainder != 0 ? c.get(Calendar.MONTH) + (3 - remainder) : c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * @param date   创建时间
     * @param minute 倒数分钟
     * @return
     */
    public static long countDownTime(long date, int minute) {
        long countDownTime = 0;

        String dateUTC = DateUtil.stringDateUTC(date);
        Date dowmTime = DateUtil.addMinutes(stringDate(dateUTC), minute);
        Date now = new Date();
        if (dowmTime.after(now)) {
            countDownTime = (dowmTime.getTime() - now.getTime()) / 1000;
        }
        if (countDownTime < 0) {
            countDownTime = 0;
        }
        return countDownTime;
    }

    /**
     * 相差秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long dateDiffSec(long time1, long time2) {
        long diff = time2 - time1;
        long diffSec = diff / 1000;
        return diffSec;
    }

    /**
     * 相差秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffSecond(Date time1, Date time2) {
        long diff = time2.getTime() - time1.getTime();
        long diffSec = diff / 1000;
        return diffSec;
    }

    /**
     * 相差秒数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffMinute(Date time1, Date time2) {
        long diff = time2.getTime() - time1.getTime();
        long diffSec = diff / (60 * 1000);
        return diffSec;
    }

    /**
     * 相差小时
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int diffHour(Date time1, Date time2) {
        long diff = time2.getTime() - time1.getTime();
        long diffSec = diff / (60 * 60 * 1000);
        return new Long(diffSec).intValue();
    }

    /**
     * 相差分钟数
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int diffMinute(long time1, long time2) {
        long diff = time2 - time1;
        long diffSec = diff / (60 * 1000);
        return new Long(diffSec).intValue();
    }

    /**
     * 获取订单提示时间
     *
     * @param startTime
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:36:03
     */
    public static String getOrdersTimeStr(Date startTime) {
        int i = diffDays(formatDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd"), formatDate(formatDate(startTime, "yyyy-MM-dd"), "yyyy-MM-dd"));
        StringBuffer sb = new StringBuffer();
        if (i == 0) {
            sb.append("今天(" + getDateOfWeek(startTime) + ")");
        } else if (i == 1) {
            sb.append("明天(" + getDateOfWeek(startTime) + ")");
        } else {
            sb.append(formatDate(startTime, "MM月dd日"));
        }
        sb.append(" " + formatDate(startTime, "HH:mm") + " 出发");
        return sb.toString();
    }

    /**
     * 将日期转换成日常短语 <br/>
     * 1:当天 : 今天 HH:mm <br/>
     * 2:当年 : MM:dd <br/>
     * 3:往年 : yyyy-MM-dd <br/>
     *
     * @param date
     * @return
     */
    public static String formatDateToPhrase(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(date);
        if (target.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
            return formatDate(date, "yyyy-MM-dd");
        }
        if (target.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)) {
            return formatDate(date, "MM-dd");
        }
        return formatDate(date, "今天 HH:mm");
    }

    /**
     * 判断当前日期是否在两个日期之间 ,包含边界
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean betweenStartDateAndEndDate(Date date, Date startTime, Date endTime) {
        if (date.compareTo(endTime) != 1 && startTime.compareTo(date) != 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前日期是否在两个日期之间 ,不包含边界
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean betweenStartDateAndEndDate2(Date date, Date startTime, Date endTime) {
        if (date.compareTo(endTime) == -1 && startTime.compareTo(date) == -1) {
            return true;
        }
        return false;
    }

    /**
     * 判断两个时间段是否重叠
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean isDateOverlapping(Date start1, Date end1, Date start2, Date end2) {
        if (start1.equals(start2) && end1.equals(end2)) {
            return true;
        }
        if (betweenStartDateAndEndDate2(start1, start2, end2)
                || betweenStartDateAndEndDate2(end1, start2, end2)
                || betweenStartDateAndEndDate2(start2, start1, end1)
                || betweenStartDateAndEndDate2(end2, start1, end1)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否在两个时间点内
     *
     * @param time 小时 以","分隔,如23,6
     * @return
     * @author 朱厚飞
     * @date 2015年3月13日 下午3:44:35
     */
    public static boolean between(String time) {
        boolean flag = false;
        Integer stime = null;
        Integer etime = null;
        if (time != null) {
            String[] times = time.split(",");
            if (times.length == 2 && isNumeric(times[0].trim()) && isNumeric(times[1].trim())) {
                stime = Integer.valueOf(times[0].trim());
                etime = Integer.valueOf(times[1].trim());
            }

        }
        if (stime != null && etime != null) {
            Calendar c = Calendar.getInstance();
            int hours = c.get(Calendar.HOUR_OF_DAY);
            if (stime >= etime) {
                if (etime >= 1) {
                    if (stime <= hours && hours <= 23 || 0 <= hours && hours <= etime - 1) {
                        flag = true;
                    }
                } else {
                    if (stime <= hours && hours <= 23 || 0 <= hours && hours <= 23) {
                        flag = true;
                    }
                }
            } else {
                if (etime >= 1) {
                    if (stime <= hours && hours <= etime - 1) {
                        flag = true;
                    }
                } else {
                    if (stime <= hours && hours <= 23) {
                        flag = true;
                    }
                }
            }

        }
        return flag;
    }

    public static boolean isNumeric(String str) {
        return NUM_PATTERN.matcher(str).matches();
    }

    public static Date format(Date date, String format) {
        return formatDate(date.getTime(), format);
    }

    /**
     * 获取消息推送格式的日期
     * <b>03月01日 08:00（周日）</b>
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:26:32
     */
    public static String getPushMessageDate(Date date) {
        String shortDate = formatDate(date, PUSH_MESSAGE_FORMAT);
        return shortDate + "（" + STR[getDayOfWeek(date) - 1] + "）";
    }

    /**
     * 获取指定日期为星期几:1-7
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:26:53
     */
    public static Integer getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定日期为星期几:1-7
     *
     * @param date
     * @return
     * @author szc
     */
    public static Integer getDayOfWeekV2(Date date) {
        try {
            Calendar c = Calendar.getInstance();
            c.setFirstDayOfWeek(Calendar.MONDAY);
            c.setTime(date);
            int tmp = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (0 == tmp) {
                tmp = 7;
            }
            return tmp;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 是否是周末
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:26:53
     */
    public static boolean isWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 获取星期几，中文的，周日~周六
     *
     * @param date
     * @return
     * @author BennyTian
     * @date 2015年3月25日 上午10:32:57
     */
    public static String getDateOfWeek(Date date) {
        return STR[getDayOfWeek(date) - 1];
    }

    /**
     * "" + day1 + "天" + hour1 + "小时" + minute1 + "分钟";
     *
     * @param minute
     * @return
     */
    public static String hourMinute(int minute) {
        minute = minute * 60;
        long day1 = minute / (24 * 3600);
        long hour1 = minute % (24 * 3600) / 3600;
        long minute1 = minute % 3600 / 60;
//		long second1 = minute % 60;
        StringBuffer sb = new StringBuffer("");
        if (day1 > 0) {
            sb.append(day1 + "天");
        }
        if (hour1 >= 0) {
            sb.append(hour1 + "小时");
        }
        if (minute1 >= 0) {
            sb.append(minute1 + "分钟");
        }

        return sb.toString();
    }

    public static Long getTime(String date, SimpleDateFormat sdf) {

        try {
            return sdf.parse(date).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * utc 转 Date
     *
     * @param date
     * @return
     */
    public static Date utc2Date(Long date) {
        if (date == null) {
            return null;
        }
        return stringDate(DateUtil.stringDateUTC(date));
    }

    /**
     * 时间戳毫秒 转 Date
     *
     * @param date
     * @return
     */
    public static Date timestamp2Date(Long date) {
        if (date == null) {
            return null;
        }
        return stringDate(DateUtil.stringDateUTC(date / 1000L));
    }

    /**
     * 时间格式化（到秒）
     *
     * @param date
     * @return
     */
    public static String formatTimeToString(Date date) {
        String newDate = "";
        if (date == null) {
            return newDate;
        }
        try {
            SimpleDateFormat longsdf = new SimpleDateFormat("yyyyMMddHHmmss");
            newDate = longsdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newDate;
    }

    public static List<String> process(String date1, String date2) {
        List<String> strs = new ArrayList<String>();


        String tmp;
        if (date1.compareTo(date2) > 0) {  //确保 date1的日期不晚于date2
            tmp = date1;
            date1 = date2;
            date2 = tmp;
        }

        tmp = SHORTSDF.format(str2Date(date1).getTime());
        int num = 0;
        while (tmp.compareTo(date2) <= 0) {
            strs.add(tmp);
            num++;
            tmp = SHORTSDF.format(str2Date(tmp).getTime() + 3600 * 24 * 1000);
        }

        Collections.reverse(strs);

        return strs;
    }

    private static Date str2Date(String str) {
        if (str == null) {
            return null;
        }

        try {
            return SHORTSDF.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 两个日期时分秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String[] getDistanceAllTime(Date date1, Date date2) {
        String[] strs = new String[]{"", ""};


        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            hour = hour + day * 24;
            float num = (float) diff / (60 * 60 * 1000);
            DecimalFormat df = new DecimalFormat("0.00");//格式化小数
            String hours = df.format(num);//返回的是String类型
            strs[0] = String.valueOf(hours);
            strs[1] = hour + "时" + min + "分" + sec + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }


        return strs;
    }


    /***
     * 将某时分秒时间转换成当前日的时分秒时间
     * by xiaopeng
     * @param hhmmss
     * @return
     */
    public static Date getNowDateByHHMMSS(Date hhmmss) {
        Date returnTime = null;
        if (hhmmss != null) {
            String toHHMMSSTimeStr = DateUtil.stringDateToHHMMSS(hhmmss);
            String nowTimeStr = DateUtil.formatDate(new Date());
            returnTime = DateUtil.stringDate(nowTimeStr + " " + toHHMMSSTimeStr);
        }
        return returnTime;
    }


    /**
     * @param time 指定时间
     * @param str  指定时间的与当前时间相比较的状态 before/after
     * @Title: specifiedTimeState
     * @Description: 判断是否在指定时间的指定状态之内
     * @author: szc
     * @date: 2017年8月9日 下午5:28:28
     * @return: boolean
     * @since V 2.0
     */
    public static boolean specifiedTimeState(String time, String str) {
        String nowDate = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());// 获取当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date nowdate = sdf.parse(nowDate);
            Date specifiedTime = sdf.parse(time);
            if (null != str && !"".equals(str)) {
                if ("before".equals(str)) {
                    return nowdate.before(specifiedTime);
                } else {
                    return nowdate.after(specifiedTime);
                }
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if ("00:00".equals(args[1])) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
    }

    /**
     * 获得月份相加
     *
     * @param date
     * @param count
     * @return
     */
    public static Date addMonth(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, count);
        return c.getTime();
    }

    /**
     * 获得月份最后一天的当前时间
     *
     * @param date
     * @return
     */
    public static Date getLastDayDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        //c.add(Calendar.DAY_OF_MONTH, -1);

        return c.getTime();
    }

    /**
     * 计算2个时间差  多少分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        //return day + "天" + hour + "小时" + min + "分钟";
        return min;
    }

    /**
     * @Title: getThisMonthStartAndEndTime
     * @Description: 获取当月的开始时间和结束时间
     * @author: szc
     * @date: 2018年10月23日 下午4:28:29
     * @return: String[]
     * @since V 1.0
     */
    public static String[] getThisMonthStartAndEndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String[] startAndEndDate = new String[2];//定义数组用于存放起始时间[0]和结束时间[1]
        //获取当月前的月的起始时间和结束时间
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);//设置当月的起始时间
        startAndEndDate[0] = formatter.format(calendar.getTime());//存放到数组中
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(
                Calendar.DAY_OF_MONTH));//设置当月结束天为当月的最大天，如：9月份最大天为30，此时设置天为30
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE), 23, 59, 59);//设置当月的结束时间
        startAndEndDate[1] = formatter.format(calendar.getTime());//存放到数组中
        return startAndEndDate;

    }

    /**
     * @param year
     * @param month
     * @Title: getBeginTime
     * @Description: 获取指定年月的开始时间
     * @author: szc
     * @date: 2018年10月23日 下午4:32:32
     * @return: Date
     * @since V 1.0
     */
    public static Date getBeginTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * @param year
     * @param month
     * @Title: getEndTime
     * @Description: 获取指定年月的结束时间
     * @author: szc
     * @date: 2018年10月23日 下午4:32:58
     * @return: Date
     * @since V 1.0
     */
    public static Date getEndTime(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * 功能：得到当前月份月初 格式为：xxxx-yy-zz
     *
     * @return
     */
    public static Date getTimeMonthFrist() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

    /**
     * 获取当前月份中能开发票最后日期：每月的26号23：59
     *
     * @param
     * @throws ParseException
     */
    public static Date getTimeMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        //cale.set(Calendar.DAY_OF_MONTH, -3);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

    /**
     * 获取时间前的几天的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date beforeDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -day);
        return calendar.getTime();
    }

    /**
     * @param timeStr 带有AM 后者PM 的时间字符串 "6:55:33 PM"
     * @return 18:55:33
     */
    public static String stringWithAMorPMtoString(String timeStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa", Locale.ENGLISH);
        Date orgTime = null;
        try {
            orgTime = sdf.parse(timeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        String newTimeString = sdf2.format(orgTime);
        return newTimeString;
    }

    public static String getCurrentDateDMY() {
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        return df.format(new Date());
    }


    public static String getCurrentDateHms() {
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        return df.format(new Date());
    }

    /**
     * 获取几分钟前的时间
     *
     * @param d      指定的日期
     * @param minute 指定的分钟数
     * @return
     */
    public static Date lessMinutes(Date d, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, -minute);
        return calendar.getTime();
    }

    /**
     * 获取 两个时间相差 年月日时分秒
     *
     * @param currentTime
     * @param firstTime
     * @return
     */
    public static String getSubtractTime(Date currentTime, Date firstTime) {
        StringBuffer timeStr = new StringBuffer();
        long diff = currentTime.getTime() - firstTime.getTime();//这样得到的差值是微秒级别
        Calendar currentTimes = dataToCalendar(currentTime);//当前系统时间转Calendar类型
        Calendar firstTimes = dataToCalendar(firstTime);//查询的数据时间转Calendar类型
        int year = currentTimes.get(Calendar.YEAR) - firstTimes.get(Calendar.YEAR);//获取年
        int month = currentTimes.get(Calendar.MONTH) - firstTimes.get(Calendar.MONTH);
        int day = currentTimes.get(Calendar.DAY_OF_MONTH) - firstTimes.get(Calendar.DAY_OF_MONTH);
        if (day < 0) {
            month -= 1;
            currentTimes.add(Calendar.MONTH, -1);
            day = day + currentTimes.getActualMaximum(Calendar.DAY_OF_MONTH);//获取日
        }
        if (month < 0) {
            month = (month + 12) % 12;//获取月str
            year--;
        }
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //获取时
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);  //获取分钟
        long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);//获取秒
        if (year > 0) {
            timeStr.append(year + "年");
        }
        if (month > 0) {
            timeStr.append(month + "月");
        }
        if (days > 0) {
            timeStr.append(days + "天");
        }
        if (hours > 0) {
            timeStr.append(hours + "时");
        }
        if (minutes > 0) {
            timeStr.append(minutes + "分");
        }
        if (s > 0) {
            timeStr.append(s + "秒");
        }
        return timeStr.toString();
    }

    //Date类型转Calendar类型
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date initDateByDay(Date date, Integer hour, Integer minutr, Integer second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (null != hour) {
            calendar.set(Calendar.HOUR_OF_DAY, hour);
        }
        if (null != minutr) {
            calendar.set(Calendar.MINUTE, minutr);
        }
        if (null != second) {
            calendar.set(Calendar.SECOND, second);
        }
        return calendar.getTime();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * 获取月份 于真实月份少1
     *
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);

        return cal1.get(Calendar.MONTH);
    }

    /**
     * 获取真实月份
     *
     * @param date
     * @return
     */
    public static Integer getMonthReal(Date date) {
        return getMonth(date) + 1;
    }

    /**
     * Date转LocalDate
     *
     * @param date
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = dateFormat.parse("2021-05-23 23:59:59");
        Date parse1 = dateFormat.parse("2020-11-30 23:59:59");
        System.out.println(getDayOfWeekV2(parse));
    }

}

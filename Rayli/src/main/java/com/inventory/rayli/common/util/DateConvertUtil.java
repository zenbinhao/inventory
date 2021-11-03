package com.inventory.rayli.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateConvertUtil {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String[] PARSE_PATTERNS = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy年MM月dd日", "yyyy年MM月", "yyyy年MM月dd日HH点mm分", "yyyy.MM.dd", "yyyy.M.d", "yyyy.MM.d", "yyyy.M.dd"};

    public DateConvertUtil() {
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            pattern = StringUtils.isNotBlank(pattern) ? pattern : "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
    }

    public static Date parseDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            try {
                return DateUtils.parseDate(date, PARSE_PATTERNS);
            } catch (ParseException var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }

    public static Date parseDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        } else {
            String[] parsePatterns = StringUtils.isNotBlank(pattern) ? new String[]{pattern} : PARSE_PATTERNS;

            try {
                return DateUtils.parseDate(date, parsePatterns);
            } catch (ParseException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static Date getMonthLastDay(String date) {
        Calendar ca = Calendar.getInstance();
        if (!StringUtils.isEmpty(date)) {
            ca.setTime(parseDate(date));
        }

        ca.set(5, ca.getActualMaximum(5));
        return ca.getTime();
    }

    public static int countDays(Date startDate, Date endDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(endDate);
        int day1 = cal1.get(6);
        int day2 = cal2.get(6);
        int year1 = cal1.get(1);
        int year2 = cal2.get(1);
        if (year1 == year2) {
            return day2 - day1;
        } else {
            int timeDistance = 0;

            for(int i = year1; i < year2; ++i) {
                if (i % 400 == 0) {
                    timeDistance += 366;
                } else if (i % 4 == 0 && i % 100 != 0) {
                    timeDistance += 366;
                } else {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        }
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        int w = calendar.get(7) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekOfDays[w];
    }

    public static String format(LocalDate date, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        String dateStr = date.format(fmt);
        return dateStr;
    }

    public static String format(LocalDateTime date, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        String dateStr = date.format(fmt);
        return dateStr;
    }

    public static Date localDate2Date(LocalDate localDate) {
        if (null == localDate) {
            return null;
        } else {
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            return Date.from(zonedDateTime.toInstant());
        }
    }

    public static LocalDate date2LocalDate(Date date) {
        return null == date ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public interface PatternType {
        String DATE = "yyyy-MM-dd";
        String YEAR_MONTH = "yyyy-MM";
        String DATETIME = "yyyy-MM-dd HH:mm:ss";
        String DATETIME_MM = "yyyy-MM-dd HH:mm";
        String DATETIME_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
        String TIME = "HH:mm";
        String TIME_SS = "HH:mm:ss";
        String SYS_DATE = "yyyy/MM/dd";
        String SYS_DATETIME = "yyyy/MM/dd HH:mm:ss";
        String SYS_DATETIME_MM = "yyyy/MM/dd HH:mm";
        String SYS_DATETIME_SSS = "yyyy/MM/dd HH:mm:ss.SSS";
        String NONE_DATE = "yyyyMMdd";
        String NONE_DATETIME = "yyyyMMddHHmmss";
        String NONE_DATETIME_MM = "yyyyMMddHHmm";
        String NONE_DATETIME_SSS = "yyyyMMddHHmmssSSS";
        String YEAR = "yyyy";
        String NONE_YEAR_MONTH = "yyyyMM";
        String CHINESE_DATETIME_MM = "yyyy年MM月dd日HH点mm分";
        String CHINESE_DATE = "yyyy年MM月dd日";
        String CHINESE_YEAR_MONTH = "yyyy年MM月";
        String DOT_YYYY_MM_DD = "yyyy.MM.dd";
        String DOT_YYYY_M_D = "yyyy.M.d";
        String DOT_YYYY_MM_D = "yyyy.MM.d";
        String DOT_YYYY_M_DD = "yyyy.M.dd";
    }
}
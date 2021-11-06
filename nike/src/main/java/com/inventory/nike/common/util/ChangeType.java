package com.inventory.nike.common.util;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 格式转换
 *
 */
public class ChangeType {

    // 用来全局控制 上一周，本周，下一周的周数变化
    private int weeks = 0;
    private int MaxDate;// 一月最大天数
    private int MaxYear;// 一年最大天数

    /**
     * 定义数组存放数字对应的大写
     */
    private final static String[] STR_NUMBER = {"零", "壹", "贰", "叁", "肆", "伍",
            "陆", "柒", "捌", "玖"};

    /**
     * 定义数组存放位数的大写
     */
    private final static String[] STR_MODIFY = {"", "拾", "佰", "仟", "万", "拾",
            "佰", "仟", "亿", "拾", "佰", "仟"};

    /**
     * 转化整数部分
     *
     * @param tempString
     * @return 返回整数部分
     */
    private static String getInteger(String tempString) {
        /** 用来保存整数部分数字串 */
        String strInteger = null;//
        /** 记录"."所在位置 */
        int intDotPos = tempString.indexOf(".");
        int intSignPos = tempString.indexOf("-");
        if (intDotPos == -1)
            intDotPos = tempString.length();
        /** 取出整数部分 */
        strInteger = tempString.substring(intSignPos + 1, intDotPos);
        strInteger = new StringBuffer(strInteger).reverse().toString();
        StringBuffer sbResult = new StringBuffer();
        for (int i = 0; i < strInteger.length(); i++) {
            sbResult.append(STR_MODIFY[i]);
            sbResult.append(STR_NUMBER[strInteger.charAt(i) - 48]);
        }

        sbResult = sbResult.reverse();
        replace(sbResult, "零拾", "零");
        replace(sbResult, "零佰", "零");
        replace(sbResult, "零仟", "零");
        replace(sbResult, "零万", "万");
        replace(sbResult, "零亿", "亿");
        replace(sbResult, "零零", "零");
        replace(sbResult, "零零零", "零");
        /** 这两句不能颠倒顺序 */
        replace(sbResult, "零零零零万", "");
        replace(sbResult, "零零零零", "");
        /** 这样读起来更习惯. */
        replace(sbResult, "壹拾亿", "拾亿");
        replace(sbResult, "壹拾万", "拾万");
        /** 删除个位上的零 */
        if (sbResult.charAt(sbResult.length() - 1) == '零'
                && sbResult.length() != 1)
            sbResult.deleteCharAt(sbResult.length() - 1);
        if (strInteger.length() == 2) {
            replace(sbResult, "壹拾", "拾");
        }
        /** 将结果反转回来. */
        return sbResult.toString();
    }

    /**
     * 转化小数部分 例：输入22.34返回叁肆
     *
     * @param tempString
     * @return
     */
    private static String getFraction(String tempString) {
        String strFraction = null;
        int intDotPos = tempString.indexOf(".");
        /** 没有点说明没有小数，直接返回 */
        if (intDotPos == -1)
            return "";
        strFraction = tempString.substring(intDotPos + 1);
        StringBuffer sbResult = new StringBuffer(strFraction.length());
        for (int i = 0; i < strFraction.length(); i++) {
            sbResult.append(STR_NUMBER[strFraction.charAt(i) - 48]);
        }
        return sbResult.toString();
    }

    /**
     * 判断传入的字符串中是否有.如果有则返回点
     *
     * @param tempString
     * @return
     */
    private static String getDot(String tempString) {
        return tempString.indexOf(".") != -1 ? "点" : "";
    }

    /**
     * 判断传入的字符串中是否有-如果有则返回负
     *
     * @param tempString
     * @return
     */
    private static String getSign(String tempString) {
        return tempString.indexOf("-") != -1 ? "负" : "";
    }

    /**
     * 将一个数字转化为金额
     *
     * @param tempNumber 传入一个double的变量
     * @return 返一个转换好的字符串
     */
    public static String numberToChinese(double tempNumber) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#########");
        String pTemp = String.valueOf(df.format(tempNumber));
        StringBuffer sbResult = new StringBuffer(getSign(pTemp)
                + getInteger(pTemp) + getDot(pTemp) + getFraction(pTemp));
        return sbResult.toString();
    }

    public static String numberToChinese(BigDecimal tempNumber) {
        return numberToChinese(tempNumber.doubleValue());
    }

    /**
     * 替代字符
     *
     * @param pValue
     * @param pSource
     * @param pDest
     */
    private static void replace(StringBuffer pValue, String pSource,
                                String pDest) {
        if (pValue == null || pSource == null || pDest == null)
            return;
        /** 记录pSource在pValue中的位置 */
        int intPos = 0;
        do {
            intPos = pValue.toString().indexOf(pSource);
            /** 没有找到pSource */
            if (intPos == -1)
                break;
            pValue.delete(intPos, intPos + pSource.length());
            pValue.insert(intPos, pDest);
        } while (true);
    }

    /**
     * 日期 获取当前时间
     *
     * @return
     */
    public Date getCurrentTime() {
        Date now = new Date();
        return now;

    }

    public static Date getCurrentDate() {
        return new java.sql.Date(new Date().getTime());
    }

    /**
     * 转换null为""
     *
     * @param value要转换的值
     * @return
     */
    public static String IsNull(String value) {
        if (null != value && !value.equals("null")) {
            return value;
        }
        return "";
    }

    /**
     * 今天年-月-日
     *
     * @return
     */
    public static String YearMonthDay() {
        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(now);

    }

    /**
     * 取昨天的值 格式：年-月-日
     *
     * @return
     */
    public static String YesterdayYearMonthDay() {
        Date Yesterday = new Date(System.currentTimeMillis() - 1000 * 60 * 60
                * 24);
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(Yesterday);
    }

    /**
     * 取明天的值,格式，年-月-日
     *
     * @return
     */
    public static String TomorrowYearMonthDay() {
        Date Yesterday = new Date(System.currentTimeMillis() + 1000 * 60 * 60
                * 24);
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(Yesterday);
    }

    public static Date StringTrunDate(String value) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.parse(value);

    }

    public static Date StringTrunDateTime(String value) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.parse(value);

    }

    /**
     * @param cycle
     * @return
     * @throws ParseException
     * @Description: 根据类型得到时间节点
     * @author: Mrli
     * @date: 2015-5-17
     */
    public static String calculationCycleDate(String cycle)
            throws ParseException {
        ChangeType cType = new ChangeType();
        String startDate = null;// 开始时间
        String endDate = null;// 结束时间
        // 1.得到当前日期
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        String day = myFmt.format(new Date());
        if (cycle.equals("0")) {// 判断是否等于当天(0-当天 1-本周 2-下周 3-上周)
            startDate = day;
            endDate = day;
        } else if (cycle.equals("1")) {// 判断是否等于本周(0-当天 1-本周 2-下周 3-上周)
            startDate = cType.getMondayOFWeek();// 本周周一
            endDate = cType.getCurrentWeekday();// 本周周末
        } else if (cycle.equals("2")) {// 判断是否等于下周(0-当天 1-本周 2-下周 3-上周)
            startDate = cType.getNextMonday();// 下周周一
            endDate = cType.getNextSunday();// 下周周末
        } else if (cycle.equals("3")) {
            startDate = cType.getPreviousWeekday();// 上周周一
            endDate = cType.getPreviousWeekSunday();// 上周周末
        } else if (cycle.equals("4")) {
            startDate = YesterdayYearMonthDay();// 昨天
            endDate = YesterdayYearMonthDay();// 昨天
        } else if (cycle.equals("5")) {
            startDate = getNextDay();// 前天
            endDate = getNextDay();// 前天
        }
        cycle = startDate + "," + endDate;
        return cycle;
    }

    /**
     * 时间转字符串
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public static String DateTurnString(Date value) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(value);
    }

    public static String DateTurnMoth(Date value) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM");
        return myFmt.format(value);
    }

    public static String DateTurnDay(Date value) throws ParseException {
        SimpleDateFormat myFmt = new SimpleDateFormat("dd");
        return myFmt.format(value);
    }

    public static Map<String, String> commonReportDate(Date date, String value)
            throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String yearStart = "";
        String yearEnd = "";
        String monthStart = "";
        String monthEnd = "";
        String tomorrowMonth = "";
        String tomorrowYear = "";
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        String years = sdfY.format(date) + "-12-" + value;
        String months = sdfYM.format(date) + "-" + value;

        if (date.getTime() < sdfYMD.parse(years).getTime()) {
            yearStart = (Integer.valueOf(sdfY.format(date)) - 1) + "-12-"
                    + value;
            if (date.getTime() < sdfYMD.parse(months).getTime()) {
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                monthStart = sdfYM.format(c.getTime()) + "-" + value;
            } else {
                monthStart = sdfYM.format(date) + "-" + value;
            }
            c.setTime(sdfYMD.parse(monthStart));
            c.add(Calendar.MONTH, 1);
            monthEnd = sdfYMD.format(c.getTime());
        } else {
            yearStart = sdfY.format(date) + "-12-" + value;
            if (date.getTime() < sdfYMD.parse(months).getTime()) {
                c.setTime(date);
                c.add(Calendar.MONTH, -1);
                monthStart = sdfYM.format(c.getTime()) + "-" + value;
            } else {
                monthStart = sdfYM.format(date) + "-" + value;
            }
            c.setTime(sdfYMD.parse(monthStart));
            c.add(Calendar.MONTH, 1);
            monthEnd = sdfYMD.format(c.getTime());
        }
        c.setTime(sdfYMD.parse(yearStart));
        c.add(Calendar.YEAR, 1);
        yearEnd = sdfYMD.format(c.getTime());

        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);// 计算选中日期下一天的日期
        tomorrowMonth = sdfYMD.format(c.getTime());

        tomorrowYear = monthEnd;// 计算年累的结束日期

        map.put("startYear", yearStart);
        map.put("endYear", yearEnd);
        map.put("startMonth", monthStart);
        map.put("endMonth", monthEnd);// 月报的月累结束日期
        map.put("tomorrowMonth", tomorrowMonth);// 日报的年累与月累结束日期
        map.put("tomorrowYear", tomorrowYear);// 月报的年累结束日期
        return map;
    }

    public static String zeroToNull(String value) {
        if (value.equals("0") || value.equals("0.0")) {
            return "";
        }
        return value;
    }

    public static List<Map<String, String>> circleSpaceDate(String startDate,
                                                            String endDate, String value) throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String yearStart = "";// 年开始时间
        String yearEnd = "";// 年结束时间
        String monthStart = "";// 月开始时间
        String monthEnd = "";// 月结束时间
        SimpleDateFormat sdfYM = new SimpleDateFormat("yyyy-MM");
        // SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfY = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdfYM.parse(startDate));
        while (!(sdfYM.parse(endDate)).before(c.getTime())) {
            Map<String, String> map = new HashMap<String, String>();
            yearStart = Integer.valueOf(sdfY.format(c.getTime())) - 1 + "-12-"
                    + value;
            yearEnd = sdfYM.format(c.getTime()) + "-" + value;
            monthEnd = yearEnd;
            c.add(Calendar.MONTH, -1);
            monthStart = sdfYM.format(c.getTime()) + "-" + value;
            map.put("yearStart", yearStart);
            map.put("yearEnd", yearEnd);
            map.put("monthStart", monthStart);
            map.put("monthEnd", monthEnd);
            list.add(map);
            c.add(Calendar.MONTH, 2);

        }
        return list;
    }

    /**
     * 时间转毫秒数
     *
     * @throws ParseException
     */
    public static long hhmmTurnTime(Date value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String str = sdf.format(value);
        SimpleDateFormat rd = new SimpleDateFormat("yyyyMMddHHmm");
        long returnlong = rd.parse(str).getTime();// 毫秒
        return returnlong;
    }

    /**
     * 合成时间
     *
     * @return
     * @throws ParseException
     */
    public static long syntheticDate(String dm, long hm) throws ParseException {
        long relong = 0;
        SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd");
        long dml = rd.parse(dm).getTime();// 毫秒
        relong = dml + hm;
        return relong;
    }

    /**
     * 时间差
     *
     * @param start
     * @param end
     * @return
     */
    public static long timepoor(long start, long end) {
        long minutes = 0;
        minutes = (end - start) / (1000 * 60);
        return minutes;
    }

    public static long datePoor(Date d) {
        long dates = 0;
        long start = new Date().getTime();
        long end = d.getTime();
        dates = (end - start) / (1000 * 60 * 60 * 24);
        return dates;
    }

    @SuppressWarnings("static-access")
    public static String dateToStringpus(int ps) {
        String r = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(c.DATE, ps);
        SimpleDateFormat rd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        r = String.valueOf(rd.format(c.getTime()));
        return r;
    }

    public static float obejctToFloat(Object o) {
        if (null == o) {
            return 0;
        } else {
            return Float.valueOf(String.valueOf(o));
        }
    }

    /**
     * 百分比
     *
     * @param value
     * @return
     */
    public static String percentage(float value) {
        if (value < 0.5 && value > 0) {
            return "<0.1%";
        }
        BigDecimal rv = new BigDecimal(value).setScale(1,
                BigDecimal.ROUND_HALF_UP);
        float rvi = Float.valueOf(String.valueOf(rv));
        if (rvi > 0) {
            return rv + "%";
        }
        return "0%";
    }

    public static String percentage(Object value) {
        if (null == value) {
            return "0%";
        }
        float fv = Float.valueOf(String.valueOf(value));
        return percentage(fv);
    }

    public static String percentagegh(float value, float value2) {
        if (value2 == 0) {
            return "（0%）";
        }
        float va = value / value2 * 100;

        if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
            return "（0%）";
        }
        if (va < 0.1) {
            return "（<0.1%）";
        }
        return "（" + String.format("%.2f", va) + "%）";
    }

    public static String percentagegh(Object value, Object value2) {
        if (null == value || null == value2) {
            return "（0%）";
        }
        float fv = Float.valueOf(String.valueOf(value));
        float fv2 = Float.valueOf(String.valueOf(value2));
        return percentagegh(fv, fv2);
    }

    public static String percentagecs(float value, float value2) {
        if (value2 == 0) {
            return "0%";
        }
        float va = value / value2 * 100;
        if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
            return "0%";
        }
        if (va < 0.1) {
            return "<0.1%";
        }
        return String.format("%.2f", va) + "%";
    }

    public static String percentagecs(Object value, Object value2) {
        if (null == value || null == value2) {
            return "0%";
        }
        float fv = Float.valueOf(String.valueOf(value));
        float fv2 = Float.valueOf(String.valueOf(value2));
        return percentagecs(fv, fv2);
    }

    /**
     * 保留两位小数
     */
    public static String towFormat(float value) {
        if (value == 0.0 || value == 0 || value == -0.0 || value == -0) {
            return "0";
        }
        return String.format("%.2f", value);
    }

    public static String towFormat(Object value) {
        if (null == value) {
            return "0";
        }
        float fv = Float.valueOf(String.valueOf(value));
        return towFormat(fv);
    }

    public static String towFormatgh(float value, float value2) {
        if (value2 == 0) {
            return "（0）";
        }
        float va = value / value2;
        if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
            return "（0）";
        }
        return "（" + String.format("%.2f", va) + "）";
    }

    public static String towFormatgh(Object value, Object value2) {
        if (null == value || null == value2) {
            return "（0）";
        }
        float fv = Float.valueOf(String.valueOf(value));
        float fv2 = Float.valueOf(String.valueOf(value2));
        return towFormatgh(fv, fv2);
    }

    public static String towFormatcs(float value, float value2) {
        if (value2 == 0) {
            return "0";
        }
        float va = value / value2;
        if (va == 0.0 || va == 0 || va == -0.0 || va == -0) {
            return "0";
        }
        return String.format("%.2f", va);
    }

    public static String towFormatcs(Object value, Object value2) {
        if (null == value || null == value2) {
            return "0";
        }
        float fv = Float.valueOf(String.valueOf(value));
        float fv2 = Float.valueOf(String.valueOf(value2));
        return towFormatcs(fv, fv2);
    }

    /***
     * MD5加密
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String EncoderByMd5(String str) throws Exception {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        char hexDigits[] = {'0', '1', '&', '2', '3', '4', 'A', 'B', 'C', 'D',
                'E', 'F', '5', '6', '7', '8', '9', '#', '*'};
        try {
            byte[] btInput = inStr.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param str
     * @return
     * @Description: 处理流水号的编号
     * @author: Enzo
     * @date: 2015-5-19
     */
    public static String codeConversion(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        if (str.length() < 8) {// 如果传入的编号长度小于8
            str += year + "0001";
        } else {
            if (str.substring(1, 2).matches("[0-9]{1,}")) {
//			        String str1 = str.substring(0, str.length() - 8);// 编号前缀
                String str1 = str.substring(0, 1);// 编号前缀
//                  String str2 = str.substring(str.length() - 8, str.length() - 4);// 编号年份
                String str2 = str.substring(1, 5);// 编号年份
//                  String str3 = str.substring(str.length() - 4, str.length());// 流水号
                String str3 = str.substring(5, str.length());// 流水号
//			        int number = Integer.parseInt(str3) + 1;
                int number = Integer.valueOf(str3) + 1;
                str3 = number + "";
                if (number < 10) {// 如果流水号小于10补0
                    str3 = "0" + str3;
                }
                if (number < 100) {// 如果流水号小于100补0
                    str3 = "0" + str3;
                }
                if (number < 1000) {// 如果流水号小于1000补0
                    str3 = "0" + str3;
                }
                if (!str2.equals(year)) {// 如果编号的年份不是今年的，那么编号从今年的1号开始重新编号
                    str2 = year;
                    str3 = "0001";
                }
                str = str1 + str2 + str3;
            } else {
//			        String str1 = str.substring(0, str.length() - 8);// 编号前缀
                String str1 = str.substring(0, 2);// 编号前缀
//                  String str2 = str.substring(str.length() - 8, str.length() - 4);// 编号年份
                String str2 = str.substring(2, 6);// 编号年份
//                  String str3 = str.substring(str.length() - 4, str.length());// 流水号
                String str3 = str.substring(6, str.length());// 流水号
//			        int number = Integer.parseInt(str3) + 1;
                int number = Integer.valueOf(str3) + 1;
                str3 = number + "";
                if (number < 10) {// 如果流水号小于10补0
                    str3 = "0" + str3;
                }
                if (number < 100) {// 如果流水号小于100补0
                    str3 = "0" + str3;
                }
                if (number < 1000) {// 如果流水号小于1000补0
                    str3 = "0" + str3;
                }
                if (!str2.equals(year)) {// 如果编号的年份不是今年的，那么编号从今年的1号开始重新编号
                    str2 = year;
                    str3 = "0001";
                }
                str = str1 + str2 + str3;
            }
        }


        return str;
    }

    /**
     * @param date
     * @return
     * @Description: 根据日期 得到周期
     * @author: Mrli
     * @date: 2015-5-25
     */
    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    /**
     * @param dBegin
     * @param dEnd
     * @return
     * @Description: 根据时段 编辑时间
     * @author: Mrli
     * @date: 2015-5-25
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }

    /**
     * 得到二个日期间的间隔天数
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = ChangeType.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    // 计算当月最后一天,返回字符串
    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 上月第一天
    public String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取当月第一天
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得本周星期日的日期
    public String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获取当天时间
    public String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    // // 获得当前日期与本周日相差的天数
    // private int getMondayPlus() {
    // Calendar cd = Calendar.getInstance();
    // // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    // int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
    // if (dayOfWeek == 1) {
    // return -6;
    // } else {
    // return 1 - dayOfWeek;
    // }
    // }

    // 获得当前日期与本周一相差的天数
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    // 获得本周一的日期
    public String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得相应周的周六的日期
    public String getSaturday() {
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期日的日期
    public String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得上周星期一的日期
    public String getPreviousWeekday() {
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期一的日期
    public String getNextMonday() {
        weeks++;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期日的日期
    public String getNextSunday() {

        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    @SuppressWarnings("unused")
    private int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    // 获得上月最后一天的日期
    public String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得下个月第一天的日期
    public String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得下个月最后一天的日期
    public String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得明年最后一天的日期
    public String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获得明年第一天的日期
    public String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    // 获得本年有多少天
    @SuppressWarnings("unused")
    private int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    // 获得本年第一天的日期
    public String getCurrentYearFirst() {
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    // 获得本年最后一天的日期 *
    public String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // 获得上年第一天的日期 *
    public String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // 获得上年最后一天的日期
    public String getPreviousYearEnd() {
        weeks--;
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
                + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    // 获得本季度
    public String getThisSeasonTime(int month) {
        int array[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days
                + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 最后一天
     */
    private int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 将一个日期字符串转化成日期
    public static Date switchStringToDate(String sDate) {
        Date date = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.parse(sDate);
        } catch (Exception e) {
            System.out.println("日期转换失败:" + e.getMessage());
        }
        return date;
    }

    /**
     * 比较时间大小
     *
     * @param s1
     * @param s2 s1>s2 true s1<s2 false
     * @return
     */
    public static boolean compare(String s1, String s2) {
        Date dateFrom = switchStringToDate(s1);
        Date dateTo = switchStringToDate(s2);
        if (dateFrom.before(dateTo)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param date
     * @param n
     * @return
     * @throws ParseException
     * @Description: date日期向前推n天的日期
     * @author: Mrli
     * @date: 2015-5-26
     */
    @SuppressWarnings("static-access")
    public static String validDate(String date, int n) throws ParseException {
        Calendar c = Calendar.getInstance();
        String currdateTime[] = date.split("-");
        c.set(Integer.parseInt(currdateTime[0]),
                Integer.parseInt(currdateTime[1]) - 1,
                Integer.parseInt(currdateTime[2]));
        c.add(c.DATE, -n);
        return "" + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-"
                + c.get(c.DATE);
    }

    /**
     * @param date
     * @param cycleState
     * @return
     * @throws ParseException
     * @Description: 计算日志编辑
     * @author: Mrli
     * @date: 2015-5-25
     */
    public static String calculationEditTime(String day, String cycleState)
            throws ParseException {
        String data = null;
        String lastWeek = null;
        String nextWeek = null;
        // 得到天数
        int time = Integer.valueOf(day) - 1;
        double carryTime = (float) time / 2;
        time = time / 2;
        if (carryTime > time) {// 判断结果是否大于正整数
            if (cycleState.equals("2")) {// 下周
                lastWeek = String.valueOf(time);
                nextWeek = String.valueOf(time);
            } else if (cycleState.equals("3")) {// 上周
                lastWeek = String.valueOf(time + 1);
                nextWeek = String.valueOf(time);
            } else {
                lastWeek = String.valueOf(time);
                nextWeek = String.valueOf(time);
            }
        } else {// 相等情况下
            lastWeek = String.valueOf(time);
            nextWeek = String.valueOf(time);
        }
        data = lastWeek + "," + nextWeek;
        return data;
    }

    /**
     * @param day
     * @param cycleState
     * @return
     * @Description: 遍历时间(根据day天数进行遍历)
     * @author: Mrli
     * @date: 2015-5-25
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    public static List<Date> iterateTime(String day, String cycleState) {
        List<Date> lDate = new ArrayList();
        List<Date> luDate = new ArrayList();
        Date date = new Date();
        lDate.add(date);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Calendar calendarj = new GregorianCalendar();
        calendarj.setTime(date);
        int i = 0;
        int j = 0;
        if (cycleState.equals("2")) {// 下周
            while (i < Integer.valueOf(day)) {
                calendar.add(calendar.DATE, 1);
                date = calendar.getTime();
                lDate.add(date);
                i++;
            }
        } else if (cycleState.equals("3")) {// 上周
            while (i < Integer.valueOf(day)) {
                calendar.add(calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                lDate.add(date);
                i++;
            }
            Collections.reverse(lDate);
            for (Date ludate : lDate) {
                luDate.add(ludate);
            }
        } else if (cycleState.equals("4")) {
            while (j < Integer.valueOf(day)) {
                calendar.add(calendar.DAY_OF_MONTH, -1);
                date = calendar.getTime();
                lDate.add(date);
                j++;
            }
            Collections.reverse(lDate);
            for (Date ludate : lDate) {
                luDate.add(ludate);
            }

            while (i < Integer.valueOf(day)) {
                calendarj.add(calendarj.DATE, 1);
                date = calendarj.getTime();
                lDate.add(date);
                i++;
            }
        }
        return lDate;
    }

    public static void main(String[] args) throws ParseException {
        ChangeType ct = new ChangeType();
        System.out.println(ct.getMondayOFWeek());
    }

    /**
     * @param oldPath
     * @param newPath
     * @Description: 文件转移
     * @author: Administrator
     * @date: 2015-6-3
     */
    @SuppressWarnings("unused")
    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @return
     * @Description: 前天
     * @author: Administrator
     * @date: 2015-6-30
     */
    public static String getNextDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        date = calendar.getTime();
        return sf.format(date);
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @Description: 比较时间大小
     * @author: Administrator
     * @date: 2015-7-7
     */
    public static int isDateBefore(String date1, String date2) {
        int count = 0;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                count = 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                count = -1;
            } else {
                count = 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * @param name
     * @return
     * @Description: 处理name值, 当为2个字符的时候中间设置空格
     * @author: Mrli
     * @date: 2015-8-14
     */
    public static String handleUserName(String name) {
		/*if(name.length()==2){
			name = name.substring(0,1)+"　"+name.substring(1,2);
		}*/
        return name;
    }



    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
//————————————————
//    版权声明：本文为CSDN博主「zxm_9783691」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//    原文链接：https://blog.csdn.net/zxm_9783691/article/details/107248011


    public static List<String> findDatesList(String dBegin, String dEnd) throws ParseException, ParseException {
        //日期工具类准备
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //设置开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));

        //装返回的日期集合容器
        List<String> Datelist = new ArrayList<String>();
        //将第一个月添加里面去
        Datelist.add(format.format(calBegin.getTime()));
        // 每次循环给calBegin日期加一天，直到calBegin.getTime()时间等于dEnd
        while (format.parse(dEnd).after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            Datelist.add(format.format(calBegin.getTime()));
        }

        System.out.println(Datelist);
        return Datelist;
    }



}

package com.inventory.rayli.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpValidator {
    private static String[][] FILTER_CHARS = new String[][]{{"<", "&lt;"}, {">", "&gt;"}, {" ", "&nbsp;"}, {"\"", "&quot;"}, {"&", "&amp;"}, {"/", "&#47;"}, {"\\", "&#92;"}, {"\n", "<br>"}};
    private static String[][] FILTER_SCRIPT_CHARS = new String[][]{{"\n", "'+'\\n'+'"}, {"\r", " "}, {"\\", "'+'\\\\'+'"}, {"'", "'+'\\''+'"}};
    private static String sqlFilterChar;

    public RegExpValidator() {
    }

    public static boolean isIntNumber(String str) {
        return match("^\\+?[1-9][0-9]*$", str);
    }

    public static boolean isPositiveDecimalNumber(String str) {
        return match("^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$", str);
    }

    private static boolean match(String regex, String str) {
        return getMatcher(regex, str).matches();
    }

    private static Matcher getMatcher(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    public static boolean isEmail(String str) {
        return match("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", str);
    }

    public static boolean isMobile(String str) {
        return match("^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$", str);
    }

    public static boolean isTelPhone(String string) {
        return match("([0-9]{3,4}-)?[0-9]{7,8}", string);
    }

    public static boolean isIp(String str) {
        String num = "(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)";
        String regex = "^((?:" + num + "\\.){3}" + num + ")$";
        return match("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", str);
    }

    public static boolean isPrice(String str) {
        return match("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,6})?$", str);
    }

    public static boolean isNumber(String str) {
        return match("^-?[0-9]+", str);
    }

    public static boolean isChineseChar(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isChinese(String values) {
        return match("^[一-龥]+$", values);
    }

    public static boolean isMessyCode(String strName) {
        String regex = "\\s*|\t*|\r*|\n*";
        Matcher m = getMatcher(regex, strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0.0F;
        float count = 0.0F;

        for(int i = 0; i < ch.length; ++i) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChineseChar(c)) {
                    ++count;
                }

                ++chLength;
            }
        }

        float result = count / chLength;
        double len = 0.4D;
        if ((double)result > len) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isUrlSite(String value) {
        String isUrl = "^((https|http|ftp|rtsp|mms)?://)?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z_!~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        return match(isUrl, value);
    }

    public static boolean isDate(String str) {
        return match("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", str);
    }

    public static boolean isDatetime(String str) {
        return match("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$", str);
    }

    public static boolean isTime(String str) {
        return match("^(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$", str);
    }

    public static boolean isSqlFilterChar(String str) {
        if (sqlFilterChar == null) {
            StringBuilder regex = new StringBuilder();
            regex.append("((");
            String[][] var2 = FILTER_CHARS;
            int var3 = var2.length;

            int var4;
            String[] arr;
            String[] var6;
            int var7;
            int var8;
            String item;
            for(var4 = 0; var4 < var3; ++var4) {
                arr = var2[var4];
                var6 = arr;
                var7 = arr.length;

                for(var8 = 0; var8 < var7; ++var8) {
                    item = var6[var8];
                    if (regex.length() > 2) {
                        regex.append("|");
                    }

                    regex.append("(.*");
                    regex.append(item);
                    regex.append(")");
                }
            }

            var2 = FILTER_SCRIPT_CHARS;
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
                arr = var2[var4];
                var6 = arr;
                var7 = arr.length;

                for(var8 = 0; var8 < var7; ++var8) {
                    item = var6[var8];
                    regex.append("(.*");
                    regex.append(item);
                    regex.append(")");
                }
            }

            regex.append(").*?|.*)");
            sqlFilterChar = regex.toString();
        }

        Pattern r = Pattern.compile(sqlFilterChar);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    public static boolean is18ByteIdCard(String str) {
        return match("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$", str);
    }

    public static boolean is18ByteIdCardComplex(String idCard) {
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
        Matcher matcher = pattern1.matcher(idCard);
        int[] prefix = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int[] suffix = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int cityLen = 2;
        int lastNum = 17;
        if (!matcher.matches()) {
            return false;
        } else {
            Map<String, String> cityMap = initCityMap();
            if (cityMap.get(idCard.substring(0, cityLen)) == null) {
                return false;
            } else {
                int idCardWiSum = 0;

                int idCardMod;
                for(idCardMod = 0; idCardMod < lastNum; ++idCardMod) {
                    idCardWiSum += Integer.valueOf(idCard.substring(idCardMod, idCardMod + 1)) * prefix[idCardMod];
                }

                idCardMod = idCardWiSum % 11;
                String idCardLast = idCard.substring(lastNum);
                String x = "x";
                if (idCardMod == cityLen) {
                    return idCardLast.equalsIgnoreCase(x);
                } else {
                    return idCardLast.equals(suffix[idCardMod] + "");
                }
            }
        }
    }

    public static int checkPasswordLevel(String str) {
        int level = 0;
        if (match("^.*(?=.{6,16})(?=.*\\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\\(\\)\\._\\+-]).*$", str)) {
            return 4;
        } else {
            if (match("^.*(?=.{6,16})(?=.*\\d).*$", str)) {
                ++level;
            }

            if (match("^.*(?=.{6,16})(?=.*[A-Z]{1,}).*$", str)) {
                ++level;
            }

            if (match("^.*(?=.{6,16})(?=.*[a-z]{1,}).*$", str)) {
                ++level;
            }

            if (match("^.*(?=.{6,16})(?=.*[!@#$%^&*?\\(\\)\\._\\+-]).*$", str)) {
                ++level;
            }

            return level;
        }
    }

    private static Map<String, String> initCityMap() {
        Map<String, String> cityMap = new HashMap(4);
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");
        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");
        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");
        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");
        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");
        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");
        cityMap.put("71", "台湾");
        cityMap.put("81", "香港");
        cityMap.put("82", "澳门");
        cityMap.put("91", "国外");
        return cityMap;
    }

    public static List<String> keywordMatch(String target, String[] keys) {
        String keyStr = StringUtils.join(keys, ")|(");
        String regex = "((" + keyStr + "))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(target);
        ArrayList matchKeys = new ArrayList();

        while(m.find()) {
            matchKeys.add(m.group());
        }

        return matchKeys;
    }

    public static void main(String[] args) {
        String[] arr = new String[]{"123456", "aaaaaa", "123aaa", "1234ZZZ", "123@123", "123AZa", "aaaaaZZZ", "ZZZZZZ", "123aaa@@@", "123AAAaaa", "123aaaZZZ@@@", "aa@1234Z", "aaa_Z12", "aaa+Z12", "aaa?Z12", "rrrAAA", "rrr%AAA", "rrr7AAA"};
        String[] var2 = arr;
        int var3 = arr.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String str = var2[var4];
            int a = checkPasswordLevel(str);
            System.out.println("[" + str + "]:" + a);
        }

    }

    public interface Regex {
        String MOBILE = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$";
        String INT_NUMBER = "^\\+?[1-9][0-9]*$";
        String POSITIVE_DECIMAL_NUMBER = "^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$";
        String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String TEL_PHONE = "([0-9]{3,4}-)?[0-9]{7,8}";
        String IP = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
        String DATETIME = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
        String DATE = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
        String TIME = "^(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";
        String PRICE = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,6})?$";
        String NUMBER = "^-?[0-9]+";
        String CHINESE = "^[一-龥]+$";
        String ID_CARD = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";
        String POSITIVE_INTEGER = "^[1-9]\\d*$";
        String NEGATIVE_INTEGER = "^-[1-9]\\d*$";
        String FLOAT_NUMBER = "^(-?\\d+)(\\.\\d+)?$";
        String ENGLISH_NUMBERS = "^[A-Za-z0-9]+$";
        String ENGLISH = "^[A-Za-z]+$";
        String LOWERCASE_LETTER = "^[A-Z]+$";
        String CAPITAL_LETTER = "^[a-z]+$";
        String ENGLISH_NUMBERS_UNDERLINE = "^\\w+$";
        String SPECIAL_CHARACTERS = "[',;=?\\x22\\+-*/!@#$%^&?]+";
        String DOMAIN_NAME = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";
        String INTERNET_URL = "[a-zA-z]+://[^\\s]*";
        String xml = "^([a-zA-Z]+-?)+[a-zA-Z0-9]+\\.[x|X][m|M][l|L]$";
        String qq = "[1-9][0-9]{4,}";
        String POSTCODE = "[1-9]\\d{5}(?!\\d)";
        String IPV4 = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";
        String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
        String PASSWORD_NUMBER = "^.*(?=.{6,16})(?=.*\\d).*$";
        String PASSWORD_CAPITAL_LETTER = "^.*(?=.{6,16})(?=.*[a-z]{1,}).*$";
        String PASSWORD_LOWERCASE_LETTER = "^.*(?=.{6,16})(?=.*[A-Z]{1,}).*$";
        String PASSWORD_SPECIAL = "^.*(?=.{6,16})(?=.*[!@#$%^&*?\\(\\)\\._\\+-]).*$";
        String PASSWORD_LEVEL4 = "^.*(?=.{6,16})(?=.*\\d)(?=.*[A-Z]{1,})(?=.*[a-z]{1,})(?=.*[!@#$%^&*?\\(\\)\\._\\+-]).*$";
    }
}

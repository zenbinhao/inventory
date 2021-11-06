package com.inventory.nike.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RandomUtil {
    public static int MAX_CODE_LENGTH = 6;
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final Map<Character, Integer> DIGIT_MAP = new HashMap();
    private static final int MAX_RADIX;
    private static final int MIN_RADIX = 2;
    private static Random random;

    public RandomUtil() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getMaxCode(String prefix, String dbMaxCode) {
        Long max = 0L;
        StringBuilder maxCode = new StringBuilder(prefix);
        if (dbMaxCode != null && !"".equals(dbMaxCode)) {
            max = Long.parseLong(dbMaxCode.substring(prefix.length()));
        }

        max = max + 1L;

        for(int i = 0; i < MAX_CODE_LENGTH - (max + "").length(); ++i) {
            maxCode.append("0");
        }

        maxCode.append(max);
        return maxCode.toString();
    }

    public static String getPrefixMaxCode(String prefix, String dbMaxCode) {
        Long max = 0L;
        StringBuilder maxCode = new StringBuilder(prefix);
        if (dbMaxCode != null && !"".equals(dbMaxCode) && dbMaxCode.indexOf(prefix) > -1) {
            max = Long.parseLong(dbMaxCode.substring(prefix.length()));
        }

        max = max + 1L;

        for(int i = 0; i < MAX_CODE_LENGTH - (max + "").length(); ++i) {
            maxCode.append("0");
        }

        maxCode.append(max);
        return maxCode.toString();
    }

    public static String genVerificationCode() {
        StringBuilder code = new StringBuilder();
        int len = 6;

        for(int i = 0; i < len; ++i) {
            code.append(Math.round(Math.random() * 9.0D));
        }

        return code.toString();
    }

    private static String toString(long i, int radix) {
        int def = 10;
        if (radix < 2 || radix > MAX_RADIX) {
            radix = def;
        }

        if (radix == def) {
            return Long.toString(i);
        } else {
            boolean size = true;
            int charPos = 64;
            char[] buf = new char[65];
            boolean negative = i < 0L;
            if (!negative) {
                i = -i;
            }

            while(i <= (long)(-radix)) {
                buf[charPos--] = DIGITS[(int)(-(i % (long)radix))];
                i /= (long)radix;
            }

            buf[charPos] = DIGITS[(int)(-i)];
            if (negative) {
                --charPos;
                buf[charPos] = '-';
            }

            return new String(buf, charPos, 65 - charPos);
        }
    }

    private static String digits(long val, int digits) {
        long hi = 1L << digits * 4;
        return toString(hi | val & hi - 1L, MAX_RADIX).substring(1);
    }

    public static String uuid19() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }

    public static long uniqId() {
        long nanoRandom = UUID.randomUUID().getMostSignificantBits();
        return nanoRandom < 0L ? nanoRandom * -1L : nanoRandom;
    }

    static {
        for(int i = 0; i < DIGITS.length; ++i) {
            DIGIT_MAP.put(DIGITS[i], i);
        }

        MAX_RADIX = DIGITS.length;
        random = new Random();
    }
}

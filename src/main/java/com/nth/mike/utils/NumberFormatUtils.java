package com.nth.mike.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFormatUtils {
    public static boolean isInteger(String s) {

        if (!isValidNumber(s)) {
            return false;
        }
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLong(String s) {
        if (!isValidNumber(s)) {
            return false;
        }
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidNumber(String str) {
        // str = str.trim().replaceAll(";", "");
        String regex = "^[-+]?(?:\\d+(?:L)?(?:[eE][-+]?\\d+)?|(?:\\d*\\.\\d+|\\.\\d+)(?:[fF])?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}

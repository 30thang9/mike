package com.nth.mike.utils;

import java.util.HashMap;
import java.util.Map;

public class ConvertUTF8Utils {

    private static final Map<Character, Character> convertUTF8;

    static {
        convertUTF8 = new HashMap<>();

        convertUTF8.put('à', 'a');
        convertUTF8.put('á', 'a');
        convertUTF8.put('ả', 'a');
        convertUTF8.put('ã', 'a');
        convertUTF8.put('ạ', 'a');
        convertUTF8.put('ă', 'a');
        convertUTF8.put('ằ', 'a');
        convertUTF8.put('ắ', 'a');
        convertUTF8.put('ẳ', 'a');
        convertUTF8.put('ẵ', 'a');
        convertUTF8.put('ặ', 'a');
        convertUTF8.put('â', 'a');
        convertUTF8.put('ầ', 'a');
        convertUTF8.put('ấ', 'a');
        convertUTF8.put('ẩ', 'a');
        convertUTF8.put('ẫ', 'a');
        convertUTF8.put('ậ', 'a');

        convertUTF8.put('đ', 'd');

        convertUTF8.put('è', 'e');
        convertUTF8.put('é', 'e');
        convertUTF8.put('ẻ', 'e');
        convertUTF8.put('ẽ', 'e');
        convertUTF8.put('ẹ', 'e');
        convertUTF8.put('ê', 'e');
        convertUTF8.put('ề', 'e');
        convertUTF8.put('ế', 'e');
        convertUTF8.put('ể', 'e');
        convertUTF8.put('ễ', 'e');
        convertUTF8.put('ệ', 'e');

        convertUTF8.put('ì', 'i');
        convertUTF8.put('í', 'i');
        convertUTF8.put('ỉ', 'i');
        convertUTF8.put('ĩ', 'i');
        convertUTF8.put('ị', 'i');

        convertUTF8.put('ò', 'o');
        convertUTF8.put('ó', 'o');
        convertUTF8.put('ỏ', 'o');
        convertUTF8.put('õ', 'o');
        convertUTF8.put('ọ', 'o');
        convertUTF8.put('ô', 'o');
        convertUTF8.put('ồ', 'o');
        convertUTF8.put('ố', 'o');
        convertUTF8.put('ổ', 'o');
        convertUTF8.put('ỗ', 'o');
        convertUTF8.put('ộ', 'o');
        convertUTF8.put('ơ', 'o');
        convertUTF8.put('ờ', 'o');
        convertUTF8.put('ớ', 'o');
        convertUTF8.put('ở', 'o');
        convertUTF8.put('ỡ', 'o');
        convertUTF8.put('ợ', 'o');

        convertUTF8.put('ù', 'u');
        convertUTF8.put('ú', 'u');
        convertUTF8.put('ủ', 'u');
        convertUTF8.put('ũ', 'u');
        convertUTF8.put('ụ', 'u');
        convertUTF8.put('ư', 'u');
        convertUTF8.put('ừ', 'u');
        convertUTF8.put('ứ', 'u');
        convertUTF8.put('ử', 'u');
        convertUTF8.put('ữ', 'u');
        convertUTF8.put('ự', 'u');

        convertUTF8.put('ỳ', 'y');
        convertUTF8.put('ý', 'y');
        convertUTF8.put('ỷ', 'y');
        convertUTF8.put('ỹ', 'y');
        convertUTF8.put('ỵ', 'y');
    }

    public static String convert(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : text.toCharArray()) {
            Character convertedChar = convertUTF8.get(Character.toLowerCase(c));
            if (convertedChar != null) {
                stringBuilder.append(convertedChar);
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

}

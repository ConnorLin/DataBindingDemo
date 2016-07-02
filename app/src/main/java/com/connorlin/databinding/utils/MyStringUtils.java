package com.connorlin.databinding.utils;

/**
 * ClassName: MyStringUtils
 * Description:
 * Author: connorlin
 * Date: Created on 2016-6-29.
 */
public class MyStringUtils {

    // 首字母大写
    public static String capitalize(final String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }
}

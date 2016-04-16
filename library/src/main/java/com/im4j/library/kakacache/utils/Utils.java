package com.im4j.library.kakacache.utils;

import com.im4j.library.kakacache.exception.ArgumentException;

/**
 * 工具类
 * @version alafighting 2016-04
 */
public class Utils {

    private Utils() {
    }

    /**
     * 不为空
     */
    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new ArgumentException("Can not be empty.");
        }
        return obj;
    }

    /**
     * 不小于0
     */
    public static void checkNotLessThanZero(long number) {
        if (number < 0) {
            throw new ArgumentException("Can not be less than zero.");
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

}

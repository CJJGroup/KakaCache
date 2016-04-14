package com.im4j.library.kakacache.utils;

import com.im4j.library.kakacache.exception.ArgumentException;

/**
 * 工具类
 * @version alafighting 2016-04
 */
public class Utils {

    private Utils() {
    }

    public static <T> T checkIsNotNull(T arg) {
        if (arg == null) {
            throw new ArgumentException("Can not be empty.");
        }
        return arg;
    }

}

package com.im4j.kakacache.common.utils;

import com.im4j.kakacache.common.exception.ArgumentException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
    public static long checkNotLessThanZero(long number) {
        if (number < 0) {
            throw new ArgumentException("Can not be < 0.");
        }

        return number;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }


    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException("length=" + arrayLength
                    + "; regionStart=" + offset
                    + "; regionLength=" + count);
        }
    }
    public static void checkOffsetAndCount(int offset, int count) {
        if ((offset | count) < 0) {
            throw new ArrayIndexOutOfBoundsException("regionStart=" + offset
                    + "; regionLength=" + count);
        }
    }
    public static void checkBytes(byte[] array, int len) {
        if (len < 0) {
            throw new ArrayIndexOutOfBoundsException("len=" + len);
        }
        if (array == null || array.length != len) {
            throw new ArrayIndexOutOfBoundsException("array=" + len+"; len="+len);
        }
    }


    /**
     * 取父类泛型
     * @param clazz
     * @return 没有则返回null
     */
    @SuppressWarnings("rawtypes")
    public static Type[] getGenericSuperclass(Class<?> clazz) {
        try {
            Type typeGeneric = clazz.getGenericSuperclass();
            if (typeGeneric != null) {
                if (typeGeneric instanceof ParameterizedType) {
                    return getGeneric((ParameterizedType) typeGeneric);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 取父接口泛型
     * @param clazz
     * @return 没有则返回null
     */
    @SuppressWarnings("rawtypes")
    public static Type[] getGenericInterfaces(Class<?> clazz) {
        try {
            Type typeGeneric = clazz.getGenericInterfaces()[0];
            if (typeGeneric != null) {
                if (typeGeneric instanceof ParameterizedType) {
                    return getGeneric((ParameterizedType) typeGeneric);
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    /**
     * 取泛型
     * @param type
     * @return 没有则返回null
     */
    @SuppressWarnings("rawtypes")
    public static Type[] getGeneric(ParameterizedType type) {
        try {
            if (type != null) {
                return type.getActualTypeArguments();
            }
        } catch (Exception e) {
        }
        return null;
    }

}

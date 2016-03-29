package com.im4j.library.kakacache.exception;

/**
 * 缓存处理错误
 * @version 0.1 king 2016-03
 */
public class CacheException extends Exception {

    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CacheException(Throwable throwable) {
        super(throwable);
    }

}

package com.im4j.kakacache.core.exception;

/**
 * 异常基类
 * @version 0.1 king 2016-03
 */
public class Exception extends RuntimeException {

    public Exception() {
    }

    public Exception(String message) {
        super(message);
    }

    public Exception(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Exception(Throwable throwable) {
        super(throwable);
    }

}

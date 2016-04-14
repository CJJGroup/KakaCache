package com.im4j.library.kakacache.exception;

/**
 * 参数错误
 * @version 0.1 king 2016-03
 */
public class ArgumentException extends Exception {

    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ArgumentException(Throwable throwable) {
        super(throwable);
    }

}

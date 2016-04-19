package com.im4j.library.kakacache.exception;

/**
 * 已读取到末尾的异常
 * @version 0.1 king 2016-04
 */
public class ReadEndException extends Exception {

    public ReadEndException() {
    }

    public ReadEndException(String message) {
        super(message);
    }

    public ReadEndException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ReadEndException(Throwable throwable) {
        super(throwable);
    }

}

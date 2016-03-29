package com.im4j.kakacache.writer;

import java.io.InputStream;

/**
 * 写入器
 * @version 0.1 king 2016-03
 */
public interface Writer<T> {

    /**
     * 读取
     * @param data
     * @return
     */
    InputStream writer(T data);

}

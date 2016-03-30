package com.im4j.library.kakacache.writer;

import com.im4j.library.kakacache.cache.disk.DiskCache;

import java.io.InputStream;

/**
 * 写入器
 * @version 0.1 king 2016-03
 */
public interface Writer<T> {

    /**
     * 写入
     * @param data
     * @param disk
     * @param expires 有效期（单位：秒）
     * @return
     */
    InputStream writer(T data, DiskCache disk, int expires);

}

package com.im4j.library.kakacache.cache.disk.writer;

import com.im4j.library.kakacache.cache.disk.IDiskCache;

import java.io.InputStream;

/**
 * 写入器
 * @version 0.1 king 2016-03
 */
public interface DiskWriter<T> {

    /**
     * 写入
     * @param data
     * @param disk
     * @param expires 有效期（单位：秒）
     * @return
     */
    InputStream writer(T data, IDiskCache disk, int expires);

}

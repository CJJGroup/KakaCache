package com.im4j.library.kakacache.cache.disk.writer;

import com.im4j.library.kakacache.cache.disk.sink.Sink;

/**
 * 写入器
 * @version 0.1 king 2016-03
 */
public interface DiskWriter<T> {

    /**
     * 写入
     * @param data
     * @param sink
     * @param expires 有效期（单位：秒）
     * @return
     */
    void writer(T data, Sink sink, int expires);

}

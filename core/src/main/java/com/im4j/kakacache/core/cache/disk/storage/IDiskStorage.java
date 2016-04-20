package com.im4j.kakacache.core.cache.disk.storage;

import com.im4j.kakacache.core.cache.disk.sink.Sink;
import com.im4j.kakacache.core.cache.disk.source.Source;
import com.im4j.kakacache.core.exception.CacheException;

import java.io.Closeable;
import java.util.Map;

/**
 * 磁盘存储
 * @version alafighting 2016-04
 */
public interface IDiskStorage extends Closeable {

    /**
     * 加载数据源
     * @param key
     * @return
     */
    Source load(String key) throws CacheException;

    /**
     * 创建数据槽
     * @param key
     */
    Sink create(String key) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, Source> snapshot();



    /**
     * 关闭
     */
    @Override
    void close() throws CacheException;

    /**
     * 是否已关闭
     * @return
     */
    boolean isClosed();

    /**
     * 删除缓存
     * @param key
     */
    void remove(String key) throws CacheException;

    /**
     * 清空缓存
     */
    void clear() throws CacheException;

    /**
     * 缓存总大小
     * @return 单位:byte
     */
    long getTotalSize();

    /**
     * 缓存总数目
     * @return 单位:缓存个数
     */
    long getTotalQuantity();

}

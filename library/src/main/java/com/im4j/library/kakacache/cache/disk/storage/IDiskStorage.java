package com.im4j.library.kakacache.cache.disk.storage;

import com.im4j.library.kakacache.cache.disk.sink.Sink;
import com.im4j.library.kakacache.cache.disk.source.Source;
import com.im4j.library.kakacache.exception.CacheException;

import java.io.Closeable;
import java.util.Map;

/**
 * 磁盘存储
 * @version alafighting 2016-04
 */
public interface IDiskStorage extends Closeable {

    /**
     * 读取
     * @param key
     * @return
     */
    Source get(String key) throws CacheException;

    /**
     * 保存
     * @param key
     */
    Sink create(String key) throws CacheException;

    /**
     * // TODO 更合理的方法解释
     * 获取末尾的Key
     * @return 末尾的Key，一般用于存储空间不足时清理空间
     */
    String getLastKey() throws CacheException;

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

}

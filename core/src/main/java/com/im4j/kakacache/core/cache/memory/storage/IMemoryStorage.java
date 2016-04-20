package com.im4j.kakacache.core.cache.memory.storage;

import com.im4j.kakacache.core.cache.IStorage;
import com.im4j.kakacache.core.exception.CacheException;

import java.io.Closeable;
import java.util.Map;

/**
 * 内存存储
 * @author alafighting 2016-04
 */
public interface IMemoryStorage extends Closeable, IStorage {

    /**
     * 读取
     * @param key
     * @return
     */
    Object load(String key) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     */
    void save(String key, Object value) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, Object> snapshot();


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

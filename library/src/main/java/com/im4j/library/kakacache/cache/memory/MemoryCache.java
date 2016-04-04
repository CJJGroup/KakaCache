package com.im4j.library.kakacache.cache.memory;

import com.im4j.library.kakacache.exception.CacheException;

import java.util.Map;

/**
 * 内存缓存
 * @author alafighting 2016-03
 */
public interface MemoryCache {

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
     * 保存
     * @param key
     * @param value
     * @param expires 有效期（单位：秒）
     */
    void save(String key, Object value, int expires) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, Object> snapshot();



    /**
     * 是否过期
     * @param key
     * @return
     */
    boolean isExpired(String key);

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
     * 缓存大小
     * @return 单位:byte
     */
    long size();

}

package com.im4j.library.kakacache.cache.memory;

import com.im4j.library.kakacache.cache.Cache;
import com.im4j.library.kakacache.exception.CacheException;

import java.util.Map;

/**
 * 内存缓存
 * @author alafighting 2016-03
 */
public interface MemoryCache extends Cache {

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

}

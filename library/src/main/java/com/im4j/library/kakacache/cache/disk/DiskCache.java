package com.im4j.library.kakacache.cache.disk;

import com.im4j.library.kakacache.cache.Cache;
import com.im4j.library.kakacache.exception.CacheException;

import java.io.InputStream;
import java.util.Map;

/**
 * 磁盘缓存
 * @author alafighting 2016-03
 */
public interface DiskCache extends Cache {

    /**
     * 读取
     * @param key
     * @return
     */
    byte[] loadBytes(String key) throws CacheException;

    /**
     * 读取
     * @param key
     * @return
     */
    InputStream loadStream(String key) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     */
    void save(String key, byte[] value) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     * @param expires 有效期（单位：秒）
     */
    void save(String key, byte[] value, int expires) throws CacheException;

    /**
     * 保存
     * @param key
     * @param stream
     */
    void save(String key, InputStream stream) throws CacheException;

    /**
     * 保存
     * @param key
     * @param stream
     * @param expires 有效期（单位：秒）
     */
    void save(String key, InputStream stream, int expires) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, InputStream> snapshot();

}

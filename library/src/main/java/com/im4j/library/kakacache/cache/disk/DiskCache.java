package com.im4j.library.kakacache.cache.disk;

import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.writer.Writer;

import java.io.InputStream;
import java.util.Map;

/**
 * 磁盘缓存
 * @author alafighting 2016-03
 */
public interface DiskCache {

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
     * 保存
     * @param key
     * @param value
     * @param writer
     */
    <T> void save(String key, T value, Writer<T> writer) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     * @param writer
     * @param expires 有效期（单位：秒）
     */
    <T> void save(String key, T value, Writer<T> writer, int expires) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, InputStream> snapshot();



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

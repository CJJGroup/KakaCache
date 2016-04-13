package com.im4j.library.kakacache.cache.disk;

import com.im4j.library.kakacache.cache.disk.snapshot.CacheEntry;
import com.im4j.library.kakacache.cache.disk.source.Source;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;
import com.im4j.library.kakacache.exception.CacheException;

import java.io.Closeable;
import java.util.Map;

/**
 * 磁盘缓存
 * @author alafighting 2016-03
 */
public interface IDiskCache extends Closeable {

    /**
     * 读取
     * @param key
     * @return
     */
    CacheEntry load(String key) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     */
    CacheEntry save(String key, byte[] value) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     * @param expires 有效期（单位：秒）
     */
    CacheEntry save(String key, byte[] value, int expires) throws CacheException;

    /**
     * 保存
     * @param key
     * @param source
     */
    CacheEntry save(String key, Source source) throws CacheException;

    /**
     * 保存
     * @param key
     * @param source
     * @param expires 有效期（单位：秒）
     */
    CacheEntry save(String key, Source source, int expires) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     * @param diskWriter
     */
    <T> CacheEntry save(String key, T value, DiskWriter<T> diskWriter) throws CacheException;

    /**
     * 保存
     * @param key
     * @param value
     * @param diskWriter
     * @param expires 有效期（单位：秒）
     */
    <T> CacheEntry save(String key, T value, DiskWriter<T> diskWriter, int expires) throws CacheException;

    /**
     * 快照
     * @return
     */
    Map<String, CacheEntry> snapshot();



    /**
     * 关闭
     */
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
     * 缓存大小
     * @return 单位:byte
     */
    long getSize();

}

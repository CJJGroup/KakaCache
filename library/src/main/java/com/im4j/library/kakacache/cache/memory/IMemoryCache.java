package com.im4j.library.kakacache.cache.memory;

import com.im4j.library.kakacache.exception.CacheException;

import java.io.Closeable;
import java.util.Map;

/**
 * 内存缓存
 * @author alafighting 2016-03
 */
public interface IMemoryCache extends Closeable {

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
     * 关闭
     */
    void close() throws CacheException;

    /**
     * 是否已关闭
     * @return
     */
    boolean isClosed();

    /**
     * 是否包含
     * @param key
     * @return
     */
    boolean containsKey(String key);

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
     * 最大缓存大小
     * @return 单位:byte
     */
    long getMaxSize();

    /**
     * 缓存大小
     * @return 单位:byte
     */
    long getSize();

}

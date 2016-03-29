package com.im4j.kakacache.cache.disk;

import com.im4j.kakacache.cache.Cache;

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
    byte[] loadBytes(String key);

    /**
     * 读取
     * @param key
     * @return
     */
    InputStream loadStream(String key);

    /**
     * 保存
     * @param key
     * @param value
     */
    void save(String key, byte[] value);

    /**
     * 保存
     * @param key
     * @param value
     * @param expires 有效期（单位：秒）
     */
    void save(String key, byte[] value, int expires);

    /**
     * 保存
     * @param key
     * @param stream
     */
    void save(String key, InputStream stream);

    /**
     * 保存
     * @param key
     * @param stream
     * @param expires 有效期（单位：秒）
     */
    void save(String key, InputStream stream, int expires);

    /**
     * 快照
     * @return
     */
    Map<String, InputStream> snapshot();

}

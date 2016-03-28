package com.im4j.kakacache.memory;

import com.im4j.kakacache.Cache;

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
    Object load(String key);

    /**
     * 保存
     * @param key
     * @param value
     */
    void save(String key, Object value);

    /**
     * 保存
     * @param key
     * @param value
     * @param expires 有效期（单位：秒）
     */
    void save(String key, Object value, int expires);

    /**
     * 快照
     * @return
     */
    Map<String, Object> snapshot();

}

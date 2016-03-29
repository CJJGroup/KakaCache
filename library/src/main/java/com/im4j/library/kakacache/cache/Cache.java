package com.im4j.library.kakacache.cache;

import com.im4j.library.kakacache.exception.CacheException;

/**
 * 缓存接口
 * @version 0.1 king 2016-03
 */
public interface Cache {

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

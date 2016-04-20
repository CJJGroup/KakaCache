package com.im4j.kakacache.core.cache;

import com.im4j.kakacache.core.exception.CacheException;

/**
 * 存储接口
 * @author alafighting 2016-04
 * // TODO 合理吗？
 */
public interface IStorage {

    /**
     * 删除缓存
     * @param key
     */
    void remove(String key) throws CacheException;

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

package com.im4j.library.kakacache.cache.disk.snapshot;

import com.im4j.library.kakacache.exception.CacheException;

/**
 * 快照
 * @version 0.1 king 2016-04
 */
public interface Snapshot {

    public boolean hasNext();

    public CacheEntry next();


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

}

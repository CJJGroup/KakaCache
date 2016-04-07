package com.im4j.library.kakacache;

import com.im4j.library.kakacache.cache.db.IDbCache;
import com.im4j.library.kakacache.cache.disk.IDiskCache;
import com.im4j.library.kakacache.cache.memory.IMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;

/**
 * 缓存管理器
 * @version 0.1 king 2016-04
 */
public abstract class CacheManager {

    /**
     * 是否包含
     * @param key
     * @return
     */
    public abstract boolean containsKey(String key);

    /**
     * 是否过期
     * @param key
     * @return
     */
    public abstract boolean isExpired(String key);

    /**
     * 删除缓存
     * @param key
     */
    public abstract void remove(String key) throws CacheException;

    /**
     * 清空缓存
     */
    public abstract void clear() throws CacheException;



    /**
     * 构造器
     */
    public static class Builder {

        public static DiskCacheManager.Builder with(IMemoryCache memory, IDiskCache disk) {
            return new DiskCacheManager.Builder(memory, disk);
        }

        public static DbCacheManager.Builder with(IDbCache db) {
            return new DbCacheManager.Builder(db);
        }

    }

}

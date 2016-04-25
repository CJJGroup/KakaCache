package com.im4j.kakacache.manager;

import com.im4j.kakacache.common.exception.CacheException;
import com.im4j.kakacache.core.cache.CacheCore;
import com.im4j.kakacache.core.cache.CacheTarget;

/**
 * 同步模式缓存管理
 * @version alafighting 2016-04
 */
public class SyncCacheManager extends CacheManager {

    SyncCacheManager(CacheCore cache) {
        super(cache);
    }

    /**
     * 读取
     */
    <T> T load(String key) throws CacheException {
        return super._load(key);
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return super._containsKey(key);
    }


    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    <T> void save(String key, T value, int expires, CacheTarget target) throws CacheException {
        super._save(key, value, expires, target);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) throws CacheException {
        super._remove(key);
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        super._clear();
    }



    /**
     * 构造器
     */
    public static class Builder extends CacheManager.Builder {
        public Builder(CacheCore cache) {
            super(cache);
        }

        public SyncCacheManager create() {
            return new SyncCacheManager(cache);
        }
    }

}

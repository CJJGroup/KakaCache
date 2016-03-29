package com.im4j.library.kakacache.cache.memory;

import com.im4j.library.kakacache.exception.CacheException;

import java.util.Map;

/**
 * 简单空实现
 * @version 0.1 king 2016-03
 */
public class SimpleMemoryCache implements MemoryCache {

    @Override
    public Object load(String key) throws CacheException {
        return null;
    }
    @Override
    public void save(String key, Object value) throws CacheException {
    }
    @Override
    public void save(String key, Object value, int expires) throws CacheException {
    }
    @Override
    public Map<String, Object> snapshot() {
        return null;
    }

    @Override
    public boolean isExpired(String key) {
        return true;
    }
    @Override
    public void remove(String key) throws CacheException {
    }
    @Override
    public void clear() throws CacheException {
    }
    @Override
    public long size() {
        return 0;
    }

}

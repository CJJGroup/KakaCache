package com.im4j.library.kakacache.cache.memory;

import com.im4j.library.kakacache.exception.CacheException;

import java.util.Map;

/**
 * 简单空实现
 * @version 0.1 king 2016-03
 */
public class SimpleMemoryCache implements IMemoryCache {

    private boolean isclosed;
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
    public void close() {
        this.isclosed = true;
    }
    @Override
    public boolean isClosed() {
        return this.isclosed;
    }
    @Override
    public boolean containsKey(String key) {
        return false;
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
    public long getMaxSize() {
        return 0;
    }
    @Override
    public long getSize() {
        return 0;
    }

}

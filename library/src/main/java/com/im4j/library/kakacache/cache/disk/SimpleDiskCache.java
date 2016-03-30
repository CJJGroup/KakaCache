package com.im4j.library.kakacache.cache.disk;

import com.im4j.library.kakacache.cache.memory.MemoryCache;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.writer.Writer;

import java.io.InputStream;
import java.util.Map;

/**
 * 简单空实现
 * @version 0.1 king 2016-03
 */
public class SimpleDiskCache implements DiskCache {

    @Override
    public byte[] loadBytes(String key) throws CacheException {
        return null;
    }
    @Override
    public InputStream loadStream(String key) throws CacheException {
        return null;
    }
    @Override
    public void save(String key, byte[] value) throws CacheException {
    }
    @Override
    public void save(String key, byte[] value, int expires) throws CacheException {
    }
    @Override
    public void save(String key, InputStream stream) throws CacheException {
    }
    @Override
    public void save(String key, InputStream stream, int expires) throws CacheException {
    }
    @Override
    public <T> void save(String key, T value, Writer<T> writer) throws CacheException {
    }
    @Override
    public <T> void save(String key, T value, Writer<T> writer, int expires) throws CacheException {
    }
    @Override
    public Map<String, InputStream> snapshot() {
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

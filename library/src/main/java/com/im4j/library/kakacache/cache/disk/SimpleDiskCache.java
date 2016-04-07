package com.im4j.library.kakacache.cache.disk;

import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;

import java.io.InputStream;
import java.util.Map;

/**
 * 简单空实现
 * @version 0.1 king 2016-03
 */
public class SimpleDiskCache implements IDiskCache {

    private boolean isclosed;
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
    public <T> void save(String key, T value, DiskWriter<T> diskWriter) throws CacheException {
    }
    @Override
    public <T> void save(String key, T value, DiskWriter<T> diskWriter, int expires) throws CacheException {
    }
    @Override
    public Map<String, InputStream> snapshot() {
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

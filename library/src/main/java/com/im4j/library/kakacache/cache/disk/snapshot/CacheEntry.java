package com.im4j.library.kakacache.cache.disk.snapshot;

import com.im4j.library.kakacache.cache.disk.source.Source;

/**
 * 缓存项
 * @version 0.1 king 2016-04
 */
public class CacheEntry {
    private final String key;
    private long expiryTime;
    private long size;
    private Source source;

    public CacheEntry(String key) {
        this.key = key;
    }
    public CacheEntry(String key, long expiryTime, long size, Source source) {
        this.key = key;
        this.expiryTime = expiryTime;
        this.size = size;
        this.source = source;
    }


    public boolean isExpiry() {
        return expiryTime-System.currentTimeMillis() <= 0;
    }


    @Override
    public String toString() {
        return "JournalEntry{" +
                "key='" + key + '\'' +
                ", expiryTime=" + expiryTime +
                ", size=" + size +
                ", source=" + source +
                '}';
    }


    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getKey() {
        return key;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}

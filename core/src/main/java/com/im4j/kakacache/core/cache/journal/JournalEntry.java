package com.im4j.kakacache.core.cache.journal;

/**
 * 日志项
 * @version 0.1 king 2016-04
 */
public class JournalEntry {
    private final String key;
    private long expiryTime;
    // TODO 有待商讨
//    private long createTime;
//    private long expiry;
//    private long size;

    public JournalEntry(String key) {
        this.key = key;
    }
    public JournalEntry(String key, long expiryTime) {
        this.key = key;
        this.expiryTime = expiryTime;
    }


    public boolean isExpiry() {
        return expiryTime-System.currentTimeMillis() <= 0;
    }


    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", expiryTime=" + expiryTime +
                '}';
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

}

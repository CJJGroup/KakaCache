package com.im4j.kakacache.core.cache;

/**
 * 日志项
 * @version 0.1 king 2016-04
 */
public class CacheEntry {
    /** KEY */
    private final String key;
    /** 创建时间 */
    private long createTime;
    /** 过期时间 */
    private long expiryTime;
    // TODO 有待商讨
//    private long size;

    public CacheEntry(String key) {
        this.key = key;
        this.createTime = System.currentTimeMillis();
    }
    public CacheEntry(String key, long createTime, long expiryTime) {
        this.key = key;
        this.createTime = createTime;
        this.expiryTime = expiryTime;
    }


    /**
     * 是否过期
     */
    public boolean isExpiry() {
        return System.currentTimeMillis() > expiryTime;
    }


    @Override
    public String toString() {
        return "{" + "key='" + key + '\'' +
                ", createTime=" + createTime +
                ", expiryTime=" + expiryTime +
                '}';
    }


    public String getKey() {
        return key;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
}

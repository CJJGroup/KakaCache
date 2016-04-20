package com.im4j.kakacache.core.cache.disk.journal;

import com.im4j.kakacache.core.cache.CacheEntry;
import com.im4j.kakacache.common.exception.CacheException;

import java.io.Closeable;
import java.util.List;

/**
 * 磁盘缓存日志
 * @version alafighting 2016-04
 */
public interface IDiskJournal extends Closeable {

    CacheEntry get(String key);

    void put(String key, CacheEntry entry);

    boolean containsKey(String key);

    /**
     * 获取准备丢弃的Key
     * @return 准备丢弃的Key（如存储空间不足时，需要清理）
     */
    String getLoseKey() throws CacheException;

    void remove(String key);

    void clear();

    List<CacheEntry> snapshot();

}

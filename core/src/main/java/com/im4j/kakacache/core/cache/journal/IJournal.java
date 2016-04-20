package com.im4j.kakacache.core.cache.journal;

import com.im4j.kakacache.core.cache.IStorage;
import com.im4j.kakacache.core.exception.CacheException;

import java.io.Closeable;
import java.util.List;

/**
 * 日志
 * @version alafighting 2016-04
 */
public interface IJournal extends Closeable {

    JournalEntry get(String key);

    void put(String key, JournalEntry entry);

    boolean containsKey(String key);

    /**
     * 获取准备丢弃的Key
     * @return 准备丢弃的Key（如存储空间不足时，需要清理）
     */
    String getLoseKey() throws CacheException;

    void remove(String key);

    void clear();

    void clean(IStorage storage);

    List<JournalEntry> snapshot();

}

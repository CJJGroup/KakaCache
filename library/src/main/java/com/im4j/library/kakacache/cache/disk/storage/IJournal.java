package com.im4j.library.kakacache.cache.disk.storage;

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

    void remove(String key);

    void clear();

    List<JournalEntry> snapshot();

}

package com.im4j.library.kakacache.cache.disk;

import com.google.gson.reflect.TypeToken;
import com.im4j.library.kakacache.cache.disk.converter.IDiskConverter;
import com.im4j.library.kakacache.cache.disk.sink.Sink;
import com.im4j.library.kakacache.cache.disk.source.Source;
import com.im4j.library.kakacache.cache.disk.storage.IDiskStorage;
import com.im4j.library.kakacache.cache.disk.storage.IJournal;
import com.im4j.library.kakacache.cache.disk.storage.JournalEntry;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.utils.Utils;

import java.io.IOException;

/**
 * 磁盘缓存包裹
 * @version 0.1 king 2016-04
 */
public final class DiskCacheWrapper {

    private final IDiskConverter mConverter;
    private final IDiskStorage mStorage;
    private final IJournal mJournal;
    private final long mMaxSize;

    public DiskCacheWrapper(IDiskConverter converter,
                            IDiskStorage storage,
                            IJournal journal,
                            long maxSize) {
        this.mConverter = converter;
        this.mStorage = storage;
        this.mJournal = journal;
        this.mMaxSize = maxSize;
    }

    /**
     * 读取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T load(String key) throws CacheException {
        Utils.checkNotNull(key);

        JournalEntry entry = mJournal.get(key);
        if (entry == null) {
            return null;
        }

        // 过期自动清理
        if (entry.isExpiry()) {
            remove(key);
            return null;
        }

        // 读取缓存
        Source source = mStorage.get(key);
        T value = (T) mConverter.load(source, new TypeToken<T>(){}.getType());
        try {
            source.close();
        } catch (IOException e) {
        }
        return value;
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(String key, T value, int expires) throws CacheException {
        Utils.checkNotNull(key);

        if (value == null) {
            remove(key);
            return;
        }

        if (getMaxSize() == 0) {
            return;
        }

        // TODO 清理存储空间，以供写入新的数据
        clearUnused();

        // 写入缓存
        Sink sink = mStorage.create(key);
        mConverter.writer(sink, value);
        try {
            sink.close();
        } catch (IOException e) {
        }

        mJournal.put(key, new JournalEntry(key, expires));

        // 清理无用数据
        clearUnused();
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return mJournal.containsKey(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) throws CacheException {
        mStorage.remove(key);
        mJournal.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        mStorage.clear();
        mJournal.clear();
    }

    /**
     * 清理无用缓存
     */
    public void clearUnused() {
        // 清理过期
        for (JournalEntry entry : mJournal.snapshot()) {
            if (entry.isExpiry()) {
                remove(entry.getKey());
            }
        }

        // 清理超支
        while (getMaxSize() < getSize()) {
            String key = mStorage.getLastKey();
            remove(key);
        }
    }

    /**
     * 最大缓存大小
     * @return 单位:byte
     */
    public long getMaxSize() {
        return mMaxSize;
    }

    /**
     * 缓存大小
     * @return 单位:byte
     */
    public long getSize() {
        long size = mStorage.getTotalSize();
        if (size < 0) {
            throw new CacheException("Cache size should not be < 0.");
        }
        return size;
    }

}

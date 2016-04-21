package com.im4j.kakacache.core.cache.disk;

import com.google.gson.reflect.TypeToken;
import com.im4j.kakacache.core.cache.Cache;
import com.im4j.kakacache.core.cache.CacheTarget;
import com.im4j.kakacache.core.cache.disk.converter.IDiskConverter;
import com.im4j.kakacache.core.cache.disk.sink.Sink;
import com.im4j.kakacache.core.cache.disk.source.Source;
import com.im4j.kakacache.core.cache.disk.storage.IDiskStorage;
import com.im4j.kakacache.core.cache.disk.journal.IDiskJournal;
import com.im4j.kakacache.core.cache.CacheEntry;
import com.im4j.kakacache.common.exception.CacheException;
import com.im4j.kakacache.common.utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * 磁盘缓存
 * @version 0.1 king 2016-04
 */
public final class DiskCache extends Cache {

    private final IDiskStorage mStorage;
    private final IDiskJournal mJournal;
    private final IDiskConverter mConverter;

    public DiskCache(IDiskStorage storage,
                     IDiskJournal journal,
                     IDiskConverter converter,
                     long maxSize,
                     long maxQuantity) {
        super(maxSize, maxQuantity);
        this.mStorage = storage;
        this.mJournal = journal;
        this.mConverter = converter;
    }


    /**
     * 读取
     * @param key
     * @param <T>
     * @return
     */

    @Override
    protected <T> T doLoad(String key) throws CacheException {
        // 读取缓存
        Source source = mStorage.load(key);
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
    @Override
    protected <T> void doSave(String key, T value, int expires, CacheTarget target) throws CacheException {
        if (target == null || target == CacheTarget.NONE || target == CacheTarget.Memory) {
            return;
        }

        // 写入缓存
        Sink sink = mStorage.create(key);
        mConverter.writer(sink, value);
        try {
            sink.close();
        } catch (IOException e) {
        }

        long createTime = System.currentTimeMillis();
        long expiresTime = createTime + expires;
        mJournal.put(key, new CacheEntry(key, createTime, expiresTime, target));
    }

    @Override
    protected boolean isExpiry(String key) {
        CacheEntry entry = mJournal.get(key);
        return entry == null || entry.isExpiry();
    }

    @Override
    public boolean containsKey(String key) {
        return mJournal.containsKey(key);
    }

    @Override
    public void remove(String key) throws CacheException {
        mStorage.remove(key);
        mJournal.remove(key);
    }

    @Override
    public void clear() throws CacheException {
        mStorage.clear();
        mJournal.clear();
    }

    @Override
    public List<CacheEntry> snapshot() {
        return mJournal.snapshot();
    }

    @Override
    public String getLoseKey() throws CacheException {
        return mJournal.getLoseKey();
    }

    @Override
    public long getTotalSize() {
        long size = mStorage.getTotalSize();
        Utils.checkNotLessThanZero(size);
        return size;
    }

    @Override
    public long getTotalQuantity() {
        long quantity = mStorage.getTotalQuantity();
        Utils.checkNotLessThanZero(quantity);
        return quantity;
    }

}

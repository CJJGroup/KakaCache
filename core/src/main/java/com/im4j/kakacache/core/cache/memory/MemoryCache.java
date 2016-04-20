package com.im4j.kakacache.core.cache.memory;

import com.im4j.kakacache.core.cache.journal.IJournal;
import com.im4j.kakacache.core.cache.journal.JournalEntry;
import com.im4j.kakacache.core.cache.memory.storage.IMemoryStorage;
import com.im4j.kakacache.core.exception.CacheException;
import com.im4j.kakacache.core.utils.Utils;

/**
 * 内存缓存
 * @version 0.1 king 2016-04
 */
public final class MemoryCache {

    private final IMemoryStorage mStorage;
    private final IJournal mJournal;

    public MemoryCache(IMemoryStorage storage,
                       IJournal journal) {
        this.mStorage = storage;
        this.mJournal = journal;
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
        return (T) mStorage.load(key);
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

        // TODO 先写入，后清理。会超出限定条件，需要一定交换空间

        // 写入缓存
        mStorage.save(key, value);
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

        // 清理超出缓存
        mJournal.clean(mStorage);
    }

    /**
     * 缓存大小
     * @return 单位:byte
     */
    public long getTotalSize() {
        long size = mStorage.getTotalSize();
        Utils.checkNotLessThanZero(size);
        return size;
    }

    /**
     * 缓存个数
     * @return 单位:个数
     */
    public long getTotalQuantity() {
        long quantity = mStorage.getTotalQuantity();
        Utils.checkNotLessThanZero(quantity);
        return quantity;
    }

}

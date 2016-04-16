package com.im4j.library.kakacache.cache.disk;

import com.google.gson.reflect.TypeToken;
import com.im4j.library.kakacache.cache.disk.loader.DiskLoader;
import com.im4j.library.kakacache.cache.disk.storage.JournalEntry;
import com.im4j.library.kakacache.cache.disk.storage.IDiskStorage;
import com.im4j.library.kakacache.cache.disk.storage.IJournal;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 磁盘缓存包裹
 * @version 0.1 king 2016-04
 */
public final class DiskCacheWrapper {

    private final IDiskStorage storage;
    private final IJournal journal;
    private final long mMaxSize;

    private final Map<Type, DiskLoader> loaderMap;
    private final Map<Type, DiskWriter> writerMap;

    public DiskCacheWrapper(IDiskStorage storage, IJournal journal, long maxSize) {
        this.storage = storage;
        this.journal = journal;
        this.mMaxSize = maxSize;
        this.loaderMap = new HashMap<>();
        this.writerMap = new HashMap<>();
    }


    /**
     * 读取
     * @param key
     * @param <T>
     * @return
     */
    public <T> T load(String key) throws CacheException {
        Utils.checkNotNull(key);

        JournalEntry entry = journal.get(key);
        if (entry == null) {
            return null;
        }

        // 过期自动清理
        if (entry.isExpiry()) {
            remove(key);
            return null;
        }

        // 读取缓存
        DiskLoader<T> diskLoader = getLoader();
        return diskLoader.load(storage.get(key));
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
        DiskWriter<T> diskWriter = getWriter();
        diskWriter.writer(value, storage.create(key), expires);
        journal.put(key, new JournalEntry(key, expires));

        // 清理无用数据
        clearUnused();
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return journal.containsKey(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) throws CacheException {
        storage.remove(key);
        journal.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        storage.clear();
        journal.clear();
    }

    /**
     * 清理无用缓存
     */
    public void clearUnused() {
        // 清理过期
        for (JournalEntry entry : journal.snapshot()) {
            if (entry.isExpiry()) {
                remove(entry.getKey());
            }
        }

        // 清理超支
        while (getMaxSize() < getSize()) {
            String key = storage.getLastKey();
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
        long size = storage.getTotalSize();
        if (size < 0) {
            throw new CacheException("Cache size should not be < 0.");
        }
        return size;
    }



    /**
     * 注册类型转换器
     * @param loader
     */
    public <T> void registerTypeConverter(DiskLoader<T> loader, DiskWriter<T> writer) {
        Type type = new TypeToken<T>(){}.getType();
        loaderMap.put(type, loader);
        writerMap.put(type, writer);
    }

    private <T> DiskLoader<T> getLoader() {
        Type type = new TypeToken<T>(){}.getType();
        DiskLoader<T> diskLoader = loaderMap.get(type);
        if (diskLoader == null) {
            throw new CacheException("loader can't find");
        }
        return diskLoader;
    }
    private <T> DiskWriter<T> getWriter() {
        Type type = new TypeToken<T>(){}.getType();
        DiskWriter<T> diskWriter = writerMap.get(type);
        if (diskWriter == null) {
            throw new CacheException("writer can't find");
        }
        return diskWriter;
    }
}

package com.im4j.library.kakacache.cache.disk;

import com.google.gson.reflect.TypeToken;
import com.im4j.library.kakacache.cache.disk.loader.DiskLoader;
import com.im4j.library.kakacache.cache.disk.snapshot.CacheEntry;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;
import com.im4j.library.kakacache.exception.CacheException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 内部缓存实现
 * @version 0.1 king 2016-04
 */
public class InternalDiskCache {

    private final IDiskCache disk;

    private Map<Type, DiskLoader> loaderMap;
    private Map<Type, DiskWriter> writerMap;

    private Map<String, CacheEntry> journal;

    public InternalDiskCache(IDiskCache disk) {
        this.disk = disk;
        this.loaderMap = new HashMap<>();
        this.writerMap = new HashMap<>();

        this.journal = new HashMap<>();
        this.journal.putAll(disk.snapshot());
    }


    /**
     * 读取
     */
    public <T> T load(String key) throws CacheException {
        CacheEntry entry = disk.load(key);
        if (entry != null) {
            // 过期自动清理
            if (entry.isExpiry()) {
                remove(key);
                return null;
            } else {
                DiskLoader<T> diskLoader = getLoader();
                return diskLoader.load(entry.getSource());
            }
        }
        return null;
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(String key, T value, int expires) throws CacheException {
        if (value == null) {
            remove(key);
            return;
        }

        DiskWriter<T> diskWriter = getWriter();
        CacheEntry entry = disk.save(key, value, diskWriter, expires);
        journal.put(key, entry);

        // 过期自动清理
        clearExpiry();
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
        disk.remove(key);
        journal.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        disk.clear();
        journal.clear();
    }

    /**
     * 清理过期缓存
     */
    public void clearExpiry() {
        for (CacheEntry entry : journal.values()) {
            if (entry.isExpiry()) {
                remove(entry.getKey());
            }
        }
    }

    /**
     * 缓存大小
     * @return 单位:byte
     */
    public long getSize() {
        return disk.getSize();
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

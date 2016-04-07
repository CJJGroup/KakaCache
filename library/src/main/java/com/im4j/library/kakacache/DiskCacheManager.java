package com.im4j.library.kakacache;

import com.google.gson.reflect.TypeToken;
import com.im4j.library.kakacache.cache.disk.IDiskCache;
import com.im4j.library.kakacache.cache.disk.SimpleDiskCache;
import com.im4j.library.kakacache.cache.memory.IMemoryCache;
import com.im4j.library.kakacache.cache.memory.SimpleMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.cache.disk.loader.DiskLoader;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 磁盘缓存管理器
 * @version 0.1 king 2016-03
 */
public class DiskCacheManager extends CacheManager {

    private IMemoryCache memory;
    private IDiskCache disk;
    private Map<Type, DiskLoader> loaderMap;
    private Map<Type, DiskWriter> writerMap;

    private DiskCacheManager(IMemoryCache memory, IDiskCache disk) {
        this.memory = memory;
        this.disk = disk;
        this.loaderMap = new HashMap<>();
        this.writerMap = new HashMap<>();
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

    /**
     * 注册类型转换器
     * @param loader
     */
    public <T> void registerTypeConverter(DiskLoader<T> loader, DiskWriter<T> writer) {
        Type type = new TypeToken<T>(){}.getType();
        loaderMap.put(type, loader);
        writerMap.put(type, writer);
    }

    /**
     * 读取
     */
    public <T> T load(String key) throws CacheException {
        T result = (T) memory.load(key);
        if (result != null) {
            return result;
        }

        DiskLoader<T> diskLoader = getLoader();

        InputStream stream = disk.loadStream(key);
        if (stream != null) {
            result = diskLoader.load(stream);
            return result;
        }

        return null;
    }

    /**
     * 保存
     */
    public <T> void save(String key, T value) throws CacheException {
        save(key, value, -1);
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(String key, T value, int expires) throws CacheException {
        if (value == null) {
            memory.remove(key);
            disk.remove(key);
            return;
        }

        DiskWriter<T> diskWriter = getWriter();

        memory.save(key, value, expires);
        disk.save(key, value, diskWriter, expires);
    }

    @Override
    public boolean containsKey(String key) {
        return memory.containsKey(key) || disk.containsKey(key);
    }

    @Override
    public boolean isExpired(String key) {
        return memory.isExpired(key) || disk.isExpired(key);
    }

    @Override
    public void remove(String key) throws CacheException {
        memory.remove(key);
        disk.remove(key);
    }

    @Override
    public void clear() throws CacheException {
        memory.clear();
        disk.clear();
    }

    /**
     * 构造器
     */
    public static class Builder {
        private static final IMemoryCache DEFAULT_MEMORY_CACHE = new SimpleMemoryCache();
        private static final IDiskCache DEFAULT_DISK_CACHE = new SimpleDiskCache();

        private IMemoryCache memory = DEFAULT_MEMORY_CACHE;
        private IDiskCache disk = DEFAULT_DISK_CACHE;

        public Builder(IMemoryCache memory, IDiskCache disk) {
            if (memory == null) {
                memory = DEFAULT_MEMORY_CACHE;
            }
            this.memory = memory;


            if (disk == null) {
                disk = DEFAULT_DISK_CACHE;
            }
            this.disk = disk;
        }

        public DiskCacheManager create() {
            return new DiskCacheManager(memory, disk);
        }
    }

}

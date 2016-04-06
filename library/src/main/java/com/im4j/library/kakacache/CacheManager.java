package com.im4j.library.kakacache;

import com.im4j.library.kakacache.cache.disk.IDiskCache;
import com.im4j.library.kakacache.cache.disk.SimpleDiskCache;
import com.im4j.library.kakacache.cache.memory.IMemoryCache;
import com.im4j.library.kakacache.cache.memory.SimpleMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.loader.Loader;
import com.im4j.library.kakacache.writer.Writer;

import java.io.InputStream;

/**
 * 缓存管理器
 * @version 0.1 king 2016-03
 */
public class CacheManager {

    private IMemoryCache memory;
    private IDiskCache disk;

    private CacheManager(IMemoryCache memory, IDiskCache disk) {
        this.memory = memory;
        this.disk = disk;
    }

    /**
     * 读取
     */
    public <T> T load(String key, Loader<T> loader) throws CacheException {
        T result = (T) memory.load(key);
        if (result != null) {
            return result;
        }

        InputStream stream = disk.loadStream(key);
        if (stream != null) {
            result = loader.load(stream);
            return result;
        }

        return null;
    }

    /**
     * 保存
     */
    public <T> void save(String key, T value, Writer<T> writer) throws CacheException {
        if (value == null) {
            memory.remove(key);
            disk.remove(key);
            return;
        }

        memory.save(key, value);
        disk.save(key, value, writer);
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(String key, T value, int expires, Writer<T> writer) throws CacheException {
        if (value == null) {
            memory.remove(key);
            disk.remove(key);
            return;
        }

        memory.save(key, value, expires);
        disk.save(key, value, writer, expires);
    }


    /**
     * 构造器
     */
    public static class Builder {
        private static final IMemoryCache DEFAULT_MEMORY_CACHE = new SimpleMemoryCache();
        private static final IDiskCache DEFAULT_DISK_CACHE = new SimpleDiskCache();

        private IMemoryCache memory = DEFAULT_MEMORY_CACHE;
        private IDiskCache disk = DEFAULT_DISK_CACHE;

        public Builder memory(IMemoryCache memory) {
            this.memory = memory;
            if (this.memory == null) {
                this.memory = DEFAULT_MEMORY_CACHE;
            }
            return this;
        }

        public Builder disk(IDiskCache disk) {
            this.disk = disk;
            if (this.disk == null) {
                this.disk = DEFAULT_DISK_CACHE;
            }
            return this;
        }

        public CacheManager create() {
            return new CacheManager(memory, disk);
        }
    }

}

package com.im4j.library.kakacache;

import com.im4j.library.kakacache.cache.disk.DiskCache;
import com.im4j.library.kakacache.cache.disk.SimpleDiskCache;
import com.im4j.library.kakacache.cache.memory.MemoryCache;
import com.im4j.library.kakacache.cache.memory.SimpleMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.loader.Loader;
import com.im4j.library.kakacache.writer.Writer;

import java.io.InputStream;
import java.util.Map;

/**
 * 缓存管理器
 * @version 0.1 king 2016-03
 */
public class CacheManager {

    private MemoryCache memory;
    private DiskCache disk;

    private CacheManager(MemoryCache memory, DiskCache disk) {
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
        disk.save(key, writer.writer(value));
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
        // TODO 空实现仍然会调用
        disk.save(key, writer.writer(value), expires);
    }


    /**
     * 构造器
     */
    public static class Builder {
        private static final MemoryCache DEFAULT_MEMORY_CACHE = new SimpleMemoryCache();
        private static final DiskCache DEFAULT_DISK_CACHE = new SimpleDiskCache();

        private MemoryCache memory;
        private DiskCache disk;
        public void memory(MemoryCache memory) {
            this.memory = memory;
        }
        public void disk(DiskCache disk) {
            this.disk = disk;
        }
        public CacheManager create() {
            MemoryCache memory = this.memory;
            if (memory == null) {
                memory = DEFAULT_MEMORY_CACHE;
            }
            DiskCache disk = this.disk;
            if (disk == null) {
                disk = DEFAULT_DISK_CACHE;
            }
            return new CacheManager(memory, disk);
        }
    }

}

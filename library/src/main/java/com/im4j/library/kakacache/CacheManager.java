package com.im4j.library.kakacache;

import com.im4j.library.kakacache.cache.disk.IDiskCache;
import com.im4j.library.kakacache.cache.disk.InternalDiskCache;
import com.im4j.library.kakacache.cache.memory.IMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;

/**
 * 缓存管理器
 * @version 0.1 king 2016-04
 */
public class CacheManager {

    private IMemoryCache memory;
    private InternalDiskCache disk;

    private CacheManager(IMemoryCache memory, IDiskCache disk) {
        // TODO checkArguments throw exception
        this.memory = memory;
        this.disk = new InternalDiskCache(disk);
    }


    /**
     * 读取
     */
    public <T> T load(String key) throws CacheException {
        T result = (T) memory.load(key);
        if (result != null) {
            return result;
        }

        result = disk.load(key);
        if (result != null) {
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

        memory.save(key, value, expires);
        disk.save(key, value, expires);
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return memory.containsKey(key) || disk.containsKey(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) throws CacheException {
        memory.remove(key);
        disk.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        memory.clear();
        disk.clear();
    }

    /**
     * 构造器
     */
    public static class Builder {
        private IMemoryCache memory;
        private IDiskCache disk;

        public Builder(IMemoryCache memory, IDiskCache disk) {
            this.memory = memory;
            this.disk = disk;
        }

        public CacheManager create() {
            return new CacheManager(memory, disk);
        }
    }

}

package com.im4j.library.kakacache.cache;

import com.im4j.library.kakacache.cache.disk.DiskCacheWrapper;
import com.im4j.library.kakacache.cache.disk.loader.DiskLoader;
import com.im4j.library.kakacache.cache.disk.storage.IDiskStorage;
import com.im4j.library.kakacache.cache.disk.storage.IJournal;
import com.im4j.library.kakacache.cache.disk.writer.DiskWriter;
import com.im4j.library.kakacache.cache.memory.IMemoryCache;
import com.im4j.library.kakacache.exception.CacheException;
import com.im4j.library.kakacache.utils.Utils;

/**
 * 缓存核心
 * @version 0.1 king 2016-04
 */
public class CacheCore {

    private IMemoryCache memory;
    private DiskCacheWrapper disk;

    private CacheCore(IMemoryCache memory, DiskCacheWrapper disk) {
        this.memory = Utils.checkNotNull(memory);
        this.disk = Utils.checkNotNull(disk);
    }


    /**
     * 读取
     */
    public <T> T load(String key) throws CacheException {
        if (memory != null) {
            T result = (T) memory.load(key);
            if (result != null) {
                return result;
            }
        }

        if (disk != null) {
            T result = disk.load(key);
            if (result != null) {
                return result;
            }
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

        if (memory != null) {
            memory.save(key, value, expires);
        }
        if (disk != null) {
            disk.save(key, value, expires);
        }
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        if (memory != null) {
            if (memory.containsKey(key)) {
                return true;
            }
        }
        if (disk != null) {
            if (disk.containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(String key) throws CacheException {
        if (memory != null) {
            memory.remove(key);
        }
        if (disk != null) {
            disk.remove(key);
        }
    }

    /**
     * 清空缓存
     */
    public void clear() throws CacheException {
        if (memory != null) {
            memory.clear();
        }
        if (disk != null) {
            disk.clear();
        }
    }


    /**
     * 构造器
     */
    public static class Builder {
        private IMemoryCache memory;
        private DiskCacheWrapper disk;

        public Builder() {
        }

        public Builder memory(IMemoryCache memory) {
            this.memory = Utils.checkNotNull(memory);
            return this;
        }

        public Builder disk(IDiskStorage storage, IJournal journal, long maxSize) {
            Utils.checkNotNull(storage);
            Utils.checkNotNull(journal);
            Utils.checkNotLessThanZero(maxSize);

            this.disk = new DiskCacheWrapper(storage, journal, maxSize);
            return this;
        }

        /**
         * 注册类型转换器
         * @param loader
         */
        public <T> Builder registerTypeConverter(DiskLoader<T> loader, DiskWriter<T> writer) {
            Utils.checkNotNull(disk);
            Utils.checkNotNull(loader);
            Utils.checkNotNull(writer);

            disk.registerTypeConverter(Utils.checkNotNull(loader), Utils.checkNotNull(writer));
            return this;
        }

        public CacheCore create() {
            return new CacheCore(memory, disk);
        }
    }

}

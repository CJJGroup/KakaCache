package com.im4j.kakacache.core.cache;

import com.im4j.kakacache.core.cache.disk.DiskCache;
import com.im4j.kakacache.core.cache.disk.converter.IDiskConverter;
import com.im4j.kakacache.core.cache.disk.storage.IDiskStorage;
import com.im4j.kakacache.core.cache.journal.IJournal;
import com.im4j.kakacache.core.cache.memory.storage.IMemoryStorage;
import com.im4j.kakacache.core.cache.memory.MemoryCache;
import com.im4j.kakacache.core.exception.CacheException;
import com.im4j.kakacache.core.utils.Utils;

/**
 * 缓存核心
 * @version 0.1 king 2016-04
 */
public class CacheCore {

    private MemoryCache memory;
    private DiskCache disk;

    private CacheCore(MemoryCache memory, DiskCache disk) {
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
        private IMemoryStorage memory;
        private IJournal memoryJournal;

        private IDiskStorage disk;
        private IJournal diskJournal;
        private IDiskConverter converter;

        public Builder() {
        }

        public Builder memory(IMemoryStorage memory) {
            this.memory = Utils.checkNotNull(memory);
            return this;
        }

        public Builder memoryJournal(IJournal journal) {
            this.memoryJournal = Utils.checkNotNull(journal);
            return this;
        }

        public Builder disk(IDiskStorage disk) {
            this.disk = Utils.checkNotNull(disk);
            return this;
        }

        public Builder diskJournal(IJournal journal) {
            this.diskJournal = Utils.checkNotNull(journal);
            return this;
        }

        public Builder converter(IDiskConverter converter) {
            this.converter = Utils.checkNotNull(converter);
            return this;
        }

        public CacheCore create() {
            // TODO 根据配置，选择合适的构造方法
            return new CacheCore(new MemoryCache(memory, memoryJournal),
                    new DiskCache(disk, diskJournal, converter));
        }
    }

}

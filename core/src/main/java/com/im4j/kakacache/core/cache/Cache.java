package com.im4j.kakacache.core.cache;

import com.im4j.kakacache.common.exception.CacheException;
import com.im4j.kakacache.common.utils.Utils;

import java.util.List;

/**
 * 缓存基类
 * @version alafighting 2016-04
 */
public abstract class Cache {

    protected final long mMaxSize;
    protected final long mMaxQuantity;

    public Cache(long maxSize, long maxQuantity) {
        this.mMaxSize = maxSize;
        this.mMaxQuantity = maxQuantity;
    }


    /**
     * 读取
     * @param key
     * @param <T>
     * @return
     */
    public final <T> T load(String key) throws CacheException {
        Utils.checkNotNull(key);

        if (!containsKey(key)) {
            return null;
        }

        // 过期自动清理
        if (isExpiry(key)) {
            remove(key);
            return null;
        }

        // 读取缓存
        return doLoad(key);
    }

    /**
     * 读取
     * @param key
     * @param <T>
     * @return
     */
    protected abstract <T> T doLoad(String key) throws CacheException;

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public final <T> void save(String key, T value, int expires, CacheTarget target) throws CacheException {
        Utils.checkNotNull(key);

        if (value == null) {
            remove(key);
            return;
        }

        // TODO 先写入，后清理。会超出限定条件，需要一定交换空间

        // 写入缓存
        doSave(key, value, expires, target);

        // 清理无用数据
        clearUnused();
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    protected abstract <T> void doSave(String key, T value, int expires, CacheTarget target) throws CacheException;


    /**
     * 是否过期
     */
    protected abstract boolean isExpiry(String key);

    /**
     * 是否包含
     * @param key
     * @return
     */
    public abstract boolean containsKey(String key);

    /**
     * 删除缓存
     * @param key
     */
    public abstract void remove(String key) throws CacheException;

    /**
     * 清空缓存
     */
    public abstract void clear() throws CacheException;


    /**
     * 日志快照
     */
    public abstract List<CacheEntry> snapshot();


    /**
     * 获取准备丢弃的Key
     * @return 准备丢弃的Key（如存储空间不足时，需要清理）
     */
    public abstract String getLoseKey() throws CacheException;

    /**
     * 缓存大小
     * @return 单位:byte
     */
    public abstract long getTotalSize();

    /**
     * 缓存个数
     * @return 单位:个数
     */
    public abstract long getTotalQuantity();

    /**
     * 清理无用缓存
     */
    public void clearUnused() {
        // 清理过期
        for (CacheEntry entry : snapshot()) {
            if (entry.isExpiry()) {
                remove(entry.getKey());
            }
        }

        // 清理超出缓存
        if (mMaxSize != 0) {
            while (mMaxSize < getTotalSize()) {
                remove(getLoseKey());
            }
        }
        if (mMaxQuantity != 0) {
            while (mMaxQuantity < getTotalQuantity()) {
                remove(getLoseKey());
            }
        }
    }

}

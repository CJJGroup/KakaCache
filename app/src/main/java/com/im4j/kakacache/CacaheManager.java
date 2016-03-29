package com.im4j.kakacache;

import com.im4j.kakacache.loader.Loader;
import com.im4j.kakacache.writer.Writer;

/**
 * 缓存管理器
 * @version 0.1 king 2016-03
 */
public class CacaheManager {

    /**
     * 读取
     */
    public <T> T load(String key, Loader<T> loader) {
        return null;
    }

    /**
     * 保存
     */
    public <T> void save(String key, Object value, Writer<T> writer) {
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(String key, Object value, int expires, Writer<T> writer) {
    }


}

package com.im4j.library.kakacache.cache.disk.loader;

import com.im4j.library.kakacache.cache.disk.source.Source;

import java.io.InputStream;

/**
 * 加载器
 * @version 0.1 king 2016-03
 */
public interface DiskLoader<T> {

    /**
     * 读取
     * @param source
     * @return
     */
    T load(Source source);

}

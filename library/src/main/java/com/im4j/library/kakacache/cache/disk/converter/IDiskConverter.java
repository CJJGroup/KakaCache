package com.im4j.library.kakacache.cache.disk.converter;

import com.im4j.library.kakacache.cache.disk.sink.Sink;
import com.im4j.library.kakacache.cache.disk.source.Source;

import java.lang.reflect.Type;

/**
 * 通用转换器
 * @version alafighting 2016-04
 */
public interface IDiskConverter {

    /**
     * 读取
     * @param source
     * @return
     */
    Object load(Source source, Type type);

    /**
     * 写入
     * @param sink
     * @param data
     * @return
     */
    void writer(Sink sink, Object data);

}

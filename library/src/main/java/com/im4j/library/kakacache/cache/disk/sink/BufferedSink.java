package com.im4j.library.kakacache.cache.disk.sink;

import com.im4j.library.kakacache.cache.disk.source.Source;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 带缓冲的数据槽
 * @version 0.1 king 2016-04
 */
public interface BufferedSink extends Sink {

    void writeString(String str, Charset charset) throws IOException;
    void writeString(String str, int beginIndex, int endIndex, Charset charset) throws IOException;

}

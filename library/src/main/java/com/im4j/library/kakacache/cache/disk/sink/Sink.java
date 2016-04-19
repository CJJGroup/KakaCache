package com.im4j.library.kakacache.cache.disk.sink;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * 数据槽
 * @version 0.1 king 2016-04
 */
public interface Sink extends Closeable, Flushable {

    /**
     * 写入一个字节
     * @param oneByte 从0到255的整数
     * @throws IOException
     */
    void write(int oneByte) throws IOException;

    void write(byte[] buffer, int offset, int byteCount) throws IOException;

    @Override
    void flush() throws IOException;

    @Override
    void close() throws IOException;

}

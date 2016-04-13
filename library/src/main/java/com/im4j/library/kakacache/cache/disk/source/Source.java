package com.im4j.library.kakacache.cache.disk.source;

import java.io.Closeable;
import java.io.IOException;

/**
 * 数据源
 * @version 0.1 king 2016-04
 */
public interface Source extends Closeable {

    int read() throws IOException;
    int read(byte[] buffer) throws IOException;
    int read(byte[] buffer, int offset, int byteCount) throws IOException;

    @Override
    void close() throws IOException;

}

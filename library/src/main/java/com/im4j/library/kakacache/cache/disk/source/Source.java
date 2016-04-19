package com.im4j.library.kakacache.cache.disk.source;

import java.io.Closeable;
import java.io.IOException;

/**
 * 数据源
 * @version 0.1 king 2016-04
 */
public interface Source extends Closeable {

    /**
     * 读取一个字节，并将其作为一个从0到255的整数
     * @return 如果返回-1，表示已达到末尾
     * @throws IOException
     */
    int read() throws IOException;

    int read(byte[] buffer, int offset, int byteCount) throws IOException;

    @Override
    void close() throws IOException;

}

package com.im4j.kakacache.core.cache.disk.sink;

import java.io.IOException;

/**
 * 包含基本操作的数据槽
 * @version 0.1 king 2016-04
 */
public class BasicSink implements Sink {

    volatile Sink sink;

    public BasicSink(Sink sink) {
        this.sink = sink;
    }

    @Override
    public void write(int oneByte) throws IOException {
        sink.write(oneByte);
    }

    @Override
    public void write(byte[] buffer, int offset, int byteCount) throws IOException {
        sink.write(buffer, offset, byteCount);
    }

    @Override
    public void flush() throws IOException {
        sink.flush();
    }

    @Override
    public void close() throws IOException {
        sink.close();
    }

}

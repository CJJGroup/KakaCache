package com.im4j.library.kakacache.cache.disk.source;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 带缓冲的数据源
 * @version 0.1 king 2016-04
 */
public interface BufferedSource extends Source {

    String readString(int offset, int byteCount, Charset charset) throws IOException;
    String readLine(Charset charset) throws IOException;

}

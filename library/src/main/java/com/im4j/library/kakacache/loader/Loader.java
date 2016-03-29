package com.im4j.library.kakacache.loader;

import java.io.InputStream;

/**
 * 加载器
 * @version 0.1 king 2016-03
 */
public interface Loader<T> {

    /**
     * 读取
     * @param stream
     * @return
     */
    T load(InputStream stream);

}

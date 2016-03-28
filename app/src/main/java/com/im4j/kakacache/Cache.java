package com.im4j.kakacache;

/**
 * 缓存接口
 * @version 0.1 king 2016-03
 */
public interface Cache {

    /**
     * 是否过期
     * @param key
     * @return
     */
    boolean isExpired(String key);

    /**
     * 删除缓存
     * @param key
     */
    void remove(String key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 缓存大小
     * @return 单位:byte
     */
    long size();

}

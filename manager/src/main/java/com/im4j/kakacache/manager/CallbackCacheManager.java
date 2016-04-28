package com.im4j.kakacache.manager;

import com.im4j.kakacache.common.exception.ArgumentException;
import com.im4j.kakacache.common.exception.Exception;
import com.im4j.kakacache.common.exception.NotFoundException;
import com.im4j.kakacache.common.utils.Utils;
import com.im4j.kakacache.core.cache.CacheCore;
import com.im4j.kakacache.core.cache.CacheTarget;
import com.im4j.kakacache.manager.callback.OnCallbackListener;
import com.im4j.kakacache.manager.callback.OnFailure;

import java.util.concurrent.Executor;

/**
 * 回调模式缓存管理
 * @version alafighting 2016-04
 */
public class CallbackCacheManager extends CacheManager {

    private Executor executor;

    public CallbackCacheManager(CacheCore cache, Executor executor) {
        super(cache);
        this.executor = executor;
    }


    /**
     * 读取
     */
    public <T> void load(final String key, final OnCallbackListener<T> loader, final OnFailure failure) {
        if (loader == null) {
            throw new ArgumentException("loader can not be null");
        }
        if (failure == null) {
            throw new ArgumentException("failure can not be null");
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                T value = null;
                Exception exception = null;
                try {
                    value = _load(key);
                } catch (Exception e) {
                    exception = e;
                }

                if (value != null) {
                    loader.onCallback(value);
                } else {
                    if (exception == null) {
                        exception = new NotFoundException("找不到缓存’"+key+"'");
                    }
                    failure.onFailure(exception);
                }
            }
        });
    }

    /**
     * 保存
     * @param expires 有效期（单位：秒）
     */
    public <T> void save(final String key, final T value, final int expires,
                         final CacheTarget target,
                         final OnCallbackListener<Boolean> saver,
                         final OnFailure failure) {
        if (saver == null) {
            throw new ArgumentException("loader can not be null");
        }
        if (failure == null) {
            throw new ArgumentException("failure can not be null");
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Exception exception = null;
                try {
                    _save(key, value, expires, target);
                } catch (Exception e) {
                    exception = e;
                }

                if (exception == null) {
                    saver.onCallback(true);
                } else {
                    failure.onFailure(exception);
                }
            }
        });
    }

    /**
     * 是否包含
     * @param key
     * @return
     */
    public void containsKey(final String key, final OnCallbackListener<Boolean> finder) {
        if (finder == null) {
            throw new ArgumentException("finder can not be null");
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean value = _containsKey(key);
                finder.onCallback(value);
            }
        });
    }

    /**
     * 删除缓存
     * @param key
     */
    public void remove(final String key, final OnCallbackListener<Boolean> remove) {
        if (remove == null) {
            throw new ArgumentException("remove can not be null");
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Exception exception = null;
                try {
                    _remove(key);
                } catch (Exception e) {
                    exception = e;
                }

                if (exception == null) {
                    remove.onCallback(true);
                } else {
                    remove.onCallback(false);
                }
            }
        });
    }

    /**
     * 清空缓存
     */
    public void clear(final OnCallbackListener<Boolean> clear) {
        if (clear == null) {
            throw new ArgumentException("clear can not be null");
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Exception exception = null;
                try {
                    _clear();;
                } catch (Exception e) {
                    exception = e;
                }

                if (exception == null) {
                    clear.onCallback(true);
                } else {
                    clear.onCallback(false);
                }
            }
        });
    }



    /**
     * 构造器
     */
    public static class Builder extends CacheManager.Builder {
        private Executor executor;

        public Builder(CacheCore cache) {
            super(cache);
        }

        public Builder executor(Executor executor) {
            this.executor = Utils.checkNotNull(executor);
            return this;
        }

        public CallbackCacheManager create() {
            return new CallbackCacheManager(cache, executor);
        }
    }

}

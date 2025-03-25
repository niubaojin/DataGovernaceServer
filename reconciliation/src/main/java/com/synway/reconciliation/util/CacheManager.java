package com.synway.reconciliation.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 缓存管理类
 * @author
 */
public class CacheManager {
    private static final LoadingCache<String, Object> LOADING_CACHE;

    static {
        LOADING_CACHE = CacheBuilder.newBuilder()
                /**初始化缓存大小*/
                .initialCapacity(100)
                /**设置过期时间,60分钟*/
                .expireAfterWrite(60, TimeUnit.MINUTES)
                /**开启缓存统计功能*/
                .recordStats()
                /**构建缓存*/
                .build(new CacheLoader<Object, Object>() {
                    @Override
                    public Object load(Object key) throws Exception {
                        return null;
                    }
                    @Override
                    public Map<Object, Object> loadAll(Iterable<?> keys) throws Exception {
                        return super.loadAll(keys);
                    }
                });
    }


    public static void invalidateAll() {
        LOADING_CACHE.invalidateAll();
    }

    public static void invalidate(String key) {
        LOADING_CACHE.invalidate(key);
    }

    public static Object getIfPresent(String key) {
        return LOADING_CACHE.getIfPresent(key);
    }

    public static void put(String key, Object value) {
        LOADING_CACHE.put(key, value);
    }

}

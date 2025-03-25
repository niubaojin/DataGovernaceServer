package com.synway.datastandardmanager.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存存储的相关内容
 * @date 2020/06/02
 */
@Component
public class CacheManager<T> {
    private Map<String,T> cache = new ConcurrentHashMap<>();

    public T getValue(Object key){
        return cache.get(key);
    }

    public void addOrUpdateCache(String key,T value){
        cache.put(key,value);
    }

    public void evictCache(String key){//根据key移除元素
        cache.remove(key);
    }

    public void evictCache(){
        cache.clear();
    }


    public boolean checkKeyIsExist(String key){
        if(StringUtils.isBlank(key)){
            return false;
        }
        return cache.containsKey(key);
    }

}

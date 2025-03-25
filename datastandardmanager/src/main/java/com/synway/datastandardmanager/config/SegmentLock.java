package com.synway.datastandardmanager.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangdongwei
 * @date 2021/7/30 10:41
 */
public class SegmentLock<T> {
    /**
     * 默认预先创建的锁数量.
     */
    private int DEFAULT_LOCK_COUNT = 20;

    private final ConcurrentHashMap<Integer, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public SegmentLock() {
        init(null, false);
    }

    public SegmentLock(Integer count, boolean isFair) {
        init(count, isFair);
    }

    private void init(Integer count, boolean isFair) {
        if (count != null && count != 0) {
            this.DEFAULT_LOCK_COUNT = count;
        }
        // 预先初始化指定数量的锁
        for (int i = 0; i < this.DEFAULT_LOCK_COUNT; i++) {
            this.lockMap.put(i, new ReentrantLock(isFair));
        }
    }

    public ReentrantLock get(T key) {
        return this.lockMap.get((key.hashCode() >>> 1) % DEFAULT_LOCK_COUNT);
    }

    public void lock(T key) {
        ReentrantLock lock = this.get(key);
        lock.lock();
    }

    public void unlock(T key) {
        ReentrantLock lock = this.get(key);
        lock.unlock();
    }
}

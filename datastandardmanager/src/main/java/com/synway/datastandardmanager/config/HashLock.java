package com.synway.datastandardmanager.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangdongwei
 * @date 2021/7/30 10:38
 */
public class HashLock<T> {
    private boolean fair = false;
    private final SegmentLock<T> segmentLock = new SegmentLock<>();
    private final ConcurrentHashMap<T, ReentrantLockCount> lockMap = new ConcurrentHashMap<>();

    public HashLock() {

    }

    public HashLock(boolean fair) {
        this.fair = fair;
    }

    public void lock(T key) {
        ReentrantLockCount lock;
        // 通过分段锁来保证获取锁时的线程安全
        this.segmentLock.lock(key);
        try {
            lock = this.lockMap.get(key);
            if (lock == null) {
                lock = new ReentrantLockCount(this.fair);
                this.lockMap.put(key, lock);
            } else {
                // map 中已经存在说明锁已经创建，直接数量加一
                lock.incrementAndGet();
            }
        } finally {
            this.segmentLock.unlock(key);
        }
        lock.lock();
    }

    public void unlock(T key) {
        ReentrantLockCount reentrantLockCount = this.lockMap.get(key);
        // 判断加锁的次数等于一的话可以将 map 中的锁移除
        if (reentrantLockCount.getCount() == 1) {
            this.segmentLock.lock(key);
            try {
                if (reentrantLockCount.getCount() == 1) {
                    this.lockMap.remove(key);
                }
            } finally {
                this.segmentLock.unlock(key);
            }
        }
        reentrantLockCount.unlock();
    }

    static class ReentrantLockCount {
        private ReentrantLock reentrantLock;
        // 记录加锁的次数
        private AtomicInteger count = new AtomicInteger(1);

        public ReentrantLockCount(boolean fair) {
            this.reentrantLock = new ReentrantLock(fair);
        }

        public void lock() {
            this.reentrantLock.lock();
        }

        public void unlock() {
            this.count.decrementAndGet();
            this.reentrantLock.unlock();
        }

        public int incrementAndGet() {
            return this.count.incrementAndGet();
        }

        public int getCount() {
            return this.count.get();
        }
    }
}

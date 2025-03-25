package com.synway.datarelation.service.common.impl;

import com.alibaba.excel.util.CollectionUtils;
import com.hazelcast.core.HazelcastInstance;
import com.synway.datarelation.constant.Common;
import com.synway.datarelation.pojo.databloodline.NodePageCache;
import com.synway.datarelation.service.common.CacheManageService;
import com.synway.common.exception.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author wangdongwei
 * @date 2021/5/11 19:53
 */
@Service
public class CacheManageServiceImpl  implements CacheManageService {

    private static Logger logger = LoggerFactory.getLogger(CacheManageServiceImpl.class);

    private final HazelcastInstance hazelcastInstance;
    @Autowired
    public CacheManageServiceImpl(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * 多线程会涉及 数据同步的情况
     * @param nodePageCache
     * @return
     * @throws Exception
     */
    @Override
    public String putNodeToCache(NodePageCache nodePageCache) throws Exception {
        logger.info("页面["+nodePageCache.getPageId()+"]开始将节点id["+nodePageCache.getLockNodeId()+"]页面上的数据加入到缓存中");
        nodePageCache.setInsertDate(System.currentTimeMillis());
        ConcurrentMap<String, List<NodePageCache>> map = hazelcastInstance.getMap(Common.LOCK_NODE_PAGE);
        Lock lock = hazelcastInstance.getCPSubsystem().getLock(nodePageCache.getPageId());
        try{
            logger.info("pageId:"+nodePageCache.getPageId()+"开始加锁");
            lock.tryLock(2, TimeUnit.SECONDS);
            // 将数据写入到列表中  不知道有没有 原子性
            map.putIfAbsent(nodePageCache.getPageId(),new ArrayList<>());
            List<NodePageCache> list = map.get(nodePageCache.getPageId());
            list.removeIf(d -> StringUtils.equalsIgnoreCase(d.getLockNodeId(),nodePageCache.getLockNodeId()));
            list.add(nodePageCache);
            map.put(nodePageCache.getPageId(),list);
        }catch (Exception e){
            logger.error("节点添加报错"+ExceptionUtil.getExceptionTrace(e));
        }finally {
            logger.info("pageId:"+nodePageCache.getPageId()+"解锁成功");
            lock.unlock();
        }
        return "页面["+nodePageCache.getPageId()+"]节点id["+nodePageCache.getLockNodeId()+"]缓存加入成功";
    }

    @Override
    public NodePageCache getNodePageCacheById(String pageId, String nodeId) {
        logger.info("开始获取缓存中锁定节点的数据，pageId: "+pageId+" nodeId: "+nodeId);
        if(StringUtils.isBlank(pageId)){
            throw new NullPointerException("pageId为空,不能获取缓存中的数据");
        }
        if(StringUtils.isBlank(nodeId)){
            throw new NullPointerException("nodeId为空,不能获取缓存中的数据");
        }
        // 先获取缓存中的所有数据
        ConcurrentMap<String, List<NodePageCache>> map = hazelcastInstance.getMap(Common.LOCK_NODE_PAGE);
        List<NodePageCache> list = map.getOrDefault(pageId,null);
        if(CollectionUtils.isEmpty(list)){
            logger.error("pageId: "+pageId+"缓存中该页面保存的信息已经被删除");
            throw new NullPointerException("缓存中该页面保存的信息已经被删除");
        }
        Optional<NodePageCache> data = list.stream().filter( d-> StringUtils.equalsIgnoreCase(d.getLockNodeId(),nodeId))
                .max(Comparator.comparingLong(NodePageCache::getInsertDate));
        if(data.isPresent()){
            logger.info("pageId: "+pageId+" nodeId: "+nodeId+"缓存中的数据查询成功");
            return data.get();
        }else{
            logger.error("pageId: "+pageId+" nodeId: "+nodeId+"缓存中保存的信息已经被删除");
            throw new NullPointerException("缓存中该页面保存节点["+nodeId+"]的信息已经被删除");
        }
    }

    /**
     * 删除时需要加锁  根据  pageId 来锁
     * @param pageId
     * @param nodeId
     * @return
     */
    @Override
    public String delNodeCache(String pageId, String nodeId) {
        logger.info("开始删除缓存中锁定节点的数据，pageId: "+pageId+" nodeId: "+nodeId);
        if(StringUtils.isBlank(pageId)){
            throw new NullPointerException("pageId为空,不能删除缓存中的数据");
        }
        ConcurrentMap<String, List<NodePageCache>> map = hazelcastInstance.getMap(Common.LOCK_NODE_PAGE);
        if(StringUtils.isBlank(nodeId)){
            logger.info("节点id为空，删除pageId"+pageId+"中的所有数据");
            map.remove(pageId);
            return "删除pageId"+pageId+"中的所有数据成功";
        }
        List<NodePageCache> list = map.getOrDefault(pageId,null);
        if(CollectionUtils.isEmpty(list)){
            logger.info("pageId"+pageId+"缓存中的数据为空，无法删除nodeId"+nodeId+"对应的数据");
            return "pageId"+pageId+"缓存中的数据为空，无法删除nodeId"+nodeId+"对应的数据";
        }
        Lock lock = hazelcastInstance.getCPSubsystem().getLock(pageId);
        String resultStr = null;
        try{
            logger.info("pageId:"+pageId+"开始加锁");
            lock.tryLock(2, TimeUnit.SECONDS);
            boolean delFlag = list.removeIf(d -> StringUtils.equalsIgnoreCase(d.getLockNodeId(),nodeId));
            if(delFlag){
                map.put(pageId,list);
                logger.info("pageId:"+pageId+" nodeId:"+nodeId+"缓存删除成功");
                resultStr =  "pageId:"+pageId+" nodeId:"+nodeId+"缓存删除成功";
            }else{
                logger.info("pageId:"+pageId+" nodeId:"+nodeId+"缓存删除失败");
                resultStr = "pageId:"+pageId+" nodeId:"+nodeId+"缓存删除失败";
            }
        }catch (Exception e){
            logger.error("删除指定的nodeId"+nodeId+"的缓存信息报错"+ ExceptionUtil.getExceptionTrace(e));
        }finally {
            logger.info("pageId:"+pageId+"解锁");
            lock.unlock();
        }
        return resultStr;
    }

    @Override
    public void deleteExpiredNodeCache() {
        logger.info("开始删除锁定节点的过期缓存");
        long nowLong = System.currentTimeMillis();
        ConcurrentMap<String, List<NodePageCache>> map = hazelcastInstance.getMap(Common.LOCK_NODE_PAGE);
        for(Map.Entry<String, List<NodePageCache>> data:map.entrySet()){
            if(StringUtils.isBlank(data.getKey())){
                map.remove(data.getKey());
                logger.info(data.getKey()+"为空字符串，删除该map中的数据");
                continue;
            }
            if(CollectionUtils.isEmpty(data.getValue())){
                map.remove(data.getKey());
                logger.info(data.getKey()+"中的数据为空，将key值删除");
                continue;
            }
            // 这里也需要加锁，防止数据出现问题
            Lock lock = hazelcastInstance.getCPSubsystem().getLock(data.getKey());
            try{
                lock.tryLock(2, TimeUnit.SECONDS);
                List<NodePageCache> list = data.getValue();
                boolean delFlag = list.removeIf(d-> (nowLong - d.getInsertDate() >= Common.TIME_OUT));
                logger.info("pageId"+data.getKey()+"对应的缓存信息中30分钟前的数据删除状态"+delFlag);
                map.put(data.getKey(),list);
            }catch (Exception e){
                logger.error(ExceptionUtil.getExceptionTrace(e));
            }finally {
                lock.unlock();
            }
        }
        long endLong = System.currentTimeMillis();
        logger.info("删除锁定节点的过期缓存结束,总共耗时"+(endLong-nowLong)/1000);
    }

    @Override
    public ConcurrentMap<String, CopyOnWriteArrayList<NodePageCache>> getAllCache() {
        return hazelcastInstance.getMap(Common.LOCK_NODE_PAGE);
    }

}

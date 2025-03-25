package com.synway.datarelation.service.common;

import com.synway.datarelation.pojo.databloodline.NodePageCache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  锁定之后的缓存管理
 * @author wangdongwei
 */
public interface CacheManageService {


    /**
     *  将需要锁定的节点信息插入到缓存中
     * @param nodePageCache
     * @return
     * @throws Exception
     */
    String putNodeToCache(NodePageCache nodePageCache) throws Exception;


    /**
     * 查询缓存中的数据
     * @param pageId
     * @param nodeId
     * @return
     */
    NodePageCache getNodePageCacheById(String pageId, String nodeId);

    /**
     * 删除缓存中的数据
     * @param pageId
     * @param nodeId
     * @return
     */
    String delNodeCache(String pageId, String nodeId);


    /**
     * 定时删除缓存中的过期节点的信息
     */
    void deleteExpiredNodeCache();


    /**
     * 获取分布式缓存中 锁定节点的所有缓存信息
     * @return
     */
    ConcurrentMap<String, CopyOnWriteArrayList<NodePageCache>> getAllCache();

}

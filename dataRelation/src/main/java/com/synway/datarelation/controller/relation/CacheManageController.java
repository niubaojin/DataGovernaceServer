package com.synway.datarelation.controller.relation;

import com.synway.common.bean.ServerResponse;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.pojo.databloodline.NodePageCache;
import com.synway.datarelation.service.common.CacheManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *  锁定时需要把页面上的数据存储在缓存中
 * @author wangdongwei
 * @date 2021/5/11 17:29
 */
@RestController
@RequestMapping(value = "/cache")
public class CacheManageController {
    private static Logger logger = LoggerFactory.getLogger(CacheManageController.class);

    @Autowired
    private CacheManageService cacheManageServiceImpl;

    /**
     * 锁定节点之后将相关数据插入到缓存中
     * @param nodePageCache
     * @param bindingResult
     * @return
     */
    @PostMapping(value="/putNodeToCache")
    public ServerResponse<String> putNodeToCache(@RequestBody @Valid NodePageCache nodePageCache,
                                                 BindingResult bindingResult){
        logger.info("开始将需要锁定的节点信息插入到缓存中");
        ServerResponse<String> serverResponse = null;
        try{
            if(bindingResult.hasErrors()){
                serverResponse = ServerResponse.asErrorResponse(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
                return serverResponse;
            }
            String result = cacheManageServiceImpl.putNodeToCache(nodePageCache);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("将锁定表信息写入到缓存中报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("将锁定表信息写入到缓存中报错"+e.getMessage());
        }
        return serverResponse;
    }


    /**
     * 获取缓存中的数据
     * @param pageId   页面的uuid
     * @param nodeId    锁定的节点id
     * @return
     */
    @GetMapping(value="/getNodePageCacheById")
    public ServerResponse<NodePageCache>  getNodePageCacheById(@RequestParam(value = "pageId")  String pageId,
                                                @RequestParam(value = "nodeId") String nodeId){
        ServerResponse<NodePageCache> serverResponse = null;
        try{
            NodePageCache nodePageCache = cacheManageServiceImpl.getNodePageCacheById(pageId,nodeId);
            serverResponse = ServerResponse.asSucessResponse(nodePageCache);
        }catch (Exception e){
            logger.error("获取缓存中锁定节点的数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取缓存中锁定节点的数据报错"+e.getMessage());
        }
        return serverResponse;
    }



    /**
     * 删除缓存中的数据，pageId不能为空，如果nodid为空，则删除指定pageId中所有的缓存数据
     * @param pageId  页面uuid 锁定的节点id
     * @return
     */
    @DeleteMapping(value="/delNodeCache")
    public ServerResponse<String>  delNodeCache(@RequestParam(value = "pageId")  String pageId,
                                                @RequestParam(value = "nodeId") String nodeId){
        ServerResponse<String> serverResponse = null;
        try{
            String result = cacheManageServiceImpl.delNodeCache(pageId,nodeId);
            serverResponse = ServerResponse.asSucessResponse(result);
        }catch (Exception e){
            logger.error("删除缓存中锁定节点的数据报错"+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("删除缓存中锁定节点的数据报错"+e.getMessage());

        }
        return serverResponse;
    }



    @GetMapping(value="/getAllCacheData")
    public ConcurrentMap<String, CopyOnWriteArrayList<NodePageCache>> getAllCacheData(){

        try{
            return cacheManageServiceImpl.getAllCache();
        }catch (Exception e){
            logger.error("获取缓存中所有数据报错"+ ExceptionUtil.getExceptionTrace(e));
            return null;
        }
    }

}

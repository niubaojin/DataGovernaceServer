package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.pojo.sourcedata.BackDataConstruct;
import com.synway.datastandardmanager.pojo.sourcedata.DubboBackData;
import com.synway.datastandardmanager.pojo.sourcedata.OrganDataDubbo;
import com.synway.datastandardmanager.service.DubboMessageService;
//import com.synway.portalinterface.dubbo.organuser.OrganUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdongwei
 * @ClassName DubboMessageServiceImpl
 * @description TODO
 * @date 2020/9/9 21:06
 */
@Service
public class DubboMessageServiceImpl implements DubboMessageService {
    private static Logger logger= LoggerFactory.getLogger(DubboMessageServiceImpl.class);

//    @Resource(name = "referenceConfig")
//    private OrganUserService referenceConfig;

    /**
     * 获取dubbo中管理单位的名称
     * @return
     */
    @Override
    public List<BackDataConstruct> getResourceManagementUnit() {
        List<BackDataConstruct> list  = new ArrayList<>();
//        DubboBackData dubboBackData = JSONObject.parseObject(referenceConfig.findOrganAll(),DubboBackData.class);
//        logger.info("dubbo调用成功");
//        if(dubboBackData.isSuccess()){
//            logger.info("开始封装返回前端台的数据结构");
//            List<OrganDataDubbo> organDatas = dubboBackData.getData();
//            getOrganDataChildren(organDatas, Long.valueOf("0"), list);
//        }else{
//            logger.error("dubbo调用门户接口出错"+dubboBackData.getData());
//        }
        return list;
    }

    /**
     * 迭代获取管理单位的相关信息
     * @param organDatas 从dubbo中获取到的数据
     * @param organFather  父节点
     * @param list   存储的数据
     */
    private void getOrganDataChildren(List<OrganDataDubbo> organDatas, Long organFather,List<BackDataConstruct> list){
        for (OrganDataDubbo organDataDubbo:organDatas) {
            if(organDataDubbo.getOrganFather() == organFather){
                BackDataConstruct backDataConstruct = new BackDataConstruct();
                backDataConstruct.setValue(String.valueOf(organDataDubbo.getSymbol12()));
                backDataConstruct.setLabel(organDataDubbo.getOrganName());
                List<BackDataConstruct> childList = new ArrayList<>();
                getOrganDataChildren(organDatas, organDataDubbo.getOrganId(),childList);
                backDataConstruct.setChildren(childList);
                list.add(backDataConstruct);
            }
        }
    }
}

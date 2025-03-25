package com.synway.datastandardmanager.controller;


import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.dao.master.ResourceManageAddDao;
import com.synway.datastandardmanager.pojo.TagData;
import com.synway.datastandardmanager.pojo.buildtable.TableImformation;
import com.synway.datastandardmanager.pojo.enums.DataAcquisitionMethodType;
import com.synway.datastandardmanager.pojo.enums.DataResourceLocation;
import com.synway.datastandardmanager.pojo.sourcedata.PublicDataInfo;
import com.synway.datastandardmanager.pojo.summaryobjectpage.LocalTableInfo;
import com.synway.datastandardmanager.pojo.warehouse.DetectedTable;
import com.synway.datastandardmanager.service.LabelsManageService;
import com.synway.datastandardmanager.service.ResourceManageAddService;
import com.synway.datastandardmanager.service.ResourceManageService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import com.synway.datastandardmanager.util.RestTemplateHandle;
import com.synway.common.bean.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.env.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class ResourceManageDataOtherController {
    private static Logger LOGGER = LoggerFactory.getLogger(ResourceManageDataOtherController.class);

    @Autowired
    ResourceManageAddDao resourceManageAddDao;

    @Autowired
    ResourceManageService resourceManageServiceImpl;

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired()private Environment env;

    @Autowired
    private LabelsManageService labelsManageServiceImpl;
    /**
     * 获取所有的标签值码表信息
     * @param
     * @return
     */
    @RequestMapping(value="/getTagAllDataMap")
    @ResponseBody
    public ServerResponse<TagData> resourceManageObjectDetail(@RequestParam("bodyTag")String bodyTag1){
        LOGGER.info("开始查询所有标签表码表,查询条件为："+ JSONObject.toJSONString(bodyTag1));
        ServerResponse<TagData> serverResponse = null;
        try{
            List bodyTag1List = new ArrayList();
            if(StringUtils.isNotEmpty(bodyTag1.trim())){
                bodyTag1List = Arrays.asList(bodyTag1.trim().split(","));
            }
            List<TagData>  tagDataList= resourceManageAddDao.getAllTagDataList();
            TagData newTageData = new TagData();
            List<TagData> onePageList = new ArrayList<>();
            List<TagData> twoPageList = new ArrayList<>();
            List<TagData> threePageList = new ArrayList<>();
            List<TagData> fourePageList = new ArrayList<>();
            List<TagData> fivePageList = new ArrayList<>();
            List<TagData> sixPageList = new ArrayList<>();
            for(TagData tagData:tagDataList){
                switch (tagData.getLabelLevel()){
                    case "1":
                        onePageList.add(tagData);
                        break;
                    case "2":
                        if(bodyTag1List.size() > 0){
                            if(bodyTag1List.contains(tagData.getParentId())){
                                twoPageList.add(tagData);
                            }
                        }else{
                            twoPageList.add(tagData);
                        }
                        break;
                    case "3":
                        if(bodyTag1List.size() > 0){
                            if(bodyTag1List.contains(tagData.getParentId())){
                                threePageList.add(tagData);
                            }
                        }else{
                            threePageList.add(tagData);
                        }
//                        threePageList.add(tagData);
                        break;
                    case "4":
                        if(bodyTag1List.size() > 0){
                            if(bodyTag1List.contains(tagData.getParentId())){
                                fourePageList.add(tagData);
                            }
                        }else{
                            fourePageList.add(tagData);
                        }
//                        fourePageList.add(tagData);
                        break;
                    case "5":
                        if(bodyTag1List.size() > 0){
                            if(bodyTag1List.contains(tagData.getParentId())){
                                fivePageList.add(tagData);
                            }
                        }else{
                            fivePageList.add(tagData);
                        }
//                        fivePageList.add(tagData);
                        break;
                    case "6":
                        if(bodyTag1List.size() > 0){
                            if(bodyTag1List.contains(tagData.getParentId())){
                                sixPageList.add(tagData);
                            }
                        }else{
                            sixPageList.add(tagData);
                        }
//                        sixPageList.add(tagData);
                        break;
                }
            }
            newTageData.setBodyTag1(onePageList);
            newTageData.setElementTag2(twoPageList);
            newTageData.setObjectDescTag3(threePageList);
            newTageData.setBehaviorTag4(fourePageList);
            newTageData.setRelationShipTag5(fivePageList);
            newTageData.setLocationTag6(sixPageList);
            serverResponse = ServerResponse.asSucessResponse(newTageData);
        }catch (Exception e){
            LOGGER.error(""+ ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    /**
     *  查看表是否已经存在
     *  1： 从 table_organization_assets统计表中查询该表是否已经存在（该表可能在数据库中不存在）
     *  2： 从 STANDARD_TABLE_CREATED中查询该表是否已经存在
     */
    @RequestMapping("/queryTableIsExist")
    @ResponseBody
    public ServerResponse<Boolean> queryTableIsExist(
            @RequestParam("tableName")String tableName){
        LOGGER.info("开始查询表"+tableName+"是否已经存在");
        ServerResponse<Boolean> serverResponse = null;
        Boolean exitsFlag = false;
        try{
            if(StringUtils.isNotEmpty(tableName)){
                LOGGER.info("开始在syndmg_table_all表中查询表是否存在");
                int assetsCount = 0;
                try{
//                    assetsCount = resourceManageAddDao.getTableCountByAssets(tableName);
                    // 20201130 使用大柱哥的接口来查询表是否存在
                    List<DetectedTable> list = restTemplateHandle.getTableImformationList();
                    if (list != null){
                        long countCreated = list.stream().filter(d -> { return StringUtils.isNotBlank(d.getTableNameEN())
                                && d.getTableNameEN().equalsIgnoreCase(tableName);}).count();
                        assetsCount = Long.bitCount(countCreated);
                    }
                }catch (Exception e){
                    LOGGER.error(ExceptionUtil.getExceptionTrace(e));
                }
                assetsCount += resourceManageAddDao.getTableCountByCreated(tableName);
                if(assetsCount > 0){
                    exitsFlag = true;
                }
            }
            serverResponse = ServerResponse.asSucessResponse(exitsFlag);
        }catch (Exception e){
            LOGGER.error("获取分级分类信息报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取分级分类信息报错"+e.getMessage());
        }
        return serverResponse;
    }


    /**
     * 获取跳转到 表登记 页面的url
     * @param tableName  表英文名 格式 tableBase|tableproject.tableName  odps|synods.nb_tab_wfwtest
     * @param tableId    表协议id
     * @param objectId
     * @return
     */
    @RequestMapping("/getTableRegisterUrl")
    @ResponseBody
    public ServerResponse<String> getTableRegisterUrl(@RequestParam("tableName")String tableName,
                                                      @RequestParam("tableId")String tableId,
                                                      @RequestParam("objectId")String objectId){
        LOGGER.info("开始查询跳转表登记页面的url，参数为tableName:"+tableName+" tableId:"+tableId+" objectId:"+objectId);
        ServerResponse<String> serverResponse = null;
        try{
            if(StringUtils.isEmpty(tableName)){
                LOGGER.error("在数据仓库的表名为空，不能获取表登记的跳转url");
                return ServerResponse.asErrorResponse("在数据仓库的表名为空，不能获取表登记的跳转url");
            }
            if(StringUtils.isEmpty(tableId)){
                LOGGER.error("在数据仓库的tableId为空，不能获取表登记的跳转url");
                return ServerResponse.asErrorResponse("在数据仓库的tableId为空，不能获取表登记的跳转url");
            }
            if(StringUtils.isEmpty(objectId)){
                LOGGER.error("在数据仓库的objectId为空，不能获取表登记的跳转url");
                return ServerResponse.asErrorResponse("在数据仓库的objectId为空，不能获取表登记的跳转url");
            }
            String url = resourceManageServiceImpl.getTableRegisterUrlService(tableName,tableId,objectId);
            serverResponse = ServerResponse.asSucessResponse(url,url);
        }catch (Exception e){
            LOGGER.error("获取表登记的url报错"+ExceptionUtil.getExceptionTrace(e));
            serverResponse = ServerResponse.asErrorResponse("获取表登记的url报错"+e.getMessage());
        }
        return serverResponse;
    }

    /**
     * 获取 数据来源信息 从表 PUBLIC_DATA_INFO 中查询
     * @param tableId  表协议ID
     * @return
     */
    @RequestMapping("/getSourceRelationData")
    @ResponseBody
    public ServerResponse<PublicDataInfo> getSourceRelationData(@RequestParam("tableId")String tableId){
        LOGGER.info("获取数据来源信息中查询参数tableId:"+tableId);
        ServerResponse<PublicDataInfo> serverResponse = null;
        try{
            List<PublicDataInfo> publicDataInfoList = resourceManageAddDao.getSourceRelationDataDao(tableId);
            if(publicDataInfoList.isEmpty()){
                serverResponse = ServerResponse.asErrorResponse("标准表PUBLIC_DATA_INFO中未查到协议["+tableId+"]相关信息");
            }else{
                PublicDataInfo publicDataInfo = publicDataInfoList.get(0);
                if(StringUtils.isNotEmpty(publicDataInfo.getSJZZYJFL().trim())){
                    //  数据组织一级分类中文名
                    String  yjflCn = resourceManageAddDao.getCodeNameCn(publicDataInfo.getSJZZYJFL().trim());
                    publicDataInfo.setSJZZYJFL_CN(StringUtils.isEmpty(yjflCn)?"":yjflCn);
                }
                if(StringUtils.isNotEmpty(publicDataInfo.getSJZZEJFL().trim())){
                    //  数据组织二级分类中文名
                    String  ejflCn = resourceManageAddDao.getCodeNameCn(publicDataInfo.getSJZZEJFL().trim());
                    publicDataInfo.setSJZZEJFL_CN(StringUtils.isEmpty(ejflCn)?"":ejflCn);
                }
                if(StringUtils.isNotEmpty(publicDataInfo.getSJZYLYLX())){
                    //  数据资源来源类型中文名
                    String  lyflCn = resourceManageAddDao.getCodeNameCn(publicDataInfo.getSJZYLYLX());
                    //  数据来源分为2种 一种是 互联网数据/即时通信   另一种是 电信数据  只有这一种分类
                    publicDataInfo.setSJZYLYLX_CN(StringUtils.isEmpty(lyflCn)?"":lyflCn);
                    List<String> codeTextList = resourceManageAddDao.getParCodeTextById(publicDataInfo.getSJZYLYLX());
                    if(codeTextList.size() == 1){
                        publicDataInfo.setSJZYLYYJLX_CN(codeTextList.get(0));
                    }else{
                        publicDataInfo.setSJZYLYYJLX_CN("");
                    }
                }
                //
                if("JZ_RESOURCE".equalsIgnoreCase(publicDataInfo.getSOURCEID())
                        || "JZ_SOURCE".equalsIgnoreCase(publicDataInfo.getSOURCEID())){
                    publicDataInfo.setSOURCEID("");
                }
//                废弃
//                publicDataInfo.setSJHQFS_CH(DataAcquisitionMethodType.getValueById(publicDataInfo.getSJHQFS()));
                publicDataInfo.setSJZYWZ_CH(DataResourceLocation.getValueById(publicDataInfo.getSJZYWZ()));

                // 把6个标签信息汇总
                labelsManageServiceImpl.setLabelToList(publicDataInfo);
                serverResponse = ServerResponse.asSucessResponse(publicDataInfo);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            serverResponse = ServerResponse.asErrorResponse(e.getMessage());
        }
        return serverResponse;
    }

    @RequestMapping("/getJumpOrthePage")
    @ResponseBody
    public ServerResponse getJumpOrthePage(){
        String flag =env.getProperty("dataProcessJump","true");
        if("true".equalsIgnoreCase(flag)){
            return ServerResponse.asSucessResponse(true);
        }else{
            return ServerResponse.asSucessResponse(false);
        }
    }

}

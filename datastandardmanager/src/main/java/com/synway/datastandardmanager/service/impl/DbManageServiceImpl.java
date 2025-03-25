package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.dao.master.ObjectStoreInfoDao;
import com.synway.datastandardmanager.dao.master.ResourceManageDao;
import com.synway.datastandardmanager.dao.standard.ResourceManageAddColumnDao;
import com.synway.datastandardmanager.dao.standard.TableOrganizationDao;
import com.synway.datastandardmanager.dao.standard.ObjectDao;
import com.synway.datastandardmanager.databaseparse.DataBaseType;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.entity.BuildTableInfoVo;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.BuildTableManage;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.ObjectFieldStandard;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.dataresource.CreateTableData;
import com.synway.datastandardmanager.service.DbManageService;
import com.synway.datastandardmanager.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 * @date 2019/10/25 10:02
 */
@Service
public class DbManageServiceImpl implements DbManageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbManageServiceImpl.class);

    @Autowired
    private RestTemplateHandle restTemplateHandle;

    @Autowired
    private ObjectDao objectDao;

    @Autowired
    private TableOrganizationDao tableOrganizationDao;

    @Autowired
    ResourceManageDao resourceManageDao;
    @Autowired
    ResourceManageAddColumnDao resourceManageAddColumnDao;
    @Autowired()private Environment env;

    @Autowired
    private ObjectStoreInfoDao objectStoreInfoDao;


    /**
     * 创建 odps /ads / datahub 表
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String buildAdsOrOdpsTable(BuildTableInfoVo buildTableInfoVo) throws Exception{
        String tableId = buildTableInfoVo.getTableId();
        List<ObjectField> objectFields = buildTableInfoVo.getColumnData();
        if(StringUtils.isBlank(tableId)){
            throw new NullPointerException("需要指定协议编号！！！");
        }
        if(null==objectFields||objectFields.size()==0){
            throw new NullPointerException("字段信息不能为空！！！");
        }
        String tableName = buildTableInfoVo.getTableName();
        if(StringUtils.isBlank(tableName)){
            throw new NullPointerException("需要指定表名信息！！！");
        }
        String tableNameCH = buildTableInfoVo.getTableNameCH();
        if(StringUtils.isBlank(tableNameCH)){
            throw new NullPointerException("需要指定表中文名！！！");
        }
        String targetDbType = buildTableInfoVo.getDsType();
        if(StringUtils.isBlank(targetDbType)){
            throw new NullPointerException("前端传输的目标库类型为空!!!");
        }
        int temp = objectDao.countObjectByTableId(tableId,tableName);
        if(temp==0) {
            throw new NullPointerException("标准中无表名或协议编号信息!!!");
        }
        int i = 0;
        if(StringUtils.isBlank(buildTableInfoVo.getProjectName())){
            buildTableInfoVo.setProjectName(buildTableInfoVo.getSchema());
            i = objectStoreInfoDao.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getSchema(), buildTableInfoVo.getTableName());
        }else{
            i = objectStoreInfoDao.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getTableName());
        }
        if(i == 1){
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR, "仓库已存在该表");
        }
        for (ObjectField objectField:objectFields) {
            if(StringUtils.isEmpty(objectField.getCreateColumnType())){
                throw new NullPointerException("建表字段类型没有匹配成功，请点击高级按钮手动获取建表类型");
            }
        }
        buildTableInfoVo.setColumnData(objectFields);

        // 生成sql的相关信息
        String handleClaStr = DataBaseType.getCla(buildTableInfoVo.getDsType()+"_addcolumn");
        TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
        String sql = tableColumnHandle.getCreateSql(buildTableInfoVo);
        // 发送建表信息
        CreateTableData createTableData = new CreateTableData();
        createTableData.setData(sql);
        createTableData.setResId(buildTableInfoVo.getDataId());
        createTableData.setTableId(buildTableInfoVo.getTableId());
        createTableData.setTableName(String.format("%s.%s", buildTableInfoVo.getSchema(),buildTableInfoVo.getTableName()));
        createTableData.setType(buildTableInfoVo.getDsType());
        LOGGER.info("建表发送的信息为："+JSONObject.toJSONString(createTableData));
        // 发送建表的相关sql
        String result = restTemplateHandle.sendCreateTableInfo(createTableData);
        insertCreateTableInfo(buildTableInfoVo);
        return result;
    }

    /**
     * 建表成功之后将相关信息插入到相关表中
     * @param buildTableInfoVo
     */
    private void insertCreateTableInfo(BuildTableInfoVo buildTableInfoVo){
        if(!"datahub".equalsIgnoreCase(buildTableInfoVo.getDsType())){
            // odps 建表都是分区表 所以当是odps时且生命周期为空时 都为 -1
            if("odps".equalsIgnoreCase(buildTableInfoVo.getDsType()) && buildTableInfoVo.getLifeCycle() == null){
                buildTableInfoVo.setLifeCycle(0);
            }else if("ads".equalsIgnoreCase(buildTableInfoVo.getDsType()) && StringUtils.isNotBlank(buildTableInfoVo.getPartitionSecondNum())){
                buildTableInfoVo.setLifeCycle(Integer.parseInt(buildTableInfoVo.getPartitionSecondNum()));
            }
            try{
                tableOrganizationDao.insertInfo(buildTableInfoVo);
                tableOrganizationDao.insertInfoTemp(buildTableInfoVo);
            }catch (Exception e){
                LOGGER.error(ExceptionUtil.getExceptionTrace(e));
            }
        }
    }


    /**
     *  presto 和 hive 的建表接口
     * @return
     * @throws Exception
     */
    @Override
    public String createHuaWeiTableService(BuildTableInfoVo buildTableInfoVo) throws Exception {
        int i = 0;
        if(StringUtils.isBlank(buildTableInfoVo.getProjectName())){
            buildTableInfoVo.setProjectName(buildTableInfoVo.getSchema());
            i = objectStoreInfoDao.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getSchema(), buildTableInfoVo.getTableName());
        }else{
            i = objectStoreInfoDao.searchDataResourceTable(buildTableInfoVo.getDataId(), buildTableInfoVo.getProjectName(), buildTableInfoVo.getTableName());
        }
        if(i == 1){
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR, "仓库已存在该表");
        }
        String dataBaseType = buildTableInfoVo.getDsType();
        String handleClaStr = DataBaseType.getCla(dataBaseType+"_addcolumn");
        if(StringUtils.isBlank(handleClaStr)){
            throw new NullPointerException("没有编写"+dataBaseType+"类型的建表处理类");
        }
        TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
        String message = tableColumnHandle.createTableByPage(buildTableInfoVo);
        return message;
    }

    /**
     * 获取需要插入的字段信息
     * @param allObjectList   页面中的字段信息
     * @return
     * @throws Exception
     */
    @Override
    public List<ObjectField> getCommonColumnService(List<ObjectField> allObjectList) throws Exception {
        //  先获取页面中字段最大字段序号
        int maxRecnoInt = -1;
        if(allObjectList != null && !allObjectList.isEmpty() ){
            Optional<Integer> maxRecno = allObjectList.stream().map(ObjectField::getRecno).distinct().collect(Collectors.toList()).stream().reduce(Integer::max);
            if(maxRecno.isPresent()){
                maxRecnoInt = maxRecno.get();
            }
        }
        // 获取字段的信息
        // 20200605 字段从数据库中获取
        List<String> fieldIdList = Arrays.asList(new String[]{"03J0002","09E0009","08A0007","03S0005","08A0034","09A0009"
                ,"08A0014","08A0001","08A0005","08A0006","18A0903","08A0060"});
        List<ObjectFieldStandard> allCommonList = new ArrayList<>();
        for(String fieldId:fieldIdList){
            try{
                ObjectFieldStandard objectFieldStandard = resourceManageAddColumnDao.getCommonColumnByListDao(fieldId);
                if(objectFieldStandard != null){
                    allCommonList.add(objectFieldStandard);
                }
            }catch (Exception e){
                LOGGER.error("添加公共字段报错："+e.getMessage());
            }
        }
//        List<ObjectFieldStandard> allCommonList = StandardCommonColumnUtil.getCommon_Column(objectId,maxRecnoInt);
        // 拼接一个查询信息
        List<String> queryList = allCommonList.stream().filter(Objects::nonNull).map(ObjectFieldStandard::getFieldId).distinct().collect(Collectors.toList());
        Map<String,List<ObjectFieldStandard>> allCoumnMap = allCommonList.stream().collect(Collectors.groupingBy(ObjectFieldStandard::getFieldId));
        //  判断指定objectId里面是否有公共字段
        if(allObjectList != null){
            allObjectList.forEach((element)->{
                if(StringUtils.isNotEmpty(element.getFieldId()) &&
                        queryList.contains(element.getFieldId().toUpperCase())){
                    allCoumnMap.remove(element.getFieldId());
                }
            });
        }
        // 存储需要添加的公共字段信息
        List<ObjectField> needColumnList = new ArrayList<>();
        for(String onekey:allCoumnMap.keySet()){
            ObjectFieldStandard oneObjectField = allCoumnMap.get(onekey).get(0);
            maxRecnoInt++;
            ObjectField objectField = JSONObject.parseObject(JSONObject.toJSONString(oneObjectField),ObjectField.class);
            objectField.setRecno(maxRecnoInt);
            objectField.setStandardRecno(maxRecnoInt);
            objectField.setUpdateStatus(Byte.valueOf("1"));
            objectField.setMd5IndexStatus(false);
            objectField.setPkRecnoStatus(false);
            objectField.setClustRecnoStatus(false);
            if(onekey.equalsIgnoreCase("09A0009")||onekey.equalsIgnoreCase("08A0014")
                    ||onekey.equalsIgnoreCase("08A0006")||onekey.equalsIgnoreCase("08A0005")
                    ||onekey.equalsIgnoreCase("08A0001")){
                objectField.setNeedValue(0);
                objectField.setNeedv("0");
            }
            needColumnList.add(objectField);
        }
        LOGGER.info("需要添加的公共字段信息为："+JSONObject.toJSONString(needColumnList));
        return needColumnList;
    }

    /**
     * 先判断传入的参数是否符合条件 如果符合要求，则向 审批流程中发送请求
     * @param buildTableInfoVo
     * @return
     */
    @Override
    public String buildTableToApprovalInfoService(BuildTableInfoVo buildTableInfoVo) throws Exception {
        String tableId = buildTableInfoVo.getTableId();
        List<ObjectField> objectFields = buildTableInfoVo.getColumnData();
        if(null==tableId){
            throw new Exception("需要指定协议编号！！！");
        }
        if(null==objectFields||objectFields.size()==0){
            throw new Exception("字段信息不能为空！！！");
        }
        String tableName = buildTableInfoVo.getTableName();
        if(null==tableName){
            throw new Exception("需要指定表名信息！！！");
        }
        String targetDbType = buildTableInfoVo.getDsType();
        if(null==targetDbType||targetDbType.trim().length()==0){
            throw new Exception("前端传输的目标库类型为空!!!");
        }
        int temp = objectDao.countObjectByTableId(tableId,tableName);
        if(temp==0) {
            throw new Exception("标准中无表名或协议编号信息!!!");
        }

        for (ObjectField objectField:objectFields) {
            if(StringUtils.isEmpty(objectField.getCreateColumnType())){
                throw new Exception("建表字段类型没有匹配成功，请点击高级按钮手动获取建表类型");
            }
            String fieldTypeStr = objectField.getCreateColumnType();
            if(StringUtils.isEmpty(fieldTypeStr)){
                throw new Exception(String.format("字段[%s]对应的类型[%s]为空，请配置对应的建表类型",objectField.getColumnName(),fieldTypeStr));
            }
        }
        BuildTableManage buildTableManage = new BuildTableManage();
        buildTableManage.setTableName(tableName);
        buildTableManage.setDbType(targetDbType);
        buildTableManage.setBuildTableInfoVo(buildTableInfoVo);
        buildTableManage.setApprovalId(buildTableInfoVo.getApprovalId());
        buildTableManage.setTableId(tableId);
        String url = pushApprovalInfoMessage(buildTableManage);
        return url;
    }

    /**
     *
     * @param buildTableInfoVo
     * @return
     * @throws Exception
     */
    @Override
    public String buildHuaWeiTableToApprovalInfoService(BuildTableInfoVo buildTableInfoVo) throws Exception {
        String dataBaseType = buildTableInfoVo.getDsType();
        String tableName = buildTableInfoVo.getTableName();
        String handleClaStr = DataBaseType.getCla(dataBaseType+"_addcolumn");
        if(StringUtils.isBlank(handleClaStr)){
            throw new NullPointerException("没有编写"+dataBaseType+"类型的建表处理类");
        }
        TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
        // 检查参数是否正确
        String tableId = tableColumnHandle.createTableBeforeCheck(buildTableInfoVo);
        if(StringUtils.isBlank(tableId)){
            throw new NullPointerException("从相关处理类中获取到的tableId为空，请检查日志信息");
        }
        BuildTableManage buildTableManage = new BuildTableManage();
        buildTableManage.setTableName(tableName);
        buildTableManage.setDbType(dataBaseType);
        buildTableManage.setBuildTableInfoVo(buildTableInfoVo);
        buildTableManage.setApprovalId(buildTableInfoVo.getApprovalId());
        buildTableManage.setTableId(tableId);
        String url = pushApprovalInfoMessage(buildTableManage);
        return url;
    }

    @Override
    public List<PageSelectOneValue> getPartitionType() {
        PageSelectOneValue partitionTypeOne = new PageSelectOneValue("1", "按天分区");
        PageSelectOneValue partitionTypeTwo = new PageSelectOneValue("2", "按周分区");
        PageSelectOneValue partitionTypeThree = new PageSelectOneValue("3", "按月分区");
        PageSelectOneValue partitionTypeFour = new PageSelectOneValue("0", "其它");
        List<PageSelectOneValue> result = new ArrayList<>();
        result.add(partitionTypeOne);
        result.add(partitionTypeTwo);
        result.add(partitionTypeThree);
        result.add(partitionTypeFour);
        return result;
    }

    @Override
    public Boolean searchDataResourceTable(String resId, String projectName, String tableName) {
        return null;
    }

    public String pushApprovalInfoMessage(BuildTableManage buildTableManage) throws Exception{
        LOGGER.info("传递的参数为："+JSONObject.toJSONString(buildTableManage));
        //  拼接传递给 审批流程 的接口
        String approvalId = buildTableManage.getApprovalId();
        ApprovalInfoParams approvalInfoParams = new ApprovalInfoParams();
        approvalInfoParams.setApprovalId(approvalId);
        if(StringUtils.isBlank(approvalId)){
            approvalInfoParams.setOperationName("建表");
            approvalInfoParams.setModuleName("标准管理");
        }
        approvalInfoParams.setModuleId(ApprovalInfoParams.CREATE_TABLE);
        if(buildTableManage.getDbType().equalsIgnoreCase(BuildTableManage.ADS)
                || buildTableManage.getDbType().equalsIgnoreCase(BuildTableManage.ODPS)
                    || BuildTableManage.DATAHUB.equalsIgnoreCase(buildTableManage.getDbType())){
            approvalInfoParams.setApplicationInfo("在"+buildTableManage.getDbType()+"数据库中创建表名为： "+buildTableManage.getTableName()+" 项目名为:"+buildTableManage.getBuildTableInfoVo().getSchema());
        }else{
            approvalInfoParams.setApplicationInfo("在"+buildTableManage.getDbType()+"数据库中创建表名为： "+buildTableManage.getTableName()+" 项目名为:"+buildTableManage.getBuildTableInfoVo().getSchema());
        }
        approvalInfoParams.setCallbackData(JSONObject.toJSONString(buildTableManage));
//        RestTemplate restTemplate = new RestTemplate();
        approvalInfoParams.setCallbackUrl(env.getProperty("nginxIp")+"/factorygateway/datastandardmanager/dataStandardManager/buildTableApprovalInfo");
//        JSONObject returnObject= restTemplate.postForObject("http://192.168.71.117:8181/datagovernance/process/saveOrUpdateApprovalInfo",
//                approvalInfoParams,JSONObject.class);
        LOGGER.info("向审批流程发送数据为："+JSONObject.toJSONString(approvalInfoParams));
        JSONObject returnObject=restTemplateHandle.saveOrUpdateApprovalInfo(approvalInfoParams);
        LOGGER.info("审批流程返回的数据为："+JSONObject.toJSONString(JSONObject.toJSONString(returnObject)));
        if(returnObject.getInteger("status") == 1){
            // 表示调用审批流程成功
            approvalId = returnObject.getString("result");
        }else{
            throw new Exception(returnObject.getString("message"));
        }
        String approvalInfoHtmlUrl = env.getProperty("nginxIp")+"/factorygateway/datagovernance/datagovernance/process/approval?approvalId="+approvalId;
        return approvalInfoHtmlUrl;
    }
}







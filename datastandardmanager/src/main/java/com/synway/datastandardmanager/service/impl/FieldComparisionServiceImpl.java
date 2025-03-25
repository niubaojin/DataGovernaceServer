package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.dao.master.FieldComparisionDao;
import com.synway.datastandardmanager.databaseparse.DataBaseType;
import com.synway.datastandardmanager.databaseparse.TableColumnHandle;
import com.synway.datastandardmanager.message.RecordService;
import com.synway.datastandardmanager.pojo.*;
import com.synway.datastandardmanager.pojo.DataProcess.DataProcess;
import com.synway.datastandardmanager.pojo.approvalInfo.ApprovalInfoParams;
import com.synway.datastandardmanager.pojo.enums.SynlteFieldType;
import com.synway.datastandardmanager.pojo.warehouse.FieldInfo;
import com.synway.datastandardmanager.service.FieldComparisionService;
import com.synway.datastandardmanager.service.ObjectStoreInfoService;
import com.synway.datastandardmanager.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName FieldComparisionServiceImpl
 * @description TODO
 * @date 2020/9/24 14:33
 */
@Service
public class FieldComparisionServiceImpl implements FieldComparisionService {
    private static final Logger logger = LoggerFactory.getLogger(FieldComparisionServiceImpl.class);

    @Autowired
    private RestTemplateHandle restTemplateHandle;
    @Autowired()private Environment env;
    @Autowired()@Qualifier("MsgHandlerChain")
    private RecordService recordServiceChain;
    @Autowired
    private FieldComparisionDao fieldComparisionDao;
    @Autowired
    private ObjectStoreInfoService objectStoreInfoServiceimpl;
    private static ThreadPoolExecutor asyncExecutor = RetryUtil.createThreadPoolExecutor();

    /**
     *  根据查询参数 获取字段对比的相关页面返回信息
     * @param query
     * @return
     */
    @Override
    public ColumnComparisionPage getColumnComparisionPage(ColumnComparisionSearch query) throws Exception {
        // 如果 dataId 为空，则查询本地数据仓库的 否则则按照dataId的数据查询相关信息

        String dataId = query.getCreatedTableMessage().getDataId();
        // 这里加个到时间后终止的程序
        List<FieldInfo> list = RetryUtil.asyncExecuteWithRetry(new Callable<List<FieldInfo>>() {
            @Override
            public List<FieldInfo> call() throws Exception {
                if(StringUtils.isBlank(dataId)){
                    logger.info("该数据的dataId为空，开始查询本地数据仓库中指定表"+query.getCreatedTableMessage().getTableNameEn()+"的字段信息");
                    List<FieldInfo> fieldInfoList = restTemplateHandle.requestGetTableStructure(query.getCreatedTableMessage().getDataId(),
                            query.getCreatedTableMessage().getTableProject(),
                            query.getCreatedTableMessage().getTableName());
                    List<FieldInfo> list1 = fieldInfoList;
                    return list1;
                }else{
                    logger.info("该数据的dataId为"+dataId+"，开始查询本地数据仓库中指定表"+query.getCreatedTableMessage().getTableNameEn()+"的字段信息");
                    List<FieldInfo> fieldInfos = restTemplateHandle.requestGetTableStructure(dataId,
                            query.getCreatedTableMessage().getTableProject(),
                            query.getCreatedTableMessage().getTableName());
                    List<FieldInfo> list1 = fieldInfos;
                    return list1;
                }
            }
        },2,1,false,30000,asyncExecutor);
        if(list.isEmpty()){
            throw new NullPointerException("从数据仓库中获取表名"+query.getCreatedTableMessage().getTableNameEn()+"的字段信息为空");
        }
        ColumnComparisionPage columnComparisionPage = new ColumnComparisionPage();
        //获取建表字段中所有的字段列表，然后循环遍历标准表中字段，判断哪些是标准表中存在但是建表字段中不存在的
        List<String> createColumnList = list.stream().filter(d -> StringUtils.isNotBlank(d.getFieldName()))
                .map(d -> d.getFieldName().toLowerCase()).collect(Collectors.toList());
        List<TableColumnPage> leftList = new ArrayList<>();
        // 左侧的数据
        Map<String,String>  resultColumnMap = FieldTypeUtil.getFieldTypeMap("standardize",
                query.getCreatedTableMessage().getTableBase());
        AtomicInteger num = new AtomicInteger(0);
        query.getLeftStandardColumn().forEach(d ->{
            String columnName = d.getColumnName().toLowerCase();
            TableColumnPage tableColumnPage = new TableColumnPage();
            tableColumnPage.setColumnEngname(columnName);
            tableColumnPage.setRowNum(String.valueOf(num.getAndIncrement()));
            tableColumnPage.setColumnChinese(d.getFieldChineseName());
            // 根据标准建表字段类型来自动获取相关的对应数据库类型 这个是数字 需要先将数字转换
            tableColumnPage.setColumnType(resultColumnMap.get(
                    SynlteFieldType.getSynlteFieldType(Integer.parseInt(d.getFieldType()))));

            tableColumnPage.setType("left");
            if("1".equalsIgnoreCase(d.getOdpsPattition())){
                tableColumnPage.setPartition(true);
            }else{
                tableColumnPage.setPartition(false);
            }
            if(!createColumnList.contains(columnName)){
                tableColumnPage.setNeedAdd(true);
            }
            leftList.add(tableColumnPage);
        });
        // 右侧的数据
        List<TableColumnPage> rightList = new ArrayList<>();
        boolean tablePartition = false;
        for(int i = 0; i< list.size(); i++){
            TableColumnPage tableColumnPage = new TableColumnPage();
            tableColumnPage.setColumnEngname(list.get(i).getFieldName());
            tableColumnPage.setRowNum(String.valueOf(i));
            tableColumnPage.setColumnChinese(list.get(i).getComments());
            // 字段类型
            tableColumnPage.setColumnType(list.get(i).getType());
            tableColumnPage.setType("right");
//            if(StringUtils.isNotBlank(list.get(i).getIsPartition()) && "是".equals(list.get(i).getIsPartition())){
//                tablePartition = true;
//            }
            rightList.add(tableColumnPage);
        }
        columnComparisionPage.setLeftTableColumn(leftList);
        columnComparisionPage.setRightTableColumn(rightList);
        columnComparisionPage.setTablePartition(tablePartition);
        logger.info("查询字段对比的相关信息结束");
        return columnComparisionPage;
    }

    /**
     *  如果需要审批，则先在审批流程中保存数据
     * @param data
     * @return
     */
    @Override
    public String saveOrUpdateApprovalInfoService(SaveColumnComparision data) throws Exception{
        logger.info("开始向审批流程中保存数据");
        // approvalId必须 为空  目前只能 新增
        String approvalId = "";
        ApprovalInfoParams approvalInfoParams = new ApprovalInfoParams();
        approvalInfoParams.setOperationName("新增字段");
        approvalInfoParams.setModuleName("标准管理");
        approvalInfoParams.setModuleId(ApprovalInfoParams.ADD_TABLE_COLUMN);
        List<String> columnList = data.getColumnList().stream().map(TableColumnPage::getColumnEngname).collect(Collectors.toList());
        // 生成sql的相关操作
        String handleClaStr = DataBaseType.getCla(data.getCreatedTableData().getTableBase()+"_addcolumn");
        TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
        String sql = tableColumnHandle.getAddColumnSql(data);
        String message = String.format("在[%s]数据库[%s]表中新增字段[%s],具体的sql为：\n %s", data.getCreatedTableData().getTableBase(),
                data.getCreatedTableData().getTableNameEn(),StringUtils.join(columnList,","), sql);
        approvalInfoParams.setApplicationInfo(message);
        approvalInfoParams.setCallbackData(JSONObject.toJSONString(data));
        approvalInfoParams.setCallbackUrl(env.getProperty("nginxIp")+"/factorygateway/datastandardmanager/dataStandardManager/saveFieldComparisonStr");

        logger.info("向审批流程发送数据为："+JSONObject.toJSONString(approvalInfoParams));
        JSONObject returnObject=restTemplateHandle.saveOrUpdateApprovalInfo(approvalInfoParams);
        logger.info("审批流程返回的数据为："+JSONObject.toJSONString(JSONObject.toJSONString(returnObject)));
        if(returnObject.getInteger("status") == 1){
            // 表示调用审批流程成功
            approvalId = returnObject.getString("result");
        }else{
            throw new NullPointerException(returnObject.getString("message"));
        }
        return env.getProperty("nginxIp")+"/factorygateway/datagovernance/datagovernance/process/approval?approvalId="+approvalId;
    }


    /**
     *  根据相关信息生成对应的建表sql
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public String saveFieldComparison(SaveColumnComparision data) throws Exception {
        String dataBaseType = data.getCreatedTableData().getTableBase();
        if(StringUtils.isBlank(dataBaseType)){
            throw new NullPointerException("数据库类型为空,不能增加字段信息");
        }
        // 此时根据dataId和sql去新增字段 有2种情况一种是 dataId为空时，先查询本地仓指定表的字段信息，然后再获取到具体的dataId信息，
        //之后再根据 相关信息来建表
        String dataId = data.getCreatedTableData().getDataId();
        if(StringUtils.isBlank(dataId)){
            List<FieldInfo> fieldInfoList = restTemplateHandle.requestGetTableStructure(dataId, data.getCreatedTableData().getTableProject(),
                    data.getCreatedTableData().getTableNameEn());
            List<FieldInfo> tableFields = fieldInfoList;
            if(tableFields.isEmpty()){
                throw new NullPointerException(String.format("表%s从本地仓库中获取到的dataId信息为空，" +
                        "说明该本地%s数据库中没有该表，新增字段失败",data.getCreatedTableData().getTableNameEn(),dataBaseType));
            }else{
                // 需要去除掉 里面的项目名.表名
//                dataId = tableFields.get(0).getTableId().toLowerCase()
//                        .replaceAll(data.getCreatedTableData().getTableNameEn().toLowerCase(),"");
            }
        }
        if(StringUtils.isBlank(dataId)){
            throw new NullPointerException(String.format("表%s获取到的dataId信息为空，" +
                    "说明该本地%s数据库中没有该表，新增字段失败",data.getCreatedTableData().getTableNameEn(),dataBaseType));
        }
        StandardTableCreated createdTableData = data.getCreatedTableData();
        createdTableData.setDataId(dataId);
        data.setCreatedTableData(createdTableData);
        //根据数据库类型获取具体的处理类
        String handleClaStr = DataBaseType.getCla(dataBaseType+"_addcolumn");
        if(StringUtils.isBlank(handleClaStr)){
            return "["+dataBaseType+"]类型的数据没有编写新增字段的相关处理方法，请联系开发人员";
        }
        TableColumnHandle tableColumnHandle = (TableColumnHandle)Class.forName(handleClaStr).newInstance();
        String message = tableColumnHandle.addColumnByData(data);
        String sql = tableColumnHandle.getAddColumnSql(data);
        //当修改成功之后将数据发送到数据历程的页面
        DataProcess dataProcess = new DataProcess();
        dataProcess.setDataBaseType(dataBaseType);
        dataProcess.setTableNameEn(data.getCreatedTableData().getTableNameEn().toUpperCase());
        dataProcess.setTableId(data.getTableId());
        dataProcess.setModuleId("BZGL");
        dataProcess.setIp(data.getLocalIp());
        dataProcess.setOperateTime(DateUtil.formatDate(new Date(),DateUtil.DEFAULT_PATTERN_DATETIME));
        dataProcess.setLogType("BZGL002");
        dataProcess.setDigest("开始在"+dataBaseType+"中给表"+data.getCreatedTableData().getTableNameEn()+"新增字段，相关的sql语句如下："+
                "  " +sql);
        recordServiceChain.sendMessage(dataProcess,data.getUserId());
        try{
            objectStoreInfoServiceimpl.updateColumnToInfo(data,1);
        }catch (Exception e){
            logger.error("保存修改时间报错"+ExceptionUtil.getExceptionTrace(e));
        }
        return message;
    }

    /**
     * 检查这张表是否正在使用 主要是到 数据加工血缘中检查表是否存在 以及存储使用热度表的情况
     * 存在则表示 这张表正在使用，返回的字符创中展示具体的使用情况
     * eg：1：表[syndw_dev.ysk_0104_cssj091711_1111]正在工作流节点[syndm.零星贩毒]中使用
     *     2：表[synods.nb_tab_mee]正在工作流节点[syndm.零星贩毒]中使用
     *
     * @param tableNameEn  表英文名 项目名.表名
     * @return
     */
    @Override
    public String checkTableUsage(String tableNameEn) {
        logger.info("开始在数据加工血缘和使用热度中查询表["+tableNameEn+"]是否正在使用");
        if(StringUtils.isBlank(tableNameEn)){
            throw new NullPointerException("查询的参数表名为空");
        }
        StringBuilder resultStr = new StringBuilder();
        int type = 0;
        // 反之 则查询 表 m_node_in_out_table
        List<String> nodeNameList = fieldComparisionDao.getNodeNameByTableName(tableNameEn,type);
        if(!nodeNameList.isEmpty()){
            for(int i = 1; i <= nodeNameList.size(); i++){
                resultStr.append(i+".表["+tableNameEn+"]正在工作流节点["+nodeNameList.get(i-1)+"]中使用<br/> ");
            }
        }
        // 查询 storecycleanduseheat表中该表的使用次数
        try{
            int useCount = fieldComparisionDao.getTableUsedCount(tableNameEn);
            if( useCount > 0){
                resultStr.append("表["+tableNameEn+"]一个月内的使用次数为"+useCount);
            }
        }catch (Exception e){
            logger.error("查询表storecycleanduseheat中表"+tableNameEn+"使用次数报错"+ ExceptionUtil.getExceptionTrace(e));
        }
        logger.info("检查表的使用情况返回信息为："+resultStr.toString());
        return resultStr.toString();
    }

}

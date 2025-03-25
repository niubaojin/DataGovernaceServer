package com.synway.datarelation.service.datablood.impl;

import com.aliyun.odps.Column;
import com.synway.datarelation.pojo.databloodline.DataRelationVo;
import com.synway.datarelation.pojo.databloodline.OdpsFiled;
import com.synway.datarelation.service.datablood.DataRelationService;
import com.synway.common.exception.ExceptionUtil;
import com.synway.datarelation.util.OdpsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataRelationServiceImpl implements DataRelationService {
    private Logger logger = LoggerFactory.getLogger(DataRelationServiceImpl.class);
    @Autowired()private OdpsClient odpsClient;

    @Override
    public List<OdpsFiled> getOdpsFiledMsg(String projectName, String tablename) {
        List<OdpsFiled> odpsFiledList=new ArrayList<>();
        try{
            List<Column> columnList=odpsClient.getColumns(projectName,tablename);
            if(columnList==null){
                logger.error("未获取Odps中["+projectName+"]项目中的["+tablename+"]表结构");
                return null;
            }
            for(Column column:columnList){
                String type=column.getTypeInfo().getTypeName().toUpperCase();
                if((!"DATETIME".equals(type))&&(!"BOOLEAN".equals(type))){
                    OdpsFiled odpsFiled=new OdpsFiled();
                    String columnName=column.getName();
                    odpsFiled.setFieldName(columnName);
                    odpsFiled.setFieldType(type);
                    odpsFiledList.add(odpsFiled);
                }
            }
            logger.info("已获取Odps中["+projectName+"]项目中的["+tablename+"]表结构,字段个数为["+odpsFiledList.size()+"]个");
        }catch (Exception e){
            logger.error("查询Odps中["+projectName+"]项目中的["+tablename+"]表结构报错"+ ExceptionUtil.getExceptionTrace(e));
            for(int i=0;i<16;i++){
                OdpsFiled odpsFiled=new OdpsFiled();
                odpsFiled.setFieldName("bbbb=="+i);
                odpsFiled.setFieldType("String");
                odpsFiledList.add(odpsFiled);
            }
        }
        return odpsFiledList;
    }

    @Override
    public DataRelationVo getDataExist(DataRelationVo dataRelationVo) {
        String tableNameS=dataRelationVo.getTableName();
        try{
            String[] tableNameSplit=tableNameS.split("\\.");
            String sql=parseSql(tableNameSplit[1],dataRelationVo.getSelectedList(),dataRelationVo.getSelectList(),0);
            List<Object[]> objList= odpsClient.execQueryBySql(tableNameSplit[0],sql);
            int index=Integer.parseInt(objList.get(0)[0].toString());
            if(index==0){
                dataRelationVo.setExistFlag(false);
            }else{
                dataRelationVo.setExistFlag(true);
            }
        }catch (Exception e){
            logger.error("查询Odps中["+tableNameS+"]表数据是否存在报错"+ ExceptionUtil.getExceptionTrace(e));
            dataRelationVo.setExistFlag(false);
        }
        return dataRelationVo;
    }

    @Override
    public DataRelationVo getDataDetail(DataRelationVo dataRelationVo) {
        String tableNameS=dataRelationVo.getTableName();
        List<OdpsFiled> odpsFiledList=dataRelationVo.getOdpsFiledList();
        List<Map<String,String>> mapList=new ArrayList<>();
        List<String> fieldNames=new ArrayList<>();
        try{
            String[] tableNameSplit=tableNameS.split("\\.");
            String sql=parseSql(tableNameSplit[1],dataRelationVo.getSelectedList(),dataRelationVo.getSelectList(),1);
            logger.info("查询Odps中SQL语句为["+sql+"]");
            List<Object[]> objList= odpsClient.execQueryBySql(tableNameSplit[0],sql);
            for(int j=0;j<objList.size();j++){
                Map<String,String> map=new HashMap<>();
                for(int i=0;i<objList.get(j).length;i++){
                    map.put(odpsFiledList.get(i).getFieldName(),objList.get(j)[i].toString());
                }
                mapList.add(map);
                if(j==200){
                    break;
                }
            }
            for(OdpsFiled odpsFiled:odpsFiledList){
                fieldNames.add(odpsFiled.getFieldName());
            }
            dataRelationVo.setData(mapList);
            dataRelationVo.setFieldNames(fieldNames);
            logger.info("查询Odps中数据溯源方法，查询数据成功，已查询["+mapList.size()+"]条数据");
        }catch (Exception e){
            logger.error("查询Odps中["+tableNameS+"]表数据是否存在报错"+ ExceptionUtil.getExceptionTrace(e));
            Map<String,String> map=new HashMap<>();
            for(int i=0;i<odpsFiledList.size();i++){
                map.put(odpsFiledList.get(i).getFieldName(),i+"");
                if(i==200){
                    break;
                }
            }
            mapList.add(map);
            dataRelationVo.setData(mapList);
            for(OdpsFiled odpsFiled:odpsFiledList){
                fieldNames.add(odpsFiled.getFieldName());
            };
            dataRelationVo.setFieldNames(fieldNames);
        }
        return dataRelationVo;
    }

    /**
     * 组装sql语句
     * @param tablename
     * @param selectedList
     * @param selectList
     * @param indexFlag
     * @return
     */
    private String parseSql(String tablename,List<OdpsFiled> selectedList,List<OdpsFiled> selectList,int indexFlag){
        String sql="";
        StringBuffer sb=new StringBuffer();
        StringBuffer sbFiled=new StringBuffer();
        for(OdpsFiled odpsFiled:selectedList){
            String fieldName=odpsFiled.getFieldName();
            String filedValue=odpsFiled.getFieldValue().trim();
            if(filedValue==null||filedValue==""){
                continue;
            }
            String fieldType=odpsFiled.getFieldType().trim().toUpperCase();
            if(filedValue!=null&&!("".equals(filedValue))&&filedValue!=""){
                switch (fieldType){
                    case "BIGINT":case "DOUBLE":
                        sb.append(" and "+fieldName+"="+filedValue);
                        break;
                    case "BOOLEAN":
                        // sb.append(" and"+fieldName+"="+filedValue);
                        break;
                    case "DATETIME":
                        String filedFormat=odpsFiled.getFieldFormat();
                        sb.append(" and "+fieldName+"=to_date('"+filedValue+"','"+filedFormat+"')");
                        break;
                    case "STRING":
                        sb.append(" and "+fieldName+"='"+filedValue+"'");
                        break;
                    default:
                        break;
                }
            }
        }
        String whereSql=sb.toString();
        if(indexFlag==0){
            sql="select count(1) from "+tablename;
        }else if(indexFlag==1){
            for(OdpsFiled odpsFiled:selectList){
                sbFiled.append(odpsFiled.getFieldName()+",");
            }
            String fieldStr=sbFiled.toString();
            if(fieldStr.length()>1){
                fieldStr=fieldStr.substring(0,fieldStr.length()-1);
            }
            sql="select "+fieldStr+" from "+tablename;
        }
        if(whereSql.length()>4){
            whereSql= whereSql.substring(4,whereSql.length());
            sql=sql+" where "+whereSql;
        }
        return sql;
    }
}

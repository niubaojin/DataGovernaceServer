package com.synway.property.service.impl;

import com.synway.property.dao.TableAdvancedTermDao;
import com.synway.property.pojo.PageSelectOneValue;
import com.synway.property.pojo.tableAdvancedTerm.AdvancedTable;
import com.synway.property.pojo.tableAdvancedTerm.SynlteField;
import com.synway.property.service.TableAdvancedTermService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author majia
 * @version 1.0
 * @date 2020/11/6 14:27
 */
@Service
public class TableAdvancedTermServiceImpl implements TableAdvancedTermService {
    private static Logger logger = LoggerFactory.getLogger(TableAdvancedTermServiceImpl.class);

    @Autowired
    private TableAdvancedTermDao termDao;

    @Override
    public List<PageSelectOneValue> loadFieldTerms(String fieldTermType, String query) {
        List<PageSelectOneValue> list = null;
        if ("semantic".equals(fieldTermType)) {
            /*获取语义类型集合*/
            list = termDao.getSemanticType(query);
        } else if ("elementSet".equals(fieldTermType)) {
            /*获取元素集中文名集合*/
            list = termDao.getElementSetType(query);
        }
        return list;
    }

    @Override
    public List<AdvancedTable> loadFilteredData(String fieldTermType, List<String> fieldTermConfirmed, String composeTerm) {
        List<SynlteField> tempList;
        Map<String, List<SynlteField>> tempMap = new HashMap<>();
        List<AdvancedTable> result = new ArrayList<>();
        /*按语义类型进行合并操作*/
        if ("semantic".equals(fieldTermType)) {
            /*语义类型进行并和或操作*/
            tempList = termDao.getSynlteFieldBySemantic(fieldTermConfirmed);
            tempMap = tempList.stream().collect(Collectors.groupingBy(SynlteField::getSameName));
        }
        /*元素集中文名进行并和或操作*/
        else if ("elementSet".equals(fieldTermType)) {
            for (String item : fieldTermConfirmed) {
                List<SynlteField> list = termDao.getSynlteFieldByElementChSet(item);
                if(list.size()>0){
                    tempMap.put(item, list);
                }
            }
        }
        result = handleField(fieldTermType, tempMap, fieldTermConfirmed.size(), composeTerm);
        return result;
    }

    private List<AdvancedTable> handleField(String fieldTermType,
                                            Map<String, List<SynlteField>> tempMap,
                                            int size,
                                            String composeTerm) {
        Map<String, List<AdvancedTable>> resultMap = new ConcurrentHashMap<>();
        List<AdvancedTable> result = new ArrayList<>();
        /*分组后，判断是否每个分组都有fieldId，若是没有，取交集的话就可以直接返回，因为交集必定为空*/
        if (tempMap.size() != size && "and".equals(composeTerm)) {
            return result;
        }
        /*取出每个分组里所代表的fieldId所代表的objectId，两个是多对多关系，
        根据objectId分组，把fieldChineseName合并，获取每个分组映射对应object的map*/
        tempMap.forEach((key, value) -> {
            /*做field和fieldChineseName的映射*/
            Map map = value.stream().collect(Collectors.toMap(SynlteField::getFieldId, SynlteField::getFieldChineseName));
            /*获取objectId，以及表名*/
            List<AdvancedTable> list = termDao.getAdvancedTable(value);
            /*根据objectId分组*/
            Map<String, List<AdvancedTable>> objectMap = list.stream().collect(Collectors.groupingBy(AdvancedTable::getObjectId));
            List<AdvancedTable> operationList = new ArrayList<>();
            /*把分组后的对象进行赋值*/
            objectMap.forEach((k, v) -> {
                AdvancedTable table = new AdvancedTable();
                table.setObjectId(k);
                if ("semantic".equals(fieldTermType)) {
                    table.setSemanticType(key);
                } else if("elementSet".equals(fieldTermType)){
                    table.setElementSetCh(key);
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < v.size(); i++) {
                    AdvancedTable temp = v.get(i);
                    if (i == 0) {
                        table.setTableNameEn(temp.getTableNameEn());
                        table.setTableNameCh(temp.getTableNameCh());
                        stringBuilder.append(map.get(temp.getFieldId()));
                    } else {
                        stringBuilder.append(",").append(map.get(temp.getFieldId()));
                    }
                }
                table.setElementChSets(stringBuilder.toString());
                operationList.add(table);
            });
            /*把每个语义代表的对象都放入map中*/
            resultMap.put(key, operationList);
        });
        /*并集直接插入即可*/
        if ("or".equals(composeTerm)) {
            resultMap.forEach((k, v) -> {
                result.addAll(v);
            });
        } else if ("and".equals(composeTerm)) {
            /*交集*/
            List<AdvancedTable> andList = new ArrayList<>();
            int i = 0;
            /*根据objectId做交集*/
            for (Map.Entry<String, List<AdvancedTable>> entry : resultMap.entrySet()) {
                List<AdvancedTable> v = entry.getValue();
                if (i == 0) {
                    andList = v;
                } else {
                    andList.retainAll(v);
                }
                i++;
            }
            /*根据交集寻找到对应类型的多张表，添加至结果集中*/
            for (Map.Entry<String, List<AdvancedTable>> entry : resultMap.entrySet()) {
                List<AdvancedTable> v = entry.getValue();
                for (AdvancedTable item : v) {
                    if (andList.contains(item)) {
                        result.add(item);
                    }
                }
            }
        }
        /*根据英文名排序*/
        result.sort(Comparator.comparing(AdvancedTable::getTableNameEn));
        return result;
    }
}

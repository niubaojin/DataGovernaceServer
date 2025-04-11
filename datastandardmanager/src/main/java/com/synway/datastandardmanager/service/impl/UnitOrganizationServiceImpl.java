package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.config.HashLock;
import com.synway.datastandardmanager.dao.master.UnitOrganizationDao;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.exceptionhandler.ErrorCode;
import com.synway.datastandardmanager.exceptionhandler.SystemException;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationParameter;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationPojo;
import com.synway.datastandardmanager.pojo.unitManagement.UnitOrganizationTree;
import com.synway.datastandardmanager.service.UnitOrganizationService;
import com.synway.datastandardmanager.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author obito
 * @version 1.0
 */
@Service
@Slf4j
public class UnitOrganizationServiceImpl implements UnitOrganizationService {

    @Autowired
    private UnitOrganizationDao unitOrganizationDao;

    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;

    private static HashLock<String> HASH_LOCK = new HashLock<>();

    @Override
    public List<UnitOrganizationTree> getLeftTree() {
        log.info("开始获取单位机构管理左侧树信息");
        List<UnitOrganizationTree> resultUnitLeftTreeInfo = new ArrayList<>();
        //获取政府机构的列表
        List<UnitOrganizationTree> governmentLeftTreeInfoList = unitOrganizationDao.getLeftTreeInfo(1);
        //获取非政府机构的列表
        List<UnitOrganizationTree> notGovernmentLeftTreeInfoList = unitOrganizationDao.getLeftTreeInfo(2);

        //政府机构root信息
        UnitOrganizationTree governmentRootNode = new UnitOrganizationTree();
        governmentRootNode.setId("1");
        governmentRootNode.setUnitType(1);
        governmentRootNode.setUnitLevel(-1);
        //非政府机构root信息
        UnitOrganizationTree notGovernmentRootNode = new UnitOrganizationTree();
        notGovernmentRootNode.setId("2");
        notGovernmentRootNode.setUnitType(2);
        notGovernmentRootNode.setUnitLevel(-1);

        // 政府机构的左侧树
        List<UnitOrganizationTree> arrayRoutine1 = new ArrayList<>();
        if (governmentLeftTreeInfoList == null || governmentLeftTreeInfoList.isEmpty()){
            governmentRootNode.setUnitName("政府机构(0)");
        }else {
            if (governmentLeftTreeInfoList.size()==1){
                arrayRoutine1 = routineUnitConvert(governmentLeftTreeInfoList, governmentLeftTreeInfoList.get(0).getId(), new ArrayList<>());
            }else {
                arrayRoutine1 = routineUnitConvert(governmentLeftTreeInfoList, "1", new ArrayList<>());
            }
            governmentRootNode.setUnitName("政府机构(" + governmentLeftTreeInfoList.size() + ")");
            governmentRootNode.setChildrenUnit(arrayRoutine1);
        }
        //非政府机构的左侧树
        List<UnitOrganizationTree> arrayRoutine2 = new ArrayList<>();
        if(notGovernmentLeftTreeInfoList == null || notGovernmentLeftTreeInfoList.isEmpty()){
            notGovernmentRootNode.setUnitName("非政府机构(0)");
        }else{
            if(notGovernmentLeftTreeInfoList.size()==1){
                arrayRoutine2 = routineUnitConvert(notGovernmentLeftTreeInfoList, notGovernmentLeftTreeInfoList.get(0).getId(), new ArrayList<>());
            }else {
                arrayRoutine2 = routineUnitConvert(notGovernmentLeftTreeInfoList, "1", new ArrayList<>());
            }
            notGovernmentRootNode.setUnitName("非政府机构(" + notGovernmentLeftTreeInfoList.size() + ")");
            notGovernmentRootNode.setChildrenUnit(arrayRoutine2);
        }

        resultUnitLeftTreeInfo.add(governmentRootNode);
        resultUnitLeftTreeInfo.add(notGovernmentRootNode);
        return resultUnitLeftTreeInfo;
    }

    @Override
    public Map<String,Object> searchUnitOrganizationTable(UnitOrganizationParameter parameter) {
        log.info("开始获取单位机构的表格信息");
        // 搜索内容如果存在空值，将其变为 null值
        if(StringUtils.isBlank(parameter.getSearchText())){
            parameter.setSearchText(null);
        }

        PageHelper.startPage(parameter.getPageIndex(), parameter.getPageSize());
        // 单位机构id不为空，则是点击了左侧树，获取信息
        if(StringUtils.isNotBlank(parameter.getUnitCode()) && StringUtils.isBlank(parameter.getSearchText())){
            List<UnitOrganizationPojo> unitOrganizationPojoList = getUnitList(parameter.getUnitCode(), parameter.getSearchText());
            log.info("单位机构表格信息获取结束{}",unitOrganizationPojoList);
            PageInfo<UnitOrganizationPojo> unitOrganizationPojoPageInfo = new PageInfo<>(unitOrganizationPojoList);
            Map<String,Object> map = new HashMap<>();
            map.put("total",unitOrganizationPojoPageInfo.getTotal());
            map.put("rows",unitOrganizationPojoPageInfo.getList());
            return map;
        }
        //获取表格信息
        List<UnitOrganizationPojo> unitOrganizationPojoList = unitOrganizationDao.searchUnitOrganizationTable(parameter);
        //类型翻译
        unitOrganizationPojoList.stream().forEach(d ->{
            if(d.getUnitType() == 1){
                d.setUnitTypeCh("政府单位机构");
            }else{
                d.setUnitTypeCh("非政府单位机构");
            }
        });
        PageInfo<UnitOrganizationPojo> unitOrganizationPojoPageInfo = new PageInfo<>(unitOrganizationPojoList);
        Map<String,Object> map = new HashMap<>();
        map.put("total",unitOrganizationPojoPageInfo.getTotal());
        map.put("rows",unitOrganizationPojoPageInfo.getList());
        log.info("单位机构表格信息获取结束{}",unitOrganizationPojoList);
        return map;
    }

    @Override
    public String addOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo) {
        log.info("要插入的单位机构信息为:{}",unitOrganizationPojo);
        int count = unitOrganizationDao.searchUnitOrganizationCountById(unitOrganizationPojo.getUnitCode());
        if(count != 0){
            throw SystemException.asSystemException(ErrorCode.CHECK_UNION_ERROR,"单位机构["+unitOrganizationPojo.getUnitName()+"]重复");
        }
        int addCount = unitOrganizationDao.addOneUnitOrganization(unitOrganizationPojo);
        log.info("插入到单位机构表中的数据为:{}",addCount);
        // 发送操作日志
        operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.ADD, "单位机构管理", unitOrganizationPojo);
        return "添加单位机构成功";
    }

    @Override
    public String deleteOneUnitOrganization(String unitCode) {
        // 获取机构名称
        List<String> unitNames = new ArrayList<>();
        List<UnitOrganizationPojo> unitOrganizationPojoList = unitOrganizationDao.searchUnitOrganizationTable(new UnitOrganizationParameter());
        unitOrganizationPojoList.stream().forEach(data ->{
            if (unitCode.contains(data.getUnitCode())){
                unitNames.add(data.getUnitName());
            }
        });

        log.info("要删除的单位机构信息为:{}",unitCode);
        List<String> unitCodes =Arrays.asList(unitCode.split(","));
        int deleteCount = unitOrganizationDao.deleteOneUnitOrganization(unitCodes);
        log.info("删除的单位机构信息为:{}条",deleteCount);
        // 发送操作日志
        UnitOrganizationPojo unitOrganizationPojo = new UnitOrganizationPojo();
        unitOrganizationPojo.setUnitCode(unitCode);
        unitOrganizationPojo.setUnitName(String.join(",",unitNames));
        operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.DELETE, "单位机构管理", unitOrganizationPojo);
        return "删除成功";
    }

    @Override
    public String updateOneUnitOrganization(UnitOrganizationPojo unitOrganizationPojo) {
        log.info("要修改的数据为:{}",unitOrganizationPojo);
        //检查该机构是否存在
        checkUpdateData(unitOrganizationPojo.getUnitCode());

        HASH_LOCK.lock(unitOrganizationPojo.getUnitCode());
        try{
            unitOrganizationPojo.setMemo(unitOrganizationPojo.getMemo() == null ? "" : unitOrganizationPojo.getMemo());
            int updateCount = unitOrganizationDao.updateOneUnitOrganization(unitOrganizationPojo);
            if(updateCount == 0){
                throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "更新失败，请刷新后重新更改数据");
            }
            // 发送操作日志
            operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.ALTER, "单位机构管理", unitOrganizationPojo);
        }finally{
            HASH_LOCK.unlock(unitOrganizationPojo.getUnitCode());
        }
        return "更新成功";
    }

    @Override
    public List<LayuiClassifyPojo> getAreaInfo() {
        log.info("开始查询所属地区信息");
        List<LayuiClassifyPojo> resultList = new ArrayList<>();
        try{
            //查询所属地区信息
            List<FieldCodeVal> searchList = unitOrganizationDao.getAreaInfo();

            //筛选出所属地区一级总共的名称列表
            List<String> primaryChList = searchList.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText()))
                    .map(d->(d.getCodeId()+"&&"+d.getCodeText())).distinct().collect(toList());
            //根据出所属地区一级分组总共有多少数据
            Map<String ,List<FieldCodeVal>> primaryListMap = searchList.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText()))
                    .collect(Collectors.groupingBy(d->(d.getCodeId()+"&&"+d.getCodeText())));

            for(String data : primaryChList){
                LayuiClassifyPojo parentPojo = new LayuiClassifyPojo();
                parentPojo.setValue(data.split("&&")[0]);
                parentPojo.setLabel(data.split("&&")[1]);
                //获取当前地区下的数据
                List<FieldCodeVal> childrenList = primaryListMap.get(data);
                List<LayuiClassifyPojo> childrenLayuiList = new ArrayList<>();
                //地区二级数据
                List<String> secondaryList = childrenList.stream().filter(d ->
                            StringUtils.isNotEmpty(d.getValText()) && d.getValValue().substring(d.getValValue().length() - 2).equalsIgnoreCase("00")
                        ).map(d -> (d.getValValue()+"&&"+d.getValText())).distinct().collect(toList());
                //地区三级数据
                List<String> threeList = childrenList.stream().filter(d ->
                        StringUtils.isNotEmpty(d.getValText()) && !d.getValValue().substring(d.getValValue().length() - 2).equalsIgnoreCase("00")
                ).map(d -> (d.getValValue()+"&&"+d.getValText())).distinct().collect(toList());

                for(String childrenData:secondaryList){
                    LayuiClassifyPojo secondaryLayuiClassifyPojo = new LayuiClassifyPojo();
                    secondaryLayuiClassifyPojo.setValue(childrenData.split("&&")[0]);
                    secondaryLayuiClassifyPojo.setLabel(childrenData.split("&&")[1]);
                    //注入地区三级数据
                    List<LayuiClassifyPojo> threeChildrenList = new ArrayList<>();
                    for(String threeChild:threeList){
                        LayuiClassifyPojo threeLayuiClassifyPojo = new LayuiClassifyPojo();
                        threeLayuiClassifyPojo.setValue(threeChild.split("&&")[0]);
                        threeLayuiClassifyPojo.setLabel(threeChild.split("&&")[1]);
                        threeChildrenList.add(threeLayuiClassifyPojo);
                    }
                    secondaryLayuiClassifyPojo.setChildren(threeChildrenList);
                    childrenLayuiList.add(secondaryLayuiClassifyPojo);
                }
                parentPojo.setChildren(childrenLayuiList);
                resultList.add(parentPojo);
            }
            return resultList;
        }catch (Exception e){
            log.error(ExceptionUtil.getExceptionTrace(e));
            throw SystemException.asSystemException(ErrorCode.DATA_IS_NULL, "数据查询失败");
        }
    }

    @Override
    public void downloadUnitOrganization(HttpServletResponse response, List<UnitOrganizationPojo> unitOrganizationList, String name, Object object) {
        try{
            log.info("=====开始下载单位机构信息=====");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(name,"UTF-8")+".xlsx";
            response.setHeader("Content-disposition",
                    "attachment;filename*=utf-8''" +fileName);
            int recno = 1;
            for(UnitOrganizationPojo data : unitOrganizationList){
                if(data.getUnitType() == 1){
                    data.setUnitTypeCh("政府单位机构");
                }else{
                    data.setUnitTypeCh("非政府单位机构");
                }
                data.setRecno(recno);
                recno++;
            }

            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("单位机构管理").doWrite(unitOrganizationList);
            log.info("======下载单位机构信息结束=====");
        }catch (Exception e){
            log.error("下载单位机构管理报错"+ ExceptionUtil.getExceptionTrace(e));
        }
    }

    @Override
    public List<FilterObject> getFilterObject() {
        List<FilterObject> list = unitOrganizationDao.searchAllFilterTable();
        if(list == null || list.isEmpty()){
            return new ArrayList<>();
        }else{
            list.stream().forEach(d -> {
                d.setText(d.getValue());
            });
        }
        return list;
    }

    /**
     * 检查单位机构是否唯一
     * @return
     */
    private void checkUpdateData(String unitCode){
        int num = unitOrganizationDao.searchUnitOrganizationCountById(unitCode);
        if(num != 1){
            throw SystemException.asSystemException(ErrorCode.CHECK_ERROR, "要素标识符["+unitCode
                    +"]在数据库中不存在，更新数据失败");
        }

    }

    /**
     * 获取左侧树信息
     * @return
     */
    private List<UnitOrganizationPojo> getUnitList(String code, String searchText){
        ArrayList<UnitOrganizationPojo> list = new ArrayList<>();
        //通过父id获取单位机构信息列表
        List<UnitOrganizationPojo> unitOrganizationList = unitOrganizationDao.searchUnitOrganizationListByParentId(code, searchText);
        if(unitOrganizationList.isEmpty()){
            return list;
        }else {
            for(UnitOrganizationPojo data :unitOrganizationList){
                list.add(data);
                List<UnitOrganizationPojo> allUnitOrganizationList = getUnitList(data.getUnitCode(), searchText);
                list.addAll(allUnitOrganizationList);
            }
            return list;
        }
    }

    /**
     * 获取子树信息
     * @return
     */
    private void getChildrenTree(List<UnitOrganizationTree> dataInfo,UnitOrganizationTree parentNode){
        dataInfo.stream().forEach(d -> {
            if( parentNode.getId().equalsIgnoreCase(d.getParentId())){
                List<UnitOrganizationTree> children = parentNode.getChildrenUnit();
                if(children == null){
                    children = new ArrayList<>();
                }
                if(!children.contains(d)){
                    children.add(d);
                }
                parentNode.setChildrenUnit(children);
            }
        });
    }

    public List<UnitOrganizationTree> routineUnitConvert(List<UnitOrganizationTree> tables, String codeId, List<UnitOrganizationTree> array) {
        for (UnitOrganizationTree table : tables) {
            if (table.getParentId().toString().equalsIgnoreCase(codeId)){
                UnitOrganizationTree jsonObject = new UnitOrganizationTree();
                jsonObject.setId(table.getId());
                jsonObject.setUnitName(table.getUnitName());
                jsonObject.setUnitLevel(table.getUnitLevel());
                jsonObject.setChildrenUnit(routineUnitConvert(tables, table.getId(), new ArrayList<>()));
                array.add(jsonObject);
            }
        }
        return array;
    }

}

package com.synway.datastandardmanager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.UnitOrganizationDTO;
import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeUnitManageEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.PageVO;
import com.synway.datastandardmanager.entity.vo.UnitOrganizationTreeVO;
import com.synway.datastandardmanager.entity.vo.ValueLabelVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.enums.OperateLogHandleTypeEnum;
import com.synway.datastandardmanager.mapper.FieldCodeMapper;
import com.synway.datastandardmanager.mapper.StandardizeUnitManageMapper;
import com.synway.datastandardmanager.mapper.SynlteFieldMapper;
import com.synway.datastandardmanager.service.UnitOrganizationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class UnitOrganizationServiceImpl implements UnitOrganizationService {

    @Resource
    StandardizeUnitManageMapper standardizeUnitManageMapper;
    @Resource
    FieldCodeMapper fieldCodeMapper;
    @Resource
    SynlteFieldMapper synlteFieldMapper;
    @Resource
    private OperateLogServiceImpl operateLogServiceImpl;


    @Override
    public List<UnitOrganizationTreeVO> getLeftTree() {
        log.info(">>>>>>开始获取单位机构管理左侧树信息");
        List<UnitOrganizationTreeVO> resultUnitLeftTreeInfo = new ArrayList<>();
        try {
            LambdaQueryWrapper<StandardizeUnitManageEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeUnitManageEntity::getUnitType, 1);
            //获取政府机构的列表
            List<UnitOrganizationTreeVO> governmentLeftTreeInfoList = getUnitOrganizationTree(1);
            //获取非政府机构的列表
            List<UnitOrganizationTreeVO> notGovernmentLeftTreeInfoList = getUnitOrganizationTree(2);

            //政府机构root信息
            UnitOrganizationTreeVO governmentRootNode = new UnitOrganizationTreeVO();
            governmentRootNode.setId("1");
            governmentRootNode.setUnitType(1);
            governmentRootNode.setUnitLevel(-1);
            //非政府机构root信息
            UnitOrganizationTreeVO notGovernmentRootNode = new UnitOrganizationTreeVO();
            notGovernmentRootNode.setId("2");
            notGovernmentRootNode.setUnitType(2);
            notGovernmentRootNode.setUnitLevel(-1);

            // 政府机构的左侧树
            List<UnitOrganizationTreeVO> arrayRoutine1 = new ArrayList<>();
            if (governmentLeftTreeInfoList == null || governmentLeftTreeInfoList.isEmpty()) {
                governmentRootNode.setUnitName("政府机构(0)");
            } else {
                if (governmentLeftTreeInfoList.size() == 1) {
                    arrayRoutine1 = routineUnitConvert(governmentLeftTreeInfoList, governmentLeftTreeInfoList.get(0).getId(), new ArrayList<>());
                } else {
                    arrayRoutine1 = routineUnitConvert(governmentLeftTreeInfoList, "1", new ArrayList<>());
                }
                governmentRootNode.setUnitName("政府机构(" + governmentLeftTreeInfoList.size() + ")");
                governmentRootNode.setChildrenUnit(arrayRoutine1);
            }
            //非政府机构的左侧树
            List<UnitOrganizationTreeVO> arrayRoutine2 = new ArrayList<>();
            if (notGovernmentLeftTreeInfoList == null || notGovernmentLeftTreeInfoList.isEmpty()) {
                notGovernmentRootNode.setUnitName("非政府机构(0)");
            } else {
                if (notGovernmentLeftTreeInfoList.size() == 1) {
                    arrayRoutine2 = routineUnitConvert(notGovernmentLeftTreeInfoList, notGovernmentLeftTreeInfoList.get(0).getId(), new ArrayList<>());
                } else {
                    arrayRoutine2 = routineUnitConvert(notGovernmentLeftTreeInfoList, "1", new ArrayList<>());
                }
                notGovernmentRootNode.setUnitName("非政府机构(" + notGovernmentLeftTreeInfoList.size() + ")");
                notGovernmentRootNode.setChildrenUnit(arrayRoutine2);
            }
            resultUnitLeftTreeInfo.add(governmentRootNode);
            resultUnitLeftTreeInfo.add(notGovernmentRootNode);
        } catch (Exception e) {
            log.error(">>>>>>获取单位机构管理左侧树信息出错：", e);
        }
        return resultUnitLeftTreeInfo;
    }

    // unitType，1：政府机构的列表，2：非政府机构的列表
    public List<UnitOrganizationTreeVO> getUnitOrganizationTree(Integer unitType) {
        LambdaQueryWrapper<StandardizeUnitManageEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StandardizeUnitManageEntity::getUnitType, unitType);
        List<UnitOrganizationTreeVO> unitOrganizationTreeVOS = new ArrayList<>();
        List<StandardizeUnitManageEntity> entities = standardizeUnitManageMapper.selectList(wrapper);
        for (StandardizeUnitManageEntity entity : entities) {
            UnitOrganizationTreeVO treeVO = new UnitOrganizationTreeVO();
            treeVO.setId(entity.getUnitCode());
            treeVO.setUnitName(entity.getUnitName());
            treeVO.setParentId(entity.getPUnitCode());
            treeVO.setUnitLevel(entity.getUnitLevel());
            unitOrganizationTreeVOS.add(treeVO);
        }
        return unitOrganizationTreeVOS;
    }

    public List<UnitOrganizationTreeVO> routineUnitConvert(List<UnitOrganizationTreeVO> tables, String codeId, List<UnitOrganizationTreeVO> array) {
        for (UnitOrganizationTreeVO table : tables) {
            if (table.getParentId().toString().equalsIgnoreCase(codeId)) {
                UnitOrganizationTreeVO jsonObject = new UnitOrganizationTreeVO();
                jsonObject.setId(table.getId());
                jsonObject.setUnitName(table.getUnitName());
                jsonObject.setUnitLevel(table.getUnitLevel());
                jsonObject.setChildrenUnit(routineUnitConvert(tables, table.getId(), new ArrayList<>()));
                array.add(jsonObject);
            }
        }
        return array;
    }

    @Override
    public PageVO searchUnitOrganizationTable(UnitOrganizationDTO dto) {
        PageVO pageVO = new PageVO<>();
        try {
            PageHelper.startPage(dto.getPageIndex(), dto.getPageSize());
            // 单位机构id不为空，则是点击了左侧树，获取信息
            if (StringUtils.isNotBlank(dto.getUnitCode()) && StringUtils.isBlank(dto.getSearchText())) {
                List<StandardizeUnitManageEntity> unitOrganizationPojoList = getUnitList(dto.getUnitCode(), dto.getSearchText());
                log.info("单位机构表格信息获取结束{}", unitOrganizationPojoList);
                PageInfo<StandardizeUnitManageEntity> unitManageEntityPageInfo = new PageInfo<>(unitOrganizationPojoList);
                pageVO.setTotal(unitManageEntityPageInfo.getTotal());
                pageVO.setRows(unitManageEntityPageInfo.getList());
                return pageVO;
            }
            //获取表格信息
            List<StandardizeUnitManageEntity> unitManageEntities = standardizeUnitManageMapper.searchUnitOrganizationTable(dto);
            //类型翻译
            unitManageEntities.stream().forEach(d -> {
                if (d.getUnitType() == 1) {
                    d.setUnitTypeCh("政府单位机构");
                } else {
                    d.setUnitTypeCh("非政府单位机构");
                }
            });
            PageInfo<StandardizeUnitManageEntity> unitManageEntityPageInfo = new PageInfo<>(unitManageEntities);
            pageVO.setTotal(unitManageEntityPageInfo.getTotal());
            pageVO.setRows(unitManageEntityPageInfo.getList());
            return pageVO;
        } catch (Exception e) {
            log.error(">>>>>>获取单位机构管理表格信息出错：", e);
            return pageVO.emptyResult();
        }
    }

    /**
     * 获取左侧树信息
     */
    private List<StandardizeUnitManageEntity> getUnitList(String code, String searchText) {
        ArrayList<StandardizeUnitManageEntity> list = new ArrayList<>();
        //通过父id获取单位机构信息列表
        List<StandardizeUnitManageEntity> unitOrganizationList = standardizeUnitManageMapper.searchUnitOrganizationListByParentId(code, searchText);
        if (!unitOrganizationList.isEmpty()) {
            for (StandardizeUnitManageEntity data : unitOrganizationList) {
                list.add(data);
                List<StandardizeUnitManageEntity> allUnitOrganizationList = getUnitList(data.getUnitCode(), searchText);
                list.addAll(allUnitOrganizationList);
            }
        }
        return list;
    }

    @Override
    public String addOneUnitOrganization(StandardizeUnitManageEntity standardizeUnitManage) {
        try {
            log.info(">>>>>>要插入的单位机构信息为:{}", standardizeUnitManage);
            LambdaQueryWrapper<StandardizeUnitManageEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeUnitManageEntity::getUnitCode, standardizeUnitManage.getUnitCode());
            if (standardizeUnitManageMapper.selectCount(wrapper) != 0) {
                throw new Exception(String.format("%s：单位机构[%s]重复", ErrorCodeEnum.CHECK_UNION_ERROR, standardizeUnitManage.getUnitName()));
            }
            standardizeUnitManageMapper.insert(standardizeUnitManage);
            // 发送操作日志
            operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.ADD, "单位机构管理", standardizeUnitManage);
            return Common.ADD_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>插入单位机构信息失败：", e);
            return Common.ADD_FAIL;
        }
    }

    @Override
    public String deleteOneUnitOrganization(String unitCode) {
        try {
            log.info(">>>>>>要删除的单位机构信息为:{}", unitCode);
            List<String> unitCodes = Arrays.asList(unitCode.split(","));
            LambdaQueryWrapper<StandardizeUnitManageEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.in(StandardizeUnitManageEntity::getUnitCode, unitCodes);
            standardizeUnitManageMapper.delete(wrapper);
            // 获取机构名称
            List<String> unitNames = new ArrayList<>();
            List<StandardizeUnitManageEntity> unitManageEntities = standardizeUnitManageMapper.searchUnitOrganizationTable(new UnitOrganizationDTO());
            unitManageEntities.stream().forEach(data ->{
                if (unitCode.contains(data.getUnitCode())){
                    unitNames.add(data.getUnitName());
                }
            });
            // 发送操作日志
            StandardizeUnitManageEntity unitManageEntity = new StandardizeUnitManageEntity();
            unitManageEntity.setUnitCode(unitCode);
            unitManageEntity.setUnitName(String.join(",",unitNames));
            operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.DELETE, "单位机构管理", unitManageEntity);
            return Common.DEL_SUCCESS;
        } catch (Exception e) {
            log.error("");
            return Common.DEL_FAIL;
        }
    }

    @Override
    public String updateOneUnitOrganization(StandardizeUnitManageEntity unitManageEntity) {
        try {
            log.info(">>>>>>要修改的数据为:{}", unitManageEntity);
            //检查该机构是否存在
            LambdaQueryWrapper<StandardizeUnitManageEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(StandardizeUnitManageEntity::getUnitCode, unitManageEntity.getUnitCode());
            if (standardizeUnitManageMapper.selectCount(wrapper) != 1) {
                throw new Exception(String.format("%s：要素标识符[%s]在数据库中不存在", ErrorCodeEnum.CHECK_ERROR, unitManageEntity.getUnitCode()));
            }
            unitManageEntity.setMemo(unitManageEntity.getMemo() == null ? "" : unitManageEntity.getMemo());
            standardizeUnitManageMapper.updateOneUnitOrganization(unitManageEntity);
            // 发送操作日志
            operateLogServiceImpl.unitOrganizationSuccessLog(OperateLogHandleTypeEnum.ALTER, "单位机构管理", unitManageEntity);
            return Common.UPDATE_SUCCESS;
        } catch (Exception e) {
            log.error(">>>>>>更新单位机构信息失败：", e);
        }
        return Common.UPDATE_FAIL;
    }

    @Override
    public List<ValueLabelVO> getAreaInfo() {
        List<ValueLabelVO> resultList = new ArrayList<>();
        try {
            //查询所属地区信息
            List<FieldCodeEntity> searchList = fieldCodeMapper.getAreaInfo();
            //根据出所属地区一级分组总共有多少数据
            Map<String, List<FieldCodeEntity>> primaryListMap = searchList.stream().filter(d -> StringUtils.isNotEmpty(d.getCodeText()))
                    .collect(Collectors.groupingBy(d -> (d.getCodeId() + "&&" + d.getCodeText())));
            for (String data : primaryListMap.keySet()) {
                ValueLabelVO parentPojo = new ValueLabelVO();
                parentPojo.setValue(data.split("&&")[0]);
                parentPojo.setLabel(data.split("&&")[1]);
                List<ValueLabelVO> childrenSecond = new ArrayList<>();
                //获取当前地区下的数据
                List<FieldCodeEntity> fieldCodeValsSecond = primaryListMap.get(data);
                //市级列表
                List<FieldCodeEntity> secondList = fieldCodeValsSecond.stream().filter(d ->
                                StringUtils.isNotEmpty(d.getValText()) && d.getValValue().substring(d.getValValue().length() - 2).equalsIgnoreCase("00"))
                        .collect(toList());
                for (FieldCodeEntity fieldCodeVal : secondList) {
                    ValueLabelVO secondaryLayuiClassifyPojo = new ValueLabelVO();
                    secondaryLayuiClassifyPojo.setValue(fieldCodeVal.getValValue());
                    secondaryLayuiClassifyPojo.setLabel(fieldCodeVal.getValText());
                    List<FieldCodeEntity> fieldCodeValsThree = fieldCodeValsSecond.stream().filter(d -> {
                        String valValue = d.getValValue().substring(0, 4);
                        String valValueSec = fieldCodeVal.getValValue().substring(0, 4);
                        boolean isProvincial = d.getValValue().substring(d.getValValue().length() - 2).equalsIgnoreCase("00");
                        if (valValue.equalsIgnoreCase(valValueSec) && !isProvincial) {
                            return true;
                        } else {
                            return false;
                        }
                    }).collect(toList());
                    //注入地区三级数据
                    List<ValueLabelVO> threeChildrenList = new ArrayList<>();
                    for (FieldCodeEntity threeChild : fieldCodeValsThree) {
                        ValueLabelVO threeLayuiClassifyPojo = new ValueLabelVO();
                        threeLayuiClassifyPojo.setValue(threeChild.getValValue());
                        threeLayuiClassifyPojo.setLabel(threeChild.getValText());
                        threeChildrenList.add(threeLayuiClassifyPojo);
                    }
                    secondaryLayuiClassifyPojo.setChildren(threeChildrenList);
                    childrenSecond.add(secondaryLayuiClassifyPojo);
                }
                parentPojo.setChildren(childrenSecond);
                resultList.add(parentPojo);
            }
        } catch (Exception e) {
            log.error(">>>>>>查询所属地区信息失败：", e);
        }
        return resultList;
    }

    @Override
    public void downloadUnitOrganization(HttpServletResponse response, List<StandardizeUnitManageEntity> unitOrganizationList, String fileName, Object object) {
        try {
            log.info(">>>>>>开始下载单位机构信息=====");
            response.setContentType("application/x-xls");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8") + ".xlsx";
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
            int recno = 1;
            for (StandardizeUnitManageEntity data : unitOrganizationList) {
                if (data.getUnitType() == 1) {
                    data.setUnitTypeCh("政府单位机构");
                } else {
                    data.setUnitTypeCh("非政府单位机构");
                }
                data.setRecno(recno);
                recno++;
            }
            EasyExcel.write(response.getOutputStream(), object.getClass()).autoCloseStream(Boolean.FALSE)
                    .sheet("单位机构管理").doWrite(unitOrganizationList);
        } catch (Exception e) {
            log.error(">>>>>>下载单位机构管理报错：", e);
        }
    }

    @Override
    public List<KeyValueVO> getFilterObject() {
        List<KeyValueVO> list = new ArrayList<>();
        try {
            list = synlteFieldMapper.getFilterObjectForSF();
            if (list != null && !list.isEmpty()) {
                list.stream().forEach(d -> {
                    d.setLabel(d.getValue());
                });
            }
        } catch (Exception e) {
            log.error(">>>>>>查询数据元的筛选内容：", e);
        }
        return list;
    }

}

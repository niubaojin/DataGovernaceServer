package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.GetTreeReqDTO;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.entity.vo.StandardTableRelationVO;
import com.synway.datastandardmanager.entity.vo.TreeNodeValueVO;
import com.synway.datastandardmanager.enums.KeyIntEnum;
import com.synway.datastandardmanager.mapper.CommonMapper;
import com.synway.datastandardmanager.mapper.StandardizeInputObjectRelateMapper;
import com.synway.datastandardmanager.service.ApiInterfceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ApiInterfceServiceImpl implements ApiInterfceService {
    @Resource
    private StandardizeInputObjectRelateMapper standardizeInputObjectRelateMapper;
    @Resource
    private CommonMapper commonMapper;

    @Override
    public List<KeyValueVO> getStandardOutTableIdBySourceIdService(String sourceId, String sourceCode, String sourceFirmCode) {
        List<KeyValueVO> keyValueVOS = new ArrayList<>();
        try {
            log.info(String.format("查询的参数为，sourceId：%s, sourceCode:%s, sourceFirmCode:%s", sourceId, sourceCode, sourceFirmCode));
            if (StringUtils.isEmpty(sourceId) || StringUtils.isEmpty(sourceCode) || StringUtils.isEmpty(sourceFirmCode)) {
                throw new NullPointerException("传入的参数值为空，请先填写sourceId/sourceCode/sourceFirmCode对应的值");
            }
            // 来源厂商只能是中文
            Integer sourceFirmNum = KeyIntEnum.getKeyByNameAndType(sourceFirmCode, Common.MANUFACTURER_NAME);
            keyValueVOS = standardizeInputObjectRelateMapper.getStandardOutTableIdBySourceIdDao(sourceId, sourceCode, String.valueOf(sourceFirmNum));
        } catch (Exception e) {
            log.error(">>>>>>查询数据报错：", e);
        }
        return keyValueVOS;
    }

    @Override
    public List<Map<String, String>> getStandardTableBySourceId(String sourceId) {
        List<Map<String, String>> jsonObjects = new ArrayList<>();
        try {
            if (StringUtils.isNotBlank(sourceId)) {
                throw new NullPointerException("传入的参数值为空，请先填写sourceId对应的值");
            }
            List<KeyValueVO> keyValueVOS = standardizeInputObjectRelateMapper.getStandardOutTableIdBySourceIdDao(sourceId, "", "");
            for (KeyValueVO keyValueVO : keyValueVOS) {
                Map<String, String> map = new HashMap<>(1);
                map.put("tableId", keyValueVO.getValue());
                jsonObjects.add(map);
            }
            log.info(">>>>>>查询结果为：" + JSONObject.toJSONString(jsonObjects));
        } catch (Exception e) {
            log.error(">>>>>>查询数据报错：", e);
        }
        return jsonObjects;
    }

    @Override
    public List<TreeNodeValueVO> externalgetTableOrganizationTree(GetTreeReqDTO req, Boolean isQueryTable, Boolean showLabel) {
        List<TreeNodeValueVO> treeNodes = new ArrayList<>();
        // 数据组织资产那边需要查询所有的分类 标准和非标准都要
        List<StandardTableRelationVO> standardRelationList = commonMapper.getOrganizationZTreeNodesAll(req.getNodeName(), req.getType());
        List<String> parimaryClassList = standardRelationList.stream().map(o -> o.getPrimaryClassifyCh()).distinct().collect(toList());
        Map<String, List<StandardTableRelationVO>> parimaryClassMap = standardRelationList.stream().collect(Collectors.groupingBy(StandardTableRelationVO::getPrimaryClassifyCh));
        for (String parent : parimaryClassList) {
            //  一级分类的内容
            TreeNodeValueVO levelOne = new TreeNodeValueVO();
            levelOne.setId(parent);
            List<StandardTableRelationVO> levelOneList = parimaryClassMap.get(parent);
            if (showLabel) {
                levelOne.setLabel(parent + "(" + levelOneList.size() + ")");
            } else {
                levelOne.setLabel(parent);
            }
            levelOne.setLevel(1);
            // 一级分类之后的子节点数组
            List<TreeNodeValueVO> chlidOne = new ArrayList<>();
            List<String> secondaryClassList = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).map(o -> o.getSecondaryClassifyCh()).distinct().collect(toList());
            Map<String, List<StandardTableRelationVO>> secondaryClassMap = levelOneList.stream().filter(d -> StringUtils.isNotEmpty(d.getSecondaryClassifyCh())).collect(Collectors.groupingBy(StandardTableRelationVO::getSecondaryClassifyCh));
            for (String child : secondaryClassList) {
                // 二级分类的数据
                TreeNodeValueVO levelTwo = new TreeNodeValueVO();
                levelTwo.setId(child);
                levelTwo.setParent(parent);
                List<StandardTableRelationVO> levelTwoList = secondaryClassMap.get(child);
                if (showLabel) {
                    levelTwo.setLabel(child + "(" + levelTwoList.size() + ")");
                } else {
                    levelTwo.setLabel(child);
                }
                levelTwo.setLevel(2);
                List<TreeNodeValueVO> chlidTwo = new ArrayList<>();
                // 对于三级分类，有些表id存在三级分类，有些表id不存在三级分类，所以需要区分
                // 存在三级分类的数据
                List<String> threeNotNullClassList = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .map(o -> o.getThreeClassifyCh()).distinct().collect(toList());
                Map<String, List<StandardTableRelationVO>> threeClassMap = levelTwoList.stream().filter(o -> StringUtils.isNotEmpty(o.getThreeClassifyCh()))
                        .collect(Collectors.groupingBy(StandardTableRelationVO::getThreeClassifyCh));
                for (String twoChild : threeNotNullClassList) {
                    //  三级分类
                    TreeNodeValueVO levelThree = new TreeNodeValueVO();
                    levelThree.setId(twoChild);
                    levelThree.setParent(child);
                    levelThree.setGrandpar(parent);
                    List<StandardTableRelationVO> levelThreeList = threeClassMap.get(twoChild);
                    if (showLabel) {
                        levelThree.setLabel(twoChild + "(" + levelThreeList.size() + ")");
                    } else {
                        levelThree.setLabel(twoChild);
                    }
                    levelThree.setLevel(3);
                    List<TreeNodeValueVO> chlidThree = new ArrayList<>();
                    if (isQueryTable) {
                        for (StandardTableRelationVO s1 : levelThreeList) {
                            // 以下为 具体的表信息
                            TreeNodeValueVO tableTreeNode = new TreeNodeValueVO();
                            tableTreeNode.setId(s1.getTableId());
                            tableTreeNode.setLabel(s1.getTableNameCh());
                            tableTreeNode.setLevel(4);
                            tableTreeNode.setParent(twoChild);
                            chlidThree.add(tableTreeNode);
                        }
                    }
                    levelThree.setChildren(chlidThree);
                    chlidTwo.add(levelThree);
                }
                // 存储三级分类是空的数据
                if (isQueryTable) {
                    for (StandardTableRelationVO standardTableRelation : levelTwoList) {
                        if (StringUtils.isEmpty(standardTableRelation.getThreeClassifyCh())) {
                            TreeNodeValueVO levelTwoTable = new TreeNodeValueVO();
                            levelTwoTable.setId(standardTableRelation.getTableId());
                            levelTwoTable.setLabel(standardTableRelation.getTableNameCh());
                            levelTwoTable.setLevel(3);
                            levelTwoTable.setParent(child);
                            chlidTwo.add(levelTwoTable);
                        }
                    }
                }
                levelTwo.setChildren(chlidTwo);
                chlidOne.add(levelTwo);
            }
            levelOne.setChildren(chlidOne);
            treeNodes.add(levelOne);
        }
        log.info(">>>>>>查询到的数据为：" + JSONObject.toJSONString(treeNodes));
        return treeNodes;
    }

}

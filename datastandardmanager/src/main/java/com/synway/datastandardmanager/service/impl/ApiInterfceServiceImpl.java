package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.KeyIntEnum;
import com.synway.datastandardmanager.mapper.StandardizeInputObjectRelateMapper;
import com.synway.datastandardmanager.service.ApiInterfceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
public class ApiInterfceServiceImpl implements ApiInterfceService {
    @Resource
    private StandardizeInputObjectRelateMapper standardizeInputObjectRelateMapper;

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

}

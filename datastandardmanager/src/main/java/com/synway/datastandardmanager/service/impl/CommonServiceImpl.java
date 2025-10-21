package com.synway.datastandardmanager.service.impl;

import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.enums.ErrorCodeEnum;
import com.synway.datastandardmanager.mapper.FieldCodeValMapper;
import com.synway.datastandardmanager.service.CommonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private FieldCodeValMapper fieldCodeValMapper;

    @Override
    public List<KeyValueVO> searchValtext() {
        List<KeyValueVO> valtextList = new ArrayList<>();
        try {
            log.info(">>>>>>开始查询厂商信息");
            valtextList = fieldCodeValMapper.queryLabelValueByCodeId("JZCODEZJCSXX");
            if (valtextList.isEmpty()) {
                throw new Exception(String.format("%s：synlte.fieldcodeval表中没有数据", ErrorCodeEnum.QUERY_SQL_ERROR));
            }
            valtextList.sort((s1, s2) -> Collator.getInstance(java.util.Locale.CHINA).compare(s1.getLabel(), s2.getLabel()));
        } catch (Exception e) {
            log.error(">>>>>>查询厂商信息失败：", e);
        }
        return valtextList;
    }

}

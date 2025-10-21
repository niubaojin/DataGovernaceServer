package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.entity.pojo.FieldCodeEntity;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.pojo.StandardizeOriginalDFEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 标准字典管理参数
 *
 * @author obito
 * @version 1.0
 * @date 2022/03/023
 */
@Data
public class StandardDictionaryDTO implements Serializable {
    private static final long serialVersionUID = 5242123343516761376L;

    /**
     * 下载标准/原始字典数据项信息
     */
    private String id;
    private List<FieldCodeValEntity> standardDictionaryFieldPojoList;
    private List<StandardizeOriginalDFEntity> originalDictionaryFieldPojoList;

    /**
     * 保存元素代码集值信息
     */
    private FieldCodeEntity oneFieldcode;
    private List<FieldCodeValEntity> oneFieldCodeVal;
}

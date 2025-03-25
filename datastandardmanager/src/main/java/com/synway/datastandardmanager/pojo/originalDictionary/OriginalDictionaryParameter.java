package com.synway.datastandardmanager.pojo.originalDictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 原始字典管理参数
 * @author obito
 * @version 1.0
 * @date 2022/03/023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OriginalDictionaryParameter implements Serializable {
    private static final long serialVersionUID = 5242123343516761376L;

    private String id;

    private List<OriginalDictionaryFieldPojo> originalDictionaryFieldPojoList;
}

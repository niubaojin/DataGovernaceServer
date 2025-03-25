package com.synway.datastandardmanager.pojo.datastandDictionary;

import com.synway.datastandardmanager.pojo.FieldCodeVal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 标准字典管理参数
 * @author obito
 * @version 1.0
 * @date 2022/03/023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataStandardDictionaryParameter implements Serializable {
    private static final long serialVersionUID = 5242123343516761376L;

    private String id;

    private List<FieldCodeVal> standardDictionaryFieldPojoList;
}

package com.synway.datastandardmanager.pojo.synlteelement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 通用的SQL返回数据要素实体类,被用于数据要素新增时的主体下拉框、生成方式下拉框、数据定义页面筛选值
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectField {

    private String id;

    private String value;

    private String sameId;

    private String name;
}

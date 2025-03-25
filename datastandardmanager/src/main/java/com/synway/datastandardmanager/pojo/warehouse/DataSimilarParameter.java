package com.synway.datastandardmanager.pojo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * 获取探查推荐相似表的参数实体
 * @author obito
 * @version 1.0
 * @date 2022/04/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSimilarParameter implements Serializable {
    private static final long serialVersionUID = 124615424684L;

    //数据源id
    private String resId;

    //项目空间名称
    private String projectName;

    //表英文名
    private String tableNameEn;

}

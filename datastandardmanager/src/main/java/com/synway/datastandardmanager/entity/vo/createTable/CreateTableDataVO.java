package com.synway.datastandardmanager.entity.vo.createTable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangdongwei
 * @date 2021/9/15 16:46
 */
@Data
public class CreateTableDataVO implements Serializable {

    @NotNull(message = "表id不能为空")
    private String tableId;

    @NotNull(message = "格式为：schema.tableName")
    private String tableName;

    //数据源id 唯一
    @NotNull(message = "数据源id不能为空")
    private String resId;

    //用于与 resId 查询到的类型对比
    @NotNull(message = "数据库类型不能为空")
    private String type;

    /**
     * 用于建表的相关信息：
     * 如果是hive/hbase/ck/odps/ads，该值存储的是直接建表的sql信息
     * 如果是datahub，该值为datahub建表对象的json字符串
     */
    @NotNull(message = "建表内容不能为空")
    private String data;

}

package com.synway.datastandardmanager.pojo.dataresource;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangdongwei
 * @date 2021/9/15 16:46
 */
@Data
public class CreateTableData implements Serializable {
    /**
     * 数据源id 唯一
     */
    @NotNull(message = "数据源id不能为空")
    private String resId;

    /**
     * 用于建表的相关信息，
     * 如果是 hive/hbase/ck/odps/ads ,该值存储的是 直接建表的sql信息
     * 如果是 datahub 该值为 datahub 建表对象的 json字符串
     *
     */
    @NotNull(message = "建表内容不能为空")
    private String data;

    /**
     * 建表的数据库类型不能为空，用于与 resId 查询到的类型对比
     */
    @NotNull(message = "数据库类型不能为空")
    private String type;

    @NotNull(message = "表id不能为空")
    private String tableId;

    @NotNull(message = "格式为：Schema.tableName")
    private String tableName;

}

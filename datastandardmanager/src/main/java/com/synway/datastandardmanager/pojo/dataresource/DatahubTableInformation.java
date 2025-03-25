package com.synway.datastandardmanager.pojo.dataresource;

import com.synway.datastandardmanager.pojo.buildtable.TableColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 在建表和新增字段中使用的相关实体类
 * @author wangdongwei
 * @date 2021/9/23 19:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatahubTableInformation implements Serializable {

    /**
     * 表字段信息
     */
    private List<TableColumn> columns;

    /**
     * 项目名
      */
    private String topicProjectName;


    /**
     * 生命周期
     */
    private Integer lifeCycle;

    /**
     * 备注信息
     */
    private String comment;

    /**
     * 通道数
     */
    private String shardCount;

    /**
     * toplic名称
     */
    private String topicName;

}

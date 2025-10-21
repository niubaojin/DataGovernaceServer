package com.synway.datastandardmanager.entity.vo.createTable;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 在建表和新增字段中使用的相关实体类
 * @author wangdongwei
 * @date 2021/9/23 19:16
 */
@Data
public class DatahubTableInfoVO implements Serializable {

    //toplic名称
    private String topicName;

    //项目名
    private String topicProjectName;

    //通道数
    private String shardCount;

    //生命周期
    private Integer lifeCycle;

    //备注信息
    private String comment;

    //表字段信息
    private List<TableColumnVO> columns;

}

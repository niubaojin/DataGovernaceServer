package com.synway.datastandardmanager.entity.vo;

import com.synway.datastandardmanager.entity.pojo.ObjectFieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 20211209 标准信息导出对象
 *
 * @author obito
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportObjectInfoVO implements Serializable {

    private String objectId;

    /**
     * 表中文名
     */
    private String objectName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 标准表名
     */
    private String tableId;

    /**
     * 源数据名称
     */
    private String sourceId;


    /**
     * 对应的数据项描述
     */
    private String objectMemo;

    /**
     * MD_ID取值
     */
    private String md5Index;

    /**
     * 主键
     */
    private String pkRecno;

    /**
     * 分区列
     */
    private String partitionRecno;

    /**
     * 二级分区列
     */
    private String secondPartitionRecno;

    /**
     * 聚集列
     */
    private String clustRecno;

    /**
     * 数据资源一级分类
     */
    private String parOrganizationClassify;

    /**
     * 数据资源二级分类
     */
    private String secondOrganizationClassify;

    /**
     * 标准表的字段信息
     */
    private List<ObjectFieldEntity> objectFieldInfo;

}

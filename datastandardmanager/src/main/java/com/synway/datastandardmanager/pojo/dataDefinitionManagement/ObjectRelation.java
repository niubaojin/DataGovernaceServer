package com.synway.datastandardmanager.pojo.dataDefinitionManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据项对标 数据集pojo
 * @author obito
 * @version 1.0
 * @date 2022/01/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectRelation implements Serializable {
    private static final long serialVersionUID = 429871246423634546L;

    /**
     * 表id
     */
    private String id;

    /**
     * 数据集 id
     */
    @NotBlank(message="[数据集 id]不能为空")
    private Long objectId;

    /**
     * 数据名称
     */
    @NotBlank(message="[数据名称]不能为空")
    private String objectName;

    /**
     * 标准编码
     */
    @NotBlank(message="[标准编码]不能为空")
    private String tableId;

    /**
     * 当object_id原始汇聚，是：-1，否：关联的原始汇聚的id
     */
    private String parentId;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}

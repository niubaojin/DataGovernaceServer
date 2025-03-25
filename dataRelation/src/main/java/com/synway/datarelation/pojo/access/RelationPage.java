package com.synway.datarelation.pojo.access;

import com.synway.datarelation.pojo.databloodline.QueryBloodlineRelationInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询数据血缘信息
 */
@Data
public class RelationPage implements Serializable {

    private int pageNum;

    private long totalPageNum;

    private List<QueryBloodlineRelationInfo> relationInfos;

}

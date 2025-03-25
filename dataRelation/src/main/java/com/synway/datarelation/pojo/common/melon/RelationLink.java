package com.synway.datarelation.pojo.common.melon;

import lombok.Data;

/**
 * @Author chenfei
 * @Data 2024/6/6 10:26
 * @Description 数据血缘link信息
 */
@Data
public class RelationLink {

    private RelationNode leftNode;

    private RelationNode node;

    private RelationNode rightNode;

}

package com.synway.governace.pojo.largeScreen;

import lombok.Data;

/**
 * 查询条件
 *
 * @author ywj
 * @date 2020/7/23 14:26
 */
@Data
public class QueryInfo {
    // 数据id
    private String dataId;
    // 数据时间
    private String dataTime;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;

    //数据组织分类代码，技侦：JZCODEJZSJZZFL，公安：JZCODEGASJZZFL
    private String sjzzflCodeId = "JZCODEGASJZZFL";

}

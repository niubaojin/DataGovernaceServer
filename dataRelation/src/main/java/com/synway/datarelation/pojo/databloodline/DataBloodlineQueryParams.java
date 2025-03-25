package com.synway.datarelation.pojo.databloodline;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *  查询参数
 */
public class DataBloodlineQueryParams {
    public static final String LIKE = "LIKE";
    public static final String EXACT = "EXACT";
    // 查询数据 -- 协议名英文名（jz_xx）、协议中文名、表英文名(不包含项目名)
    private String queryinfo="";
    //值域：“LIKE”：模糊匹配  “EXACT”：精确查询
    private String querytype="LIKE";
    // 增加参数，判断查询类型(只在我这边程序使用)
    private String nodeQueryType="";

    public String getNodeQueryType() {
        return nodeQueryType;
    }

    public void setNodeQueryType(String nodeQueryType) {
        this.nodeQueryType = nodeQueryType;
    }

    public String getQueryinfo() {
        return queryinfo;
    }

    public void setQueryinfo(String queryinfo) {
        this.queryinfo = queryinfo;
    }

    public String getQuerytype() {
        return querytype;
    }

    public void setQuerytype(String querytype) {
        this.querytype = querytype;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

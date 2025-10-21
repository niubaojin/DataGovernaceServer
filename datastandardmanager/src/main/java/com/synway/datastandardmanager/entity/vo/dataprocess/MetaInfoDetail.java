package com.synway.datastandardmanager.entity.vo.dataprocess;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaInfoDetail implements Serializable {

    private String key;
    private String fieldno;
    private String engname;
    private String cnname;
    private String dbname;
    private int len;
    private int fieldtype;
    private int partition;
    private int type;
    private int notNull;    // 字段是否必填，0:非必填,1:必填,2:ADS必填

}

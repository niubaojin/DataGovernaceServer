package com.synway.datastandardmanager.entity.vo.dataprocess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushMetaInfo implements Serializable {

    private int type;
    private String sys;
    private String syscnname;
    private String sysengname;
    private String protocolengname;
    private String protocolcnname;
    private String source;
    private String tablename;
    private int db;
    private List<MetaInfoDetail> field;

}

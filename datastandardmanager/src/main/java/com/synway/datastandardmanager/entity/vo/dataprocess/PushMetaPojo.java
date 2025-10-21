package com.synway.datastandardmanager.entity.vo.dataprocess;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushMetaPojo implements Serializable {

    private String userId;
    private String userName;
    private List<PushMetaInfo> pushMetaInfoList;

}

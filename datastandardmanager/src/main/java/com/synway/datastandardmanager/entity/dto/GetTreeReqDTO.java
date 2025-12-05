package com.synway.datastandardmanager.entity.dto;

import lombok.Data;

@Data
public class GetTreeReqDTO {
    private int type;
    private int tache;
    private String nodeName;
    private String startTime;
    private String endTime;
}

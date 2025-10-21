package com.synway.datastandardmanager.entity.vo.createTable;

import lombok.Data;

import java.io.Serializable;

/**
 * 建表相关的实体类
 * @author
 * @date 2019/1/11 10:31
 */
@Data
public class CreateTableVO implements Serializable {

    private String message;
    private Boolean switchFlag = false;
    private Boolean approvalInfo = false;
    private String url;

}

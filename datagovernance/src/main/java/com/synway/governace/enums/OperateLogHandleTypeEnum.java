package com.synway.governace.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateLogHandleTypeEnum {

    LOGIN(0, "登录"),
    QUERY(1, "查询"),
    ADD(2,  "新增"),
    ADD_PARTITION(2,  "新增探查分区"),
    ADD_FILESYSTEM(2,  "新增文件系统探查"),
    ADD_EXAMPLEDATA(2,  "新增探查样例数据"),
    ADD_DATASETDETECTMAPPING(2,  "新增探查映射关系数据"),
    ADD_REGISTER(2,  "新增注册表数据"),
    ALTER(3, "修改"),
    DELETE(4, "删除"),
    LOGOUT(5, "登出"),
    EXPORT(6, "导出"),
    REGISTER(7, "注册"),
    CREATETABLE(8, "建表");

    private final int code;
    private final String message;

}

package com.synway.datastandardmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperateLogFailReasonEnum {

    YHFM("1000", "用户方面的错误"),
    WXSR("1001",  "无效输入"),
    QTYHFM("1999", "其他用户方面的错误"),
    YYXTFM("2000", "应用系统方面的错误"),
    AQJCLFM("3000", "安全检查类方面的错误"),
    IPSX("3001", "IP受限"),
    SJSX("3002", "时间受限"),
    CZCSSX("3003", "操作次数受限"),
    HYMYMMBPP("3004", "用户名与密码不匹配"),
    SZZSBZX("3005", "数字证书被注销"),
    YHZHBDJ("3006", "用户账号被冻结"),
    WCZQX("3007", "无操作权限"),
    QTAQJCFM("3999", "其他安全检查方面的错误");

    private final String code;

    private final String message;

}

package com.synway.property.pojo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * USER_AUTHORITY 表对应的实体类
 * @author wdw
 * @version 1.0
 * @date 2021/5/31 18:24
 */
@Data
@ToString
public class UserAuthority implements Serializable {
    private static final long serialVersionUID = 6274449040164777427L;
    /**
     * 这个值一般都要大写
     */
    @NotNull(message = "id值不能为空")
    @Size(max= 254, message = "id字段长度不能超过254")
    private String id;

    private String cmnMemo = " ";

    @NotNull(message = "moduleCode值不能为空")
    @Size(max= 254, message = "moduleCode字段长度不能超过254")
    private String moduleCode = "FXXSJKJK";

    @NotNull(message = "id值不能为空")
    @Size(max= 254, message = "moduleName字段长度不能超过254")
    private String moduleName = "分析型数据库监控";

    private boolean isCreater = true;

    @NotNull(message = "id值不能为空")
    @Size(max= 254, message = "userName字段长度不能超过254")
    private String userName;

    @NotNull(message = "id值不能为空")
    @Size(max= 254, message = "userId字段长度不能超过254")
    private String userId;

    @NotNull(message = "id值不能为空")
    @Size(max= 254, message = "cmnName字段长度不能超过254")
    private String cmnName=" ";


}

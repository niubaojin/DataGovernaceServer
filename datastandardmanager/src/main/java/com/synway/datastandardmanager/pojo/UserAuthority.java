package com.synway.datastandardmanager.pojo;

import lombok.Data;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "id字段长度不能超过254")
    private String id;

    private String cmnMemo = " ";

    @NotBlank(message = "moduleCode值不能为空")
    @Size(max= 254, message = "moduleCode字段长度不能超过254")
    private String moduleCode = "BZGL";

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "moduleName字段长度不能超过254")
    private String moduleName = "标准管理";

    private boolean isCreater = true;

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "userName字段长度不能超过254")
    private String userName;

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "userId字段长度不能超过254")
    private String userId;

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "cmnName字段长度不能超过254")
    private String cmnName=" ";

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "organId字段长度不能超过254")
    private String organId;

    @NotBlank(message = "id值不能为空")
    @Size(max= 254, message = "organName字段长度不能超过254")
    private String organName;


}

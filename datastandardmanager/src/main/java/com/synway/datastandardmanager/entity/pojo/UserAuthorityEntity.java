package com.synway.datastandardmanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 权限表
 *
 * @author nbj
 * @Date 2025年8月6日13:48:08
 */
@Data
@TableName("USER_AUTHORITY")
public class UserAuthorityEntity {

    //业务资源ID,仓库模块数据源ID、协议编码（tableID）
    @NotNull(message = "id值不能为空")
    @TableField("ID")
    private String id;

    //业务资源ID对应信息
    @TableField("CMN_MEMO")
    private String cmnMemo;

    //模块编码
    @NotNull(message = "moduleCode值不能为空")
    @TableField("MODULECODE")
    private String moduleCode = "BZGL";

    //模块名称
    @NotNull(message = "moduleName值不能为空")
    @TableField("MODULENAME")
    private String moduleName = "标准管理";

    //0：非创建人 1：创建人
    @NotNull(message = "isCreater值不能为空")
    @TableField("ISCREATER")
    private Integer isCreater = 1;

    //有访问权限的用户名
    @NotNull(message = "userName值不能为空")
    @TableField("USERNAME")
    private String userName;

    //业务资源名
    @TableField("CMN_NAME")
    private String cmnName;

    //有访问权限的用户id
    @NotNull(message = "userId值不能为空")
    @TableField("USERID")
    private Integer userId;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    private Date createTime;

    //机构ID
    @TableField("ORGANID")
    private String organId;

    //机构名称
    @TableField("ORGANNAME")
    private String organName;

}

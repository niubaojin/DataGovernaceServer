package com.synway.datastandardmanager.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 20191010 对象详细信息表发生更改 不再展示之前的内容 按照最新的展示
 * @author wangdongwei
 */
@Data
@Setter
@Getter
@ToString
public class ObjectPojoTable implements Serializable{
    //序号
    private String objectId;
    // 数据名
    @Size(max = 80,message = "[表中文名]长度不能超过80")
    private String dataSourceName ="";
    //真实表名
    @Size(max=80, message = "[物理表名]长度不能超过80")
    private String realTablename="";
    //对应的数据协议 系统代码

    @Size(max=3, message = "[表数据来源的系统]长度不能超过80")
    private String codeTextTd;

    //对应新版数据定义的源应用系统m名称的1级
    private String parentCodeTextId;

    // 表协议
    @Size(max=40, message = "[资源标识]长度不能超过40")
    private String tableId="  ";
    // 目标协议对应厂商
    private String ownerFactory;
    private String ownerFactoryCode;
    //存储表状态
    private String storageTableStatus;
    //存储方式
    private String storageDataMode;
    //更新表类型 20200507 majia添加
    private Integer isActiveTable;
    //存储数据源
    private String storageDataSource;

    // 在调用修改表分级分类接口时，需要用到该字段，多个用英文逗号分隔
    //  数据组织分类的ids
    private String classIds="";
    // 数据来源分类的ids
    private String sourceClassIds="";
    // 一级二级拼接在一起，用 / 区分  数据组织   有些存在 3级分类  一级/二级/三级
    private String organizationClassify;
    // 一级二级拼接在一起，用 / 区分  数据来源
    private String sourceClassify;

    //在对应的输入输出表中定义输入输出的 guid
    private String oobjGuidStr;
    private String iobjGuidStr;

    //  对应的数据协议 系统代码的中文名称
    private String codeTextCh="";

    //  源表ID
    private String sourceId = " ";
    //  描述的信息
    @Size(max=200, message = "[表描述信息]长度不能超过200")
    private String objectMemo="";

    //  判断是否为流程
    private Boolean flow = false;

    // datatype 类型  20200309新增
    private Integer dataType;
    // 新增 6类标签 代码值用英文逗号分隔  20200318新增
    private String bodyTag1Val="";
    private String elementTag2Val="";
    private String objectDescTag3Val="";
    private String behaviorTag4Val="";
    private String relationShipTag5Val="";
    private String locationTag6Val="";
    private String bodyTag1Text="";
    private String elementTag2Text="";
    private String objectDescTag3Text="";
    private String behaviorTag4Text="";
    private String relationShipTag5Text="";
    private String locationTag6Text="";
    private String labels="";

    // 20201009 新增4个字段
    // 标准新增时间，默认sysdate  CREATETIME  yyyy-mm-dd hh24:mi:ss
    private String createTime;
    private Date createTimeRel;
    // 标准最新修改时间 UPDATETIME

    private String updateTime;
    private Date updateTimeRel;
    // 标准创用户名称  CREATOR  标准创用户名称，新建定义时登录用户信息，第三方调用页面时保存“源应用系统建设公司”信息
    @Size(max=100, message = "[创建人]长度不能超过100")
    private String creator="";
    // 标准最新修改用户名称 UPDATER  编辑操作时登录用户信息，第三方调用页面时保存“源应用系统建设公司”信息。
    @Size(max=100, message = "[更新人]长度不能超过100")
    private String updater="";

    /**
     * 登陆的用户id值
     */
    private String userId="";
    /**
     * 登陆的用户名
     */
    private String userName="";

    /**
     * 大版本
     */
    private String versions;

    /**
     * 小版本
     */
    private Integer version;

    /**
     * 标准表版本主键
     */
    private String objectVersion="";

    /**
     * 20210915 数据分级
     */
    private String dataLevel = "";

    /**
     * 20210915 数据分级中文名称
     */
    private String dataLevelCh = "";

    /**
     * 数据资源标签1
     */
    private String sjzybq1;

    /**
     * 数据资源标签2
     */
    private String sjzybq2;

    /**
     * 数据资源标签3
     */
    private String sjzybq3;

    /**
     * 数据资源标签4
     */
    private String sjzybq4;

    /**
     * 数据资源标签5
     */
    private String sjzybq5;

    /**
     * 数据资源标签6
     */
    private String sjzybq6;

    /**
     * 标准来源类型
     */
    private Integer standardType;

    /**
     * 数据组织一级分类
     */
    private String SJZZYJFLVALLUE;

    /**
     * 数据组织二级分类
     */
    private String SJZZEJFLVALUE;

    /**
     * 数据来源分类
     */
    private String SJZYLYLXVALUE;


}

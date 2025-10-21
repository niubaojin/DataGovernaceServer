package com.synway.datastandardmanager.entity.vo.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class DetectedTable implements Serializable {

    /**
     * 数据源ID
     */
    private String resId;

    /**
     * 探查ID,新增无，更新有
     */
    private String detectId;

    /**
     * 项目空间或ftp父目录
     */
    private String projectName;

    /**-----------------接入方式信息------------------------**/

    /**
     * 表/视图英文名/ftp当前目录/当前文件名
     */
    private String tableNameEN;

    /**
     * 表中文名
     */
    private String tableNameCN;

    /**
     * 0：全部 1：表 2：视图 3：文本类型/kafka/redis
     */
    private int tableType;

    //数据库类型
    private String resType;
    //数据源名称
    private String resName;

    //数据中心id
    private String centerId;
    //数据中心名称
    private String centerName;

    /**
     * 仓库用户
     */
    private String userName;

    /**
     * 密码密文
     */
    private String password;

    /**
     * 连接参数
     */
    private String jdbcUrl;

    /**
     * 文件系统相关 通配符
     */
    private String wildcard;

    /**
     * 递归子目录
     */
    private int isRecursion;

    /**
     * 提供数据格式
     */
    private String dataFormat;

    /**
     * 提供方式
     */
    private String provideType;

    /**--------------------接入服务信息---------------------**/

    /**
     * 提供人员
     */
    private String provider;

    /**
     * 提供厂商
     */
    private String provideUnit;

    /**
     * 提供人电话
     */
    private String provideTel;

    /**
     * 提供时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date provideTime;

    /**
     * 接入服务人
     */
    private String inceptor;

    /**
     * 接入人服务方
     */
    private String inceptUnit;

    /**
     * 接入人电话
     */
    private String inceptTel;

    /**
     * 接入时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date inceptTime;

    /**
     * 接入说明
     */
    private String inceptDesc;

    /**--------------------数据业务信息---------------------**/

    /**
     * 获取方式
     */
    private String inceptType;

    /**
     * 数据分级
     */
    private String dataLevel;

    /**
     * 行业分类
     */
    private String tradeClassify;

    /**
     * 业务号段
     */
    private String businessMark;

    /**
     * 事权单位
     */
    private String routineUnit;

    /**
     * 事权单位编码
     */
    private String routineCode;

    /**
     * 事权单位权联系人
     */
    private String routineLinkMan;

    /**
     * 事权单位权联系电话
     */
    private String routineTel;

    /**
     * 管理单位名称
     */
    private String manageUnit;

    /**
     * 管理单位代码
     */
    private String manageUnitCode;

    /**
     * 数据资源位置
     */
    private String resPostion;

    /**
     * 存储位置描述
     */
    private String postionDesc;

    /**
     * 应用系统名称
     */
    private String appName;

    /**
     * 应用系统编号
     */
    private String appCode;

    //应用系统一级编号
    private String parentAppCode;

    /**
     * 应用管理单位
     */
    private String appManageUnit;

    /**
     * 应用建设单位
     */
    private String appBuildUnit;

    /**
     * 来源分级
     */
    private String sourceClassify;

    /**
     * 来源协议编码
     */
    private String sourceCode;

    /**
     * 数据资源描述
     */
    private String resDesc;

    /**--------------------数据集规模探查---------------------**/

    /**
     * 数据存储记录数
     */
    private String recordCounts;

    /**
     * 数据存储记录数(单位)
     */
    private String recordUnit;

    /**
     * 增量数据存储记录数
     */
    private String incRecordCounts;

    /**
     * 增量数据存储记录数((单位))
     */
    private String incRecordUnit;

    /**
     * 当前存储数据物理大小(
     */
    private String physicalSize;

    /**
     * 数据存储总量单位
     */
    private String physicalUnit;

    /**
     * 增量数据存储总量
     */
    private String incPhysicalSize;

    /**
     * 增量数据存储总量单位
     */
    private String incPhysicalUnit;

    /**
     * 资源存储周期
     */
    private String storeCycle;

    /**
     * 资源存储周期单位
     */
    private String storeCycleUnit;

    /**
     * 资源更新周期
     */
    private String updateCycle;

    /**
     * 资源更新模式
     */
    private String updateType;

    /**
     * 资源更新描述
     */
    private String updateDesc;

    /**--------------------ds_project_table获取--------------------**/

    /**
     * 0：全部 1：非分区  2:分区
     */
    private int isPartitioned;

    /**
     * 最后表结构修改时间
     */
    private String lastDDLTime;

    /**
     * 最后数据操作时间
     */
    private String lastDMLTime;

    /**
     * 现场调研问题
     */
    private String detectQuestion;

    /**--------------------其他---------------------**/

    /**
     * 注册  1:未注册， 2：已注册
     */
    private int isRegistered;

    /**
     * 审批  1:未审批， 2：审批中  3：审批通过  4：审批失败
     */
    private int isApproved;

    /**
     * 创建时间，新增必无
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间,新增必无，更新必有
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 用户信息
     */
    private String detectUser ="";

    /**
     * 文件编码格式
     */
    private String encode="";

    /**
     * 序列化格式
     */
    private String serialFormat;

    /**
     * 字段分隔符
     */
    private String separator;

    /**
     * 记录分隔符
     */
    private String recordSeparator;

    /**
     * 分区数
     */
    private int partitionNum;

    /**
     * 副本数
     */
    private int replicasNum;

    /**
     * 探查类型，1:预探查(文件系统专用) 2:正式探查
     */
    private int detectedType;

    /**
     * 存储格式
     */
    private String storageFormat;

    /**
     * redis value格式（txt:文本;json:json）
     */
    private String valueFormat;

    /**
     * redis key值（1:取字段内容;2:固定常量）
     */
    private int keySource;

}

package com.synway.governace.pojo.largeScreen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 表格中的数据对象
 * @author wdw
 * @version 1.0
 * @date 2021/6/8 19:14
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LabelManageData implements Serializable {

    private static final long serialVersionUID = -2825993061767074226L;
    /**
     * 唯一值
     */
    private String id;
    /**
     * 标签名称
     */
    @NotNull(message = "【标签名称】不能为空")
    @Size(max=50,message = "【标签名称】长度不能超过50")
    private String labelName="";


    /**
     * 标签代码
     */
    @Size(max=20,message = "【标签代码】长度不能超过20")
    private String labelCode="";

    /**
     * 标签级别中文名
     */
    private String labelLevelStr="";
    /**
     * 标签级别 代码
     */
    @NotNull(message = "【标签类型代码】不能为空")
    private Integer labelLevel;


    /**
     * 标签所属分类代码
     */
    @Size(min = 2, max=10,message = "【常用组织分类代码】长度不能超过10")
//    @ExcelProperty("常用组织分类代码")
    private String classId="";

    /**
     * 标签所属分类中文名
     */
    private String classIdStr="";

    /**
     * 标签说明
     */
    @Size(max=500,message = "【标签说明】长度不能超过500")
    private String labelDescribe="";

    /**
     * 关联的表数据量
     */
    private Integer tableCount;

    /**
     * 修订人
     */
    @NotNull(message = "【修订人】不能为空")
    @Size(max=100,message = "【修订人】长度不能超过100")
    private String author="";

    /**
     * 修订时间
     */
    private Date modDate;


    private String modDateStr="";



}

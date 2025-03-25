package com.synway.datastandardmanager.pojo.synlteelement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

/**
 * 在页面上展示的数据要素
 *
 * @author obito
 * @version 1.0
 * @date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynlteElementVO implements Serializable {
    private static final long serialVersionUID = 4245613712692796413L;

    private int num;

    /**
     * 关联主体
     * 1.人员 2.物 3.组织 4.地 5.事 6.时间 7.信息
     */
    @ListValue(vals = {"1", "2", "3", "4", "5", "6", "7"},
            message = "[主体]的值只能为1/2/3/4/5/6/7")
    @NotNull(message = "【关联主体】不能为空")
    @Size(max = 50, message = "【关联主体】长度不能超过50")
    private String elementObject;

    /**
     * 主体信息翻译
     */
    private String elementObjectType;


    /**
     * 是否内部要素标识符 (要素来源)
     * 1.是(公司内部定义) 2.否(客户现场定义)
     */
    @NotNull(message = "【是否内标标识符】不能为空")
    @Size(max = 4, message = "【是否内标标识符】长度不能超过4")
    private String isElement;

    /**
     * 要素来源翻译
     */
    private String isElementType;

    /**
     * 要素名称
     */
    @NotNull(message = "【要素名称】不能为空")
    @Size(max = 200, message = "【要素名称】长度不能超过200")
    private String elementChname;

    /**
     * 要素标识符
     */
    @NotNull(message = "【主键ID】不能为空")
    @Size(max = 50, message = "【主键ID】长度不能超过50")
    private String elementCode;

    /**
     * 规则
     */
    private String elementRule;

    /**
     * 修订人
     */
    @Size(max = 50, message = "【修订人】长度不能超过50")
    private String author;

    /**
     * 修改\新增时间 页面不需要展示 用来排序
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modDate;

    /**
     * 生成方式
     * 1：数据元编码
     * 2：数据源编码+Z字典码值
     */
    private Integer createMode;

    private String createModeType;

    /**
     * 语义
     */
    @Size(max = 50, message = "【语义】长度不能超过50")
    private String sameId = "";
}

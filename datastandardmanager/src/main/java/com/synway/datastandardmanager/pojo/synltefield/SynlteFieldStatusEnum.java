package com.synway.datastandardmanager.pojo.synltefield;

import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerCode;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据元定义 状态的 枚举类
 * 状态指数据元在其注册的全生存期（即生命周期）内所处的状态。数据元在其注册的全部生存期内存在七种状态：
 * @author wangdongwei
 * @date 2021/7/22 15:13
 */
public enum SynlteFieldStatusEnum {
    /**
     * 已经创建数据元并提交。提交新的数据需求和对现行数据元的修改建议都从本状态开始。
     */
    YS("01","新建"),
    /**
     * 经过数据元注册机构形式审查后，等待技术审查
     */
    CA("02","草案"),
    /**
     * 经过技术初审后，正在征求意见中。
     */
    ZQYJ("03","征求意见"),
    /**
     * 经过技术终审后，等待审批
     */
    BP("04","报批"),
    /**
     * 新增或变更的数据元，经过标准化过程的协调和审查，已得到数据元管理机构批准
     */
    BZ("05","发布"),
    /**
     * 在新增或变更数据元的流程中，在任何一个阶段未能通过审查或批准
     */
    WPZ("06","未批准"),
    /**
     * 不再需要其支持信息需求，经数据元管理机构批准，该数据元的内容即将从标准中删去
     */
    FZ("07","废止");
    private String id;
    private String value;

    SynlteFieldStatusEnum(String id, String value){
        this.id = id;
        this.value = value;
    }

    public static String getValueById(String id){
        if(StringUtils.isBlank(id)){
            return "";
        }
        for (SynlteFieldStatusEnum element : values()) {
            if(element.id.equalsIgnoreCase(id)){
                return element.value;
            }
        }
        return id;
    }

}

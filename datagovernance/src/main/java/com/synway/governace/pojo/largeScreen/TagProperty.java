package com.synway.governace.pojo.largeScreen;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 标签个数、标签加工的数据量、标签被使用热度排行信息
 * @author wdw
 * @version 1.0
 * @date 2021/6/5 18:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagProperty implements Serializable {

    private static final long serialVersionUID = 5043975962080511856L;
    /**
     *  标签种类
     */
    private Integer tagCount;

    /**
     * 标签数据总量
     */
    private Long dataCount;

    /**
     * 使用热度
     */
    private List<TagUses> tagUses;



    @Data
    public static class TagUses{
        /**
         * 标签标识
         */
        private String code;
        /**
         * 标签分类编号
         */
        private String typeId;
        /**
         * 标签名称
         */
        private String name;
        /**
         *标签使用次数
         */
        private Long tagUseCount;

    }
}

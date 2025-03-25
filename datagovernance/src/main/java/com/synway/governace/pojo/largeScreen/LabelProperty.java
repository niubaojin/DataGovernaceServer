package com.synway.governace.pojo.largeScreen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/4/25 14:45
 */
public class LabelProperty implements Serializable {

    /**
     * 标签种类  有多少种标签
     */
    private long labelTypes = 0;


    /**
     * 标签对应表的数据量 具体值
     */
    private long labelTableNumbers = 0;

    /**
     * 标签对应表的数据量 页面展示值
     */
    private String labelTableNumbersStr = "0条";


    /**
     * 标签的使用热度 目前是 top 4
     */
    private List<UseHeat> labelUseHeatList = new ArrayList<>();

    public long getLabelTypes() {
        return labelTypes;
    }

    public void setLabelTypes(long labelTypes) {
        this.labelTypes = labelTypes;
    }

    public long getLabelTableNumbers() {
        return labelTableNumbers;
    }

    public void setLabelTableNumbers(long labelTableNumbers) {
        this.labelTableNumbers = labelTableNumbers;
    }

    public String getLabelTableNumbersStr() {
        return labelTableNumbersStr;
    }

    public void setLabelTableNumbersStr(String labelTableNumbersStr) {
        this.labelTableNumbersStr = labelTableNumbersStr;
    }

    public List<UseHeat> getLabelUseHeatList() {
        return labelUseHeatList;
    }

    public void setLabelUseHeatList(List<UseHeat> labelUseHeatList) {
        this.labelUseHeatList = labelUseHeatList;
    }

    public static class UseHeat{

        /**
         *  标签名称
         */
        private String labelName;

        /**
         * 标签的数据量
         */
        private long labelTableNumbers;

        public String getLabelName() {
            return labelName;
        }

        public void setLabelName(String labelName) {
            this.labelName = labelName;
        }

        public long getLabelTableNumbers() {
            return labelTableNumbers;
        }

        public void setLabelTableNumbers(long labelTableNumbers) {
            this.labelTableNumbers = labelTableNumbers;
        }
    }

}

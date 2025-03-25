package com.synway.property.pojo.dataResource;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class ResourceOverView {
    //数据源id,唯一
    private String resId;
    //数据总数
    private long dataSum=0;
    //探查数
    private long detectNum=0;
    //对标数
    private long standNum=0;
    //注册数
    private long registerNum=0;
    //探查率
    private double detectRate;
    //对标率
    private double standRate=0.0;
    //注册率
    private double registerRate=0.0;
    //容量(MB)
    private long totalSpace=0;
    //存储(MB)
    private long usedSpace=0;
    //占用率(MB)
    private double employRate=0.0;

    public double getDetectRate() {
        DecimalFormat format = new DecimalFormat("0.0000");
        String rate = format.format(detectRate);
        return Double.parseDouble(rate);
    }

    public double getStandRate() {
        DecimalFormat format = new DecimalFormat("0.0000");
        String rate = format.format(standRate);
        return Double.parseDouble(rate);
    }

    public double getRegisterRate() {
        DecimalFormat format = new DecimalFormat("0.0000");
        String rate = format.format(registerRate);
        return Double.parseDouble(rate);
    }

    public double getEmployRate() {
        DecimalFormat format = new DecimalFormat("0.0000");
        String rate = format.format(employRate);
        return Double.parseDouble(rate);
    }
}

package com.synway.property.pojo.homepage;

import lombok.Data;

/**
 *
 *
 * @author majia
 * @date 2020/06/02
 */
@Data
public class DataBaseState {
    private String name;            //名字
    private String liveTableRote;   //活表率
    private String usedCapacity;    //已使用物理储存
    private String bareCapacity;    //总物理储存
    private String tableCount;      //总记录数
    private String tableSum;        //表数量
}

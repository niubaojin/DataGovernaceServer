package com.synway.dataoperations.pojo.dataPiledMonitor;

import com.synway.dataoperations.pojo.ClassifyInfoTree;
import lombok.Data;

import java.util.List;

@Data
public class TreeAndCartPiled {
    private Integer dataClassSum;               // 数据种类
    private Integer normalDataClassSum;         // 正常数据种类
    private Integer piledDataClassSum;          // 堆积数据种类
    private Integer abnormalDataClassSum;       // 异常数据种类
    private List<ClassifyInfoTree> leftTree;    // 左侧树
}

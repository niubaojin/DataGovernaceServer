package com.synway.governace.pojo.largeScreen.ForXJ;

import com.synway.governace.pojo.largeScreen.ForXJ.DataInfo;
import lombok.Data;

import java.util.List;

/**
 * 数据服务信息
 * @author Administrator
 */
@Data
public class DataServiceInfo {

    /**
     * 数据列表
     */
    List<DataInfo> data;

    /**
     * 分类名称
     */
    private String name;
}

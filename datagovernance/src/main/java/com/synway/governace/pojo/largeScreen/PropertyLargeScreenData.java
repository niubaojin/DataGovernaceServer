package com.synway.governace.pojo.largeScreen;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据大屏上页面上用到的对象信息
 * @author wangdongwei
 * @date 2021/4/25 13:32
 */
@Data
@ToString
public class PropertyLargeScreenData implements Serializable {


    private static final long serialVersionUID = -3382897966123608000L;
    /**
     * 字段分类的相关数据
     */
    private List<FiledClassData> filedClassDataList;


    /**
     * 资产使用情况
     */
    private PropertyUsage propertyUsage;

    /**
     * 对外共享的相关数据
     */
    private PublishInfo publishInfo;

    /**
     * 原始库的日接入情况
     * 按照数据量排序倒叙
     */
    private List<StandardLabelData> originalDataList;

    /**
     * 资源库资产情况
     * 按照数据量排序倒叙
     */
    private List<StandardLabelData> resourceLibraryList;

    /**
     * 主题库资产情况
     * 按照数据量排序倒叙
     */
    private List<StandardLabelData> themeLibraryList;


    /**
     * 标签库资产情况
     */
    private LabelProperty labelProperty;


    /**
     * 数据总资产
     */
    private TotalDataProperty totalDataProperty;


    /**
     *  数据共享
     *  地图数据 代码 9
     */
    private List<DataShareMap> dataShareMaps;


    /**
     *  运营商数据日接入情况 (技侦版本)
     *  只是 展示具体的数据
     */
    private List<OperatorData> operatorDataList;

    /**
     *  运营商 近7天的数据量 (科信版本)
     *   这个是 折线图 代码 10
     */
    private Map<String,List<Object>> originalDataMap;


    //   新疆资产大屏特有的字段----------------------------------
    /**
     * 原始库资产中  标准表表的 表数据量
     */
    private long originalTableCount;

    /**
     * 原始业务库总种类数据量
     */
    private long originalBusinessTableCount;

    /**
     * 资源库资产情况 表数据量
     */
    private long resourceLibraryTableCount;

    /**
     * 主题库资产情况
     */
    private long themeLibraryTableCount;

    /**
     * 原始业务库资产情况 代码 11
     */
    private List<StandardLabelData> originalBusinessDataList;

    /**
     * 拥有分类的标准表数据量
     */
    private int classifyTableCount;

    /**
     * 大存储量数据排行榜
     */
    private List<LargeStorageData> largeStorageDataList;

    /**
     * 普元信息总线的相关数据
     */
    private PyPublishInfo pyPublishInfo;

    // 数据地图资产
    private DataMapAssets dataMapAssets;

    public PropertyLargeScreenData(){
        this.propertyUsage = new PropertyUsage();
        this.publishInfo = new PublishInfo();
        this.totalDataProperty = new TotalDataProperty();
        this.labelProperty = new LabelProperty();
    }


}

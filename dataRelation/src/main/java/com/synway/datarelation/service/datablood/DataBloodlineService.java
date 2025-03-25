package com.synway.datarelation.service.datablood;


import com.synway.common.bean.ServerResponse;
import com.synway.datarelation.pojo.common.TreeNode;
import com.synway.datarelation.pojo.databloodline.*;

import java.util.List;

/**
 *  数据血缘的相关接口
 * @author wangdongwei
 */
public interface DataBloodlineService {

    /**
     * 左侧页面的 搜索按钮点击事件
     * @param dataBloodlineQueryParams  查询事件
     * @param dataBloodlineQueryParams
     * @return
     * @throws Exception
     */
    public ServerResponse<List<TreeNode>> queryDataBloodlineTable(
            DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception;

    /**
     * 获取单个流程的数据
     * @param queryData
     * @param queryType
     * @param queryNodeId
     * @param nodeShow
     * @return
     * @throws Exception
     */
    public DataBloodlineNode getOneBloodlineNodeLink(QueryDataBloodlineTable queryData, String queryType,
                                                     String queryNodeId, Boolean nodeShow) throws Exception;


    /**
     * 数据大屏那边获取数据量
     * @return
     */
    default public AllBloodCount getAllBloodCount(){
        return null;
    }


}

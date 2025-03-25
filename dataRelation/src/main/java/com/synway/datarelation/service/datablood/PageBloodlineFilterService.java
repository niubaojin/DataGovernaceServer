package com.synway.datarelation.service.datablood;

import com.synway.datarelation.pojo.databloodline.BloodlineFilterPage;
import com.synway.datarelation.pojo.databloodline.DataBloodlineNode;

/**
 * 页面筛选的相关信息
 * @author wangdongwei
 */
public interface PageBloodlineFilterService {


    /**
     * 页面筛选之后的相关信息
     * @param bloodlineFilterPage
     * @return
     */
    DataBloodlineNode PageBloodlineFilter(BloodlineFilterPage bloodlineFilterPage);

    /**
     * 页面上隐藏应用血缘的节点信息，加工血缘的节点直接连接应用血缘模型的节点
     * @param data
     * @return
     */
    DataBloodlineNode pageHiddenNode(DataBloodlineNode data);
}

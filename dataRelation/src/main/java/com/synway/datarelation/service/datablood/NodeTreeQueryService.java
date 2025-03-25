package com.synway.datarelation.service.datablood;

import com.synway.datarelation.pojo.common.TreeNode;
import com.synway.datarelation.pojo.databloodline.DataBloodlineQueryParams;

/**
 * 主要查询左侧树的节点信息
 * @author wangdongwei
 */
public interface NodeTreeQueryService {

    /**
     *   查询各个节点类型的返回数据
     * @param dataBloodlineQueryParams
     * @return
     * @throws Exception
     */
    TreeNode getTreeNodeInfo(DataBloodlineQueryParams dataBloodlineQueryParams) throws Exception;

}

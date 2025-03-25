package com.synway.governace.pojo.navbar;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 20210817导航栏的相关地址
 * *{"children": [ {  "component": "page/dataStandardManager/dataElementManage/index",  "meta": {"icon": "dataElementManage","title": "数据元管理"  },
 *  *  "name": "dataElementManage",  "path": "dataElementManage" }, {  "component": "page/dataStandardManager/dataDefinitionManagement/index",
 *  *  "meta": {"icon": "dataDefinitionManagement","title": "数据集管理"  },  "name": "dataDefinitionManagement",  "path": "dataDefinitionManagement" },
 *  *  {  "component": "page/dataStandardManager/determinerManage/index",  "meta": {"icon": "determinerManage","title": "限定词管理"  },
 *  *  "name": "determinerManage",  "path": "determinerManage" }, {  "component": "page/dataStandardManager/nationalCodeTableMonitor",
 *  *  "meta": {"icon": "nationalCodeTableMonitor","title": "数据字典管理"  },  "name": "nationalCodeTableMonitor",
 *  *  "path": "nationalCodeTableMonitor" }, {  "component": "page/dataStandardManager/synlteElementManage/index",
 *  *  "meta": {"icon": "synlteElementManage","title": "数据要素管理"  },  "name": "synlteElementManage",  "path": "synlteElementManage" },
 *  *  {  "component": "page/dataStandardManager/resourceTagManage/index",  "meta": {"icon": "resourceTagManage","title": "资源标签管理"  },
 *  *  "name": "resourceTagManage",  "path": "resourceTagManage" }, {  "component": "page/dataStandardManager/SemanticTableManageHtml",
 *  *  "meta": {"icon": "semanticTableManageHtml","title": "数据语义管理"  },  "name": "semanticTableManageHtml",  "path": "semanticTableManageHtml" }],
 *  *  "component": "Layout","meta": { "icon": "dataStandardManager", "title": "数据标准"},"name": "dataStandardManager"
 *  *  ,"path": "/dataStandardManager"  }
 * @author wangdongwei
 * @date 2021/8/17 15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayerNavBar implements Serializable {
    private static final long serialVersionUID = -6531366253311437918L;
    /**
     * 导航栏节点的的地址信息
     */
    private String path;

    /**
     * 导航栏节点的名称
     */
    private String name;

    /**
     * 节点类型  Layout/404 为根节点
     * 其它的具体地址信息为具体的导航栏地址
     */
    private String component;

    private Meta meta;

    /**
     * 子节点的相关信息
     */
    private List<LayerNavBar> children;

    /**
     * 部分 数据需要参数 直接从数据库中字段 nav_blank
     * 如果为0 或者为空 ，该值也为空，如果存在别的值直接赋值就可以
     */
    private JSONObject query ;

    /**
     * 重定向地址
     */
    private String redirect;


    private boolean hidden = false;


    @Data
    public static class Meta{
        private String icon;
        private String title;

    }
}



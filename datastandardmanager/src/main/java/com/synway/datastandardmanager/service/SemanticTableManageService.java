package com.synway.datastandardmanager.service;

import com.github.pagehelper.PageInfo;
import com.synway.datastandardmanager.pojo.Sameword;
import com.synway.datastandardmanager.pojo.Synltefield;

import java.util.List;
import java.util.Map;

/**
 * @author wangdongwei
 */
public interface SemanticTableManageService {

    /**
     * 数据语义管理页面表数据
     * @param sameId 语义id
     * @param word 语义英文名称
     * @param wordName 语义中文名称
     * @return
     */
	public List<Sameword> findByCondition(String sameId, String wordName, String word) ;

    /**
     * 添加一种语义信息
     * @param sameword
     * @return
     * @throws Exception
     */
	String addOneSemanticManage(Sameword sameword) throws Exception;

    /**
     * 删除一种语义信息
     * @param sameword
     * @return
     * @throws Exception
     */
	String delOneSemanticManage(Sameword sameword) throws Exception;

    /**
     * 根据语义id获取对应的关联元素集信息
     * @param pageIndex
     * @param pageSize
     * @param sameId
     * @return
     */
    PageInfo<Synltefield> getMetadataDefineTableBySameidService(int pageIndex,int pageSize,String sameId);

    /**
     * 根据上传的excel文件中获取到语义数据导入到数据库中
     * @param resultList
     * @return
     * @throws Exception
     */
    String uploadSemanticFileService(List<Map> resultList) throws Exception;
	

}

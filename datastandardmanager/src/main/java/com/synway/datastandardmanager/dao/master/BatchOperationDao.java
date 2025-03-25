package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.batchoperation.ObjectClassifyEditPojo;
import com.synway.datastandardmanager.pojo.batchoperation.ObjectFieldEditPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 批量修改的相关操作
 * @author wangdongwei
 * @date 2021/7/15 14:12
 */
@Mapper
@Repository
public interface BatchOperationDao {

    /**
     * 修改 public_data_info 里面的使用状态
     * @param tableIdList  大写的表协议id
     * @param status  0 1
     * @return
     */
    int updatePublicDataInfoStatus(@Param("list")List<String> tableIdList,
                                   @Param("status") String status);

    /**
     * 修改 object 里面的使用状态
     * @param tableIdList  大写的表协议id
     * @param status   -1：   1
     * @return
     */
    int updateObjectStatus(@Param("list")List<String> tableIdList,
                                   @Param("status") String status);

    /**
     * 更新object 里面的分类信息
     * @param editPojo
     * @return
     */
    int updateObjectClassifyAndSourceIfy(ObjectClassifyEditPojo editPojo);


    /**
     *  更新
     * @param editPojo
     * @return
     */
    int updateFieldResourceInfoClassify(ObjectFieldEditPojo editPojo);


    /**
     * 更新objectfield指定字段表的分类信息
     * @param editPojo
     * @return
     */
    int updateObjectClassify(ObjectFieldEditPojo editPojo);

    /**
     * 更新字段的分类
     * @param editPojo
     * @return
     */
    int updateSynlteField(ObjectFieldEditPojo editPojo);

}

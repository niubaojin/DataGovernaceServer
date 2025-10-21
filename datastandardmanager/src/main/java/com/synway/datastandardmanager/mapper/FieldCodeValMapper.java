package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;
import com.synway.datastandardmanager.entity.vo.KeyValueVO;
import com.synway.datastandardmanager.interceptor.AuthorControl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FieldCodeValMapper extends BaseMapper<FieldCodeValEntity> {

    List<KeyValueVO> queryLabelValueByCodeId(@Param("codeId") String codeId);

    int uploadCodeValXlsFileDao(@Param("rcList") List<FieldCodeValEntity> allFieldCodeValList);

    /**
     * 获取 一级分类信息
     */
    @AuthorControl(tableNames ={"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<KeyValueVO> getPrimaryClassifyData(@Param("mainClassify") String mainClassify);

    /**
     * 获取二级分类信息
     */
    @AuthorControl(tableNames ={"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<KeyValueVO> getSecondaryClassifyData(@Param("mainClassify") String mainClassify,
                                              @Param("primaryClassifyCode") String primaryClassifyCode,
                                              @Param("primaryClassifySecondCode") String primaryClassifySecondCode);

    /**
     * 获取三级分类信息
     */
    @AuthorControl(tableNames = {"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<KeyValueVO> getThreeClassifyData(@Param("primaryClassifyCode") String primaryClassifyCode,
                                          @Param("primaryClassifySecondCode") String secondCodeId);

    /**
     * 获取字段分类的码表信息
     */
    List<FieldCodeValEntity> getAllFieldClassList();

}

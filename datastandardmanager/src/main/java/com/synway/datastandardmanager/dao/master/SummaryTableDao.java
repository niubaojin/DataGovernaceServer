package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.interceptor.AuthorControl;
import com.synway.datastandardmanager.pojo.AuthorizedUser;
import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryObjectTable;
import com.synway.datastandardmanager.pojo.summaryobjectpage.SummaryQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangdongwei
 * @ClassName SummaryTableServiceImplDao
 * @description TODO
 * @date 2020/11/30 10:10
 */
@Mapper
@Repository
public interface SummaryTableDao {

    /**
     * 查询汇总信息的表
     * @param
     * @return
     */
    @AuthorControl(tableNames ={"classify_interface_all_date","synlte.\"OBJECT\"","synlte.object"},columnNames = {"sjxjbm","tableid","tableid"})
    List<SummaryObjectTable> searchSummaryTableByParams(@Param("summaryQueryParams") SummaryQueryParams summaryQueryParams,
                                                        @Param("dataOrganizationClassifyList") List<String> dataOrganizationClassifyList,
                                                        @Param("dataSourceClassifyList") List<String> dataSourceClassifyList,
                                                        @Param("dataLabelClassifyList1") List<String> dataLabelClassifyList1,
                                                        @Param("dataLabelClassifyList2") List<String> dataLabelClassifyList2,
                                                        @Param("dataLabelClassifyList3") List<String> dataLabelClassifyList3,
                                                        @Param("dataLabelClassifyList4") List<String> dataLabelClassifyList4,
                                                        @Param("dataLabelClassifyList5") List<String> dataLabelClassifyList5,
                                                        @Param("dataLabelClassifyList6") List<String> dataLabelClassifyList6,
                                                        @Param("useTageValueList") List<String> useTageValueList,
                                                        @Param("dataObjectClassifyList") List<String> dataObjectClassifyList);


    /**
     * 获取 一级分类信息
     * @param mainClassify
     * @return
     */
    @AuthorControl(tableNames ={"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<PageSelectOneValue> getPrimaryClassifyData(@Param("mainClassify") String mainClassify);


    /**
     * 获取二级分类信息
     * @param mainClassify
     * @param primaryClassifyCode
     * @return
     */
    @AuthorControl(tableNames ={"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<PageSelectOneValue> getSecondaryClassifyData(@Param("mainClassify") String mainClassify,
                                                      @Param("primaryClassifyCode") String primaryClassifyCode,
                                                      @Param("primaryClassifySecondCode") String primaryClassifySecondCode );

    /**
     * 获取三级分类信息
     * @param primaryClassifyCode
     * @param secondCodeId
     * @return
     */
    @AuthorControl(tableNames = {"classify_interface_all_date"},columnNames = {"sjxjbm"})
    List<PageSelectOneValue> getThreeClassifyData(@Param("primaryClassifyCode")String primaryClassifyCode,
                                                  @Param("primaryClassifySecondCode") String secondCodeId);

    /**
     * 获取资源状态
     * @return
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<PageSelectOneValue>  getResourceStatus();

    /**
     *  搜索提示内容
     * @param searchValue
     * @return
     */
    @AuthorControl(tableNames ={"synlte.object","synlte.\"OBJECT\""},columnNames = {"tableid","tableid"})
    List<String> queryConditionSuggestion(@Param("searchValue") String searchValue);


//    /**
//     * 获取所有的数据
//     * @return
//     */
//    @AuthorControl(tableNames ={"classify_interface_all_date"},columnNames = {"sjxjbm"})
//    List<SummaryObjectTable> searchAllData();
}

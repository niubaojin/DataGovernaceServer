package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.LayuiClassifyPojo;
import com.synway.datastandardmanager.pojo.OneSuggestValue;
import com.synway.datastandardmanager.pojo.synlteelement.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */

@Mapper
@Repository
public interface SynlteElementDao {

    /**
     * 表格查询相关的数据
     * @param parameter
     * @return
     */
    List<SynlteElementVO> searchElementTable(SynlteElementParameter parameter);

    /**
     * 输入搜索框要素名称提示选择框
     * @param searchName
     * @return
     */
    List<String> searchElementChname(@Param("searchName") String searchName);

    /**
     * 根据id获取数据量
     * @param elementCode 数据要素标识符
     * @return
     */
    int findCountByElementCode(@Param("elementCode")String elementCode);

    /**
     * 根据id查询单个数据要素
     * @param elementCode 数据要素标识符
     * @return
     */
    SynlteElement searchOneDataByCode(@Param("elementCode")String elementCode);

    /**
     *  插入要素
     * @param data
     * @return
     */
    int addOneElement(SynlteElement data);

    /**
     *  根据元素标识符更新数据要素
     * @param data
     * @return
     */
    int updateElement(SynlteElementVO data);

    /**
     * 判断该id是否已经存在
     * @param elementCode
     * @return
     */
    int getCountById(@Param("elementCode")String elementCode);

    /**
     * 根据id删除该要素
     * @param elementCode
     * @return
     */
    int deleteCountById(@Param("elementCode")String elementCode);

    /**
     * 左侧树 查询数据要素关联主体
     * @return
     */
    List<OneSuggestValue> selectElementObject();

    /**
     * 生成方式第一行数据元查询 过滤"wb"开头
     * @param searchName 搜索关键字
     * @return
     */
    List<SelectField> searchField(@Param("searchName") String searchName);

    /**
     * 生成方式第二行数据元查询 过滤codeId为空的
     * @param searchName
     * @return
     */
    List<SelectField> searchSecondField(@Param("searchName") String searchName);

    /**
     * 第二行的第二个下拉框里的内容是根据第二行第一个下拉框选择的数据元的codeid获取的
     * @param codeId
     * @return
     */
    List<SelectField> searchAnotherField(@Param("codeId") String codeId);

    /**
     * 验证要素是否被标准使用
     * @param elementCode
     * @return
     */
    int checkIsRelevance(@Param("elementCode")String elementCode);

    /**
     * 新增时主体下拉列表
     * @return
     */
    List<SelectField> selectObjectList();

    /**
     * 新增时语义查找
     * @param dataElementId
     * @return
     */
    String setSameId(@Param("fieldId") String dataElementId);

    /**
     * 查询数据来源
     * @return
     */
    List<String> searchIsElement();

    /**
     * 根据sameId查询对应的语义类型
     * @param sameId
     * @return
     */
    String searchSameWord(@Param("sameId") String sameId);


    /**
     * 数据定义页面筛选条件
     * @return
     */
    List<SelectField> searchElementTotal();


    /**
     * 数据定义页面二级列表筛选条件
     * @param elementObject
     * @return
     */
    List<LayuiClassifyPojo> searchSecondElementName(@Param("elementObject") String elementObject);

    /**
     * 通过id查找数据要素中文名称
     * @param elementCode
     * @return
     */
    String searchElementNameById(@Param("elementCode")String elementCode);

}

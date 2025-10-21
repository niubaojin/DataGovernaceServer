package com.synway.datastandardmanager.entity.dto;

import com.synway.datastandardmanager.entity.pojo.SynlteFieldEntity;
import com.synway.datastandardmanager.valid.ListValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 数据元的查询参数
 * @author wangdongwei
 * @date 2021/7/22 11:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSynlteFieldDTO implements Serializable {
    private static final long serialVersionUID = 4159691268578534863L;

    //查询内容 支持唯一编码检索、内部标识符、中文名、英文名列关键字搜索
    private String searchName;

    //字段分类的筛选内容
    private List<String> fieldClassList = null;

    //安全级别分类代码值
    private List<String> secretClassList= null;

    //状态代码值的筛选
    private List<String> statusFilterList= null;

//    //这个参数不需要传递
//    private boolean fieldClassIsNull = false;
//    //这个参数不需要传递
//    private boolean secretClassIsNull = false;
//    //这个参数不需要传递
//    private boolean statusIsNull = false;

    //排序字段名称
    private String sort;

    //排序的相关参数 字段顺序  desc / asc
    @ListValue(vals = {"desc","asc"},message = "[字段顺序]的值只能为[desc/asc]")
    private String sortOrder = "desc";

    //分页当前页数
    private Integer pageIndex;

    //分页每页条数
    private Integer pageSize;

    //选中的数据元条数
    private List<SynlteFieldEntity> synlteFieldObjectList;

}

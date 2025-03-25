package com.synway.datastandardmanager.dao.master;

import com.synway.datastandardmanager.pojo.PageSelectOneValue;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerParameter;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminerVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author wangdongwei
 * @date 2021/7/16 10:44
 */
@Mapper
@Repository
public interface FieldDeterminerDao {

    /**
     * 表格查询相关的数据
     * @param parameter
     * @return
     */
    List<FieldDeterminer>  searchTable(FieldDeterminerParameter parameter);


    /**
     * 获取所有的表信息
     * @return
     */
    List<FieldDeterminer>  searchAllTable();


    /**
     * 根据id获取数据量
     * @param id
     * @return
     */
    int findCountByDeterminerId(@Param("id")String id);

    /**
     * 获取数据库中符合前缀的所有 限定词标示符
     * @param name  中文拼音的首字母
     * @return
     */
    List<String> findAllDBNameList(@Param("name")String name);

    /**
     *  插入数据
     * @param data
     * @return
     */
    int addOneData(FieldDeterminer data);

    /**
     * 更新数据
     * @param data
     * @return
     */
    int upOneData(FieldDeterminer data);


    /**
     * 获取 限定词id 和 限定词中文名称 对应的数据是否存在
     * 不存在表示中文名称已经被修改
     * @param id
     * @param chinesName
     * @return
     */
    int getCountByIdAndChinesName(@Param("id")String id,
                                  @Param("chinesName")String chinesName);


    /**
     * 判断该id是否已经存在
     * @param id
     * @return
     */
    int getCountById(@Param("id")String id);

    /**
     * 更新数据
     * @param id
     * @param state
     * @param modDate
     * @return
     */
    int updateDeterminerState(@Param("id")String id,
                              @Param("state")String state,
                              @Param("modDate")String modDate,
                              @Param("releaseDate")Integer releaseDate);

    /**
     * 检查参数是否正确
     * @param id
     * @param modDate
     * @return
     */
    int getCountByIdModDate(@Param("id")String id,
                            @Param("modDate")String modDate);

    /**
     * 检查数据是否已经被使用
     * @param determinerID
     * @return
     */
    int checkIsDeactivate(@Param("id")String determinerID);

    /**
     * 获取所有的限定词内部编码
     * @return
     */
    List<String> getDeterminerIdList();

    /**
     * 通过Id获取限定词
     * @param id
     * @return
     */
    FieldDeterminer getFieldDeterminerById(@Param("id") String id);


    /**
     * 将限定词保存至备份表
     * @param data
     * @return
     */
    int saveOneOldData(FieldDeterminer data);

    /**
     * 将限定词保存至备份表
     * @param fieldDeterminerVersion
     * @return
     */
    int saveFieldDeterminerVersion(FieldDeterminerVersion fieldDeterminerVersion);

    /**
     * 查询状态为05的限定词名称列表
     * @return
     */
    List<PageSelectOneValue> searchFieldDeterminerNameList(@Param("searchName") String searchName);

//    /**
//     * 根据限定词Id查询限定词名称
//     * @param determinerId
//     * @return
//     */
//    String getDeterminerNameById(@Param("determinerId")String determinerId);


}

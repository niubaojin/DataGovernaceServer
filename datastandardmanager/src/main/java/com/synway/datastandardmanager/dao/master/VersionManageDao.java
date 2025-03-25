package com.synway.datastandardmanager.dao.master;



import com.synway.datastandardmanager.pojo.FilterObject;
import com.synway.datastandardmanager.pojo.ObjectField;
import com.synway.datastandardmanager.pojo.ObjectPojo;
import com.synway.datastandardmanager.pojo.ObjectPojoTable;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.versionManage.FieldDeterminerVersionVo;
import com.synway.datastandardmanager.pojo.versionManage.ObjectVersionVo;
import com.synway.datastandardmanager.pojo.versionManage.SynlteFieldVersionVo;
import com.synway.datastandardmanager.pojo.versionManage.VersionManageParameter;
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
public interface VersionManageDao {

    /**
     * 表格查询object_version的数据
     * @param parameter
     * @return
     */
    List<ObjectVersionVo> searchObjectVersionTable(VersionManageParameter parameter);

    /**
     * 表格查询数据元_version的数据
     * @param parameter
     * @return
     */
    List<SynlteFieldVersionVo> searchSynlteFieldVersionTable(VersionManageParameter parameter);

    /**
     * 表格查询限定词_version的数据
     * @param parameter
     * @return
     */
    List<FieldDeterminerVersionVo> searchFieldDeterminerVersionTable(VersionManageParameter parameter);

    /**
     * 查询object的历史数据
     * @param objectVersionVo
     * @return
     */
    ObjectPojo searchOldObjectById(ObjectVersionVo objectVersionVo);

    /**
     * 根据ObjectId查出标准表字段历史信息
     * @param objectId
     * @return
     */
    List<ObjectField> searchOldField(@Param("objectId") String objectId);

    /**
     * 查询数据元的历史数据
     * @param synlteFieldVersionVo
     * @return
     */
    SynlteFieldObject searchOldSynlteField(SynlteFieldVersionVo synlteFieldVersionVo);

    /**
     * 查询限定词的历史数据
     * @param fieldDeterminerVersionVo
     * @return
     */
    FieldDeterminer searchOldFieldDeterminer(FieldDeterminerVersionVo fieldDeterminerVersionVo);

    /**
     * 大版本号筛选值
     * @param table
     * @return
     */
    List<FilterObject> searchVersionsList(@Param("table")String table);

    /**
     * 修订人筛选值
     *  @param table
     * @return
     */
    List<FilterObject> searchAuthorList(@Param("table")String table);
}

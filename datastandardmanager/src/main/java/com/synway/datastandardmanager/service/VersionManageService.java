package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.pojo.StandardObjectManage;
import com.synway.datastandardmanager.pojo.fielddeterminermanage.FieldDeterminer;
import com.synway.datastandardmanager.pojo.synltefield.SynlteFieldObject;
import com.synway.datastandardmanager.pojo.versionManage.*;
import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public interface VersionManageService {

    /**
     * 获取所有object版本的信息 页面表格
     * @param parameter
     * @return
     */
    List<ObjectVersionVo> searchAllObjectVersion(VersionManageParameter parameter);

    /**
     * 获取所有数据元版本的信息 页面表格
     * @param parameter
     * @return
     */
    List<SynlteFieldVersionVo> searchAllSynlteFieldVersion(VersionManageParameter parameter);

    /**
     * 获取所有限定词版本的信息 页面表格
     * @param parameter
     * @return
     */
    List<FieldDeterminerVersionVo> searchAllFieldDeterminerVersion(VersionManageParameter parameter);


    /**
     * 根据objectId和修订时间 来查询标准历史信息
     * @param objectVersionVo
     * @return
     */
    StandardObjectManage searchOldObject(ObjectVersionVo objectVersionVo);

    /**
     * 根据修改的时间来获取对应数据元历史信息
     * @param synlteFieldVersionVo
     * @return
     */
    SynlteFieldObject searchOldSynlteField(SynlteFieldVersionVo synlteFieldVersionVo);

    /**
     * 根据修改的时间来获取对应限定词历史信息
     * @param fieldDeterminerVersionVo
     * @return
     */
    FieldDeterminer searchOldFieldDeterminer(FieldDeterminerVersionVo fieldDeterminerVersionVo);

    /**
     * 查询大版本号和修订人的筛选值
     * @param table
     * @return
     */
    FilterList searchVersionAndAuthor(String table);


}

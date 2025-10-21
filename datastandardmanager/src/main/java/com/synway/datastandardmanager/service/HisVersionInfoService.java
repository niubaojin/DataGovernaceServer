package com.synway.datastandardmanager.service;

import com.synway.datastandardmanager.entity.dto.HisVersionInfoDTO;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.*;
import com.synway.datastandardmanager.entity.vo.FilterListVO;

import java.util.List;

public interface HisVersionInfoService {

    /**
     * 获取所有object版本的信息 页面表格
     */
    List<ObjectVersionEntity> searchAllObjectVersion(HisVersionInfoDTO dto);

    /**
     * 获取所有数据元版本的信息 页面表格
     */
    List<SynlteFieldVersionEntity> searchAllSynlteFieldVersion(HisVersionInfoDTO dto);

    /**
     * 获取所有限定词版本的信息 页面表格
     */
    List<FieldDeterminerVersionEntity> searchAllFieldDeterminerVersion(HisVersionInfoDTO dto);

    /**
     * 根据objectId和修订时间 来查询标准历史信息
     */
    ObjectManageDTO searchOldObject(ObjectVersionEntity objectVersion);

    /**
     * 根据修改的时间来获取对应数据元历史信息
     */
    SynlteFieldHisEntity searchOldSynlteField(SynlteFieldVersionEntity synlteFieldVersion);

    /**
     * 根据修改的时间来获取对应限定词历史信息
     */
    FieldDeterminerHisEntity searchOldFieldDeterminer(FieldDeterminerVersionEntity fieldDeterminerVersion);

    /**
     * 查询大版本号和修订人的筛选值
     */
    FilterListVO searchVersionAndAuthor(String table);

}

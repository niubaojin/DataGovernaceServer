package com.synway.property.service;

import com.synway.property.pojo.datastoragemonitor.NeedAddRealTimeTable;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 22:24
 */
public interface UserAuthorityManageService {

    String addUserAddRealTimeTable(List<NeedAddRealTimeTable> list,String type);

    String delUserAddRealTimeTable(List<NeedAddRealTimeTable> list);
}

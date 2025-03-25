package com.synway.property.service.impl;

import com.synway.property.dao.UserAuthorityManageDao;
import com.synway.property.interceptor.AuthorizedUserUtils;
import com.synway.property.pojo.AuthorizedUser;
import com.synway.property.pojo.LoginUser;
import com.synway.property.pojo.UserAuthority;
import com.synway.property.pojo.datastoragemonitor.NeedAddRealTimeTable;
import com.synway.property.service.UserAuthorityManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 22:26
 */
@Service
@Slf4j
public class UserAuthorityManageServiceImpl  implements UserAuthorityManageService {
    @Autowired
    private UserAuthorityManageDao userAuthorityManageDao;


    @Override
    public String addUserAddRealTimeTable(List<NeedAddRealTimeTable> list, String type) {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if(authorizedUser == null || authorizedUser.getUserId() == null){
            log.info("用户信息为空，不需要进行权限控制");
            return "";
        }
        if(list == null ||list.isEmpty()){
            return "";
        }
//        for(NeedAddRealTimeTable needAddRealTimeTable:list){
//            UserAuthority userAuthority = new UserAuthority();
//            // 如果是已经存在，则更新 如果不存在，则插入
//            userAuthority.setId(needAddRealTimeTable.getTableUuid());
//            userAuthority.setCmnName(StringUtils.isBlank(needAddRealTimeTable.getTableNameCH())?"":needAddRealTimeTable.getTableNameCH());
//            userAuthority.setUserId(String.valueOf(authorizedUser.getUserId()));
//            userAuthority.setUserName(authorizedUser.getUserName());
//            int update = userAuthorityManageDao.updateInsertUserAuthority(userAuthority);
//            if(update == 0){
//                throw new NullPointerException("将表信息"+needAddRealTimeTable.getTableNameEN()+"数据插入到用户表中报错");
//            }
//        }
        return "";
    }

    @Override
    public String delUserAddRealTimeTable(List<NeedAddRealTimeTable> list) {
        LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
        if(authorizedUser == null || authorizedUser.getUserId() == null){
            log.info("用户信息为空，不需要进行权限控制");
            return "";
        }
        if(list == null ||list.isEmpty()){
            return "";
        }
        int delNum = userAuthorityManageDao.delUserAddRealTimeTable(list,Integer.parseInt(authorizedUser.getUserId()),"FXXSJKJK");
        log.info("用户表中删除的数据量为：{}",delNum);
        return null;
    }
}

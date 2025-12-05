package com.synway.datastandardmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.datastandardmanager.constants.Common;
import com.synway.datastandardmanager.entity.dto.ObjectManageDTO;
import com.synway.datastandardmanager.entity.pojo.LoginUser;
import com.synway.datastandardmanager.entity.pojo.UserAuthorityEntity;
import com.synway.datastandardmanager.interceptor.AuthorizedUserUtils;
import com.synway.datastandardmanager.mapper.UserAuthorityMapper;
import com.synway.datastandardmanager.service.UserAuthorityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

    @Resource
    UserAuthorityMapper userAuthorityMapper;

    @Override
    public String addUserAuthorityData(ObjectManageDTO objectManageDTO) {
        try {

            LoginUser authorizedUser = AuthorizedUserUtils.getInstance().getAuthor();
            if (authorizedUser == null) {
                log.info(">>>>>>用户信息为空，不需要进行权限控制");
                return Common.NULL;
            }
            if (StringUtils.isNotBlank(objectManageDTO.getObjectId())) {
                log.info(">>>>>>修改标准信息，不需要更改表的用户权限");
                return Common.NULL;
            }
            UserAuthorityEntity userAuthority = new UserAuthorityEntity();
            // 如果是已经存在，则更新 如果不存在，则插入
            userAuthority.setId(objectManageDTO.getTableId().toUpperCase());
            userAuthority.setUserId(Integer.valueOf(authorizedUser.getUserId()));
            userAuthority.setUserName(authorizedUser.getUserName());
            userAuthority.setCmnName(objectManageDTO.getObjectPojoTable().getDataSourceName());
            userAuthority.setCreateTime(new Date());
            userAuthority.setOrganId(authorizedUser.getOrganId());
            userAuthority.setOrganName(authorizedUser.getOrganName());
            LambdaQueryWrapper<UserAuthorityEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(UserAuthorityEntity::getId, userAuthority.getId());
            wrapper.eq(UserAuthorityEntity::getUserId, userAuthority.getUserId());
            if (userAuthorityMapper.selectCount(wrapper) >= 1) {
                log.info(String.format("开始更新权限表user_authority...\nuserName:%s\nuserId:%s\norganId:%s\ntableId:%s\n",
                        userAuthority.getUserName(), userAuthority.getUserId(), userAuthority.getOrganId(), userAuthority.getId()));
                userAuthorityMapper.updateUserAuthority(userAuthority);
            } else {
                log.info(String.format("开始插入权限表user_authority...\nuserName:%s\nuserId:%s\norganId:%s\ntableId:%s\n",
                        userAuthority.getUserName(), userAuthority.getUserId(), userAuthority.getOrganId(), userAuthority.getId()));
                userAuthorityMapper.insert(userAuthority);
            }
            return Common.ADD_SUCCESS;
        }catch (Exception e){
            log.error(">>>>>>新增/更新权限表失败：", e);
            return Common.ADD_FAIL;
        }
    }

}

package com.synway.datastandardmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.synway.datastandardmanager.entity.pojo.UserAuthorityEntity;

public interface UserAuthorityMapper extends BaseMapper<UserAuthorityEntity> {

    int updateUserAuthority(UserAuthorityEntity userAuthority);

}

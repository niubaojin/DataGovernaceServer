package com.synway.governace.service.navbar.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.synway.governace.dao.DgnNavbarDao;
import com.synway.governace.dao.NavBarDao;
import com.synway.governace.entity.pojo.DgnNavbarEntity;
import com.synway.governace.pojo.navbar.NavBars;
import com.synway.governace.service.navbar.NavBarService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NavBarServiceImpl implements NavBarService {

    @Autowired
    private NavBarDao navBarDao;

    @Resource
    private DgnNavbarDao dgnNavbarDao;

    @Override
    public List<NavBars> getAllNavBars() {
        List<NavBars> navBars = null;
        try{
            navBars=navBarDao.getAllNavBars();
        } catch (Exception e) {
            log.error("获取所有菜单栏数据失败：", e);
        }
        return navBars;
    }

    @Override
    public List<NavBars> getAllNewNavBars() {
        List<NavBars> navBars = null;
        try{
            navBars=navBarDao.getAllNewNavBars();
        } catch (Exception e) {
            log.error("新获取所有菜单栏数据失败：", e);
        }
        return navBars;
    }

    @Override
    public boolean getNavStatusByName(String name) {
        boolean status = false;
        try{
            log.info(">>>>>>获取菜单栏Item[{}]的状态：", name);
            LambdaQueryWrapper<DgnNavbarEntity> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(DgnNavbarEntity::getNavName, name);
            DgnNavbarEntity entity = dgnNavbarDao.selectOne(wrapper);
            if (entity != null){
                status = entity.getNavShow().equalsIgnoreCase("1") ? true : false;
            }
        }catch (Exception e) {
            log.error(">>>>>>获取菜单栏Item状态失败：", e);
        }
        return status;
    }
}
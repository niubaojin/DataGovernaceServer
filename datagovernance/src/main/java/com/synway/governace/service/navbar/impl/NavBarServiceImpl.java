package com.synway.governace.service.navbar.impl;

import com.synway.governace.dao.NavBarDao;
import com.synway.governace.pojo.navbar.NavBars;
import com.synway.governace.service.navbar.NavBarService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NavBarServiceImpl implements NavBarService {
    private Logger logger = Logger.getLogger(NavBarServiceImpl.class);

    @Autowired
    private NavBarDao navBarDao;

    @Override
    public List<NavBars> getAllNavBars() {
        List<NavBars> navBars = null;
        try{
            navBars=navBarDao.getAllNavBars();
        } catch (Exception e) {
            logger.error("获取所有菜单栏数据失败：", e);
        }
        return navBars;
    }

    @Override
    public List<NavBars> getAllNewNavBars() {
        List<NavBars> navBars = null;
        try{
            navBars=navBarDao.getAllNewNavBars();
        } catch (Exception e) {
            logger.error("新获取所有菜单栏数据失败：", e);
        }
        return navBars;
    }

    @Override
    public boolean getNavStatusByName(String name) {
        boolean status = false;
        try{
            status = navBarDao.getNavStatusByName(name);
        }catch (Exception e) {
            logger.error("获取菜单栏Item状态失败：", e);
        }
        return status;
    }
}
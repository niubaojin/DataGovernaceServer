package com.synway.governace.dao;

import com.synway.governace.pojo.navbar.NavBars;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavBarDao {
    List<NavBars> getAllNavBars();

    List<NavBars> getAllNewNavBars();

    boolean getNavStatusByName(String name);
}

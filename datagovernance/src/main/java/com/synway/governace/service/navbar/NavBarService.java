package com.synway.governace.service.navbar;

import com.synway.governace.pojo.navbar.NavBars;

import java.util.List;

public interface NavBarService {

    List<NavBars> getAllNavBars();

    List<NavBars> getAllNewNavBars();

    boolean getNavStatusByName(String name);
}

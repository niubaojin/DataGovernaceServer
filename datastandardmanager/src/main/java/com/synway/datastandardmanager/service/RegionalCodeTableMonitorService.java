package com.synway.datastandardmanager.service;


import com.synway.datastandardmanager.pojo.RegionalCodeTable;

import java.util.List;

public interface RegionalCodeTableMonitorService {

    public String createDMZDZWMTree(String dmzdzwm);

    public List<String> dmzdzwmQuery(String dmzdzwm);

    public List<RegionalCodeTable> CodeTableQuery(String dmzd);

    public List<RegionalCodeTable> CodeValTable(int pageNum, int pageSize, String dmzd, String dmmc);

    public List<String> dmmcQuery(String dmzd, String dmmc);

    public void updateDMAndDMMC(String dmzd, String dm, String dmmc, String dmNew, String dmmcNew);

    public void batchInsertionOfData(List<RegionalCodeTable> lists);

    public void CodeValTableDelete(String dmzd, String dmzdzwm, String dm, String dmmc);

}

package com.synway.dataoperations.service;

import com.synway.dataoperations.pojo.dataPiledMonitor.RequestParameterDPM;
import com.synway.dataoperations.pojo.dataPiledMonitor.ReturnResultDPM;
import com.synway.dataoperations.pojo.dataPiledMonitor.TreeAndCartPiled;

public interface DataPiledMonitorService {

    TreeAndCartPiled getTreeAndCardData(String searchName, String dataType);

    ReturnResultDPM getChartDataDPM(RequestParameterDPM requestParameterDPM);

    void getDataPiledMonitor();
}

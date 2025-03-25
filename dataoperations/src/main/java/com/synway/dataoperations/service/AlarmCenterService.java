package com.synway.dataoperations.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.dataoperations.pojo.AlarmReturnResultMap;
import com.synway.dataoperations.pojo.DataGFMsg;
import com.synway.dataoperations.pojo.DataGFReturnMap;
import com.synway.dataoperations.pojo.RequestParameter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @description 告警中心
 * @author niubaojin
 * @date 2022年2月22日15:38:02
 */
public interface AlarmCenterService {

    // 获取告警中心数据
    AlarmReturnResultMap getAlarmData(RequestParameter requestParameter);

    // 获取治理跟踪数据
    DataGFReturnMap getDGFData(RequestParameter requestParameter);

    // 获取治理跟踪对话框下拉菜单数据
    JSONArray getDGFSelect(String queryKey);

    // 保存治理跟踪数据
    void saveDGFData(DataGFMsg dataGFMsg);

    // 更新结论信息
    JSONObject updateConclusion(RequestParameter requestParameter);

    // 删除数据
    void deleteDFGData(JSONObject ids);

    void exportDFGData(JSONObject jsonObject, HttpServletResponse response) throws IOException;

}

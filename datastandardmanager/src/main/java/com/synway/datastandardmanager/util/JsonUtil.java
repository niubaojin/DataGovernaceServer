package com.synway.datastandardmanager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
    // 删除空的子项
    public static JSONArray delEmptyChildren(JSONArray data){
        for(int i = 0; i < data.size(); i++){
            JSONArray childrenArray = (JSONArray) ((JSONObject) data.get(i)).get("children");
            if (childrenArray != null){
                if(childrenArray.size() == 0){
                    ((JSONObject) data.get(i)).fluentRemove("children");
                }else{
                    delEmptyChildren(childrenArray);
                }
            }
        }
        return data;
    }
}

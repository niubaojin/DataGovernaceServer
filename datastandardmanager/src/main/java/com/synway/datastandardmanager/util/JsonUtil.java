package com.synway.datastandardmanager.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synway.datastandardmanager.entity.pojo.FieldCodeValEntity;

import java.util.List;

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

    /**
     * 递归转换树形json数据
     * @param tables
     * @param codeId
     * @param array
     */
    public static JSONArray convert2Tree(List<FieldCodeValEntity> tables, String codeId, JSONArray array) {
        for (FieldCodeValEntity table:tables) {
            if (table.getCodeId().equalsIgnoreCase(codeId)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("value",table.getValValue());
                jsonObject.put("label",table.getValText());
                jsonObject.put("children",convert2Tree(tables,table.getCodeValId(), new JSONArray()));
                array.add(jsonObject);
            }
        }
        return array;
    }

}

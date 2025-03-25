package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.synway.datastandardmanager.dao.master.NationalCodeTableMonitorDao;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;
import com.synway.datastandardmanager.pojo.TreeNode;
import com.synway.datastandardmanager.service.NationalCodeTableMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synway.datastandardmanager.pojo.FieldCodeVal;
import com.synway.datastandardmanager.pojo.Fieldcode;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NationalCodeTableMonitorServiceImpl implements NationalCodeTableMonitorService {

    @Autowired
    private NationalCodeTableMonitorDao nationalCodeTableMonitorDao;


    @Override
    public String codeTextQuery(String codeText) {
        List<TreeNode> treeNodeLists=new ArrayList<TreeNode>();
        //获取一级节点
        List<Map<String,Object>> treeNodes = nationalCodeTableMonitorDao.codeTextQuery(codeText);
        for(int i=0;i<treeNodes.size();i++){
            TreeNode treeNode = new TreeNode();
            Map<String,Object> map = treeNodes.get(i);
            treeNode.setText(String.valueOf(map.get("CODETEXT")));
            treeNode.setIcon("");
            List<Object> tabs=new ArrayList<Object>();
            tabs.add(map.get("CODEID"));
            treeNode.setTabs(tabs);
            treeNodeLists.add(treeNode);
        }
        String treeNodesStr = JSONArray.toJSONString(treeNodeLists.stream().sorted((s1, s2) ->
                Collator.getInstance(Locale.CHINA).compare(s1.getText(), s2.getText())).collect(Collectors.toList()));
        return treeNodesStr;
    }

    @Override
    public List<String> codeTextsQuery(String codeText) {
        return nationalCodeTableMonitorDao.codeTextsQuery(codeText);
    }

    @Override
    public List<Fieldcode> fieldCodeTableQuery(String codeId) {
        List<Fieldcode> list = new ArrayList<>();
        list =  nationalCodeTableMonitorDao.fieldCodeTableQuery(codeId);
        return list;
    }


    @Override
    public List<FieldCodeVal> fieldCodeValTable(int pageNum, int  pageSize, String codeId, String valText) {
        List<FieldCodeVal> list = new ArrayList<>();
        if(pageNum!=0 && pageSize!=0) {
            Page page = PageHelper.startPage(pageNum,pageSize,true);
        }
        list = nationalCodeTableMonitorDao.fieldCodeValTable(codeId,valText);
        return list;
    }

    @Override
    public List<String> valTextQuery(String codeId, String valText) {
        return nationalCodeTableMonitorDao.valTextQuery(codeId,valText);
    }


}

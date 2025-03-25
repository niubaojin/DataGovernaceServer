package com.synway.datastandardmanager.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.synway.datastandardmanager.dao.master.RegionalCodeTableMonitorDao;
import com.synway.datastandardmanager.pojo.RegionalCodeTable;
import com.synway.datastandardmanager.pojo.TreeNode;
import com.synway.datastandardmanager.service.RegionalCodeTableMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RegionalCodeTableMonitorServiceImpl implements RegionalCodeTableMonitorService {

    @Autowired
    private RegionalCodeTableMonitorDao regionalCodeTableMonitorDao;

    @Override
    public String createDMZDZWMTree(String dmzdzwm) {
        List<TreeNode> treeNodeLists=new ArrayList<TreeNode>();
        //获取一级节点
        List<Map<String,Object>> treeNodes = regionalCodeTableMonitorDao.createDMZDZWMTree(dmzdzwm);
        for(int i=0;i<treeNodes.size();i++){
            TreeNode treeNode = new TreeNode();
            Map<String,Object> map = treeNodes.get(i);
            treeNode.setText(String.valueOf(map.get("DMZDZWM")));
            treeNode.setIcon("");
            List<Object> tabs=new ArrayList<Object>();
            tabs.add(map.get("DMZD"));
            treeNode.setTabs(tabs);
            treeNodeLists.add(treeNode);
        }
        String treeNodesStr = JSONArray.toJSONString(treeNodeLists.stream().sorted((s1, s2) ->
                Collator.getInstance(Locale.CHINA).compare(s1.getText(), s2.getText())).collect(Collectors.toList()));
        return treeNodesStr;
    }

    @Override
    public List<String> dmzdzwmQuery(String dmzdzwm) {
        return regionalCodeTableMonitorDao.dmzdzwmQuery(dmzdzwm);
    }

    @Override
    public List<RegionalCodeTable> CodeTableQuery(String dmzd) {
        return regionalCodeTableMonitorDao.CodeTableQuery(dmzd);
    }

    @Override
    public List<RegionalCodeTable> CodeValTable(int pageNum, int pageSize, String dmzd, String dmmc) {
        List<RegionalCodeTable> list = new ArrayList<>();
        if(pageNum!=0 && pageSize!=0) {
            Page page = PageHelper.startPage(pageNum,pageSize,true);
        }
        list = regionalCodeTableMonitorDao.CodeValTable(dmzd,dmmc);
        return list;
    }

    @Override
    public List<String> dmmcQuery(String dmzd, String dmmc) {
        return regionalCodeTableMonitorDao.dmmcQuery(dmzd,dmmc);
    }

    @Override
    public void updateDMAndDMMC(String dmzd,String dm,String dmmc,String dmNew,String dmmcNew) {
        regionalCodeTableMonitorDao.updateDMAndDMMC(dmzd,dm,dmmc,dmNew,dmmcNew);
    }

    @Override
    public void batchInsertionOfData(List<RegionalCodeTable> lists) {
           regionalCodeTableMonitorDao.batchInsertionOfData(lists);
    }

    @Override
    public void CodeValTableDelete(String dmzd, String dmzdzwm, String dm, String dmmc) {
        regionalCodeTableMonitorDao.CodeValTableDelete(dmzd,dmzdzwm,dm,dmmc);
    }


}

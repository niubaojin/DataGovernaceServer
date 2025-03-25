package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.pojo.labelmanage.LabelManageExcel;
import com.synway.datastandardmanager.pojo.labelmanage.TemplateCodeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 11:03
 */
public class TemplateExcelUtils {

    public static List<TemplateCodeValue>  getListData(){
        List<TemplateCodeValue> list = new ArrayList<>();
        TemplateCodeValue labelManageData1 = new TemplateCodeValue();
        labelManageData1.setCodeValue("标签类型名称");
        labelManageData1.setCodeName("标签类型代码");
        TemplateCodeValue labelManageData2 = new TemplateCodeValue();
        labelManageData2.setCodeValue("标签1");
        labelManageData2.setCodeName("1");
        TemplateCodeValue labelManageData3 = new TemplateCodeValue();
        labelManageData3.setCodeValue("标签2");
        labelManageData3.setCodeName("2");
        TemplateCodeValue labelManageData4 = new TemplateCodeValue();
        labelManageData4.setCodeValue("标签3");
        labelManageData4.setCodeName("3");
        TemplateCodeValue labelManageData5 = new TemplateCodeValue();
        labelManageData5.setCodeValue("标签4");
        labelManageData5.setCodeName("4");
        TemplateCodeValue labelManageData6 = new TemplateCodeValue();
        labelManageData6.setCodeValue("标签5");
        labelManageData6.setCodeName("5");
        TemplateCodeValue labelManageData7 = new TemplateCodeValue();
        labelManageData7.setCodeValue("标签6");
        labelManageData7.setCodeName("6");

        TemplateCodeValue labelManageData8 = new TemplateCodeValue();
        labelManageData8.setCodeValue("常用组织分类名称");
        labelManageData8.setCodeName("常用组织分类代码");
        TemplateCodeValue labelManageData9 = new TemplateCodeValue();
        labelManageData9.setCodeValue("原始库");
        labelManageData9.setCodeName("01");
        TemplateCodeValue labelManageData10 = new TemplateCodeValue();
        labelManageData10.setCodeValue("资源库");
        labelManageData10.setCodeName("02");
        TemplateCodeValue labelManageData11 = new TemplateCodeValue();
        labelManageData11.setCodeValue("主题库");
        labelManageData11.setCodeName("03");
        TemplateCodeValue labelManageData12 = new TemplateCodeValue();
        labelManageData12.setCodeValue("知识库");
        labelManageData12.setCodeName("04");
        TemplateCodeValue labelManageData13 = new TemplateCodeValue();
        labelManageData13.setCodeValue("业务库");
        labelManageData13.setCodeName("05");
        TemplateCodeValue labelManageData14 = new TemplateCodeValue();
        labelManageData14.setCodeValue("业务要素索引库");
        labelManageData14.setCodeName("06");
        list.add(labelManageData1);
        list.add(labelManageData2);
        list.add(labelManageData3);
        list.add(labelManageData4);
        list.add(labelManageData5);
        list.add(labelManageData6);
        list.add(labelManageData7);
        list.add(labelManageData8);
        list.add(labelManageData9);
        list.add(labelManageData10);
        list.add(labelManageData11);
        list.add(labelManageData12);
        list.add(labelManageData13);
        list.add(labelManageData14);
        return list;
    }
}

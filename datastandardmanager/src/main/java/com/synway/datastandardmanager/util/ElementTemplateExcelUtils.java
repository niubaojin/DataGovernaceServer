package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.pojo.labelmanage.TemplateCodeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public class ElementTemplateExcelUtils {

    public static List<TemplateCodeValue> getListData(){
        List<TemplateCodeValue> list = new ArrayList<>();
        TemplateCodeValue elementMangeData1 = new TemplateCodeValue();
        elementMangeData1.setCodeValue("主题类型名称");
        elementMangeData1.setCodeName("标签类型代码");
        TemplateCodeValue elementMangeData2 = new TemplateCodeValue();
        elementMangeData2.setCodeValue("人员");
        elementMangeData2.setCodeName("1");
        TemplateCodeValue elementMangeData3 = new TemplateCodeValue();
        elementMangeData3.setCodeValue("物");
        elementMangeData3.setCodeName("2");
        TemplateCodeValue elementMangeData4 = new TemplateCodeValue();
        elementMangeData4.setCodeValue("组织");
        elementMangeData4.setCodeName("3");
        TemplateCodeValue elementMangeData5 = new TemplateCodeValue();
        elementMangeData5.setCodeValue("地");
        elementMangeData5.setCodeName("4");
        TemplateCodeValue elementMangeData6 = new TemplateCodeValue();
        elementMangeData6.setCodeValue("事");
        elementMangeData6.setCodeName("5");
        TemplateCodeValue elementMangeData7 = new TemplateCodeValue();
        elementMangeData7.setCodeValue("时间");
        elementMangeData7.setCodeName("6");
        TemplateCodeValue elementMangeData8 = new TemplateCodeValue();
        elementMangeData8.setCodeValue("信息");
        elementMangeData8.setCodeName("7");
        TemplateCodeValue elementMangeData9 = new TemplateCodeValue();
        elementMangeData9.setCodeValue("生成方式名称");
        elementMangeData9.setCodeName("生成方式代码");
        TemplateCodeValue elementMangeData10 = new TemplateCodeValue();
        elementMangeData10.setCodeValue("数据元");
        elementMangeData10.setCodeName("1");
        TemplateCodeValue elementMangeData11 = new TemplateCodeValue();
        elementMangeData11.setCodeValue("数据元+数据字典");
        elementMangeData11.setCodeName("2");
        TemplateCodeValue elementMangeData12 = new TemplateCodeValue();
        elementMangeData12.setCodeValue("要素来源名称");
        elementMangeData12.setCodeName("要素来源代码");
        TemplateCodeValue elementMangeData13 = new TemplateCodeValue();
        elementMangeData13.setCodeValue("标准");
        elementMangeData13.setCodeName("1");
        TemplateCodeValue elementMangeData14 = new TemplateCodeValue();
        elementMangeData14.setCodeValue("私有");
        elementMangeData14.setCodeName("2");
        list.add(elementMangeData1);
        list.add(elementMangeData2);
        list.add(elementMangeData3);
        list.add(elementMangeData4);
        list.add(elementMangeData5);
        list.add(elementMangeData6);
        list.add(elementMangeData7);
        list.add(elementMangeData8);
        list.add(elementMangeData9);
        list.add(elementMangeData10);
        list.add(elementMangeData11);
        list.add(elementMangeData12);
        list.add(elementMangeData13);
        list.add(elementMangeData14);
        return list;




    }
}

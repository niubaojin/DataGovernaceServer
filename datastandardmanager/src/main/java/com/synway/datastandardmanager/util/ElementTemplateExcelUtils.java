package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.entity.vo.TemplateCodeValueVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author obito
 * @version 1.0
 * @date
 */
public class ElementTemplateExcelUtils {

    public static List<TemplateCodeValueVO> getListData(){
        List<TemplateCodeValueVO> list = new ArrayList<>();
        TemplateCodeValueVO elementMangeData1 = new TemplateCodeValueVO();
        elementMangeData1.setCodeValue("主题类型名称");
        elementMangeData1.setCodeName("标签类型代码");
        TemplateCodeValueVO elementMangeData2 = new TemplateCodeValueVO();
        elementMangeData2.setCodeValue("人员");
        elementMangeData2.setCodeName("1");
        TemplateCodeValueVO elementMangeData3 = new TemplateCodeValueVO();
        elementMangeData3.setCodeValue("物");
        elementMangeData3.setCodeName("2");
        TemplateCodeValueVO elementMangeData4 = new TemplateCodeValueVO();
        elementMangeData4.setCodeValue("组织");
        elementMangeData4.setCodeName("3");
        TemplateCodeValueVO elementMangeData5 = new TemplateCodeValueVO();
        elementMangeData5.setCodeValue("地");
        elementMangeData5.setCodeName("4");
        TemplateCodeValueVO elementMangeData6 = new TemplateCodeValueVO();
        elementMangeData6.setCodeValue("事");
        elementMangeData6.setCodeName("5");
        TemplateCodeValueVO elementMangeData7 = new TemplateCodeValueVO();
        elementMangeData7.setCodeValue("时间");
        elementMangeData7.setCodeName("6");
        TemplateCodeValueVO elementMangeData8 = new TemplateCodeValueVO();
        elementMangeData8.setCodeValue("信息");
        elementMangeData8.setCodeName("7");
        TemplateCodeValueVO elementMangeData9 = new TemplateCodeValueVO();
        elementMangeData9.setCodeValue("生成方式名称");
        elementMangeData9.setCodeName("生成方式代码");
        TemplateCodeValueVO elementMangeData10 = new TemplateCodeValueVO();
        elementMangeData10.setCodeValue("数据元");
        elementMangeData10.setCodeName("1");
        TemplateCodeValueVO elementMangeData11 = new TemplateCodeValueVO();
        elementMangeData11.setCodeValue("数据元+数据字典");
        elementMangeData11.setCodeName("2");
        TemplateCodeValueVO elementMangeData12 = new TemplateCodeValueVO();
        elementMangeData12.setCodeValue("要素来源名称");
        elementMangeData12.setCodeName("要素来源代码");
        TemplateCodeValueVO elementMangeData13 = new TemplateCodeValueVO();
        elementMangeData13.setCodeValue("标准");
        elementMangeData13.setCodeName("1");
        TemplateCodeValueVO elementMangeData14 = new TemplateCodeValueVO();
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

package com.synway.datastandardmanager.util;

import com.synway.datastandardmanager.entity.vo.TemplateCodeValueVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/6/11 11:03
 */
public class TemplateExcelUtil {

    public static List<TemplateCodeValueVO> getListData() {
        List<TemplateCodeValueVO> list = new ArrayList<>();
        TemplateCodeValueVO labelManageData1 = new TemplateCodeValueVO();
        labelManageData1.setCodeValue("标签类型名称");
        labelManageData1.setCodeName("标签类型代码");
        TemplateCodeValueVO labelManageData2 = new TemplateCodeValueVO();
        labelManageData2.setCodeValue("标签1");
        labelManageData2.setCodeName("1");
        TemplateCodeValueVO labelManageData3 = new TemplateCodeValueVO();
        labelManageData3.setCodeValue("标签2");
        labelManageData3.setCodeName("2");
        TemplateCodeValueVO labelManageData4 = new TemplateCodeValueVO();
        labelManageData4.setCodeValue("标签3");
        labelManageData4.setCodeName("3");
        TemplateCodeValueVO labelManageData5 = new TemplateCodeValueVO();
        labelManageData5.setCodeValue("标签4");
        labelManageData5.setCodeName("4");
        TemplateCodeValueVO labelManageData6 = new TemplateCodeValueVO();
        labelManageData6.setCodeValue("标签5");
        labelManageData6.setCodeName("5");
        TemplateCodeValueVO labelManageData7 = new TemplateCodeValueVO();
        labelManageData7.setCodeValue("标签6");
        labelManageData7.setCodeName("6");

        TemplateCodeValueVO labelManageData8 = new TemplateCodeValueVO();
        labelManageData8.setCodeValue("常用组织分类名称");
        labelManageData8.setCodeName("常用组织分类代码");
        TemplateCodeValueVO labelManageData9 = new TemplateCodeValueVO();
        labelManageData9.setCodeValue("原始库");
        labelManageData9.setCodeName("01");
        TemplateCodeValueVO labelManageData10 = new TemplateCodeValueVO();
        labelManageData10.setCodeValue("资源库");
        labelManageData10.setCodeName("02");
        TemplateCodeValueVO labelManageData11 = new TemplateCodeValueVO();
        labelManageData11.setCodeValue("主题库");
        labelManageData11.setCodeName("03");
        TemplateCodeValueVO labelManageData12 = new TemplateCodeValueVO();
        labelManageData12.setCodeValue("知识库");
        labelManageData12.setCodeName("04");
        TemplateCodeValueVO labelManageData13 = new TemplateCodeValueVO();
        labelManageData13.setCodeValue("业务库");
        labelManageData13.setCodeName("05");
        TemplateCodeValueVO labelManageData14 = new TemplateCodeValueVO();
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

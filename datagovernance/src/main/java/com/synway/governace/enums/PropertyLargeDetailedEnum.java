package com.synway.governace.enums;

import com.synway.governace.pojo.largeScreen.PropertyLargeDetailed;
import com.synway.governace.service.largeScreen.DetailedLargeScreenService;
import com.synway.governace.service.largeScreen.impl.DetailedLargeScreenServiceImpl;
import com.synway.governace.util.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 *
 * 枚举策略模式，不同的查询类型
 * @author wdw
 * @version 1.0
 * @date 2021/6/7 12:58
 */
@Slf4j
public enum PropertyLargeDetailedEnum {

    /**
     * 获取原始库资产的二级分类信息
     */
    ORIGINAL_DATA("原始库资产"){
        @Override
        public List<PropertyLargeDetailed> execute(String searchName){
            DetailedLargeScreenService detailedLargeScreenService = SpringBeanUtil.getBean(DetailedLargeScreenService.class);
            return detailedLargeScreenService.getDetailedOriginalData(searchName);
        }

    },
    /**
     * 资源库资产的二级分类
     */
    RESOURCE_DATA("资源库资产"){

        @Override
        public List<PropertyLargeDetailed> execute(String searchName){
            DetailedLargeScreenServiceImpl detailedLargeScreenService = SpringBeanUtil.getBean(DetailedLargeScreenServiceImpl.class);
            return detailedLargeScreenService.getDetailedResourceThemeData(searchName,"02");
        }

    },
    /**
     *主题库资产
     */
    THEME_DATA("主题库资产"){
        @Override
        public List<PropertyLargeDetailed> execute(String searchName){
            DetailedLargeScreenServiceImpl detailedLargeScreenService = SpringBeanUtil.getBean(DetailedLargeScreenServiceImpl.class);
            return detailedLargeScreenService.getDetailedResourceThemeData(searchName,"03");
        }

    },
    // PublishInfo
    PUBLISH_INFO("对外共享"){
        @Override
        public List<PropertyLargeDetailed> execute(String searchName){
            DetailedLargeScreenService detailedLargeScreenService = SpringBeanUtil.getBean(DetailedLargeScreenService.class);
            return detailedLargeScreenService.getPublicInfo(searchName);
        }

    };

    // --Commented out by Inspection (2024/6/26 16:15):DetailedLargeScreenService detailedLargeScreenService;

// --Commented out by Inspection START (2024/6/26 16:15):
//    public String getCodeStr() {
//        return codeStr;
//    }
// --Commented out by Inspection STOP (2024/6/26 16:15)


// --Commented out by Inspection START (2024/6/26 16:16):
//    public void setCodeStr(String codeStr) {
//        this.codeStr = codeStr;
//    }
// --Commented out by Inspection STOP (2024/6/26 16:16)

    String codeStr;

    PropertyLargeDetailedEnum(String codeStr){
        this.codeStr = codeStr;
    }

    public static PropertyLargeDetailedEnum getInstance(String codeStr){
        PropertyLargeDetailedEnum[] values = values();
        for(PropertyLargeDetailedEnum detailedEnum: values){
            if(StringUtils.equalsIgnoreCase(detailedEnum.codeStr,codeStr)){
                return detailedEnum;
            }
        }
        throw new IllegalArgumentException("No enum constant" + codeStr);
    }



    public abstract List<PropertyLargeDetailed> execute(String searchName);

}

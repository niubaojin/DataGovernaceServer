package com.synway.property.util;/**
 * @author Chen KaiWei
 * @date 2020/9/9 10:12
 */

import com.aliyun.odps.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author ckw
 * @date 2020/9/9 10:12
 * @description TODO
 */
public class PageUtil {

    public static void setPaginationAndOrderPinYin(int currentPage, int pageSize, String sortName, String sortOrder){
        Page page= PageHelper.startPage(currentPage,pageSize);
        StringBuffer sb = new StringBuffer(sortName);
        if(StringUtils.isNotBlank(sortName)){
            if(StringUtils.isNotBlank(sortOrder)){
                page.setOrderBy(new StringBuffer("NLSSORT(").append(sortName).append(",'NLS_SORT = SCHINESE_PINYIN_M')")
                        .append("\t").append(sortOrder).toString());
            }else{
                page.setOrderBy(sortName);
            }
        }
    }

    public static void setPaginationAndOrder(int currentPage, int pageSize, String sortName, String sortOrder){
        Page page= PageHelper.startPage(currentPage,pageSize);
        if(StringUtils.isBlank(sortName)){
            sortName = "updateTime";
            sortOrder = "desc";
        }

        StringBuffer sb = new StringBuffer(sortName);
        if(StringUtils.isNotBlank(sortOrder)){
            page.setOrderBy(new StringBuffer(sortName).append("\t").append(sortOrder).append(" nulls last").toString());
        }else{
            page.setOrderBy(sortName);
        }

    }

}

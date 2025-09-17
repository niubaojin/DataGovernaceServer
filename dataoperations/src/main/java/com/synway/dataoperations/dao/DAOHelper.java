package com.synway.dataoperations.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义批量入库
 *
 * @author majia
 */
public class DAOHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(DAOHelper.class);


    public static boolean insertDelList(List<?> list, BaseDAO regularDAO, String methodName, int limitCount)
            throws Exception {
        //获取RegularDAO下的method方法
        Method[] methods = regularDAO.getClass().getMethods();
        Method targetMethod = null;
        for (Method method : methods) {
            if (method.getName().contains(methodName)) {
                targetMethod = method;
            }
        }

        //执行方法：每500条入库
        if (targetMethod != null) {
            for (int start = 0, step = limitCount, end = 0, length = list.size(); start < length; start = end) {
                end = (start + step) > length ? length : (start + step);
                LOGGER.info(String.format("共有[%d]条数据,当前要入的数据从%d->%d", length, start, end));
                targetMethod.invoke(regularDAO, list.subList(start, end));
            }
        } else {
            return false;
        }

        return true;
    }

    public static boolean insertDelListParallelly(List<?> list, BaseDAO regularDAO, String methodName, int limitCount)
            throws Exception {
        //获取RegularDAO下的method方法
        Method[] methods = regularDAO.getClass().getMethods();
        Method targetMethod = regularDAO.getClass().getMethod(methodName, List.class);
        List<List> parallellList = new ArrayList<>();

        //执行方法：每500条入库
        if (targetMethod != null) {
            for (int start = 0, end = 0, length = list.size(); start < length; start = end) {
                end = Math.min((start + limitCount), length);
                parallellList.add(list.subList(start, end));
            }
            parallellList.parallelStream().forEach(
                    item -> {
                        try {
                            targetMethod.invoke(regularDAO, item);
                        } catch (Exception e) {
                            LOGGER.error("入库失败");
                        }
                    }
            );
        } else {
            return false;
        }
        return true;
    }

}

package com.synway.datastandardmanager.dao.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


//定义批量入库
public class DAOHelper {
    private static Logger logger = LoggerFactory.getLogger(DAOHelper.class);


    public static boolean insertList(List<?> list,BaseDAO regularDAO,String methodName)
            throws Exception {
        //获取RegularDAO下的method方法
        Method[] methods = regularDAO.getClass().getMethods();
        Method targetMethod = null;
        for (Method method:methods) {
            if(method.getName().contains(methodName)) {
                targetMethod = method;
            }
        }

        //执行方法：每500条入库
        for (int start=0,step=500,end=0,length = list.size();start < length;start=end){
            end = (start+step) > length ? length : (start+step);
            logger.info(String.format("共有[%d]条数据,当前要入的数据从%d->%d",length,start,end));
            if(targetMethod != null){
                targetMethod.invoke(regularDAO,list.subList(start,end));
            }else{
                throw new NullPointerException("方法为空");
            }
        }
        return true;
    }

    public static boolean insertDelList(List<?> list,BaseDAO regularDAO,String methodName,int limitCount)
            throws Exception {
        //获取RegularDAO下的method方法
        Method[] methods = regularDAO.getClass().getMethods();
        Method targetMethod = null;
        for (Method method:methods) {
            if(method.getName().equals(methodName)) {
                targetMethod = method;
            }
        }

        //执行方法：每500条入库
        if(targetMethod!=null){
            for (int start=0,step=limitCount,end=0,length = list.size();start < length;start=end){
                end = (start+step) > length ? length : (start+step);
                logger.info(String.format("共有[%d]条数据,当前要入的数据从%d->%d",length,start,end));
                targetMethod.invoke(regularDAO,list.subList(start,end));
            }
        }else {
            return false;
        }

        return true;
    }

}

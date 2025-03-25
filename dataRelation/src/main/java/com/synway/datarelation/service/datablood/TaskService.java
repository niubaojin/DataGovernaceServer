package com.synway.datarelation.service.datablood;


/**
 * @author wangdongwei
 */
public interface TaskService {
    /**
     * 用于多线程的一个方法 将要解析的sql对象插入到队列中
     * @param sql
     * @param dbType
     * @param projectName
     * @param nodeId
     */
     void insert(String sql,String dbType,String projectName,String nodeId);
}

package com.synway.datastandardmanager.pojo.ExternalInterfce;


import lombok.Data;

import java.util.Objects;

/**
 * 资产注册时查询object_store_info是否存在的pojo
 * @author obito
 * @version 1.0
 * @date
 */
@Data
public class RegisterTableInfo {
    private String tableInfoId;                         //表存储信息ID
    private String tableNameCh;                         // 数据名称
    private String tableNameEn;                         // 英文表名
    private String tableId;                             // 标准协议编码
    private String projectName;                         // 项目名
    private String tableType;                           // 表类型
    private String resourceId;                          // 数据源id
    private String registerSource;                      // 资源注册来源（sjck：数据仓库跳转过来的，sjzc：数据资产跳转过来的）


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RegisterTableInfo other = (RegisterTableInfo) obj;
        return tableNameEn.equals(other.tableNameEn)
                && projectName.equals(other.projectName)
                && tableNameEn.equals(other.tableNameEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNameEn, projectName, tableNameEn);
    }

}

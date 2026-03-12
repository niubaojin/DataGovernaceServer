package com.synway.governace.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.synway.governace.common.DataBaseConstant;
import lombok.Data;

@Data
public class DbConfigProperties {

    public static DbType getMyBatisPlusDbType(String dsType) {
        if (dsType == null) {
            return null;
        }
        return switch (dsType) {
            case DataBaseConstant.ORACLE -> DbType.ORACLE;
            case DataBaseConstant.MYSQL -> DbType.MYSQL;
            case DataBaseConstant.DAMENG -> DbType.DM;
            case DataBaseConstant.KINGBASE -> DbType.KINGBASE_ES;
            case DataBaseConstant.HIGHGO -> DbType.HIGH_GO;
            case DataBaseConstant.HAILIANG, DataBaseConstant.POLAR_DB, DataBaseConstant.VASTBASE, DataBaseConstant.POSTGRESQL -> DbType.POSTGRE_SQL;
            case DataBaseConstant.GAUSS -> DbType.OPENGAUSS;
            default -> throw new RuntimeException("无效的数据库类型配置:" + dsType);
        };
    }
}

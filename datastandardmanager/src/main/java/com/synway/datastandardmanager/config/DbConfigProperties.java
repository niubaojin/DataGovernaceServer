package com.synway.datastandardmanager.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.synway.datastandardmanager.constants.Common;
import lombok.Data;

@Data
public class DbConfigProperties {

    public static DbType getMyBatisPlusDbType(String dsType) {
        if (dsType == null) {
            return null;
        }
        return switch (dsType) {
            case Common.ORACLE -> DbType.ORACLE;
            case Common.MYSQL -> DbType.MYSQL;
            case Common.DAMENG -> DbType.DM;
            case Common.KINGBASE -> DbType.KINGBASE_ES;
            case Common.HIGHGO -> DbType.HIGH_GO;
            case Common.VASTBASE, Common.POLAR_DB, Common.POSTGRESQL -> DbType.POSTGRE_SQL;
            case Common.GAUSS -> DbType.OPENGAUSS;
            default -> throw new RuntimeException("无效的数据库类型配置:" + dsType);
        };
    }
}

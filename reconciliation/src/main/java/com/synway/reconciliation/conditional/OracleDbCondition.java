package com.synway.reconciliation.conditional;

import com.alibaba.druid.util.StringUtils;
import com.synway.reconciliation.common.Constants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * oracle
 * @author ywj
 */
public class OracleDbCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String dbType = environment.getProperty("database.type");
        if(StringUtils.equalsIgnoreCase(Constants.ORACLE, dbType)){
            return true;
        }else{
            return false;
        }
    }

}

package com.synway.reconciliation.conditional;

import com.alibaba.druid.util.StringUtils;
import com.synway.reconciliation.common.Constants;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * kafka
 * @author ywj
 */
public class KafkaMqCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String used = environment.getProperty("account.used");
        if (StringUtils.equalsIgnoreCase(Constants.KAFKA, used)) {
            return true;
        } else {
            return false;
        }
    }

}

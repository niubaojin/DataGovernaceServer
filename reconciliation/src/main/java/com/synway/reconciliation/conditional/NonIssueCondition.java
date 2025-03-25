package com.synway.reconciliation.conditional;

import com.alibaba.druid.util.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Administrator
 */
public class NonIssueCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String isIssue = environment.getProperty("isIssue");
        if (StringUtils.equalsIgnoreCase(isIssue, Boolean.FALSE.toString())) {
            return true;
        } else {
            return false;
        }
    }
}

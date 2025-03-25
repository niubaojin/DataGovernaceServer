package com.synway.reconciliation.conditional;

import com.alibaba.druid.util.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 下发对账
 * @author Administrator
 */
public class IssueCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String isIssue = environment.getProperty("isIssue");
        if (StringUtils.equalsIgnoreCase(isIssue, Boolean.TRUE.toString())) {
            return true;
        } else {
            return false;
        }
    }
}

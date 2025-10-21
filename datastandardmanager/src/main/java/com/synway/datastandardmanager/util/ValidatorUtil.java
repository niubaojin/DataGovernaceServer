package com.synway.datastandardmanager.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Set;

/**
 * @author wdw
 * @version 1.0
 * @date 2021/5/31 23:25
 */
@Slf4j
public class ValidatorUtil {
    /**
     * 检查字段的权限信息
     *
     * @param object
     */
    public static boolean checkObjectValidator(Object object) throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.isEmpty()) {
            return true;
        } else {
            Iterator<ConstraintViolation<Object>> iterator = violations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Object> next = iterator.next();
                throw new Exception(next.getMessage());
            }
        }
        return false;
    }
}

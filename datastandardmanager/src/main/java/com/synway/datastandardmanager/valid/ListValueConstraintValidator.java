package com.synway.datastandardmanager.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wangdongwei
 * @ClassName ListValueConstraintValidator
 * @description TODO
 * @date 2021/1/27 13:37
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,String> {
    private Set<String> set = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
       String[] vals = constraintAnnotation.vals();
       set.addAll(Arrays.asList(vals));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}

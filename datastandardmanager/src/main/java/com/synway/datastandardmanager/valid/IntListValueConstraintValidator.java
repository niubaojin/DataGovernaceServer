package com.synway.datastandardmanager.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangdongwei
 * @ClassName ListValueConstraintValidator
 * @description TODO
 * @date 2021/1/27 13:37
 */
public class IntListValueConstraintValidator implements ConstraintValidator<IntListValue,Integer> {
    private List<Integer> set = new ArrayList<>();
    @Override
    public void initialize(IntListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        set = Arrays.stream(vals).boxed().collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}

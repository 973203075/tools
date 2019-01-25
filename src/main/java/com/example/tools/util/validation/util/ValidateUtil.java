package com.example.tools.util.validation.util;

import com.alibaba.fastjson.JSON;
import com.iciyun.adi.microframe.core.utils.SpringContextHolder;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: tiankuokuo
 * @description: 参数校验工具类
 * @date: 2018/10/18 13:44
 * @version: V1.0
 */
public class ValidateUtil {
    /**
     * @author: tiankuokuo
     * @description: 参数校验（手动校验：任意类上的校验）
     * @date: 2018/10/18 13:44
     * @return: void
     * @throws: IllegalArgumentException
     */
    public static <T> void validate(T obj) {
        Validator validator = (Validator) SpringContextHolder.getBean(Validator.class);
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        constraintViolationsResolve(constraintViolations);
    }

    /**
     * @author: tiankuokuo
     * @description: 解析非法参数
     * @date: 2018/10/18 14:24
     * @return:
     * @throws:
     */
    public static <T> void constraintViolationsResolve(Set<ConstraintViolation<T>> constraintViolations) {
        Map<String, String> collect = constraintViolations.stream().collect(Collectors
                .toMap(k -> k.getPropertyPath().toString(),
                        y -> y.getMessage()));
        throwsIllegalArgumentException(collect);
    }

    /**
     * @author: tiankuokuo
     * @description: 向上抛异常
     * @date: 2018/10/18 15:14
     * @return:
     * @throws:
     */
    private static void throwsIllegalArgumentException(Map<String, String> collect) {
        if (collect != null && !collect.isEmpty()) {
            throw new IllegalArgumentException(JSON.toJSONString(collect, false));
        }
    }

    /**
     * @author: tiankuokuo
     * @description: 解析controller中的异常(controller中的注解校验)
     * @date: 2018/10/18 15:14
     * @return:
     * @throws:
     */
    public static void ErrorsResolve(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> collect = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(x -> x.getField(), y -> y.getDefaultMessage()));
            throwsIllegalArgumentException(collect);
        }
    }
}
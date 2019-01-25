package com.example.tools.util.validation.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author: tiankuokuo
 * @description: 创建Validator(默认提示信息修改ValidationMessages_zh_CN.properties)
 * @date: 2018/10/18 14:00
 * @version: V1.0
 */
@Configuration
public class ValidatorConfiguration {

    @Bean(name = "getValidatorFactory")
    public Validator getValidatorFactory() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //是否快速返回非法参数（快速：第一个参数非法就返回，所有参数校验完返回）
                .failFast(false)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
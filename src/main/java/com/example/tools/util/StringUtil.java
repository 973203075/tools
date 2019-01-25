package com.example.tools.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class StringUtil {


    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final String ARRAYLIST = "ArrayList";
    public static final String LINKEDLIST = "LinkedList";
    public static final String HASHMAP = "HashMap";

    /**
     * @param message
     * @author: tiankuokuo
     * @description: 校验审核说明长度
     * @date: 2019/1/18 16:57
     * @return:
     * @throws:
     */
    public static boolean checkAuditMessageLength(String message) {
        if (StringUtils.isNotBlank(message)) {
            if (message.length() > ConstantUtil.AUDIT_300) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author: tiankuokuo
     * @description: 空的数值转为0
     * @date: 2019/1/24 15:29
     * @return:
     * @throws:
     */
    public static Number nullToZero(Number value) {
        if (Objects.isNull(value)) {
            return 0;
        }
        return value;
    }

    /**
     * @author: tiankuokuo
     * @description: null 转为 ""
     * @date: 2019/1/24 15:32
     * @return:
     * @throws:
     */
    public static String nullToBlank(String value) {
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * @author: tiankuokuo
     * @description: 返回一个对象，屏蔽空指针异常
     * @date: 2019/1/24 15:50
     * @return:
     * @throws:
     */
    public static Object notNullObject(Object obj, Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");
        if (Objects.nonNull(obj)) {
            return obj;
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        } catch (IllegalAccessException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return obj;
    }

    /**
     * @author: tiankuokuo
     * @description: 屏蔽容器空指针
     * @date: 2019/1/25 11:04
     * @return:
     * @throws:
     */
    public static Object notNullCollections(Object obj, String type) {
        Objects.requireNonNull(type, "type is null");
        if (Objects.nonNull(obj)) {
            return obj;
        }
        if(ARRAYLIST.equals(type)){
            return new ArrayList();
        }else if(LINKEDLIST.equals(type)){
            return new LinkedList();
        } else if(HASHMAP.equals(type)){
            return new HashMap();
        }
        return new ArrayList<>();
    }
}

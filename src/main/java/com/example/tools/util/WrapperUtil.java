package com.example.tools.util;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iciyun.adi.microframe.manufacture.persistence.entity.ManuWorkOrder;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * @author: tiankuokuo
 * @description: 查询条件封装类
 * @date: 2019/1/24 10:30
 */
public class WrapperUtil<T> {

    public static final String STRING = "String";
    public static final String DATE = "Date";
    public static final String OBJECT = "Object";
    private final Wrapper<ManuWorkOrder> wrapper;

    public WrapperUtil(Wrapper<ManuWorkOrder> wrapper) {
        this.wrapper = wrapper;
    }

    /**
     * @author: tiankuokuo
     * @description: 添加查询条件
     * @date: 2019/1/24 10:30
     * @return:
     * @throws:
     */
    public WrapperUtil add(String column, Object value, String type) {
        if (type.equals(STRING)) {
            if (StringUtils.isNotBlank((String) value)) {
                wrapper.eq(column, value);
            }
            return this;
        } else if (type.equals(DATE)) {
            if (!Objects.isNull(value)) {
                wrapper.eq(column, value);
            }
            return this;
        } else if (type.equals(OBJECT)) {
            if (!Objects.isNull(value)) {
                wrapper.eq(column, value);
            }
            return this;
        }
        return null;
    }
}

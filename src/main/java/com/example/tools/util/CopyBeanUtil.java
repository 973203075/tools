package com.example.tools.util;

import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 类名称:    OCBeanUtil
 * 类描述:    央厨订单中心工具类
 * 创建人:    王伟康
 * 修改人:
 * 修改备注:
 * 版本:      v1.0
 * 创建日期:  2018-04-11 9:14
 */
public class CopyBeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(CopyBeanUtil.class);

    public static  <T>T copyBean2Class(Object source,Class target){
        if (source == null || target == null) return null;
        T t = null;
        try{
            Constructor[] constructors = target.getConstructors();
            t = (T) constructors[0].newInstance();
            BeanUtils.copyProperties(source,t);
        } catch (Exception e){
            logger.error("Bean copy 异常");
            logger.error(e.getMessage());
        }
        return t;
    }

    public static Page replaceRecords(Page page , Class cl){
        LinkedList<Object> objects = new LinkedList<>();
        for (Object object : page.getRecords()){
            objects.add(CopyBeanUtil.copyBean2Class(object,cl));
        }
        page.setRecords(objects);
        return page;
    }

    public static List replaceList(List sourceList , Class cl){
        if (sourceList == null && cl == null) return null;
        LinkedList<Object> objects = new LinkedList<>();
        sourceList.forEach(object -> objects.add(copyBean2Class(object,cl)));
        return objects;
    }


    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString,pos);
        return currentTime_2;
    }
}

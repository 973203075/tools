package com.example.tools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author: tiankuokuo
 * @description: 兑现克隆工具类
 * @date: 2019/1/21 18:16
 */
public class CloneUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    public static Object clone(Object o) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(o);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            Object retObj = in.readObject();
            return retObj;
        } catch (IOException e) {
            logger.error("对象" + o.getClass().getName() + "克隆异常：", e);
            throw new Exception();
        } catch (ClassNotFoundException e) {
            logger.error("对象" + o.getClass().getName() + "克隆异常：", e);
            throw new Exception();
        }
    }
}

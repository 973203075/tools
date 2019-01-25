package com.example.tools.util;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: tiankuokuo
 * @description: 异常处理：详细的异常信息和底层异常处理
 * @date: 2018/12/11 14:25
 */
public class ExceptionUtil {

    /**
     * @author: tiankuokuo
     * @description: Throwable转为字符串
     * @date: 2018/12/11 14:32
     * @return: String
     * @throws:
     */
    public static String getStackTraceAsString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    /**
     * @author: tiankuokuo
     * @description: 包装原始异常
     * @date: 2018/12/11 15:10
     * @return:
     * @throws:
     */
    public static Throwable initCause(Throwable newThrowable, Throwable cause) {
        if (newThrowable == null) {
            newThrowable = new Throwable();
        }
        return newThrowable.initCause(cause);
    }

    /**
     * @author: tiankuokuo
     * @description: 是否有异常
     * @date: 2019/1/2 16:12
     * @return:
     * @throws:
     */
    public static boolean hasException(String join) {
        if (StringUtils.isBlank(join)) {
            return false;
        }
        if (StringUtils.indexOf(join, "Exception") > -1 ||
                StringUtils.indexOf(join, "Throwable") > -1) {
            return true;
        }
        return false;
    }
}

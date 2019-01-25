package com.example.tools.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

public abstract class ExportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    public void export(String templatePath, Map<String, Object> map, String newFileName) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        OutputStream outputStream = null;
        try {
            Workbook workbook = create(templatePath, map);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(newFileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", System.currentTimeMillis() + 1000L);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception ie) {
            logger.error("IOException:{}", ExceptionUtil.getStackTraceAsString(ie));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public abstract Workbook create(String templatePath, Map<String, Object> map);
}

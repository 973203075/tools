package com.example.tools.util;

import java.io.InputStream;

public class TemplateLoaderUtil {

    public static InputStream load(String filePath) {
        return TemplateLoaderUtil.class.getClassLoader().getResourceAsStream(filePath);
    }
}

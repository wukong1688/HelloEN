package com.jack.helloen.util;


import java.lang.reflect.Field;

/**
 * 动态读取资源文件
 */
public class ResourceUtil {
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

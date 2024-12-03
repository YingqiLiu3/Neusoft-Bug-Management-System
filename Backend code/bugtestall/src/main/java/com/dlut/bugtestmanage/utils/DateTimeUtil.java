package com.dlut.bugtestmanage.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;  

public class DateTimeUtil {  

    // 定义常用的日期时间格式  
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";  
    
    /**  
     * 获取当前时间的字符串表示  
     *   
     * @return 当前时间的字符串  
     */  
    public static String getCurrentTimeAsString() {  
        return getCurrentTimeAsString(DEFAULT_FORMAT);  
    }  

    /**  
     * 获取当前时间的字符串表示，使用指定的格式  
     *   
     * @param format 格式字符串  
     * @return 当前时间的字符串  
     */  
    public static String getCurrentTimeAsString(String format) {  
        LocalDateTime now = LocalDateTime.now();  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);  
        return now.format(formatter);  
    }  

    public static void main(String[] args) {  
        // 测试工具类  
        System.out.println("当前时间（默认格式）: " + getCurrentTimeAsString());  
        System.out.println("当前时间（自定义格式）: " + getCurrentTimeAsString("yyyy/MM/dd HH:mm:ss"));  
    }  
}
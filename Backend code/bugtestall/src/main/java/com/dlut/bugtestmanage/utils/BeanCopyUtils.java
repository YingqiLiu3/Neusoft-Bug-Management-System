package com.dlut.bugtestmanage.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JavaBean 之间的属性复制，提供了两个静态方法，分别用于拷贝单个对象的属性和拷贝对象列表的属性。
 * JavaBean 在开发中，通常用于封装数据，对于遵循标准写法的JavaBean组件，其它程序可以通过反射技术实例化JavaBean对象，
 * 并且通过反射那些遵守命名规范的方法，从而获知JavaBean的属性，进而调用其属性保存数据。
 */

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();   // 使用反射机制创建目标类的一个新实例。
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}

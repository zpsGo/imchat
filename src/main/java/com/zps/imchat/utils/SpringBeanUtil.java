package com.zps.imchat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author: zps
 * @desc : 获取Spring容器中的Bean工具类
 **/
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    //赋值
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         if(SpringBeanUtil.applicationContext == null)
             SpringBeanUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


}

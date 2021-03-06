package com.my.home.main.factories;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class SpringBeanFactory implements IBeanFactory
{
    private static final String SPRING_CONFIG_FILE = "spring-config.xml";
    private static SpringBeanFactory _instance;
    private static ApplicationContext _context;
    private SpringBeanFactory(){

    }

    public static SpringBeanFactory getInstance()
    {
        if(_instance == null)
        {
            _instance = new SpringBeanFactory();
            _context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
        }
        return _instance;
    }

    @Override
    public Object getBean(String beanId) {
        Object out = _context.getBean(beanId);
        return out;
    }

}

package com.my.home.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 8:56
 * To change this template use File | Settings | File Templates.
 */
public class SpringFactory implements ISpringFactory
{
    private static final String SPRING_CONFIG_FILE = "spring-config.xml";
    private static SpringFactory _instance;
    private static ApplicationContext _context;
    private SpringFactory(){

    }

    public static SpringFactory getInstance()
    {
        if(_instance == null)
        {
            _instance = new SpringFactory();
            _context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
        }
        return _instance;
    }

    @Override
    public Object getBean(String beanName) {
        Object out = _context.getBean(beanName);
        return out;
    }
}

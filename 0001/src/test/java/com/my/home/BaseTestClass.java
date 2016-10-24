package com.my.home;

import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class BaseTestClass extends TestCase
{
    private static final String SPRING_CONFIG_FILE = "spring-config.xml";
    ApplicationContext _context;
    public void setUp() throws Exception
    {
        super.setUp();
        _context = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE);
    }
    protected <V> V getBean(String beanId, Class<V> vClass)
    {
        return (V)_context.getBean(beanId);
    }
    public void tearDown() throws Exception
    {
        super.tearDown();
        _context = null;
    }
}

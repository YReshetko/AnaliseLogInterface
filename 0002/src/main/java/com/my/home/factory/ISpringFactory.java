package com.my.home.factory;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 8:55
 * To change this template use File | Settings | File Templates.
 */
public interface ISpringFactory
{
    Object getBean(String beanName);
}

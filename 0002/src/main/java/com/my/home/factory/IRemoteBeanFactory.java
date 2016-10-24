package com.my.home.factory;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 7:53
 * To change this template use File | Settings | File Templates.
 */
public interface IRemoteBeanFactory
{
    Object getBean(String jar, String beanName, String beanPackage);
}

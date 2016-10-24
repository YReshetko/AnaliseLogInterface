package com.my.home.factory;

import com.my.home.execution.IExecutionBean;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public interface IExecuteBeanFactory
{
    IExecutionBean getExecution(String command);
    boolean isCommandValid(String command);
}

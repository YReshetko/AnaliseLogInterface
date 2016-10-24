package com.my.home.execution;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 11:45
 * To change this template use File | Settings | File Templates.
 */
public interface IExecutionBean
{
    String execute(String data);
    boolean isComplete();
    String getProgress();
    String getResult();
}

package com.my.home.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 8:42
 * To change this template use File | Settings | File Templates.
 */
public class AnyException extends RuntimeException
{
    public AnyException(String msg)
    {
        super(msg);
    }
    public AnyException(Throwable throwable)
    {
        super(throwable);
    }
}

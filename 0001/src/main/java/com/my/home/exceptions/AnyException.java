package com.my.home.exceptions;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 09.09.16
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public class AnyException extends Exception
{
    List<Exception> exceptions;
    public AnyException(Exception e, String message)
    {
        exceptions = new LinkedList<>();
        exceptions.add(e);
        if (e instanceof AnyException)
        {
            for (Exception exception : ((AnyException) e).getExceptions())
            {
                exceptions.add(exception);
            }
        }
    }
    public List<Exception> getExceptions()
    {
        return exceptions;
    }
}

package com.my.home.execution;

import com.my.home.ejb.GetThreadsRemote;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 18.09.16
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class GetThreadsForDir extends AbstractExecutionBean<GetThreadsRemote>
{
    @Override
    public String execute(String data) {
        GetThreadsRemote remote = getBean();
        return remote.getThreads(data);
    }
}

package com.my.home.execution;

import com.my.home.ejb.GetThreadsRemote;

/**
 *
 */
public class RemoveThreads extends AbstractExecutionBean<GetThreadsRemote>
{
    @Override
    public String execute(String data)
    {
        return getBean().removeThreads(data);
    }
}

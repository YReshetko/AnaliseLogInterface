package com.my.home.execution;

import com.my.home.ejb.ProcessFilesRemote;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public class ProcessFilesToLog extends AbstractExecutionBean<ProcessFilesRemote>
{
    private ProcessFilesRemote remote;
    public ProcessFilesToLog(){}
    @Override
    public String execute(String data) {
        remote = getBean();
        String result = remote.process(data);
        return result;
    }

    @Override
    public boolean isComplete()
    {
        return remote.isComplete();
    }
    @Override
    public String getProgress()
    {
        return remote.getProgress();
    }
}

package com.my.home.execution;

import com.my.home.ejb.PluginProcessorRemote;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 29.10.16
 * Time: 9:39
 * To change this template use File | Settings | File Templates.
 */
public class PluginProcessorExecutor extends AbstractExecutionBean<PluginProcessorRemote>
{
    private PluginProcessorRemote bean;
    private String progress;
    private boolean isComplete;
    private String result;

    @Override
    public String execute(String data)
    {
        try {
            bean = getBean();
            progress = "0";
            isComplete = false;
            result = "";

            String out = bean.setup(data);
            new PluginProcessThread().start();

            return out;
        }
        catch (Exception e)
        {
            return "FAIL";
        }


    }
    @Override
    public boolean isComplete()
    {
        return isComplete;
    }
    @Override
    public String getProgress()
    {
        return progress;
    }
    @Override
    public String getResult()
    {
        return result;
    }

    private class PluginProcessThread extends Thread
    {
        @Override
        public void run()
        {
            while (bean!=null && !bean.isComplete())
            {
                progress = bean.getProgress();
                bean.next();
            }
            isComplete = true;
            progress = "100";
            result = bean.getResult();
        }
    }
}

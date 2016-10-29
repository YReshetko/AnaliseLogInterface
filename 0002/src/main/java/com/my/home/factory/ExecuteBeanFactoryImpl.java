package com.my.home.factory;

import com.my.home.execution.IExecutionBean;

/**
 *
 */
public class ExecuteBeanFactoryImpl implements IExecuteBeanFactory
{
    private static IExecuteBeanFactory _instance;
    private ExecuteBeanFactoryImpl(){

    }

    public static IExecuteBeanFactory getInstance()
    {
        if(_instance == null)
        {
            _instance = new ExecuteBeanFactoryImpl();
        }
        return _instance;
    }
    @Override
    public IExecutionBean getExecution(String command)
    {
        if (isCommandValid(command))
        {
            ExecutionCommands cmd = ExecutionCommands.valueOf(command);
            return cmd.getBean();
        }
        return null;
    }

    @Override
    public boolean isCommandValid(String command)
    {
        if (ExecutionCommands.valueOf(command) != null)
        {
            return true;
        }
        return false;
    }
    private enum ExecutionCommands
    {
        GET_DIR_TREE("GetDirTreeExecution"),
        GET_PROCESS_FILES("ProcessFilesToLog"),
        GET_PROCESSED_THREADS("GetThreadsForDir"),
        GET_ALL_PLUGINS("PluginsController"),
        DOWNLOAD_THREAD("DownloadThread"),
        REMOVE_THREADS("RemoveThreads"),
        EXECUTE_PLUGINS("PluginProcessorExecutor"),
        PROGRESS("");
        private String beanId;
        private ExecutionCommands(String beanId)
        {
            this.beanId = beanId;
        }
        protected IExecutionBean getBean()
        {
            ISpringFactory springFactory = SpringFactory.getInstance();
            IExecutionBean out = (IExecutionBean) springFactory.getBean(beanId);
            return out;
        }
    }
}

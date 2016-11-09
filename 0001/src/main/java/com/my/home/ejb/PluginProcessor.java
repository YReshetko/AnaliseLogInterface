package com.my.home.ejb;

import com.my.home.data.access.IDAORepository;
import com.my.home.data.access.impl.directory.DAORepositoryDir;
import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogNode;
import com.my.home.log.beans.ThreadDescriptor;
import com.my.home.log.beans.ThreadsSet;
import com.my.home.plugin.processor.PluginsExecutor;
import com.my.home.plugin.processor.PluginsProcessor;
import com.my.home.utils.JsonUtils;
import com.my.home.wrappers.LogPluginWrapper;

import javax.ejb.Stateless;
import java.util.Iterator;

/**
 *
 */
@Stateless(name = "PluginProcessorRemote")
public class PluginProcessor implements PluginProcessorRemote
{
    private PluginsProcessor processor;
    private LogPluginWrapper wrapper;
    @Override
    public String setup(String value)
    {
        wrapper = JsonUtils.getObject(value, LogPluginWrapper.class);
        ThreadsSet threadToProcess = wrapper.getThreadsSet();
        IDAORepository repository = new DAORepositoryDir();
        Iterator<LogNode> logNodeIterator = null;
        try{
            int size = 0;
            if (threadToProcess.getNames().size() > 1)
            {
                ThreadDescriptor descriptor = repository.getSortedThreadDescriptor("", threadToProcess.getPath(), threadToProcess.getNames());
                logNodeIterator = repository.getLogNodeIterator("", threadToProcess.getPath(), descriptor);
                size = descriptor.getNodesNumbers().size();
            }
            else
            {
                logNodeIterator = repository.getLogNodeIterator("", threadToProcess.getPath(), threadToProcess.getNames().get(0));
                size = repository.getThreadSize("", threadToProcess.getPath(), threadToProcess.getNames().get(0));
            }


            PluginsExecutor executor = new PluginsExecutor();
            executor.init(wrapper.getPlugins());
            processor = new PluginsProcessor(executor, logNodeIterator, size);
            return "START_PROCESS";
        }
        catch (AnyException e)
        {
            return "FAIL";
        }
    }

    @Override
    public String getProgress() {
        return processor.getProgress();
    }

    @Override
    public boolean isComplete() {
        return processor.isComplete();
    }

    @Override
    public String getResult() {
        IDAORepository repository = new DAORepositoryDir();
        ThreadsSet threadToProcess = wrapper.getThreadsSet();
        int out;
        try {
            out = repository.savePluginResult("", threadToProcess.getPath(), wrapper, processor.getResult());
        }
        catch (AnyException e)
        {
            out = 0;
        }

        return String.valueOf(out);
    }

    @Override
    public String next() {
        return processor.next();
    }
}

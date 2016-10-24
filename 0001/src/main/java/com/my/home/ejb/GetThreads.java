package com.my.home.ejb;

import com.my.home.data.access.IDAORepository;
import com.my.home.data.access.impl.directory.BaseProcessor;
import com.my.home.data.access.impl.directory.DAORepositoryDir;
import com.my.home.data.access.impl.directory.ThreadDownloaderProcessor;
import com.my.home.exceptions.AnyException;
import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.*;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.utils.JsonUtils;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.Iterator;

/**
 *
 */
@Stateful(name = "GetThreadsRemote")
public class GetThreads implements GetThreadsRemote {
    private BaseProcessor processor;
    @Override
    public String getThreads(String value)
    {
        try {
            IDAORepository repository = new DAORepositoryDir();
            ThreadsInfo info = repository.getThreads("", value);
            return JsonUtils.getJson(info);
        } catch (AnyException e) {
            return JsonUtils.getJson(e);
        }
    }

    //  TODO remove old solution
    /*@Override
    public String downloadThread(String value) {

        try {
            ThreadToDownload threadToDownload = JsonUtils.getObject(value, ThreadToDownload.class);
            IDAORepository repository = new DAORepositoryDir();
            Iterator<LogNode> logNodeIterator = repository.getLogNodeIterator("", threadToDownload.getPath(), threadToDownload.getThreadName());
            int size = repository.getThreadSize("", threadToDownload.getPath(), threadToDownload.getThreadName());
            processor = new ThreadDownloaderProcessor(logNodeIterator, size);
            //Thread th = new Thread(processor);
            //th.start();
            return "START_PROCESS";

        } catch (AnyException e) {
            return "FAIL";
        }
    }*/
    @Override
    public String downloadThread(String value) {

        try {
            ThreadsSet threadToDownload = JsonUtils.getObject(value, ThreadsSet.class);
            IDAORepository repository = new DAORepositoryDir();
            Iterator<LogNode> logNodeIterator = null;
            int size = 0;
            if (threadToDownload.getNames().size() > 1)
            {
                ThreadDescriptor descriptor = repository.getSortedThreadDescriptor("", threadToDownload.getPath(), threadToDownload.getNames());
                logNodeIterator = repository.getLogNodeIterator("", threadToDownload.getPath(), descriptor);
                size = descriptor.getNodesNumbers().size();
            }
            else
            {
                logNodeIterator = repository.getLogNodeIterator("", threadToDownload.getPath(), threadToDownload.getNames().get(0));
                size = repository.getThreadSize("", threadToDownload.getPath(), threadToDownload.getNames().get(0));
            }

            processor = new ThreadDownloaderProcessor(logNodeIterator, size);
            //Thread th = new Thread(processor);
            //th.start();
            return "START_PROCESS";

        } catch (AnyException e) {
            return "FAIL";
        }
    }

    @Override
    public String removeThreads(String value) {
        try {
            ThreadsSet threadsToRemove = JsonUtils.getObject(value, ThreadsSet.class);
            IDAORepository repository = new DAORepositoryDir();
            ThreadsInfo info = repository.getThreads("", threadsToRemove.getPath());
            for (ThreadEntry entry : info.getThreads())
            {
                if (threadsToRemove.getNames().contains(entry.getThreadName()))
                {
                    entry.setIsDelete(true);
                }
            }
            repository.saveThreadInfo("", threadsToRemove.getPath(), info);
            return getThreads(threadsToRemove.getPath());
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
        return processor.getResult();
    }

    @Override
    public String next() {
        return processor.next();
    }


}

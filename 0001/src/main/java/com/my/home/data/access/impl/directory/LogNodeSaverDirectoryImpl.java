package com.my.home.data.access.impl.directory;

import com.my.home.data.access.ILogNodeSaver;
import com.my.home.directory.RepositoryConfig;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogNode;
import com.my.home.log.beans.ThreadDescriptor;
import com.my.home.log.beans.ThreadEntry;
import com.my.home.log.beans.ThreadsInfo;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.utils.DirectoryUtils;
import com.my.home.utils.JsonUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * log saver in directory
 */
public class LogNodeSaverDirectoryImpl implements ILogNodeSaver
{
    private static final int LENGTH_NODE_FILE_NAME = 10;
    private static final int LENGTH_THREADS_FILE_NAME = 3;

    private String nodesStorage;
    private String threadStorage;
    private String processStorage;

    private long currentNodeIndex;
    private long currentThreadIndex;
    private Map<String, ThreadDescriptor> threadsCache;
    private DirectoryUtils utils;

    /**
     * Object which contains repo configs
     */
    private RepositoryConfig config;

    /**
     * Constructor of log nodes saver
     * @param nodesFolder - path to folder where nodes well be saved
     * @param threadFolder - path to folder where threads descriptors will be saved
     * @param processFolder - path to folder where ThreadsInfo will be saved
     * @param directoryUtils - DirectoryUtils
     */
    public LogNodeSaverDirectoryImpl(String nodesFolder, String threadFolder,
                                     String processFolder, DirectoryUtils directoryUtils)
    {
        nodesStorage = nodesFolder;
        threadStorage = threadFolder;
        processStorage = processFolder;

        currentNodeIndex = 0;
        currentThreadIndex = 0;

        threadsCache = new HashMap<>();
        utils = directoryUtils;
        SpringBeanFactory factory = SpringBeanFactory.getInstance();
        config = (RepositoryConfig) factory.getBean("RepositoryConfig");
    }
    /**
     * Save log node
     *
     * @param value - log node
     * @return - true if log node was saved successfully
     */
    @Override
    public boolean save(LogNode value) throws AnyException
    {
        try {
            String threadKey = value.getThread();
            String nodeFileName = getNextNodeFileName();
            ThreadDescriptor descriptor;
            if (threadsCache.containsKey(threadKey))
            {
                descriptor = threadsCache.get(threadKey);
            }
            else
            {
                descriptor = new ThreadDescriptor();
                descriptor.setName(threadKey);
                descriptor.setStartTime(value.getLongDateTime());
                threadsCache.put(threadKey, descriptor);
            }
            descriptor.getNodesNumbers().add(nodeFileName);
            descriptor.setEndTime(value.getLongDateTime());
            String node = JsonUtils.getJson(value);
            return utils.saveFile(nodeFileName, nodesStorage, node);
        }
        catch (AnyException e)
        {
            throw new AnyException(e, "Error saving nodes");
        }
    }

    /**
     * Finish save (save info files)
     *
     * @return - true if saving was successfully
     */
    @Override
    public boolean complete(FilesToProcess files) throws AnyException
    {
        try
        {
            ThreadsInfo threadsInfo = new ThreadsInfo();
            ThreadEntry threadEntry;
            String fileName;
            boolean result = true;
            for (Map.Entry<String, ThreadDescriptor> entry : threadsCache.entrySet())
            {
                fileName = getNextThreadFileName();
                threadEntry = new ThreadEntry();
                threadEntry.setFileName(fileName);
                threadEntry.setThreadName(entry.getKey());
                threadEntry.setStartTime(entry.getValue().getStartTime());
                threadEntry.setEndTime(entry.getValue().getEndTime());
                threadEntry.setIsDelete(false);
                threadsInfo.getThreads().add(threadEntry);
                String jsonDescriptor = JsonUtils.getJson(entry.getValue());
                result &= utils.saveFile(fileName, threadStorage, jsonDescriptor);
            }
            Collections.sort(threadsInfo.getThreads(), new Comparator<ThreadEntry>()
            {
                @Override
                public int compare(ThreadEntry o1, ThreadEntry o2) {
                    return Integer.valueOf((int) (o1.getStartTime() - o2.getStartTime()));
                }
            });
            String jsonInfo = JsonUtils.getJson(threadsInfo);
            result &= utils.saveFile(config.getThreadsInfoJson(), processStorage, jsonInfo);
            String jsonFilesToProcess = JsonUtils.getJson(files);
            result &= utils.saveFile(config.getProcessedFilesJson(), processStorage, jsonFilesToProcess);
            return result;
        }
        catch (AnyException e)
        {
            throw new AnyException(e, "Error saving threads mapping");
        }
    }

    /**
     * @return - next node file name
     */
    private String getNextNodeFileName()
    {
        ++currentNodeIndex;
        return getNextFileName(currentNodeIndex, config.getNodesTemplateJson(), LENGTH_NODE_FILE_NAME);
    }

    /**
     * @return - next thread descriptor file name
     */
    private String getNextThreadFileName()
    {
        ++currentThreadIndex;
        return getNextFileName(currentThreadIndex, config.getThreadTemplateJson(), LENGTH_THREADS_FILE_NAME);
    }

    /**
     * Create next file name by template and max size of files
     * @param index - index of file
     * @param nameTemplate - template to construct file name
     * @param maxSize - max size of file number (for example: if index = 87 and maxsize = 4 then result of file index will be 0087)
     * @return - next file name
     */
    private String getNextFileName(long index, String nameTemplate, int maxSize)
    {
        String outFileName = String.valueOf(index);
        while (outFileName.length() < maxSize)
        {
            outFileName = "0" + outFileName;
        }
        return String.format(nameTemplate, outFileName);
    }
}

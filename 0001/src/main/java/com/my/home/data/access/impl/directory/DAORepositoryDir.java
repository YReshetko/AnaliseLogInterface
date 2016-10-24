package com.my.home.data.access.impl.directory;

import com.my.home.data.access.IDAORepository;
import com.my.home.data.access.ILogNodeSaver;
import com.my.home.directory.RepositoryConfig;
import com.my.home.directory.beans.Directory;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.exceptions.AnyException;
import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.*;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.utils.DirectoryUtils;
import com.my.home.utils.JsonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


/**
 * Implementation which works only in directory
 */
public class DAORepositoryDir implements IDAORepository
{
    private RepositoryConfig config;
    public DirectoryUtils dirUtils;
    public DAORepositoryDir()
    {
        SpringBeanFactory factory = SpringBeanFactory.getInstance();
        config = (RepositoryConfig) factory.getBean("RepositoryConfig");
        dirUtils = new DirectoryUtils(config);
    }

    @Override
    public String getPathToLog(String repositoryId, String subRepositoryId) {

        return dirUtils.getBasePath(repositoryId, subRepositoryId);
    }

    /**
     * Return structure of dirs
     *
     * @param repositoryID - id of repository (for example folder for user)
     * @return - structure of dirs
     */
    @Override
    public Directory getRepositoryStructure(String repositoryID)
    {
        return dirUtils.getDirStructure(config.getPath() +
                (("".equals(repositoryID))?"":config.getPathSeparator()+repositoryID));
    }

    /**
     * Check if log in current sub repository has already initialized
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if sub repo initialized
     */
    @Override
    public boolean isSubRepositoryInitialized(String repositoryId, String subRepositoryId)
    {
        String path = dirUtils.getBasePath(repositoryId, subRepositoryId) +
                config.getPathSeparator() + config.getMainFolder();
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    /**
     * Initialize sub repository (for example create folders:
     * - .ali/;
     * - .ali/.processed.files/;
     * - .ali/.nodes/;
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if repo was created
     */
    @Override
    public boolean initSubRepository(String repositoryId, String subRepositoryId) throws AnyException
    {
        if (cleanSubRepository(repositoryId, subRepositoryId))
        {
            String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
            List<String> folders = new LinkedList<>();
            folders.add(basePath + config.getMainFolder());
            folders.add(basePath + config.getProcessFolder());
            folders.add(basePath + config.getNodesFolder());
            folders.add(basePath + config.getThreadsFolder());
            boolean result = true;
            for (String folder : folders)
            {
                result &= dirUtils.createDir(folder);
            }
            return result;
        }
        throw new AnyException(new RuntimeException(), "Error sub repository couldn't be cleaned");
    }

    /**
     * Check if files in filesToProcess object have already initialized
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param filesToProcess  - object which contains list files (files don't sorted according log time stamping)
     * @return - true if files have already processed
     */
    @Override
    public boolean isLogForFilesInitialized(String repositoryId, String subRepositoryId,
                                            FilesToProcess filesToProcess) throws AnyException
    {
        try
        {
            String path = dirUtils.getBasePath(repositoryId, subRepositoryId) +
                    config.getPathSeparator() + config.getProcessFolder() +
                    config.getPathSeparator() + config.getProcessedFilesJson();
            File file = new File(path);
            if(file.exists()){
                String existingFilesToProcess = dirUtils.readFile(file);
                FilesToProcess existing = JsonUtils.getObject(existingFilesToProcess, FilesToProcess.class);
                boolean flag = existing.getSelectedFiles().containsAll(filesToProcess.getSelectedFiles());
                flag &= filesToProcess.getSelectedFiles().containsAll(existing.getSelectedFiles());
                return flag;
            }
            else
            {
                return false;
            }
        }
        catch (AnyException e)
        {
            throw new AnyException(e, "Files to process was not loaded");
        }
    }

    /**
     * Init that files have bean processed
     *
     * @param repositoryID    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param filesToProcess  - object which contains list files (files don't sorted according log time stamping)
     * @return
     */
    @Override
    public boolean initLogForFiles(String repositoryID, String subRepositoryId, FilesToProcess filesToProcess) throws AnyException
    {

        String path = dirUtils.getBasePath(repositoryID, subRepositoryId) +
                config.getPathSeparator() + config.getProcessFolder() +
                config.getPathSeparator() + config.getProcessedFilesJson();
        String json = JsonUtils.getJson(filesToProcess);
        return dirUtils.saveFile(path, json);
    }

    /**
     * Init object which saves log nodes into repo
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - each time returns new instance of LogSaver
     */
    @Override
    public ILogNodeSaver getLogNodeSaver(String repositoryId, String subRepositoryId) {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String nodePath = basePath + config.getNodesFolder();
        String threadPath = basePath + config.getThreadsFolder();
        String processPath = basePath + config.getProcessFolder();
        ILogNodeSaver saver = new LogNodeSaverDirectoryImpl(nodePath, threadPath, processPath, dirUtils);
        return saver;
    }

    /**
     *  Retrieve LogNodes iterator for thread
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadId        - thread id
     * @return - iterator
     */
    @Override
    public Iterator<LogNode> getLogNodeIterator(String repositoryId, String subRepositoryId, String threadId) throws AnyException
    {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String nodePath = basePath + config.getNodesFolder();
        ThreadDescriptor descriptor = getThreadDescriptor(repositoryId, subRepositoryId, threadId);
        if (descriptor != null)
        {
            return new ThreadNodesIterator(nodePath, descriptor, dirUtils);
        }
        else
        {
            throw new AnyException(new RuntimeException(), "Thread descriptor was not found");
        }

    }

    /**
     * Retrieve LogNodes iterator for thread
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param descriptor - thread descriptor to iterator
     * @return - iterator
     * @throws AnyException - unexpected exception
     */
    @Override
    public Iterator<LogNode> getLogNodeIterator(String repositoryId, String subRepositoryId, ThreadDescriptor descriptor) throws AnyException {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String nodePath = basePath + config.getNodesFolder();
        if (descriptor != null)
        {
            return new ThreadNodesIterator(nodePath, descriptor, dirUtils);
        }
        else
        {
            throw new AnyException(new RuntimeException(), "Thread descriptor was not found");
        }
    }

    /**
     * Returns object which contains list of threads info (ThreadEntry)
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return
     */
    @Override
    public ThreadsInfo getThreads(String repositoryId, String subRepositoryId) throws AnyException
    {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String procPath = basePath + config.getProcessFolder();
        String jsonThreadsMapping = dirUtils.readFile(config.getThreadsInfoJson(), procPath);
        return JsonUtils.getObject(jsonThreadsMapping, ThreadsInfo.class);
    }

    /**
     * Returns descriptor for several threads in sorted order (as it was in the origin log)
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadNames     - list of thread names
     * @return - thread descriptor
     */
    @Override
    public ThreadDescriptor getSortedThreadDescriptor(String repositoryId, String subRepositoryId, List<String> threadNames) throws AnyException
    {
        List<String> nodes = new ArrayList<>();
        ThreadDescriptor descriptor;
        final Long ZERO = new Long(0);
        Long startTime = new Long(0);
        Long endTime = new Long(0);
        for (String threadId : threadNames)
        {
            descriptor = getThreadDescriptor(repositoryId, subRepositoryId, threadId);
            nodes.addAll(descriptor.getNodesNumbers());
            if(startTime.equals(ZERO) || startTime.compareTo(descriptor.getStartTime()) > 0)
            {
                startTime = descriptor.getStartTime();
            }
            if(endTime.equals(ZERO) || endTime.compareTo(descriptor.getEndTime()) < 0)
            {
                endTime = descriptor.getEndTime();
            }
        }
        Collections.sort(nodes);
        ThreadDescriptor out = new ThreadDescriptor();
        out.setEndTime(endTime);
        out.setStartTime(startTime);
        out.getNodesNumbers().addAll(nodes);
        out.setName("default");
        return out;
    }

    /**
     * Save threads info after updates (for example when som threads were removed)
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadsInfo     - new Thread info
     */
    @Override
    public void saveThreadInfo(String repositoryId, String subRepositoryId, ThreadsInfo threadsInfo) throws AnyException
    {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String processPath = basePath + config.getProcessFolder();
        String threadInfoFileName = config.getThreadsInfoJson();
        dirUtils.removeFile(threadInfoFileName, processPath);
        dirUtils.saveFile(threadInfoFileName, processPath, JsonUtils.getJson(threadsInfo));
    }

    /**
     * Remove all info about initialized log
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if sub repo has been cleand
     */
    @Override
    public boolean cleanSubRepository(String repositoryId, String subRepositoryId) {
        if (isSubRepositoryInitialized(repositoryId, subRepositoryId))
        {
            String path = dirUtils.getBasePath(repositoryId, subRepositoryId)+
                    config.getPathSeparator() + config.getMainFolder();
            File file = new File(path);
            return file.delete();
        }
        return true;
    }

    /**
     * Retrieve number of nodes which relate to thread in repository
     *
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadId        - thread id
     * @return - size of thread
     * @throws AnyException - any exception
     */
    @Override
    public int getThreadSize(String repositoryId, String subRepositoryId, String threadId) throws AnyException
    {
        ThreadDescriptor descriptor = getThreadDescriptor(repositoryId, subRepositoryId, threadId);
        return descriptor.getNodesNumbers().size();
    }

    /**
     * LogBlock contains log file name and first time in log to determine correct sequence of files
     * to make correct nodes if parts of node are contained in different files
     * @param filesToProcess - contain list non-sorted files name
     * @return - list of LogBlocks
     * @throws AnyException - any exception
     */
    @Override
    public List<LogBlock> getLogBlocks(FilesToProcess filesToProcess) throws AnyException
    {
        List<LogBlock> out = new ArrayList<>();
        BufferedReader br = null;
        try {
            List<String> files = filesToProcess.getSelectedFiles();
            LogNodeProcessor logNodeProcessor = (LogNodeProcessor) SpringBeanFactory.getInstance().getBean("LogNodeProcessor");
            String line = "";
            LogBlock logBlock = null;
            long fullSize = 0;
            File currentFile;
            String basePath = dirUtils.getBasePath("", filesToProcess.getBasePath()) + "/";
            String filePath;
            for (String file : files)
            {
                filePath = basePath + file;
                currentFile = new File(filePath);
                fullSize += currentFile.length();
                br = new BufferedReader(new FileReader(filePath));
                logBlock = new LogBlock();
                logBlock.setFileName(file);
                int numLine = 0;
                while ((line = br.readLine()) != null)
                {
                    ++numLine;
                    if(logNodeProcessor.hasStamp(line))
                    {
                        LogNode node = logNodeProcessor.process(line);
                        logBlock.setStartTime(node.getLongDateTime());
                        br.close();
                        break;
                    }

                }
                br.close();
                out.add(logBlock);
            }
            filesToProcess.setTotalSize(fullSize);
        }
        catch (ParseException e)
        {
            throw new AnyException(e, "Error creation log blocks");
        }
        catch (IOException e)
        {
            throw new AnyException(e, "Error reading log files for log blocks");
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return out;
    }

    /**
     * Retrieve thread descriptor with threadId in subRepositoryId
     * @param repositoryId    - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadId        - thread id
     * @return - ThreadDescriptor
     * @throws AnyException - unexpected exception
     */
    private ThreadDescriptor getThreadDescriptor(String repositoryId, String subRepositoryId, String threadId) throws AnyException
    {
        String basePath = dirUtils.getBasePath(repositoryId, subRepositoryId) + config.getPathSeparator();
        String threadsPath = basePath + config.getThreadsFolder();
        ThreadsInfo threadsInfo = getThreads(repositoryId, subRepositoryId);
        ThreadDescriptor descriptor = null;
        for (ThreadEntry threadEntry : threadsInfo.getThreads())
        {
            if (threadEntry.getThreadName().equals(threadId))
            {
                String thMapping = dirUtils.readFile(threadEntry.getFileName(), threadsPath);
                descriptor = JsonUtils.getObject(thMapping, ThreadDescriptor.class);
                break;
            }
        }
        return descriptor;
    }

}

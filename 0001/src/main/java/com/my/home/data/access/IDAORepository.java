package com.my.home.data.access;

import com.my.home.directory.beans.Directory;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogBlock;
import com.my.home.log.beans.LogNode;
import com.my.home.log.beans.ThreadDescriptor;
import com.my.home.log.beans.ThreadsInfo;

import java.util.Iterator;
import java.util.List;

/**
 * Interface for objects which will work with log repository
 */
public interface IDAORepository
{
    String getPathToLog(String repositoryId, String subRepositoryId);
    /**
     * Return structure of dirs
     * @param repositoryID - id of repository (for example folder for user)
     * @return - structure of dirs
     */
    Directory getRepositoryStructure(String repositoryID);

    /**
     * Check if log in current sub repository has already initialized
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if sub repo initialized
     */
    boolean isSubRepositoryInitialized(String repositoryId, String subRepositoryId);

    /**
     * Initialize sub repository (for example create folders:
     *  - .ali/;
     *  - .ali/.processed.files/;
     *  - .ali/.nodes/;
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if repo was created
     */
    boolean initSubRepository(String repositoryId, String subRepositoryId) throws AnyException;

    /**
     * Check if files in filesToProcess object have already initialized
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param filesToProcess - object which contains list files (files don't sorted according log time stamping)
     * @return - true if files have already processed
     */
    boolean isLogForFilesInitialized(String repositoryId, String subRepositoryId, FilesToProcess filesToProcess) throws AnyException;

    /**
     * Init that files have bean processed
     * @param repositoryID  - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param filesToProcess - object which contains list files (files don't sorted according log time stamping)
     * @return
     */
    boolean initLogForFiles(String repositoryID, String subRepositoryId, FilesToProcess filesToProcess) throws AnyException;

    /**
     * Init object which saves log nodes into repo
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - each time returns new instance of LogSaver
     */
    ILogNodeSaver getLogNodeSaver(String repositoryId, String subRepositoryId);

    /**
     * Retrieve LogNodes iterator for thread
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadId - thread id
     * @return - iterator
     */
    Iterator<LogNode> getLogNodeIterator(String repositoryId, String subRepositoryId, String threadId) throws AnyException;

    /**
     * Retrieve LogNodes iterator for thread
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param descriptor - thread descriptor to iterator
     * @return - iterator
     * @throws AnyException - unexpected exception
     */
    Iterator<LogNode> getLogNodeIterator(String repositoryId, String subRepositoryId, ThreadDescriptor descriptor) throws AnyException;
    /**
     * Returns object which contains list of threads info (ThreadEntry)
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - thread info
     */
    ThreadsInfo getThreads(String repositoryId, String subRepositoryId) throws AnyException;

    /**
     * Returns descriptor for several threads in sorted order (as it was in the origin log)
     * @param repositoryId - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadNames - list of thread names
     * @return - thread descriptor
     */
    ThreadDescriptor getSortedThreadDescriptor(String repositoryId, String subRepositoryId, List<String> threadNames) throws AnyException;
    /**
     * Save threads info after updates (for example when som threads were removed)
     * @param repositoryId  - repository ID  (D:/out)
     * @param subRepositoryId  - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadsInfo - new Thread info
     */
    void saveThreadInfo(String repositoryId, String subRepositoryId, ThreadsInfo threadsInfo) throws AnyException;
    /**
     * Remove all info about initialized log
     * @param repositoryId  - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @return - true if sub repo has been cleand
     */
    boolean cleanSubRepository(String repositoryId, String subRepositoryId);

    /**
     * Retrieve number of nodes which relate to thread in repository
     * @param repositoryId  - repository ID  (D:/out)
     * @param subRepositoryId - (for example path to sub folder) (Yury/log1/log1_1)
     * @param threadId - thread id
     * @return - size of thread
     * @throws AnyException - any exception
     */
    int getThreadSize(String repositoryId, String subRepositoryId, String threadId) throws AnyException;
    /**
     * LogBlock contains log file name and first time in log to determine correct sequence of files
     * to make correct nodes if parts of node are contained in different files
     * @param filesToProcess - contain list non-sorted files name
     * @return - list of LogBlocks
     * @throws AnyException - any exception
     */
    List<LogBlock> getLogBlocks(FilesToProcess filesToProcess) throws AnyException;
}

package com.my.home.ejb;

import com.my.home.constantes.CallBackCommand;
import com.my.home.constantes.Messages;
import com.my.home.data.access.IDAORepository;
import com.my.home.data.access.ILogNodeSaver;
import com.my.home.data.access.impl.directory.BaseProcessor;
import com.my.home.data.access.impl.directory.DAORepositoryDir;
import com.my.home.data.access.impl.directory.NodesSaverProcessor;
import com.my.home.exceptions.AnyException;
import com.my.home.exceptions.MessageBuilder;
import com.my.home.exceptions.ui.message.AliMessage;
import com.my.home.exceptions.ui.message.AliMessageType;
import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.LogBlock;
import com.my.home.log.beans.LogNode;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.utils.JsonUtils;

import javax.ejb.Stateless;
import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 *  Parser log to nodes
 *  Only one set of processed log can be saved in one subRepository
 */
@Stateless(name = "ProcessFilesRemote")
public class ProcessFiles implements ProcessFilesRemote
{

    private BaseProcessor processor;
    @Override
    public String process(String json) {
        AliMessage message;
        try
        {
            FilesToProcess filesToProcess = JsonUtils.getObject(json, FilesToProcess.class);
            IDAORepository daoRepository = new DAORepositoryDir();
            String subRepoId = filesToProcess.getBasePath();

            // Check if subRepository exists
            if (daoRepository.isSubRepositoryInitialized("", subRepoId))
            {
                // If subRepository exists and files have been already processed
                // Then return to UI message
                // Else clean repository to create new
                if (daoRepository.isLogForFilesInitialized("", subRepoId, filesToProcess))
                {
                    message = MessageBuilder.buildMessage(AliMessageType.INFO, Messages.THESE_FILES_ALREADY_PROCESSED);
                    return JsonUtils.getJson(message);
                }
                else
                {
                    daoRepository.cleanSubRepository("", subRepoId);
                }
            }
            daoRepository.initSubRepository("", subRepoId);
            // Create list of objects which contain log file name and stamps of start and end time each file
            // TODO Check if it can be used from another class (remove any methods which are not contained in interface)
            List<LogBlock> logBlocks = daoRepository.getLogBlocks(filesToProcess);

            //  We need to sort LogBlocks by time to save all parts of log
            Collections.sort(logBlocks, new Comparator<LogBlock>()
            {
                @Override
                public int compare(LogBlock o1, LogBlock o2) {
                    return (int) (o1.getStartTime() - o2.getStartTime());
                }
            });
            //  Create list of sorted files name to save it back in filesToProcess
            List<String> sortedFiles = new LinkedList<>();
            for (LogBlock logBlock : logBlocks)
            {
                sortedFiles.add(logBlock.getFileName());
            }
            // Save sorted files into filesToProcess
            filesToProcess.getSelectedFiles().clear();
            filesToProcess.getSelectedFiles().addAll(sortedFiles);
            // Save nodes according log files name
            ILogNodeSaver saver = daoRepository.getLogNodeSaver("", subRepoId);
            //createNodes(filesToProcess, saver);

            // Move processing of node creation i separate thread
            // TODO make cache which contains threads fo different users and make processing independently

            processor = new NodesSaverProcessor(filesToProcess, saver, daoRepository.getPathToLog("", subRepoId));
            Thread th = new Thread(processor);
            th.start();

            message = MessageBuilder.buildMessage(AliMessageType.COMMAND, CallBackCommand.START_PROCESS);
            return JsonUtils.getJson(message);
        }
        catch (Exception e)
        {
            message = MessageBuilder.buildMessage(AliMessageType.ERROR, Messages.ERROR_PROCESS_FILES);
            message.setMessage(message.getMessage() + "\n" + e.getMessage());
            StackTraceElement[] elements = e.getStackTrace();
            for (StackTraceElement element : elements)
            {
                String add = element.getFileName() + ": " + element.getClassName() + ": " + element.getMethodName() + ": " + element.getLineNumber();
                message.setMessage(message.getMessage() + "\n" + add);
            }
            return JsonUtils.getJson(message);
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
}

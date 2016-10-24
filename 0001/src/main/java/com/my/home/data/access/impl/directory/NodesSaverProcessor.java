package com.my.home.data.access.impl.directory;

import com.my.home.data.access.ILogNodeSaver;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.exceptions.AnyException;
import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.LogNode;
import com.my.home.main.factories.SpringBeanFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 10.09.16
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class NodesSaverProcessor extends BaseProcessor {
    private FilesToProcess filesToProcess;
    private ILogNodeSaver saver;
    private String basePath;

    /**
     * @param filesToProcess - contains list of log files name which should be processed in nodes
     * @param saver - object which save one node
     */
    public NodesSaverProcessor(FilesToProcess filesToProcess, ILogNodeSaver saver, String basePath)
    {
        this.filesToProcess = filesToProcess;
        this.saver = saver;
        currentIndex = new AtomicLong(0);
        this.basePath = basePath;
        isComplete = false;
    }

    /**
     * Save nodes from log
     * @throws AnyException - any exception
     */
    @Override
    public void run()
    {
        try
        {
            BufferedReader br = null;
            List<String> files = filesToProcess.getSelectedFiles();
            LogNodeProcessor logNodeProcessor =
                    (LogNodeProcessor) SpringBeanFactory.getInstance().getBean("LogNodeProcessor");
            StringBuilder logBuffer = null;
            LogNode node;
            String line;
            boolean result = true;
            for (String file : files)
            {
                br = new BufferedReader(new FileReader(basePath + "/" + file));
                while ((line = br.readLine()) != null)
                {
                    if (logNodeProcessor.hasStamp(line))
                    {
                        if (logBuffer != null)
                        {
                            node = logNodeProcessor.process(logBuffer.toString());
                            result &= saver.save(node);
                        }
                        logBuffer = new StringBuilder(line);
                    }
                    else
                    {
                        if(logBuffer != null)
                        {
                            logBuffer.append("\n");
                            logBuffer.append(line);
                        }
                    }
                    currentIndex.addAndGet(line.length());
                }
            }
            node = logNodeProcessor.process(logBuffer.toString());
            result &= saver.save(node);
            result &= saver.complete(filesToProcess);
        }
        catch (Exception e)
        {
            // TODO Return somehow exception
            //throw new AnyException(e, "Error processing nodes");
        }
        finally
        {
            isComplete = true;
        }
    }

    @Override
    protected long getTotalSize() {
        return filesToProcess.getTotalSize();
    }

    @Override
    public String getResult() {
        return "LOG_PARSED";
    }


}

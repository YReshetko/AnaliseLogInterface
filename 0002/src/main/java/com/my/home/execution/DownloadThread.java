package com.my.home.execution;

import com.my.home.ejb.GetThreadsRemote;
import com.my.home.utils.DirUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

/**
 *
 */
public class DownloadThread extends AbstractExecutionBean<GetThreadsRemote>
{
    private GetThreadsRemote remote;
    private String progress;
    private OutputStream result;
    private DirUtils dirUtils;
    private String tempFileName;
    private boolean isComplete;

    public DirUtils getDirUtils()
    {
        return dirUtils;
    }

    public void setDirUtils(DirUtils dirUtils) {
        this.dirUtils = dirUtils;
    }

    @Override
    public String execute(String data) {
        try{
            remote = getBean();
            String out = null;
            progress = "0";
            tempFileName = String.valueOf(data.hashCode());
            if(!dirUtils.isTempFileExist(tempFileName))
            {
                isComplete = false;
                result = new FileOutputStream(dirUtils.pathToTempFile(tempFileName));
                out = remote.downloadThread(data);
                new LogDownloader().start();
            }
            else
            {
                out = tempFileName;
                isComplete = true;
            }
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
        return tempFileName;
    }

    private class LogDownloader extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                while (!this.isInterrupted())
                {
                    String sample = remote.next();
                    progress = remote.getProgress();
                    if (sample != null)
                    {
                        result.write(sample.getBytes());
                        result.flush();
                    }
                    else
                    {
                        Thread.sleep(1000);
                        this.interrupt();
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                try
                {
                    progress = "100";
                    result.flush();
                    result.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                result = null;
                isComplete = true;
            }
        }
    }
}

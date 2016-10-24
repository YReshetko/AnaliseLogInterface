package com.my.home.data.access.impl.directory;

import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.LogNode;
import com.my.home.main.factories.SpringBeanFactory;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 12.10.16
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public class ThreadDownloaderProcessor extends BaseProcessor
{
    private static final String NEW_LINE = "\n";
    private Iterator<LogNode> iterator;
    private int size;
    private LogNodeProcessor processor;
    private StringBuffer buffer;
    public ThreadDownloaderProcessor(Iterator<LogNode> iterator, int size)
    {
        this.iterator = iterator;
        this.size = size;
        SpringBeanFactory factory = SpringBeanFactory.getInstance();
        processor = (LogNodeProcessor) factory.getBean("LogNodeProcessor");
        currentIndex = new AtomicLong(0);
        isComplete = false;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        buffer = new StringBuffer();
        currentIndex = new AtomicLong(0);
        isComplete = false;
        while (iterator.hasNext())
        {
            buffer.append(processor.process(iterator.next()));
            buffer.append(NEW_LINE);
            currentIndex.incrementAndGet();
        }
        isComplete = true;
    }

    @Override
    public String next()
    {
        currentIndex.incrementAndGet();
        StringBuffer out = null;
        if(iterator.hasNext())
        {
            out = new StringBuffer();
            out.append(processor.process(iterator.next()));
            out.append(NEW_LINE);
            isComplete = false;
            return out.toString();
        }
        else
        {
            isComplete = true;
            return null;
        }
    }
    @Override
    protected long getTotalSize() {
        return size;
    }

    @Override
    public String getResult() {
        return buffer.toString();
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }
}

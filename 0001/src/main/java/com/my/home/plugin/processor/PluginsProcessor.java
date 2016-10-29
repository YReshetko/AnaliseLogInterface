package com.my.home.plugin.processor;

import com.my.home.data.access.impl.directory.BaseProcessor;
import com.my.home.log.beans.LogNode;
import com.my.home.utils.JsonUtils;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 29.10.16
 * Time: 9:10
 * To change this template use File | Settings | File Templates.
 */
public class PluginsProcessor extends BaseProcessor
{
    private PluginsExecutor executor;
    private Iterator<LogNode> iterator;
    private int size;
    public PluginsProcessor(PluginsExecutor executor, Iterator<LogNode> iterator, int size)
    {
        this.executor = executor;
        this.iterator = iterator;
        this.size = size;
        currentIndex = new AtomicLong(0);
        isComplete = false;
    }
    @Override
    public String next()
    {

        if(iterator.hasNext())
        {
            executor.process(iterator.next());
            currentIndex.incrementAndGet();
        }
        else
        {
            isComplete = true;
        }

        return null;
    }
    @Override
    protected long getTotalSize() {
        return size;
    }

    @Override
    public String getResult() {
        String out = JsonUtils.getJson(executor.getResult());
        return out;
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
        while (iterator.hasNext())
        {
            this.next();
        }
    }
}

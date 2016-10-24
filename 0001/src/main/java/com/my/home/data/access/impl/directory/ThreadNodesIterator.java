package com.my.home.data.access.impl.directory;

import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogNode;
import com.my.home.log.beans.ThreadDescriptor;
import com.my.home.utils.DirectoryUtils;
import com.my.home.utils.JsonUtils;

import java.util.Iterator;

/**
 *
 */
public class ThreadNodesIterator implements Iterator<LogNode>
{

    private String nodesStorage;
    private ThreadDescriptor descriptor;
    DirectoryUtils utils;

    private long index;
    public ThreadNodesIterator(String nodesFolder, ThreadDescriptor descriptor, DirectoryUtils directoryUtils)
    {
        this.nodesStorage = nodesFolder;
        this.descriptor = descriptor;
        this.utils = directoryUtils;

        index = 0;
    }
    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return index < descriptor.getNodesNumbers().size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws java.util.NoSuchElementException
     *          if the iteration has no more elements
     */
    @Override
    public LogNode next()
    {
        try
        {
            String loadedJson = utils.readFile(descriptor.getNodesNumbers().get((int) index), nodesStorage);
            LogNode node = JsonUtils.getObject(loadedJson, LogNode.class);
            return node;
        }
        catch (AnyException e)
        {
            return null;
        }
        finally
        {
            ++index;
        }
    }
}

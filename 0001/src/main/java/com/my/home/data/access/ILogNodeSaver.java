package com.my.home.data.access;

import com.my.home.directory.beans.FilesToProcess;
import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogNode;

/**
 *
 */
public interface ILogNodeSaver
{
    /**
     * Save log node
     * @param value - log node
     * @return - true if log node was saved successfully
     */
    boolean save(LogNode value) throws AnyException;

    /**
     * Finish save (save info files)
     * @return - true if saving was successfully
     */
    boolean complete(FilesToProcess files) throws AnyException;
}

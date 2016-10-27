package com.my.home.plugin.storage;

import com.my.home.exceptions.AnyException;
import com.my.home.plugin.model.PluginDescription;
import com.my.home.plugin.model.StorageDescriptor;

/**
 *
 */
public interface IPluginStorage
{
    /**
     * Save pluging from byte array using it's description
     * @param pluginDescription - plugin description
     * @param byteArray - plugin as byte array
     * @return - true if plugin was saved correctly
     * @throws AnyException - unexpected exception
     */
    boolean addPlugin(PluginDescription pluginDescription, byte[] byteArray) throws AnyException;

    /**
     * Returns storage descriptor
     * @return - StorageDescriptor
     */
    StorageDescriptor getStorageDescriptor();

    /**
     * Retrieve full path to plugin
     * @param pluginDescription - plugin description
     * @param version - version of plugin
     * @return - full path
     */
    String getPathToPlugin(PluginDescription pluginDescription, int version);
}

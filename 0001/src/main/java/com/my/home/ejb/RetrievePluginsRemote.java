package com.my.home.ejb;

import javax.ejb.Remote;

/**
 * Interface for retrieving plugins description
 */
@Remote
public interface RetrievePluginsRemote
{
    /**
     * Retrieve all plugins from repository
     * @return JSON storage description
     */
    String getAllPlugins();

    /**
     * Return only short description for plugin
     * @param label - plugin label
     * @param version - plugin version
     * @return - string of description
     */
    String getPluginShortDescription(String label, String version);

    /**
     * Return only full description for plugin
     * @param label - plugin label
     * @param version - plugin version
     * @return - string of description
     */
    String getPluginFullDescription(String label, String version);
}

package com.my.home.plugin;

import com.my.home.log.beans.LogNode;

import java.util.Map;

/**
 * Interface for plugin
 * Plugin should have empty constructor
 */
public interface IAliPlugin
{
    /**
     * Executed when plugin initialized
     * @param context - contains info about execute process
     */
    void init(IPluginContext context);

    /**
     * Custom initialization of plugin (set parameters from user)
     * @param config - params
     */
    void setUp(Map<String, String> config);

    /**
     * Executes in cycle for each node in log (node is part of log from one log stamp to the next log stamp exclusive)
     * @param node - log node
     */
    void nodeProcessing(LogNode node);

    /**
     * Retrieve result of plugin execution
     * @return - map with results
     */
    Map<String, String> getResult();
}

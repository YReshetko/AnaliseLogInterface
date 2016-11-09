package com.my.home.directory;

import com.my.home.directory.beans.Directory;
import com.my.home.utils.JsonUtils;

/**
 *
 */
public class RepositoryConfig
{

    private static final String PATH_SEPARATOR = "/";
    private String pathSeparator;

    /** All mapping stored in this folder*/
    private static final String MAIN_FOLDER = ".ali";
    private String mainFolder;

    /** Contains info files about mapping to nodes*/
    private static final String PROCESSED_FOLDER = ".ali/processed";
    private String processFolder;

    /** Contains mapping threads to nodes*/
    private static final String THREADS_FOLDER = ".ali/processed/threads";
    private String threadsFolder;

    /** Contains nodes for log*/
    private static final String NODES_FOLDER = ".ali/nodes";
    private String nodesFolder;

    /**
     * File name of file with list of files which were processed
     */
    public static final String PROCESSED_FILES_JSON = "processed_files.json";
    private String processedFilesJson;

    /**
     * Template name of node it should be (00000000001.node)
     */
    public static final String NODES_TEMPLATE_JSON = "%s.node";
    private String nodesTemplateJson;

    /**
     * Template names of thread files (thread.001.json)
     */
    public static final String THREAD_TEMPLATE_JSON = "thread.%s.json";
    private String threadTemplateJson;

    /**
     * Name of file which contains base info about threads
     */
    public static final String THREADS_INFO_JSON = "threads.json";
    private String threadsInfoJson;

    /**
     * Name of folder where will be stored plugin results
     */
    public static final String PLUGINS_RESULT_FOLDER = ".ali/results";
    private String pluginsResultFolder;

    /**
     * Name of file with results description
     */
    private static final String RESULTS_DESCRIPTOR = "descriptor.json";
    private String resultsDescriptor;

    /**
     * Template of file name with plugin result
     */
    private static final String PLUGIN_RESULT_TEMPLATE = "result_%s.json";
    private String pluginResultTemplate;

    private String path;
    public RepositoryConfig()
    {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public String getMainFolder() {
        return mainFolder;
    }

    public void setMainFolder(String mainFolder) {
        this.mainFolder = mainFolder;
    }

    public String getProcessFolder() {
        return processFolder;
    }

    public void setProcessFolder(String processFolder) {
        this.processFolder = processFolder;
    }

    public String getThreadsFolder() {
        return threadsFolder;
    }

    public void setThreadsFolder(String threadsFolder) {
        this.threadsFolder = threadsFolder;
    }

    public String getNodesFolder() {
        return nodesFolder;
    }

    public void setNodesFolder(String nodesFolder) {
        this.nodesFolder = nodesFolder;
    }

    public String getProcessedFilesJson() {
        return processedFilesJson;
    }

    public void setProcessedFilesJson(String processedFilesJson) {
        this.processedFilesJson = processedFilesJson;
    }

    public String getNodesTemplateJson() {
        return nodesTemplateJson;
    }

    public void setNodesTemplateJson(String nodesTemplateJson) {
        this.nodesTemplateJson = nodesTemplateJson;
    }

    public String getThreadTemplateJson() {
        return threadTemplateJson;
    }

    public void setThreadTemplateJson(String threadTemplateJson) {
        this.threadTemplateJson = threadTemplateJson;
    }

    public String getThreadsInfoJson() {
        return threadsInfoJson;
    }

    public void setThreadsInfoJson(String threadsInfoJson) {
        this.threadsInfoJson = threadsInfoJson;
    }

    public String getPluginsResultFolder() {
        return pluginsResultFolder;
    }

    public void setPluginsResultFolder(String pluginsResultFolder) {
        this.pluginsResultFolder = pluginsResultFolder;
    }

    public String getResultsDescriptor() {
        return resultsDescriptor;
    }

    public void setResultsDescriptor(String resultsDescriptor) {
        this.resultsDescriptor = resultsDescriptor;
    }

    public String getPluginResultTemplate() {
        return pluginResultTemplate;
    }

    public void setPluginResultTemplate(String pluginResultTemplate) {
        this.pluginResultTemplate = pluginResultTemplate;
    }
}

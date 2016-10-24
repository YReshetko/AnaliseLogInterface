package com.my.home.plugin.storage.impl;

/**
 *
 */
public class PluginStorageConfig
{
    private String pathToStorage;
    private String pluginFileName;
    private String pluginsStorageConfigFileName;
    private String pluginFoldersTemplate;
    private String pluginVersionFolderTemplate;
    private String pluginDescriptorFileName;

    public String getPluginsStorageConfigFileName() {
        return pluginsStorageConfigFileName;
    }

    public void setPluginsStorageConfigFileName(String pluginsStorageConfigFileName) {
        this.pluginsStorageConfigFileName = pluginsStorageConfigFileName;
    }

    public String getPluginFoldersTemplate() {
        return pluginFoldersTemplate;
    }

    public void setPluginFoldersTemplate(String pluginFoldersTemplate) {
        this.pluginFoldersTemplate = pluginFoldersTemplate;
    }

    public String getPathToStorage() {
        return pathToStorage;
    }

    public void setPathToStorage(String pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    public String getPluginVersionFolderTemplate() {
        return pluginVersionFolderTemplate;
    }

    public void setPluginVersionFolderTemplate(String pluginVersionFolderTemplate) {
        this.pluginVersionFolderTemplate = pluginVersionFolderTemplate;
    }

    public String getPluginDescriptorFileName() {
        return pluginDescriptorFileName;
    }

    public void setPluginDescriptorFileName(String pluginDescriptorFileName) {
        this.pluginDescriptorFileName = pluginDescriptorFileName;
    }
    public String getPluginFileName() {
        return pluginFileName;
    }

    public void setPluginFileName(String pluginFileName) {
        this.pluginFileName = pluginFileName;
    }
}

package com.my.home.plugin.storage.impl;

import com.my.home.exceptions.AnyException;
import com.my.home.plugin.model.PluginDescription;
import com.my.home.plugin.model.PluginVersion;
import com.my.home.plugin.model.Plugins;
import com.my.home.plugin.model.StorageDescriptor;
import com.my.home.plugin.storage.IPluginStorage;
import com.my.home.plugin.verification.PluginVerification;
import com.my.home.utils.DirectoryUtils;
import com.my.home.utils.JsonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 *
 */
public class PluginStorage implements IPluginStorage
{
    private static final String SEPARATOR = "/";
    private static StorageDescriptor storageDescriptor;
    private PluginStorageConfig config;
    private DirectoryUtils directoryUtils;

    /**
     * Initialize plugin repo and create storageDescriptor from file
     * @throws AnyException - unexpected exception
     */
    public void init() throws AnyException
    {
        directoryUtils = new DirectoryUtils(null);
        File repoFolder = new File(config.getPathToStorage());
        File repoDescriptor = new File(config.getPathToStorage() + SEPARATOR + config.getPluginsStorageConfigFileName());
        boolean isPluginRepoExist = repoFolder.exists() && repoFolder.isDirectory();
        boolean isPluginDescriptorExist = repoDescriptor.exists() && repoDescriptor.isFile();
        if (!isPluginRepoExist)
        {
            isPluginRepoExist = repoFolder.mkdir();
        }
        if (isPluginRepoExist && !isPluginDescriptorExist)
        {
            isPluginDescriptorExist = directoryUtils.saveFile(repoDescriptor, "{}");
        }
        if (!isPluginRepoExist || !isPluginDescriptorExist)
        {
            throw new AnyException(null, "Can't create plugin repo descriptor");
        }
        else
        {
            String pluginsStorageDescriptor = directoryUtils.readFile(repoDescriptor);
            storageDescriptor = JsonUtils.getObject(pluginsStorageDescriptor, StorageDescriptor.class);
        }
    }

    /**
     * Save pluging from byte array using it's description
     * @param pluginDescription - plugin description
     * @param byteArray - plugin as byte array
     * @return - true if plugin was saved correctly
     * @throws AnyException - unexpected exception
     */
    @Override
    public boolean addPlugin(PluginDescription pluginDescription, byte[] byteArray) throws AnyException
    {
        //  TODO make synchronized block as small as possible
        //  TODO Add recalculation hash sum of byte array to understand if there is needed new version
        synchronized (storageDescriptor)
        {
            List<Plugins> plugins = storageDescriptor.getPlugins();
            Plugins currentPlugin = null;
            //  Find if plugin with package and main class already exists
            for (Plugins plugin : plugins)
            {
                boolean flag = pluginDescription.getPackageName().equals(plugin.getPackageName());
                flag &= pluginDescription.getClassName().equals(plugin.getClassName());
                if (flag)
                {
                    currentPlugin = plugin;
                    break;
                }

            }
            //  If plugin doesn't exist then create new plugin and add it to plugins array
            if (currentPlugin == null)
            {
                currentPlugin = new Plugins();
                currentPlugin.setClassName(pluginDescription.getClassName());
                currentPlugin.setPackageName(pluginDescription.getPackageName());
                currentPlugin.setLabel(pluginDescription.getLabel());
                plugins.add(currentPlugin);
            }
            //  If folder for current plugin doesn't exist then create it
            String pluginFolderName = currentPlugin.getFolder();
            if (pluginFolderName == null || pluginFolderName.equals(""))
            {
                pluginFolderName = String.format(config.getPluginFoldersTemplate(), plugins.size());
                directoryUtils.createDir(config.getPathToStorage() + SEPARATOR + pluginFolderName);
                currentPlugin.setFolder(pluginFolderName);
            }

            //  Save new plugin's version
            List<PluginVersion> versions = currentPlugin.getVersions();
            Integer versionNumber = versions.size()+1;
            String versionFolder = String.format(config.getPluginVersionFolderTemplate(), versionNumber);
            PluginVersion pluginVersion = new PluginVersion();
            versions.add(pluginVersion);
            pluginVersion.setFolder(versionFolder);
            pluginVersion.setVersion(versionNumber);

            String versionPath = config.getPathToStorage() +
                    SEPARATOR + pluginFolderName +
                    SEPARATOR + versionFolder;
            boolean saveProcess = true;
            saveProcess &= directoryUtils.createDir(versionPath);
            saveProcess &= directoryUtils.saveFile(config.getPluginDescriptorFileName(), versionPath,
                    JsonUtils.getJson(pluginDescription));
            saveProcess &= directoryUtils.saveBinaryFile(config.getPluginFileName(), versionPath, byteArray);

            PluginVerification pluginVerification = new PluginVerification();
            saveProcess &= pluginVerification.verify(currentPlugin.getPackageName(), currentPlugin.getClassName(), versionNumber);
            //  Save new storage descriptor if plugin was saved and verified
            if (saveProcess)
            {
                saveProcess &= directoryUtils.removeFile(config.getPluginsStorageConfigFileName(), config.getPathToStorage());
                saveProcess &= directoryUtils.saveFile(config.getPluginsStorageConfigFileName(), config.getPathToStorage(),
                        JsonUtils.getJson(storageDescriptor));
            }
            return saveProcess;
        }
    }

    /**
     * Returns storage descriptor
     *
     * @return - StorageDescriptor
     */
    @Override
    public StorageDescriptor getStorageDescriptor() {
        synchronized (storageDescriptor)
        {
            return storageDescriptor;
        }
    }

    /**
     * Retrieve full path to plugin
     *
     * @param pluginDescription - plugin description
     * @param version - plugin version
     * @return - full path
     */
    @Override
    public String getPathToPlugin(PluginDescription pluginDescription, int version)
    {
        List<Plugins> plugins = storageDescriptor.getPlugins();
        String path = null;
        for (Plugins plugin : plugins)
        {
            boolean flag = plugin.getPackageName().equals(pluginDescription.getPackageName());
            flag &= plugin.getClassName().equals(pluginDescription.getClassName());
            if (flag)
            {
                path = config.getPathToStorage() + SEPARATOR +
                plugin.getFolder() + SEPARATOR +
                String.format(config.getPluginVersionFolderTemplate(), version) + SEPARATOR +
                config.getPluginFileName();
                break;
            }
        }
        return path;
    }


    /**
     * Get storage config which contain descriptions of files and folders names and their templates (patterns)
     * Created by spring
     * @return - config
     */
    public PluginStorageConfig getConfig()
    {
        return config;
    }

    /**
     * Set storage config which contain descriptions of files and folders names and their templates (patterns)
     * Created by spring
     * @return - config
     */
    public void setConfig(PluginStorageConfig config) {
        this.config = config;
    }
}

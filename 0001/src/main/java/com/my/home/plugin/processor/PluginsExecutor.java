package com.my.home.plugin.processor;

import com.my.home.exceptions.AnyException;
import com.my.home.log.beans.LogNode;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.plugin.IAliPlugin;
import com.my.home.plugin.IPluginContext;
import com.my.home.plugin.factory.PluginFactory;
import com.my.home.plugin.model.Plugin;
import com.my.home.plugin.storage.IPluginStorage;

import java.util.*;

/**
 * Init plugins by descriptions
 * execute plugins
 */
public class PluginsExecutor
{
    private List<IAliPlugin> plugins;
    private IPluginContext context;
    public void init(Plugin plugin) throws AnyException
    {
        init(Collections.singletonList(plugin));
    }
    public void init(List<Plugin> plugins) throws AnyException
    {
        try {
            IPluginStorage storage = (IPluginStorage) SpringBeanFactory.getInstance().getBean("PluginStorage");
            PluginFactory pluginFactory = new PluginFactory();
            this.plugins = new ArrayList<IAliPlugin>(plugins.size());
            String path;
            IAliPlugin plugin;
            //  TODO Implement execution context
            context = new IPluginContext() {};
            //  TODO Implement init map from UI
            Map<String, String> initMap = new HashMap<>();
            for (Plugin currentPlugin : plugins)
            {
                path = storage.getPathToPlugin(currentPlugin.getDescription(), currentPlugin.getVersion());
                plugin = pluginFactory.getPlugin(currentPlugin.getDescription(), path);
                plugin.init(context);
                plugin.setUp(initMap);
                this.plugins.add(plugin);
            }
        }
        catch (Exception e)
        {
            throw new AnyException(e, "Error init plugins");
        }
    }

    public void process(LogNode node)
    {
        for (IAliPlugin plugin : plugins)
        {
            try
            {
                plugin.nodeProcessing(node);
            }
            catch (Exception e)
            {
                //  TODO save exception in context
            }
        }
    }

    public List<Map<String, String>> getResult()
    {
        List<Map<String, String>> out = new ArrayList<>();
        for (IAliPlugin plugin : plugins)
        {
            out.add(plugin.getResult());
        }
        return out;
    }
}

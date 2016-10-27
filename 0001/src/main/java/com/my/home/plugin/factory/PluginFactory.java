package com.my.home.plugin.factory;

import com.my.home.plugin.IAliPlugin;
import com.my.home.plugin.model.PluginDescription;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * TODO Create factory
 */
public class PluginFactory
{
    private static final String SEPARATOR = ".";
    private PluginClassLoader loader;
    public void init()
    {
        URL[] url = {};
        loader = new PluginClassLoader(url);
    }

    public IAliPlugin getPlugin(PluginDescription description, String path) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        loader.addJar(path);
        Class<IAliPlugin> clazz = (Class<IAliPlugin>) loader.loadClass(description.getPackageName() + SEPARATOR + description.getClassName());
        IAliPlugin instance = clazz.newInstance();
        return instance;
    }

}

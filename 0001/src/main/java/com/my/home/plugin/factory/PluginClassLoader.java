package com.my.home.plugin.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Class loader for plugins
 */
public class PluginClassLoader extends URLClassLoader
{
    private static final String[] AVAILABLE_CLASSES = new String[]
    {
        "com.my.home.log.beans",
        "com.my.home.plugin.IAliPlugin",
        "com.my.home.plugin.IPluginContext"
    };
    private ClassLoader parent;
    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls);
        this.parent = parent;
    }

    public PluginClassLoader(URL[] urls) {
        super(urls);
    }

    public PluginClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, null, factory);
        this.parent = parent;
    }

    public void addJar(String filePath) throws MalformedURLException
    {
        String urlPath = "file:" + filePath;
        addURL(new URL(urlPath));
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException
    {
        Class<?> out = null;
        if(validateClassName(name))
        {
            out = parent.loadClass(name);
        }
        else
        {
            out = super.loadClass(name);
        }
        return out;
    }

    private boolean validateClassName(String name)
    {
        boolean out = false;
        for (String str : AVAILABLE_CLASSES)
        {
            out |= name.startsWith(str);
        }
        return out;
    }
}

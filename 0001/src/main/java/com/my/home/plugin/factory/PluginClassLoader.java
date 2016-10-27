package com.my.home.plugin.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * Class loader for plugins
 */
public class PluginClassLoader extends URLClassLoader
{
    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public PluginClassLoader(URL[] urls) {
        super(urls);
    }

    public PluginClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public void addJar(String filePath) throws MalformedURLException
    {
        String urlPath = "file:" + filePath;
        addURL(new URL(urlPath));
    }
}

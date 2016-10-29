package com.my.home.utils;

import com.my.home.log.beans.ThreadsSet;
import com.my.home.plugin.model.Plugin;

import java.util.List;

/**
 *
 */
public class LogPluginWrapper
{
    private List<Plugin> plugins;
    private ThreadsSet threadsSet;

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public ThreadsSet getThreadsSet() {
        return threadsSet;
    }

    public void setThreadsSet(ThreadsSet threadsSet) {
        this.threadsSet = threadsSet;
    }
}

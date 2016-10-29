package com.my.home.ejb;

import javax.ejb.Remote;

/**
 *
 */
public interface PluginProcessorRemote
{
    String setup(String value);
    String getProgress();
    boolean isComplete();
    String getResult();
    String next();
}

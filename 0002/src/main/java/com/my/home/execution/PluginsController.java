package com.my.home.execution;

import com.my.home.ejb.RetrievePluginsRemote;

/**
 *
 */
public class PluginsController extends AbstractExecutionBean<RetrievePluginsRemote>{
    @Override
    public String execute(String data) {
        RetrievePluginsRemote bean = getBean();
        return bean.getAllPlugins();
    }
}

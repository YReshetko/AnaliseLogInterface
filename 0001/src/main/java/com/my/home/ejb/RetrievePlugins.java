package com.my.home.ejb;

import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.plugin.model.StorageDescriptor;
import com.my.home.plugin.storage.IPluginStorage;
import com.my.home.utils.JsonUtils;

import javax.ejb.Stateless;

/**
 * Created by Yury_Rashetska on 10/7/2016.
 */
@Stateless(name = "RetrievePluginsRemote")
public class RetrievePlugins implements RetrievePluginsRemote {
    /**
     * Retrieve all plugins from repository
     *
     * @return JSON storage description
     */
    @Override
    public String getAllPlugins() {
        IPluginStorage storage = (IPluginStorage) SpringBeanFactory.getInstance().getBean("PluginStorage");
        StorageDescriptor descriptor = storage.getStorageDescriptor();
        String out = JsonUtils.getJson(descriptor);
        return out;
    }

    /**
     * Return only short description for plugin
     *
     * @param label   - plugin label
     * @param version - plugin version
     * @return - string of description
     */
    @Override
    public String getPluginShortDescription(String label, String version) {
        return null;
    }

    /**
     * Return only full description for plugin
     *
     * @param label   - plugin label
     * @param version - plugin version
     * @return - string of description
     */
    @Override
    public String getPluginFullDescription(String label, String version) {
        return null;
    }
}

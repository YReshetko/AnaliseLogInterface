package com.my.home.plugin.factory;

import com.my.home.BaseTestClass;
import com.my.home.plugin.IAliPlugin;
import com.my.home.plugin.model.Plugin;
import com.my.home.plugin.model.PluginDescription;
import com.my.home.plugin.model.PluginType;

import java.net.MalformedURLException;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 30.10.16
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class PluginFactoryTest extends BaseTestClass
{
    private PluginFactory classToTest;
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        classToTest = new PluginFactory();
        classToTest.init();
    }

    public void testLoadPlugin() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        PluginDescription description = new PluginDescription();
        description.setPackageName("com.my.home.custom.plugin");
        description.setClassName("PluginToTest");
        description.setLabel("PlaginForTesting");
        description.setPluginType(PluginType.STAMPER);

        String path = "d:/plugins/plugin_1/version_1/plugin.jar";
        IAliPlugin plugin = classToTest.getPlugin(description, path);
        assertNotNull(plugin);


    }

}

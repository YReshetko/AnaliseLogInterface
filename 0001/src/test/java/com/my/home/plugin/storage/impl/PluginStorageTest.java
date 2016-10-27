package com.my.home.plugin.storage.impl;

import com.my.home.BaseTestClass;
import com.my.home.plugin.model.PluginDescription;
import com.my.home.plugin.storage.IPluginStorage;

/**
 * Created by Yury_Rashetska on 10/27/2016.
 */
public class PluginStorageTest extends BaseTestClass
{
    private IPluginStorage classToTest;
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        classToTest = getBean("PluginStorage", IPluginStorage.class);
    }
    public void testGetPathToPlugin()
    {
        PluginDescription description = new PluginDescription();
        description.setPackageName("my.plugin.some");
        description.setClassName("SomeMyPlugin");
        String path = classToTest.getPathToPlugin(description, 1);
        String expectedPath = "D:/plugins/plugin#3/version#1/plugin.jar";
        assertEquals(expectedPath, path);

    }
}

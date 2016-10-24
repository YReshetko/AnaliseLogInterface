package com.my.home.ejb;

import com.my.home.BaseTestClass;
import com.my.home.exceptions.AnyException;
import com.sun.org.apache.xerces.internal.impl.dv.util.ByteListImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yury_Rashetska on 9/23/2016.
 */
public class UploadPluginTest extends BaseTestClass
{
    private UploadPlugin classToTest;

    @Override
    public void setUp()
    {
        classToTest = new UploadPlugin();
    }

    public void testUpload() throws AnyException
    {
        Map<String, List<Byte>> plugin = getPluginMap();
        String uploadResult = classToTest.upload(plugin);
        assertEquals("SUCCESS", uploadResult);
    }

    private  Map<String, List<Byte>> getPluginMap() throws AnyException
    {
        try {
            Map<String, List<Byte>> out = new HashMap<>();
            File resourcesDir = new File("src/test/resources/plugins");

            List<Byte> descriptor = new ArrayList<>();
            List<Byte> plugin = new ArrayList<>();

            String descriptorFile = resourcesDir.getAbsolutePath() + "/PluginDescriptor.json";
            String pluginFile = resourcesDir.getAbsolutePath() + "/SomePlugin.jar";
            descriptor = getBytesFromIS(new FileInputStream(descriptorFile));
            plugin = getBytesFromIS(new FileInputStream(pluginFile));

            out.put("config", descriptor);
            out.put("file", plugin);

            return out;
        }
        catch (Exception e)
        {
            fail("Can not create input data");
            throw new AnyException(null, "Finalise test");
        }
    }

    private List<Byte> getBytesFromIS(InputStream is) throws Exception
    {
        int bufferSize = 128;
        List<Byte> out = new ArrayList<>();
        int read = 0;
        byte[] bytes = new byte[bufferSize];
        while ( (read = is.read(bytes)) != -1)
        {
            if(read < bufferSize)
            {
                bytes = Arrays.copyOfRange(bytes, 0, read);
            }
            out.addAll(new ByteListImpl(bytes));

        }
        is.close();
        return out;
    }
}

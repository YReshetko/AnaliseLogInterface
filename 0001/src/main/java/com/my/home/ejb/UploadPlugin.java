package com.my.home.ejb;

import com.my.home.exceptions.AnyException;
import com.my.home.main.factories.SpringBeanFactory;
import com.my.home.plugin.model.PluginDescription;
import com.my.home.plugin.storage.IPluginStorage;
import com.my.home.plugin.storage.impl.PluginStorage;
import com.my.home.plugin.storage.impl.PluginStorageConfig;
import com.my.home.utils.JsonUtils;

import javax.ejb.Stateless;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Stateless(name = "UploadPluginRemote")
public class UploadPlugin implements UploadPluginRemote {
    private static final String PLUGIN_BYTES_KEY = "file";
    private static final String CONFIG_BYTES_KEY = "config";
    @Override
    public String upload(Map<String, List<Byte>> value) {

        try {
            if(isMapValid(value))
            {
                String config = getStringFomBytes(value.get(CONFIG_BYTES_KEY));
                PluginDescription description = JsonUtils.getObject(config, PluginDescription.class);
                byte[] plugin = getBytesArrayFromList(value.get(PLUGIN_BYTES_KEY));
                IPluginStorage storage = (IPluginStorage) SpringBeanFactory.getInstance().getBean("PluginStorage");
                boolean isPluginUploaded = storage.addPlugin(description, plugin);
                if (isPluginUploaded)
                {
                    return "SUCCESS";
                }
                else
                {
                    return "FAIL";
                }

            }
            else
            {
                return "FAIL";
            }
        }
        catch (AnyException e)
        {
            return "FAIL";
        }
    }

    private byte[] getBytesArrayFromList(List<Byte> bytes)
    {
        byte[] out = new byte[bytes.size()];
        int iterator = 0;
        for(Byte b : bytes)
        {
            out[iterator] = b.byteValue();
            ++iterator;
        }
        return out;
    }
    private String getStringFomBytes(List<Byte> bytes)
    {
        String out = new String(getBytesArrayFromList(bytes), Charset.forName("UTF-8"));
        return out;
    }

    private boolean isMapValid(Map<String, List<Byte>> value)
    {
        boolean flag = true;
        flag &= value.containsKey(PLUGIN_BYTES_KEY);
        flag &= value.containsKey(CONFIG_BYTES_KEY);
        if(!flag)
        {
            return false;
        }
        flag &= value.get(PLUGIN_BYTES_KEY).size()>0;
        flag &= value.get(CONFIG_BYTES_KEY).size()>0;
        return flag;
    }
}

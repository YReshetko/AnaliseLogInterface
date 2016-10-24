package com.my.home.ejb;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Remote
public interface UploadPluginRemote
{
    String upload(Map<String, List<Byte>> value);
}

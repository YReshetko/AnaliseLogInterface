package com.my.home.ejb;

import javax.ejb.Remote;

/**
 *
 */
@Remote
public interface ProcessFilesRemote
{
    String process(String json);
    String getProgress();
    boolean isComplete();
}
